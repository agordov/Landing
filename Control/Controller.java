package Landing.Control;

import Landing.Model.MoveParams;
import Landing.Model.PIDController;
import Landing.Model.State;
import Landing.Model.Trajectory;
import Landing.View.View;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static void clearTextFields(List<TextField> listOfConstantFields, List<TextField> listOfFields){
        listOfFields.get(0).clear();
        listOfFields.get(1).clear();
        listOfFields.get(2).clear();
        listOfFields.get(3).clear();
        listOfConstantFields.get(0).clear();
        listOfConstantFields.get(1).clear();
        listOfConstantFields.get(2).clear();
    }
    public static void actionInfoButton(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("This program calculates the trajectory of the object's motion according to speed and coordinates.");
        alert.showAndWait();
    }
    public static void actionCalculateButton(BorderPane mainPane, List<TextField> listOfValuesFields){
        try{
            MoveParams moveParams = new MoveParams();
            PIDController pidController = new PIDController(moveParams.getEngineThrustX(), moveParams.getEngineThrustY(), 0.1);
            Trajectory trajectory = new Trajectory(pidController, moveParams);
            List<List<Double>> values = new ArrayList<>();
            List<State> trajectoryStates = trajectory.getTrajectory();
            for (State e : trajectoryStates) {
                List<Double> xy = new ArrayList<>();
                xy.add(e.getCoordinates().getX());
                xy.add(e.getCoordinates().getY());
                values.add(xy);
            }
            LineChart<Number, Number> numberLineChart = View.addChart(values, "Landing");
            mainPane.setCenter(numberLineChart);
            //listOfValuesFields.get(0).setText(String.valueOf(calcTrajectory.calculatePathLength()));
            //listOfValuesFields.get(1).setText(String.valueOf(calcTrajectory.fallingTime()));
            //listOfValuesFields.get(2).setText(String.valueOf(calcTrajectory.calculateMaxHeight()));

        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
            //borderPane.setCenter(null);
            //Controller.clearTextFields(listOfConstantFields, listOfFields);
        }catch (OutOfMemoryError e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error: out of memory. Arguments are too big");
            alert.showAndWait();
            //borderPane.setCenter(null);
            //Controller.clearTextFields(listOfConstantFields, listOfFields);

        }
    }
    public static void actionRandomButton(BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        BorderPane parametersPane = Landing.View.View.addParametersPane(borderPane, borderPane, listOfParamFields, listOfValuesFields);
        borderPane.setLeft(parametersPane);

    }
    public static void actionParametersButton(BorderPane mainPane,BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        BorderPane parametersPane = View.addParametersPane(mainPane, borderPane, listOfParamFields, listOfValuesFields);
        borderPane.setLeft(parametersPane);

    }
    public static void actionValuesButton(BorderPane mainPane, BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        BorderPane valuesPane = View.addValuesPane(mainPane, borderPane, listOfParamFields, listOfValuesFields);
        borderPane.setLeft(valuesPane);
    }
    public static void actionBuildButton(BorderPane mainPane, List<ComboBox<String>> listOfComboBoxes){

    }
}
