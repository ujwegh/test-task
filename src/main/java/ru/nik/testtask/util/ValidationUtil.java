package ru.nik.testtask.util;

import ru.nik.testtask.exceptions.BadRequestException;

import java.util.Arrays;

/**
 * @author nik_aleks
 * @version 1.0
 */

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static <T> void checkIdNotNull(T id) {
        if (id == null) throw new BadRequestException("Id must not be null");
    }

    @SafeVarargs
    public static <T> void checkAllIdNotNull(T... ids) {
        Arrays.stream(ids).forEach(ValidationUtil::checkIdNotNull);
    }
}
