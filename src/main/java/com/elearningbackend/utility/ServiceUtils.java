package com.elearningbackend.utility;

import com.elearningbackend.customerrorcode.Errors;
import org.springframework.data.domain.Sort;
import com.elearningbackend.customexception.ElearningException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

public class ServiceUtils {

    public static boolean checkDataMissing(Object obj, String... fields) throws ElearningException{
        Map<String, List<String>> errorsMap = validateRequired(obj, fields);
        return checkErrorsMap(errorsMap);
    }

    private static Map<String, List<String>> validateRequired(Object obj, String... fields){
        Map<String, List<String>> errorsMap = new HashMap<>();
        List<String> errFields = new ArrayList<>();
        for(String field : fields) {
            Field foundField = ReflectionUtils.findField(obj.getClass(), field);
            ReflectionUtils.makeAccessible(foundField);
            Object value = ReflectionUtils.getField(foundField, obj);
            if (value == null || (value instanceof String && ((String) value).isEmpty())){
                errFields.add(field);
            }
        }
        if (!errFields.isEmpty())
            errorsMap.put(Errors.ERROR_FIELD_MISS.getMessage(), errFields);
        return errorsMap;
    }

    public static Sort proceedSort(String sortBy, String direction){
        return new Sort(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
    }

    private static boolean checkErrorsMap(Map<String, List<String>> errorsMap) throws ElearningException {
        ObjectMapper mapper = new ObjectMapper();
        if (!errorsMap.isEmpty()){
            throw new ElearningException(Errors.ERROR_FIELD_MISS.getId(), errorsMap.toString());
        }
        return true;
    }
}
