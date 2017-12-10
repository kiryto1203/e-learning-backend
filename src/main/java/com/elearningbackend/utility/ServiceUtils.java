package com.elearningbackend.utility;

import com.elearningbackend.customerrorcode.Errors;
<<<<<<< HEAD
import org.springframework.data.domain.Sort;
<<<<<<< HEAD
import com.elearningbackend.customexception.ElearningException;
import com.fasterxml.jackson.databind.ObjectMapper;
=======
=======
import com.elearningbackend.dto.UserDto;
>>>>>>> Config JWT for authentication + add method convert filed null + add base controller to get CurrentUser
>>>>>>> Config JWT for authentication + add method convert filed null + add base controller to get CurrentUser
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
<<<<<<< HEAD

    public static Sort proceedSort(String sortBy, String direction){
        return new Sort(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
=======
    /**
     * Method set field for Object if this is empty or null
     */
    public static Object convertObject(Object obj,String... fields){
        for (String field: fields) {
            Field foundField = ReflectionUtils.findField(obj.getClass(), field);
            ReflectionUtils.makeAccessible(foundField);
            Object value = ReflectionUtils.getField(foundField, obj);
            if (value == null || (value instanceof String && ((String) value).isEmpty())){
                ReflectionUtils.setField(foundField,obj, "N/A");
            }
        }
        return obj;
>>>>>>> Config JWT for authentication + add method convert filed null + add base controller to get CurrentUser
    }

    private static boolean checkErrorsMap(Map<String, List<String>> errorsMap) throws ElearningException {
        ObjectMapper mapper = new ObjectMapper();
        if (!errorsMap.isEmpty()){
            throw new ElearningException(Errors.ERROR_FIELD_MISS.getId(), errorsMap.toString());
        }
        return true;
    }
}
