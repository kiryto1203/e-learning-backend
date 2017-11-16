package com.elearningbackend.utility;

import com.elearningbackend.dto.Pager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Paginator<T, R> {

    private Class<R> returnClass;

    public Pager<R> paginate(int currentPage, Page<T> pager, int noOfRowInPage, ModelMapper mapper) {
        noOfRowInPage = validateNoOfRowInPage(noOfRowInPage);
        currentPage = validateCurrentPage(currentPage);
        Pager<R> result = new Pager<>();
        result.setCurrentPage(currentPage);
        result.setTotalRow(pager.getTotalElements());
        result.setNoOfRowInPage(noOfRowInPage);
        result.setTotalPage(pager.getTotalElements() / noOfRowInPage);
        List<R> resultList = pager.getContent().stream().filter(Objects::nonNull).map(
            a -> mapper.map(a, returnClass)).collect(Collectors.toList()
        );
        result.setResults(resultList);
        return result;
    }

    private static int validateCurrentPage(int currentPage) {
        return currentPage <= Constants.ZERO ? currentPage = Constants.CURRENT_PAGE_DEFAULT_VALUE
            : currentPage;
    }

    private static int validateNoOfRowInPage(int noOfRowInPage) {
        return noOfRowInPage <= Constants.ZERO ? noOfRowInPage = Constants.NO_OF_ROWS_DEFAULT_VALUE
            : noOfRowInPage;
    }
}
