module Logic {
    requires static lombok;
    requires javafx.base;
    requires static decimal4j;
    requires static commons.math3;
    requires java.sql;


    exports DataModel;
    exports Managers;
    exports Repos;
    exports SetsModel;

}