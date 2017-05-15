package Landing.View;

import Landing.Control.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class View extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        List<TextField> listOfValuesFields = new ArrayList<>();
        List<TextField> listOfParamFields = new ArrayList<>();
        List<ComboBox<String>> listOfComboBoxes= new ArrayList<>();
        BorderPane borderPane = new BorderPane();
        BorderPane mainPane = addMainPane(listOfComboBoxes);
        borderPane.setCenter(mainPane);

        BorderPane leftPane = addParametersPane(mainPane, borderPane, listOfParamFields, listOfValuesFields);
        borderPane.setLeft(leftPane);


        Scene scene = new Scene(borderPane, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drone landing");
        primaryStage.show();
    }
    private BorderPane addMainPane(List<ComboBox<String>> listOfComboBoxes){
        BorderPane mainPane = new BorderPane();
        mainPane.getStyleClass().add("mainPane");
        mainPane.setCenter(addChart(addCheckedValues(), "example"));
        mainPane.setBottom(addBottomPane(mainPane, listOfComboBoxes));
        return mainPane;
    }
    private FlowPane addBottomPane(BorderPane mainPane, List<ComboBox<String>> listOfComboBoxes){
        FlowPane flowPane = new FlowPane();
        HBox firstBox = addChoiceBox("X axis: ", "X", listOfComboBoxes);
        HBox secondBox = addChoiceBox("Y axis: ", "Y", listOfComboBoxes);
        Button buildButton = new Button("Build");


        buildButton.setOnAction(event -> Controller.actionBuildButton(mainPane, listOfComboBoxes));

        FlowPane.setMargin(firstBox, new Insets(5,10,5,10));
        FlowPane.setMargin(secondBox, new Insets(5,10,5,10));
        FlowPane.setMargin(buildButton, new Insets(5,10,5,10));

        flowPane.getChildren().addAll(firstBox, secondBox, buildButton);
        flowPane.getStyleClass().add("bottomPane");
        return flowPane;
    }
    private HBox addChoiceBox(String label, String initValue, List<ComboBox<String>> listOfComboBoxes){
        HBox hBox = new HBox();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "X",
                "Y",
                "Vx",
                "Vy",
                "Power",
                "Time"
        );
        listOfComboBoxes.add(comboBox);
        comboBox.setValue(initValue);
        hBox.getChildren().addAll(addLabelPane(label), comboBox);
        return hBox;
    }
    private List<List<Double>> addCheckedValues(){
        List<List<Double>> values = new ArrayList<>();
        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        for(Double i = 0.0; i<1000;i++){
            xValues.add(i);
            yValues.add(i*i);
        }
        values.add(xValues);
        values.add(yValues);
        return values;
    }

    private Button addGraphButton(String textButton){
        Button graphButton = new Button(textButton);
        graphButton.setOnAction(event -> Controller.actionInfoButton());
        return graphButton;
    }
    public static LineChart<Number, Number> addChart(List<List<Double>> values, String chartTitle){
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        LineChart<Number, Number> numberLineChart = new LineChart<>(x,y);
        numberLineChart.setCreateSymbols(false);
        numberLineChart.setLegendVisible(false);
        numberLineChart.setTitle(chartTitle);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();
        for(int i = 0; i < values.size();i++){
            datas.add(new XYChart.Data<>(values.get(i).get(1), values.get(i).get(0)));
        }
        series.setData(datas);
        numberLineChart.getStyleClass().add("chart");
        numberLineChart.getData().add(series);
        return numberLineChart;
    }
    public static BorderPane addParametersPane(BorderPane mainPane,BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        List<String> parameters = new ArrayList<>();
        Collections.addAll(parameters, "PosX", "PosY","PowerX", "PowerY", "Probe Mass", "Radius","Atmosphere Radius", "Planet Mass");

        BorderPane leftPane = new BorderPane();

        HBox buttonBox = addBtnBox(mainPane, borderPane, listOfParamFields, listOfValuesFields);
        leftPane.setTop(buttonBox);

        VBox vBox = addVBox(parameters, listOfValuesFields);
        leftPane.setCenter(vBox);
        vBox.getStyleClass().add("parametersPane");


        leftPane.setBottom(addBtnPane(mainPane,borderPane,  listOfParamFields, listOfValuesFields));
        return leftPane;
    }
    private static BorderPane addBtnPane(BorderPane mainPane,BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        BorderPane btnPane = new BorderPane();
        btnPane.getStyleClass().add("btnPane");


        Button calculateBut = new Button("Calculate");
        calculateBut.setOnAction(event -> Landing.Control.Controller.actionCalculateButton(mainPane, listOfValuesFields));

        Button randomBut = new Button("Random parameters");
        randomBut.setOnAction(event -> Landing.Control.Controller.actionRandomButton(borderPane, listOfParamFields, listOfValuesFields));

        btnPane.setLeft(randomBut);
        btnPane.setRight(calculateBut);
        return btnPane;
    }
    public static BorderPane addValuesPane(BorderPane mainPane,BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        List<String> parameters = new ArrayList<>();
        Collections.addAll(parameters, "Time", "MaxSpeed","LandingPositionX", "LandingPositionY");
        BorderPane leftPane = new BorderPane();
        HBox buttonBox = addBtnBox(mainPane, borderPane, listOfParamFields, listOfValuesFields);
        leftPane.setTop(buttonBox);

        VBox vBox = addVBox(parameters, listOfValuesFields);
        leftPane.setCenter(vBox);
        vBox.getStyleClass().add("valuesPane");

        for(TextField field:listOfValuesFields){
            field.setEditable(false);
        }
        return leftPane;
    }
    private static VBox addVBox(List<String> parameters, List<TextField> listOfFields){
        VBox vBox = new VBox();

        vBox.setSpacing(10);
        for(String text:parameters){
            BorderPane borderPane = addTextPane(text, listOfFields);
            vBox.getChildren().add(borderPane);
        }
        return vBox;
    }
    private static BorderPane addTextPane(String textLabel, List<TextField> listOfFields){
        BorderPane borderPane = new BorderPane();
        Label label = new Label(textLabel);

        label.setWrapText(true);
        label.getStyleClass().add("label");
        borderPane.setLeft(addLabelPane(textLabel));
        TextField textField = new TextField();
        textField.getStyleClass().add("textField");
        //textField.prefWidthProperty().bind(hBox.widthProperty().subtract(label.widthProperty()));;
        borderPane.setRight(textField);
        borderPane.setMaxWidth(320);
        borderPane.setMinWidth(320);
        listOfFields.add(textField);
        return borderPane;
    }
    private static BorderPane addLabelPane(String textLabel){
        BorderPane borderPane = new BorderPane();
        Label label = new Label(textLabel);
        label.setWrapText(true);
        label.getStyleClass().add("label");
        borderPane.setCenter(label);
        return borderPane;
    }
    private static HBox addBtnBox(BorderPane mainPane, BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        HBox hBox = new HBox();
        hBox.getStyleClass().add("btnBox");
        Button btn1 = new Button("Parameters");
        Button btn2 = new Button("Values");
        btn1.prefWidthProperty().bind(hBox.widthProperty().multiply(0.5));
        btn2.prefWidthProperty().bind(hBox.widthProperty().multiply(0.5));
        btn1.setOnAction(event -> Controller.actionParametersButton(mainPane, borderPane, listOfParamFields, listOfValuesFields));
        btn2.setOnAction(event -> Controller.actionValuesButton(mainPane, borderPane, listOfParamFields, listOfValuesFields));
        hBox.getChildren().addAll(btn1,btn2);
        return hBox;
    }
}
