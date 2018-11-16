package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.*;
import com.elearningbackend.entity.Lesson;
import com.elearningbackend.entity.LessonReport;
import com.elearningbackend.entity.LessonReportId;
import com.elearningbackend.repository.ILessonReportRepository;
import com.elearningbackend.repository.ILessonRepository;
import com.elearningbackend.utility.CodeGenerator;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Transactional
public class LessonService implements ILessonService {

    @Autowired
    private AbstractQuestionService<QuestionDto, String> questionService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private ILessonRepository iLessonRepository;
    @Autowired
    private ILessonReportRepository iLessonReportRepository;
    Paginator<Lesson, LessonDto> paginator = new Paginator<>(LessonDto.class);

    private ModelMapper mapper = new ModelMapper();

    @Override
    public LessonDto startLesson(UserDto userDto, String subcategoryCode) throws ElearningException {
        LessonDto lessonDto = new LessonDto();
        Lesson lessonUnFinishTop = iLessonRepository.findTopByIsFinishOrderByCreationDateDesc(Constants.LESSION_UNFINISH);
        lessonDto.setUserDto(userDto);
        lessonDto.setIsFinish(Constants.LESSION_UNFINISH);
        Set<LessonReportDto> lessonReportDtos = lessonUnFinishTop != null
                ? setLessonDtoFromLessonDB(lessonDto, lessonUnFinishTop)
                : generateLessonDto(subcategoryCode, lessonDto);
        lessonDto.setMappedLessonReports(lessonReportDtos);
        lessonReportDtos.forEach(e -> {
            LessonReport lessonReport = mapper.map(e, LessonReport.class);
            try {
                lessonReport.setCorrectAnswers(ServiceUtils.convertListToJson(e.getCorrectAnswers()));
                lessonReport.setIncorrectAnswers(ServiceUtils.convertListToJson(e.getIncorrectAnswers()));
            } catch (ElearningException e1) {
                e1.printStackTrace();
            }
            iLessonReportRepository.save(lessonReport);
        });
        return lessonDto;
    }

    private Set<LessonReportDto> generateLessonDto(String subcategoryCode, LessonDto lessonDto) throws ElearningException {
        Set<LessonReportDto> lessonReportDtos;
        String lessonCode = CodeGenerator.generateLessonCode();
        lessonDto.setLessonCode(lessonCode);
        lessonReportDtos = convertToLessonReportDto(questionService.getRandomQuestionDtos(subcategoryCode), lessonCode);
        if (lessonReportDtos == null || lessonReportDtos.size() <= 0) {
            throw new ElearningException(Errors.SUBCATEGORY_NOT_FOUND.getId(), Errors.SUBCATEGORY_NOT_FOUND.getMessage());
        }
        lessonDto.setCreationDate(new Timestamp(System.currentTimeMillis()));
        iLessonRepository.save(mapper.map(lessonDto, Lesson.class));
        return lessonReportDtos;
    }

    private Set<LessonReportDto> setLessonDtoFromLessonDB(LessonDto lessonDto, Lesson lessonUnFinishTop) {
        Set<LessonReportDto> lessonReportDtos;
        lessonDto.setLessonCode(lessonUnFinishTop.getLessonCode());
        lessonDto.setCreationDate(lessonUnFinishTop.getCreationDate());
        lessonReportDtos = lessonUnFinishTop.getMappedLessonReports().parallelStream()
                .map(w -> mapper.map(w, LessonReportDto.class)).collect(Collectors.toSet());
        return lessonReportDtos;
    }

