module edu.utsa.cs3443.replog {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.utsa.cs3443.replog to javafx.fxml;
    exports edu.utsa.cs3443.replog;
    exports edu.utsa.cs3443.replog.model;
    opens edu.utsa.cs3443.replog.model to javafx.fxml;
}