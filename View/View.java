package Landing.View;

import Landing.Model.MoveParams;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View extends Application {
    private static final String domainsString = String.format("%s\n%s\n%s\n%s\n%s\n%s\n",
            String.format("%.2g%s%.2g", MoveParams.getMinX(), " <= X, Y <= ", MoveParams.getMaxX()),
            String.format("%.2g%s%.2g", MoveParams.getMinVx(), " <= Vx, Vy <= ", MoveParams.getMaxVx()),
            String.format("%.2g%s%.2g", MoveParams.getMinZondmass(), " <= Probe Mass <= ", MoveParams.getMaxZondmass()),
            String.format("%.2g%s%.2g", MoveParams.getMinPlanetRadius(), " <= Planet Radius <= ", MoveParams.getMaxPlanetRadius()),
            String.format("%.2g%s%.2g", MoveParams.getMinPlanetmass(), " <= Planet Mass <= ", MoveParams.getMaxPlanetmass()),
            String.format("%.2g%s%.2g", MoveParams.getMinAtmosphereRadius(), " <= Atmosphere thickness <= ", MoveParams.getMaxAtmosphereRadius())
    );

    static final List<String> PARAMETERS = Arrays.asList(
            "Position by X",
            "Position by Y",
            "Velocity by X",
            "Velocity by Y",
            "Probe mass",
            "Planet radius",
            "Atmosphere radius",
            "Planet mass",
            "Engine by X",
            "Engine by Y"
    );

    static final List<String> VALUES = Arrays.asList(
            "Time",
            "land speed",
            "Landing position by X",
            "Landing position by Y"
    );

    static final List<String> COMBOS = Arrays.asList(
            "X",
            "Y",
            "Velocity by X",
            "Velocity by Y",
            "Power",
            "Time"
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        List<TextField> paramFields = createTextFields(PARAMETERS);
        List<TextField> valuesFields = createTextFields(VALUES);
        List<ComboBox<String>> comboBoxes = new ArrayList<>();

        BorderPane borderPane = new BorderPane();
        BorderPane mainPane = MainPane.addMainPane(comboBoxes);
        borderPane.setCenter(mainPane);

        BorderPane leftPane = LeftPane.addParametersPane(mainPane, borderPane, paramFields, valuesFields);
        borderPane.setLeft(leftPane);

        Scene scene = new Scene(borderPane, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drone landing");
        primaryStage.show();
    }


    static BorderPane addLabelPane(String labelText) {
        BorderPane borderPane = new BorderPane();

        Label label = new Label(labelText);
        label.setWrapText(true);
        label.getStyleClass().add("label");

        borderPane.setCenter(label);
        return borderPane;
    }
    private List<TextField> createTextFields(List<String> labels) {
        List<TextField> fields = new ArrayList<>();

        for (String ignored : labels) {
            TextField field = new TextField();
            field.getStyleClass().add("textField");
            fields.add(field);
        }

        return fields;
    }
    public static void addHelpAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Domains of definitions");
        alert.setHeaderText(null);
        alert.setResizable(true);
        alert.setContentText(domainsString);
        alert.showAndWait();
    }
    public static void addCsvAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Write in CSV");
        alert.setHeaderText(null);
        alert.setResizable(true);
        alert.setContentText("Writed in CSV file");
        alert.showAndWait();
    }

}
