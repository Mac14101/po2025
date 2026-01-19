module entities {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;

    exports entities;
    exports json;
    opens entities to com.fasterxml.jackson.databind;
}