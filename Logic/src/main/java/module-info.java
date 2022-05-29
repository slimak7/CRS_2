module Logic {
    requires static lombok;
    requires javafx.base;
    requires static decimal4j;
    requires static commons.math3;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.commons.lang3;

    requires java.sql;


    exports DataModel;
    exports Managers;
    exports Repos;
    exports SetsModel;

}