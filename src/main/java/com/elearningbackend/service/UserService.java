package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.repository.IUserRepository;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends AbstractUserService<UserDto, String, User> {

    @Autowired
    public UserService(JpaRepository<User, String> repository) {
        super(repository, new Paginator<>(UserDto.class));
    }

    @Override
    public Pager<UserDto> loadAll(int currentPage, int noOfRowInPage) {
        Page<User> pager = getUserRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage, null));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public UserDto getOneByKey(String key) throws ElearningException{
        User user = getUserRepository().findOne(key);
        if (user == null) {
            throw new ElearningException(Errors.USER_NOT_FOUND.getId(), Errors.USER_NOT_FOUND.getMessage());
        }
        UserDto userDto = mapUserDto(user);
        return userDto;

    }

    private UserDto getOneByEmail(String email) {
        User user = getUserRepository().findByEmail(email);
        return user == null ? null : mapUserDto(user);
    }

    @Override
    public UserDto add(UserDto userDto) throws ElearningException {
        if (getUserRepository().findOne(userDto.getUsername()) != null)
            throw new ElearningException(Errors.USER_EXISTS.getId(), Errors.USER_EXISTS.getMessage());
        if (getUserRepository().findByEmail(userDto.getEmail()) != null)
            throw new ElearningException(Errors.EMAIL_EXISTS.getId(), Errors.EMAIL_EXISTS.getMessage());
        saveUser(userDto);
        return userDto;
    }

    @Override
    public UserDto edit(UserDto userDto) throws ElearningException {
        UserDto userDtoCheck = getOneByKey(userDto.getUsername());
        if (validateUserDtoForUpdate(userDto, userDtoCheck)) {
            saveUser(userDto);
            return userDto;
        }
        throw new ElearningException(Errors.USER_ERROR.getId(),Errors.USER_ERROR.getMessage());
    }

    @Override
    public UserDto delete(String key) throws ElearningException {
        UserDto userDto = getOneByKey(key);
        if (userDto != null){
            getUserRepository().delete(key);
            return userDto;
        }
        throw new ElearningException(Errors.USER_NOT_FOUND.getId(),Errors.USER_NOT_FOUND.getMessage());
    }

    boolean validateUserDtoForUpdate(UserDto userDto, UserDto userDtoCheck) throws ElearningException{
        if(!userDtoCheck.getPassword().equals(SecurityUtil.sha256(userDto.getPassword())))
            throw new ElearningException(Errors.USER_PASSWORD_NOT_MATCH.getId(), Errors.USER_PASSWORD_NOT_MATCH.getMessage());
        if (userDtoCheck == null) {
            throw new ElearningException(Errors.USER_NOT_FOUND.getId(), Errors.USER_NOT_FOUND.getMessage());
        }
        if (getOneByEmail(userDto.getEmail()) != null && !userDto.getEmail().equals(userDtoCheck.getEmail())){
            throw new ElearningException(Errors.EMAIL_SAME_WITH_OTHER_USERS.getId(), Errors.EMAIL_SAME_WITH_OTHER_USERS.getMessage());
        }
        return true;
    }

    private UserDto mapUserDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setPassword(user.getPasswordDigest());
        return userDto;
    }

    void saveUser (UserDto userDto){
        User entity = mapper.map(userDto, User.class);
        entity.setPasswordDigest(SecurityUtil.sha256(userDto.getPassword()));
        getUserRepository().save(entity);
    }

    IUserRepository getUserRepository() {
        return (IUserRepository) getRepository();
    }
}
