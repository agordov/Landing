package Landing.View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import landingProbe.controller.Controller;

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
        BorderPane borderPane = new BorderPane();

        BorderPane leftPane = addParametersPane(borderPane, listOfParamFields, listOfValuesFields);
        borderPane.setLeft(leftPane);
        borderPane.setCenter(addMainPane());
        Scene scene = new Scene(borderPane, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drone landing");
        primaryStage.show();
    }
    private BorderPane addMainPane(){
        BorderPane mainPane = new BorderPane();
        mainPane.getStyleClass().add("mainPane");
        mainPane.setCenter(addChart(addCheckedValues()));
        mainPane.setBottom(addFlowPane());
        return mainPane;
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
    private FlowPane addFlowPane(){
        FlowPane flowPane = new FlowPane();

        Button velBtn = addGraphButton("graph V(t)");
        Button powerBtn = addGraphButton("graph F(t)");
        Button controlBtn = addGraphButton("graph control(t)");
        flowPane.setPadding(new Insets(5));
        FlowPane.setMargin(velBtn, new Insets(5,10,5,10));
        FlowPane.setMargin(powerBtn, new Insets(5,10,5,10));
        FlowPane.setMargin(controlBtn, new Insets(5,10,5,10));
        flowPane.getChildren().addAll(velBtn, powerBtn, controlBtn);
        flowPane.getStyleClass().add("graphButtons");
        return flowPane;
    }
    private Button addGraphButton(String textButton){
        Button graphButton = new Button(textButton);
        graphButton.setOnAction(event -> Controller.actionInfoButton());
        return graphButton;
    }
    public static LineChart<Number, Number> addChart(List<List<Double>> values){
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        LineChart<Number, Number> numberLineChart = new LineChart<>(x,y);
        numberLineChart.setCreateSymbols(false);
        numberLineChart.setLegendVisible(false);
        numberLineChart.setTitle("Angular Motion: trajectory");
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();
        for(int i = 0; i < values.get(0).size();i ++){
            datas.add(new XYChart.Data<>(values.get(0).get(i), values.get(1).get(i)));

        }
        series.setData(datas);
        numberLineChart.getStyleClass().add("chart");
        numberLineChart.getData().add(series);
        return numberLineChart;
    }
    public static BorderPane addParametersPane(BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        List<String> parameters = new ArrayList<>();
        Collections.addAll(parameters, "PosX", "PosY","PowerX", "PowerY", "MassProbe", "Radius","AtmosphereRadius", "MassPlanet");

        BorderPane leftPane = new BorderPane();

        HBox buttonBox = addBtnBox(borderPane, listOfParamFields, listOfValuesFields);
        leftPane.setTop(buttonBox);

        VBox vBox = addVBox(parameters, listOfValuesFields);
        leftPane.setCenter(vBox);
        vBox.getStyleClass().add("parametersPane");


        leftPane.setBottom(addCalcPane());
        return leftPane;
    }
    private static BorderPane addCalcPane(){
        BorderPane btnPane = new BorderPane();

        Button calculateBut = new Button("Calculate");
        calculateBut.getStyleClass().add("calcButton");

        calculateBut.setOnAction(event -> Controller.actionCalculateButton());
        BorderPane.setMargin(calculateBut, new Insets(10));
        btnPane.setRight(calculateBut);
        return btnPane;
    }
    public static BorderPane addValuesPane(BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        List<String> parameters = new ArrayList<>();
        Collections.addAll(parameters, "Time", "MaxSpeed","LandingPositionX", "LandingPositionY");
        BorderPane leftPane = new BorderPane();
        HBox buttonBox = addBtnBox(borderPane, listOfParamFields, listOfValuesFields);
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
    private static HBox addBtnBox(BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        HBox hBox = new HBox();
        hBox.getStyleClass().add("btnBox");
        Button btn1 = new Button("Parameters");
        Button btn2 = new Button("Values");
        btn1.prefWidthProperty().bind(hBox.widthProperty().multiply(0.5));
        btn2.prefWidthProperty().bind(hBox.widthProperty().multiply(0.5));
        btn1.setOnAction(event -> Controller.actionParametersButton(borderPane, listOfParamFields, listOfValuesFields));
        btn2.setOnAction(event -> Controller.actionValuesButton(borderPane, listOfParamFields, listOfValuesFields));
        hBox.getChildren().addAll(btn1,btn2);
        return hBox;
    }
}
