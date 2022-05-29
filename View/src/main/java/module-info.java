module com.crs.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires Logic;
    requires java.sql;
    requires commons.math3;
    requires combinatoricslib3;
    requires javafx.swing;



    opens com.crs.view to javafx.fxml;
    exports com.crs.view;


}
