package Landing.View;

import Landing.Control.Controller;
import Landing.Model.MoveParams;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View extends Application {
    public enum PARAMETERSE {
        POSITION_BY_X,
        POSITION_BY_Y,
        VELOCITY_BY_X,
        VELOCITY_BY_Y,
        PROBE_MASS,
        PLANET_RADIUS,
        ATMOSPHERE_RADIUS,
        PLANET_MASS,
        ENGINE_BY_X,
        ENGINE_BY_Y
    }

    public enum VALUESE {
        TIME,
        MAX_SPEED,
        LANDING_POSITION_BY_X,
        LANDING_POSITION_BY_Y
    }
    private static final String domainsString = String.format("%s\n%s\n%s\n%s\n%s\n%s\n",
            String.format("%.2g%s%.2g", MoveParams.getMinX(), " <= X, Y <= ", MoveParams.getMaxX()),
            String.format("%.2g%s%.2g", MoveParams.getMinVx(), " <= Vx, Vy <= ", MoveParams.getMaxVx()),
            String.format("%.2g%s%.2g", MoveParams.getMinZondmass(), " <= Probe Mass <= ", MoveParams.getMaxZondmass()),
            String.format("%.2g%s%.2g", MoveParams.getMinPlanetRadius(), " <= Planet Radius <= ", MoveParams.getMaxPlanetRadius()),
            String.format("%.2g%s%.2g", MoveParams.getMinPlanetmass(), " <= Planet Mass <= ", MoveParams.getMaxPlanetmass()),
            String.format("%.2g%s%.2g", MoveParams.getMinAtmosphereRadius(), " <= Atmosphere thickness <= ", MoveParams.getMaxAtmosphereRadius())
    );

    public static final List<String> PARAMETERS = Arrays.asList(
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

    private static final List<String> VALUES = Arrays.asList(
            "Time",
            "land speed",
            "Landing position by X",
            "Landing position by Y"
    );

    private static final List<String> COMBOS = Arrays.asList(
            "X",
            "Y",
            "Velocity by X",
            "Velocity by Y",
            "Power",
            "Time"
    );

    private BorderPane paramPane = null;
    private BorderPane valuesPane = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        List<TextField> paramFields = createTextFields(PARAMETERS);
        List<TextField> valuesFields = createTextFields(VALUES);
        List<ComboBox<String>> comboBoxes = new ArrayList<>();

        BorderPane borderPane = new BorderPane();
        BorderPane mainPane = addMainPane(comboBoxes);
        borderPane.setCenter(mainPane);

        BorderPane leftPane = addParametersPane(mainPane, borderPane, paramFields, valuesFields);
        borderPane.setLeft(leftPane);

        Scene scene = new Scene(borderPane, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drone landing");
        primaryStage.show();
    }

    private BorderPane addMainPane(List<ComboBox<String>> comboBoxes) {
        BorderPane mainPane = new BorderPane();
        mainPane.getStyleClass().add("mainPane");
        //mainPane.setCenter(addChart(addCheckedValues(), "example"));
        mainPane.setBottom(addBottomPane(mainPane, comboBoxes));
        return mainPane;
    }

    private FlowPane addBottomPane(BorderPane mainPane, List<ComboBox<String>> comboBoxes) {
        FlowPane flowPane = new FlowPane();

        HBox xAxis = addChoiceBox("X axis: ", "X", comboBoxes);
        HBox yAxis = addChoiceBox("Y axis: ", "Y", comboBoxes);

        Button buildButton = new Button("Build");

        buildButton.setOnAction(event -> Controller.actionBuildButton(mainPane, comboBoxes));

        Button csvWrite = new Button("Write in CSV");

        csvWrite.setOnAction(event -> Controller.actionCsvWrite());

        FlowPane.setMargin(xAxis, new Insets(5, 10, 5, 10));
        FlowPane.setMargin(yAxis, new Insets(5, 10, 5, 10));
        FlowPane.setMargin(buildButton, new Insets(5, 10, 5, 10));
        FlowPane.setMargin(csvWrite, new Insets(5, 10, 5, 10));

        flowPane.getChildren().addAll(xAxis, yAxis, buildButton, csvWrite);
        flowPane.getStyleClass().add("bottomPane");
        return flowPane;
    }

    private HBox addChoiceBox(String label, String initValue, List<ComboBox<String>> comboBoxes) {
        HBox hBox = new HBox();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(COMBOS);
        comboBox.setValue(initValue);

        comboBoxes.add(comboBox);

        hBox.getChildren().addAll(addLabelPane(label), comboBox);
        return hBox;
    }

    public static LineChart<Number, Number> addChart(List<List<Double>> values,
                                                     List<List<Double>> planet,
                                                     List<List<Double>> atmosphere,
                                                     String chartTitle) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<>(x, y);
        numberLineChart.setCreateSymbols(false);
        numberLineChart.setLegendVisible(false);
        numberLineChart.setTitle(chartTitle);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();

        for (List<Double> value : values) {
            datas.add(new XYChart.Data<>(value.get(0), value.get(1)));
        }

        series.setData(datas);

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas2 = FXCollections.observableArrayList();
        for(int i = 0; i < planet.get(0).size();i ++){
            datas2.add(new XYChart.Data<>(planet.get(0).get(i), planet.get(1).get(i)));
        }
        series2.setData(datas2);

        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas3 = FXCollections.observableArrayList();
        for(int i = 0; i < planet.get(2).size();i ++){
            datas3.add(new XYChart.Data<>(planet.get(2).get(i), planet.get(3).get(i)));

        }
        series3.setData(datas3);

        XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas4 = FXCollections.observableArrayList();
        for(int i = 0; i < atmosphere.get(0).size();i ++){
            datas4.add(new XYChart.Data<>(atmosphere.get(0).get(i), atmosphere.get(1).get(i)));
        }
        series4.setData(datas4);

        XYChart.Series<Number, Number> series5 = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas5 = FXCollections.observableArrayList();
        for(int i = 0; i < atmosphere.get(2).size();i ++){
            datas5.add(new XYChart.Data<>(atmosphere.get(2).get(i), atmosphere.get(3).get(i)));

        }
        series5.setData(datas5);


        numberLineChart.getStyleClass().add("chart");
        //numberLineChart.getData().add(series2);
       numberLineChart.getData().addAll(series, series2, series3,series4,series5);
        return numberLineChart;
    }

    public static LineChart<Number, Number> addSecondChart(List<List<Double>> values, String chartTitle) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<>(x, y);
        numberLineChart.setCreateSymbols(false);
        numberLineChart.setLegendVisible(false);
        numberLineChart.setTitle(chartTitle);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();

        for (List<Double> value : values) {
            datas.add(new XYChart.Data<>(value.get(1), value.get(0)));
        }

        series.setData(datas);

        numberLineChart.getStyleClass().add("chart");
        numberLineChart.getData().add(series);
        return numberLineChart;
    }

    public static BorderPane addParametersPane(BorderPane mainPane,
                                               BorderPane borderPane,
                                               List<TextField> paramFields,
                                               List<TextField> valuesFields) {
        BorderPane leftPane = new BorderPane();

        HBox buttonBox = addBtnBox(mainPane, borderPane, paramFields, valuesFields);
        leftPane.setTop(buttonBox);

        VBox vBox = addVBox(PARAMETERS, paramFields);
        leftPane.setCenter(vBox);

        vBox.getStyleClass().add("parametersPane");

        leftPane.setBottom(addParametersBtnPane(mainPane, borderPane, paramFields, valuesFields));
        return leftPane;
    }

    private static BorderPane addParametersBtnPane(BorderPane mainPane,
                                                   BorderPane borderPane,
                                                   List<TextField> paramFields,
                                                   List<TextField> valuesFields) {
        BorderPane btnPane = new BorderPane();
        btnPane.getStyleClass().add("btnPane");

        Button calculateBtn = new Button("Calculate");
        calculateBtn.setOnAction(event -> Landing.Control.Controller.actionCalculateButton(mainPane, paramFields, valuesFields));

        Button randomBtn = new Button("Random parameters");
        randomBtn.setOnAction(event -> Landing.Control.Controller.actionRandomButton(borderPane, paramFields, valuesFields));

        Button domainOfDefBtn = new Button("?");
        domainOfDefBtn.setOnAction(event -> Landing.Control.Controller.actiondomainOfDefBtn());
        btnPane.setCenter(domainOfDefBtn);
        btnPane.setLeft(randomBtn);
        btnPane.setRight(calculateBtn);
        return btnPane;
    }

    public static BorderPane addValuesPane(BorderPane mainPane,
                                           BorderPane borderPane,
                                           List<TextField> paramFields,
                                           List<TextField> valuesFields) {
        BorderPane leftPane = new BorderPane();

        HBox buttonBox = addBtnBox(mainPane, borderPane, paramFields, valuesFields);
        leftPane.setTop(buttonBox);

        VBox vBox = addVBox(VALUES, valuesFields);
        leftPane.setCenter(vBox);

        vBox.getStyleClass().add("valuesPane");

        for (TextField field : valuesFields) {
            field.setEditable(false);
        }

        return leftPane;
    }

    private static VBox addVBox(List<String> parameters, List<TextField> fields) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        for (int i = 0; i < Math.min(parameters.size(), fields.size()); ++i) {
            BorderPane borderPane = addTextPane(parameters.get(i), fields.get(i));
            vBox.getChildren().add(borderPane);
        }

        return vBox;
    }

    private static BorderPane addTextPane(String labelText,TextField field) {
        BorderPane borderPane = new BorderPane();

        Label label = new Label(labelText);
        label.setWrapText(true);
        label.getStyleClass().add("label");

        borderPane.setLeft(addLabelPane(labelText));
        borderPane.setRight(field);
        borderPane.setMaxWidth(320);
        borderPane.setMinWidth(320);

        return borderPane;
    }

    private static BorderPane addLabelPane(String labelText) {
        BorderPane borderPane = new BorderPane();

        Label label = new Label(labelText);
        label.setWrapText(true);
        label.getStyleClass().add("label");

        borderPane.setCenter(label);
        return borderPane;
    }

    private static HBox addBtnBox(BorderPane mainPane,
                                  BorderPane borderPane,
                                  List<TextField> paramFields,
                                  List<TextField> valuesFields) {
        HBox hBox = new HBox();
        hBox.getStyleClass().add("btnBox");

        Button parametersBtn = new Button("Parameters");
        Button valuesBtn = new Button("Values");

        parametersBtn.prefWidthProperty().bind(hBox.widthProperty().multiply(0.5));
        valuesBtn.prefWidthProperty().bind(hBox.widthProperty().multiply(0.5));

        parametersBtn.setOnAction(event -> Controller.actionParametersButton(mainPane, borderPane, paramFields, valuesFields));
        valuesBtn.setOnAction(event -> Controller.actionValuesButton(mainPane, borderPane, paramFields, valuesFields));

        hBox.getChildren().addAll(parametersBtn, valuesBtn);
        return hBox;
    }

    private List<TextField> createTextFields(List<String> labels) {
        List<TextField> fields = new ArrayList<>();

        for (String text : labels) {
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
