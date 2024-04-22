module com.example.postgresql_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.postgresql_javafx to javafx.fxml;
    exports com.example.postgresql_javafx;
}