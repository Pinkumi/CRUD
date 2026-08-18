module com.crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;

    opens com.crud to javafx.fxml;
    exports com.crud;
}