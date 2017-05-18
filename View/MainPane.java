package Landing.View;

import Landing.Control.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class MainPane {
    static BorderPane addMainPane(List<ComboBox<String>> comboBoxes) {
        BorderPane mainPane = new BorderPane();
        mainPane.getStyleClass().add("mainPane");
        mainPane.setBottom(addBottomPane(mainPane, comboBoxes));
        return mainPane;
    }
    private static FlowPane addBottomPane(BorderPane mainPane, List<ComboBox<String>> comboBoxes) {
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
    private static HBox addChoiceBox(String label, String initValue, List<ComboBox<String>> comboBoxes) {
        HBox hBox = new HBox();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(View.COMBOS);
        comboBox.setValue(initValue);

        comboBoxes.add(comboBox);

        hBox.getChildren().addAll(View.addLabelPane(label), comboBox);
        return hBox;
    }

    public static LineChart<Number, Number> addChart(List<List<Double>> atmosphere,
                                                     List<List<Double>> planet,
                                                     List<List<Double>> values) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<>(x, y);
        numberLineChart.setCreateSymbols(false);
        numberLineChart.setLegendVisible(false);
        numberLineChart.setVerticalZeroLineVisible(true);
        numberLineChart.setTitle("Landing");
        numberLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        numberLineChart.getStyleClass().add("chart");
        numberLineChart.getData().add(addTrajSeries(values));
        numberLineChart.getData().add(addCircleSeries(planet));
        numberLineChart.getData().add(addCircleSeries(atmosphere));
        return numberLineChart;
    }
    private static XYChart.Series<Number, Number> addCircleSeries(List<List<Double>> values){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();
        for(int i = 0; i < values.get(0).size();i ++){
            datas.add(new XYChart.Data<>(values.get(0).get(i), values.get(1).get(i)));
        }
        series.setData(datas);
        return series;
    }
    private static XYChart.Series<Number, Number> addTrajSeries(List<List<Double>> values){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();
        for (List<Double> value : values) {
            datas.add(new XYChart.Data<>(value.get(0), value.get(1)));
        }

        series.setData(datas);
        return series;
    }
    public static LineChart<Number, Number> addSecondChart(List<List<Double>> values, String chartTitle) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<>(x, y);
        numberLineChart.setCreateSymbols(false);
        numberLineChart.setLegendVisible(false);
        numberLineChart.setTitle(chartTitle);
        numberLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        numberLineChart.getStyleClass().add("chart");
        numberLineChart.getData().add(addTrajSeries(values));
        return numberLineChart;
    }
}
