package ru.itmo.hasd.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

    public static class ExceptionMessages {
        public static final String SERIALIZATION_EXCEPTION_MESSAGE = "Произошла ошибка при сериализации";
        public static final String DESERIALIZATION_EXCEPTION_MESSAGE = "Произошла ошибка при десериализации";
        public static final String FIELD_NOT_AVAILABLE_EXCEPTION_MESSAGE_TEMPLATE = "Поле %s недоступно для редактирования";
    }

    public static class SchemaParts {
        public static final String CLASS = "class ";
        public static final String FIELD = "field ";
        public static final String TYPE = "type ";
        public static final String VALUE = "value ";
        public static final String VALUES = "values [";
        public static final String KEY_TYPE = "key-type ";
        public static final String VALUE_TYPE = "value-type ";

        public static final String EMPTY_VALUES = "[]";
        public static final String VALUES_DELIMITER = " || ";
        public static final String VALUES_DELIMITER_REGEX = " \\|\\| ";
        public static final String ENTRY_DELIMITER = "--";
    }

}
