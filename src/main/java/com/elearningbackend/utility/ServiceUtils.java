package com.elearningbackend.utility;

import com.elearningbackend.customerrorcode.Errors;
import org.springframework.data.domain.Sort;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

import static java.util.Arrays.*;

public class ServiceUtils {
    public static Map<String, List<String>> validateRequired(Object obj, String... fields){
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
}