    @Override
    public Pager<LessonDto> loadAll(CurrentUser currentUser, int isFinish, int currentPage, int noOfRowInPage, String sortBy, String direction) throws ElearningException {
        if (isFinish != Constants.LESSION_UNFINISH && isFinish != Constants.LESSION_FINISH){
            Page<Lesson> pager = iLessonRepository.findAll(
                    Paginator.getValidPageRequest(currentPage, noOfRowInPage, ServiceUtils.proceedSort(sortBy, direction)));
            checkAccess(currentUser, pager.getContent());
            return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
        }
        Page<Lesson> pager = iLessonRepository.findAllByIsFinish(isFinish, Paginator.getValidPageRequest(currentPage, noOfRowInPage, ServiceUtils.proceedSort(sortBy, direction)));
        if (pager.getContent() == null || pager.getContent().size() <= 0){
            return null;
        }
        checkAccess(currentUser, pager.getContent());
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    private void checkAccess(CurrentUser currentUser, List<Lesson> lessonList) throws ElearningException {
        if(lessonList.stream()
            .noneMatch(e -> validateUserLesson(e.getUser().getUsername(), currentUser))){
            throw new ElearningException(Errors.ACCESS_DENIED.getId(), Errors.ACCESS_DENIED.getMessage());
        }
    }

    private boolean validateUserLesson(String lessonUsername, CurrentUser currentUser){
        return lessonUsername.equals(currentUser.getUsername()) || currentUser.getRole().equals(Constants.AUTH_ADMINISTRATOR);
    }

    @Override
    public LessonDto getOneByKey(String lessonCode) throws ElearningException{
        Lesson lesson = iLessonRepository.findOne(lessonCode);
        if (lesson == null){
            throw new ElearningException(Errors.LESSION_NOT_FOUND.getId(), Errors.LESSION_NOT_FOUND.getMessage());
        }
        LessonDto lessonDto = mapper.map(lesson, LessonDto.class);
        setAnswerDtoForLessonDto(lesson, lessonDto);
        if(lesson.getIsFinish() == Constants.LESSION_FINISH){
            lessonDto.setMappedLessonReportsFinish(convertToLessonReportDtoFinish(lessonDto.getMappedLessonReports()));
            lessonDto.setMappedLessonReports(null);
        }
        return lessonDto;
    }

    @Override
    public LessonDto edit(UserDto currentUser, String lessonCode, LessonDto lessonDto) throws ElearningException {
        Lesson lesson = iLessonRepository.findOne(lessonCode);
        if (lesson == null){
            throw new ElearningException(Errors.LESSION_NOT_FOUND.getId(), Errors.LESSION_NOT_FOUND.getMessage());
        }
        lessonDto.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
        lessonDto.setIsFinish(Constants.LESSION_FINISH);
        setAnswerDtoForLessonDto(lesson, lessonDto);
        lessonDto.setMappedLessonReportsFinish(convertToLessonReportDtoFinish(lessonDto.getMappedLessonReports()));

        Map<LessonReportDtoFinish, List<AnswerDto>> correctAnswerDtoFromDBMap = new TreeMap<>();
        lessonDto.getMappedLessonReportsFinish().stream().filter(e -> e.getCorrectAnswers().size() > 0).forEach(e -> {
            correctAnswerDtoFromDBMap.put(e, e.getCorrectAnswers());
        });

        Map<LessonReportDto, List<AnswerDto>> userAnswerDtosFromClient = new TreeMap<>();
        List<LessonReportDto> filteredLessonReportDtos = lessonDto.getMappedLessonReports().stream().filter(e -> e.getUserAnswers() != null).collect(Collectors.toList());
        filteredLessonReportDtos.forEach(e -> userAnswerDtosFromClient.put(e, e.getUserAnswers()));

        if (userAnswerDtosFromClient.size() > 0) {
            userAnswerDtosFromClient.forEach((k,v) -> {
                switch (k.getQuestionType()){
                    case Constants.Q_TYPE_CHOOSE_ONE:
                        correctAnswerDtoFromDBMap.forEach((e,z) -> {
                            if (v.stream().anyMatch(v1 -> z.stream().anyMatch(z1 -> v1.getAnswerBankDto().getAnswerCode().equals(z1.getAnswerBankDto().getAnswerCode())))) {
                                k.setUserPoint(e.getQuestionPoint());
                            }
                        });
                        break;
                    case Constants.Q_TYPE_CHOOSE_MULTIPLE:
                        userAnswerDtosFromClient.forEach((e,z) -> {
                            if (v.stream().anyMatch(v1 -> z.stream().allMatch(z1 -> v1.getAnswerBankDto().getAnswerCode().equals(z1.getAnswerBankDto().getAnswerCode())))) {
                                k.setUserPoint(e.getQuestionPoint());
                            }
                        });
                        break;
                    case Constants.Q_TYPE_ENTER:
                        userAnswerDtosFromClient.forEach((e,z) -> {
                            if (v.stream().anyMatch(v1 -> z.stream().anyMatch(z1 -> v1.getAnswerBankDto().getAnswerCode().equals(z1.getAnswerBankDto().getAnswerCode())))) {
                                k.setUserPoint(e.getQuestionPoint());
                            }
                        });
                        break;
                }
            });
        }
        lessonDto.setUserDto(currentUser);
        lessonDto.setMappedLessonReports(userAnswerDtosFromClient.keySet());
        lessonDto.setTotalPercent(calculateTotalPercent(lessonDto.getMappedLessonReports(), lessonDto.getMappedLessonReportsFinish()));
        Lesson lesson1 = iLessonRepository.save(mapper.map(lessonDto, Lesson.class));
        lessonDto.getMappedLessonReports().forEach(e -> {
            LessonReport lessonReport = iLessonReportRepository.save(mapper.map(e, LessonReport.class));
        });
        return lessonDto;

    }

    private void setAnswerDtoForLessonDto(Lesson lesson, LessonDto lessonDto) {
        lessonDto.getMappedLessonReports().stream().filter(e -> e.getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e.getLessonReportId().getLessonReportQuestionCode())).forEach(e -> {
            lesson.getMappedLessonReports().stream().filter(e1 -> e1.getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e1.getLessonReportId().getLessonReportQuestionCode())).forEach(w -> {
                if (e.getLessonReportId().getLessonReportQuestionCode().equals(w.getLessonReportId().getLessonReportQuestionCode())
                        && e.getLessonReportId().getLessonReportLessonCode().equals(w.getLessonReportId().getLessonReportLessonCode())){
                    try {
                        List<AnswerDto> correctAnswerDto = ServiceUtils.convertAnswerDtoJsonToList(w.getCorrectAnswers());
                        List<AnswerDto> incorrectAnswerDto = ServiceUtils.convertAnswerDtoJsonToList(w.getIncorrectAnswers());
                        e.setCorrectAnswers(correctAnswerDto);
                        e.setIncorrectAnswers(incorrectAnswerDto);
                        List<AnswerDto> answerDtos = new ArrayList<>();
                        answerDtos.addAll(incorrectAnswerDto);
                        answerDtos.addAll(correctAnswerDto);
                        e.setAnswers(new ArrayList<>(answerDtos));
                    } catch (ElearningException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        });
    }

    private Set<LessonReportDto> convertToLessonReportDto(List<QuestionDto> questionDtos, String lessonCode){
        return questionDtos.stream().map(e -> {
            LessonReportDto lessonReportDto = new LessonReportDto();
            lessonReportDto.setLessonReportId(new LessonReportId(lessonCode, e.getQuestionBankDto().getQuestionCode()));
            lessonReportDto.setQuestionContent(e.getQuestionBankDto().getQuestionContent());
            lessonReportDto.setQuestionType(e.getQuestionBankDto().getQuestionType());
            lessonReportDto.setQuestionParentCode(e.getQuestionBankDto().getQuestionParentCode());
            lessonReportDto.setQuestionPoint(e.getQuestionBankDto().getPoint());
            lessonReportDto.setSubcategoryCode(e.getQuestionBankDto().getSubcategory().getSubcategoryCode());
            if (e.getQuestionBankDto().getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e.getQuestionBankDto().getQuestionCode())) {
                lessonReportDto.setCorrectAnswers(
                    e.getAnswerDtos()
                        .stream().filter(w -> w.getSystemResultDto().getSystemResultIsCorrect() == Constants.FETCH_ANSWER_CORRECT).collect(Collectors.toList()));
                lessonReportDto.setIncorrectAnswers(
                    e.getAnswerDtos()
                        .stream().filter(w -> w.getSystemResultDto().getSystemResultIsCorrect() == Constants.FETCH_ANSWER_INCORRECT).collect(Collectors.toList()));
                lessonReportDto.setAnswers(hideAnswerResult(e.getAnswerDtos()));
            }
            return lessonReportDto;
        }).collect(Collectors.toSet());
    }

    private Set<LessonReportDtoFinish> convertToLessonReportDtoFinish(Set<LessonReportDto> lessonReportDtos) throws ElearningException{
        return lessonReportDtos.stream()
            .filter(e -> e.getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !questionBankService.hasChildren(e.getLessonReportId().getLessonReportQuestionCode())).map(e -> {
            LessonReportDtoFinish lessonReportDtoFinish = new LessonReportDtoFinish();
            lessonReportDtoFinish.setQuestionContent(e.getQuestionContent());
            lessonReportDtoFinish.setLessonReportId(e.getLessonReportId());
            lessonReportDtoFinish.setQuestionParentCode(e.getQuestionParentCode());
            lessonReportDtoFinish.setQuestionPoint(e.getQuestionPoint());
            lessonReportDtoFinish.setSubcategoryCode(e.getSubcategoryCode());
            lessonReportDtoFinish.setIncorrectAnswers(e.getIncorrectAnswers());
            lessonReportDtoFinish.setCorrectAnswers(e.getCorrectAnswers());
            if (e.getUserAnswers() != null){
                if (e.getUserAnswers().size() > 0) lessonReportDtoFinish.setUserPoint(e.getUserPoint());
            }
            return lessonReportDtoFinish;
        }).collect(Collectors.toSet());
    }

    private Double calculateTotalPercent(Set<LessonReportDto> lessonReportDtos, Set<LessonReportDtoFinish> lessonReportDtoFinishes){
        Double totalUserPoint = 0.0;
        Double totalQuestionPoint = lessonReportDtoFinishes.stream().mapToDouble(LessonReportDtoFinish::getQuestionPoint).sum();
        totalUserPoint = lessonReportDtos.stream().filter(e -> e.getUserPoint() != null).mapToDouble(LessonReportDto::getUserPoint).sum();
        if (totalQuestionPoint == 0.0) return 0.0;
        return (totalUserPoint / totalQuestionPoint) * 100;
    }

    private List<AnswerDto> hideAnswerResult(List<AnswerDto> answerDtos){
        return answerDtos.stream().peek(e -> e.getSystemResultDto().setSystemResultIsCorrect(Constants.ANSWER_HIDDEN_RESULT))
            .collect(Collectors.toList());
    }
}
