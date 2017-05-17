package Landing.Control;

import Landing.Model.*;
import Landing.Util.Logger;
import Landing.View.View;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static List<State> stateList = new ArrayList<>(0);

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
    public static void actionCalculateButton(BorderPane mainPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        try{
            MoveParams moveParams = new MoveParams(Double.parseDouble(listOfParamFields.get(0).getText()),
                    Double.parseDouble(listOfParamFields.get(1).getText()),
                    Double.parseDouble(listOfParamFields.get(2).getText()),
                    Double.parseDouble(listOfParamFields.get(3).getText()),
                    Double.parseDouble(listOfParamFields.get(4).getText()),
                    Double.parseDouble(listOfParamFields.get(5).getText()),
                    Double.parseDouble(listOfParamFields.get(6).getText()),
                    Double.parseDouble(listOfParamFields.get(7).getText()),
                    Double.parseDouble(listOfParamFields.get(8).getText()),
                    Double.parseDouble(listOfParamFields.get(9).getText()));
            String status = checkMoveParams(moveParams);
            if (!status.equals("")) { //throw Exception if one of params is out of bounds
                throw new IllegalArgumentException(status + "is/are out of bounds");
            }
            PIDController pidController = new PIDController(moveParams.getMaxEngineThrustX(), moveParams.getMaxEngineThrustY(), 0.1);
            Trajectory trajectory = new Trajectory(pidController, moveParams);
            List<List<Double>> values = new ArrayList<>();
            List<List<Double>> planet = new ArrayList<>();
            List<List<Double>> atmosphere = new ArrayList<>();
            stateList = trajectory.getTrajectory();
            Logger.save(stateList);
            for (State e : stateList) {
                List<Double> xy = new ArrayList<>();
                xy.add(e.getCoordinates().getX());
                xy.add(e.getCoordinates().getY());
                values.add(xy);
            }
            LineChart<Number, Number> numberLineChart = View.addChart(values, planet, atmosphere,  "Landing");

            //numberLineChart.getData().add();
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

    //return "" if all move params are valid or string of invalid parameters
    private static String checkMoveParams(MoveParams moveParams) {
        String invalidParams = "" + (moveParams.checkX() ? "" : "X ") + (moveParams.checkY() ? "" : "Y ") +
                (moveParams.checkVx() ? "" : "Vx ") + (moveParams.checkVy() ? "" : "Vy ") +
                (moveParams.checkPlanetMass() ? "" : "Planet Mass ") + (moveParams.checkPlanetRadius() ? "" : "Planet Radius ") +
                (moveParams.checkProbeMass() ? "" : "Probe Mass ") + (moveParams.checkAtmosphereRadius() ? "" : "Atmosphere Radius");
        return invalidParams.equals("") ? "" : invalidParams;
    }

    public static void actionRandomButton(BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        MoveParams moveParams = new MoveParams();
        listOfParamFields.get(0).setText(String.valueOf(moveParams.getX()));
        listOfParamFields.get(1).setText(String.valueOf(moveParams.getY()));
        listOfParamFields.get(2).setText(String.valueOf(moveParams.getVx()));
        listOfParamFields.get(3).setText(String.valueOf(moveParams.getVy()));
        listOfParamFields.get(4).setText(String.valueOf(moveParams.getProbeMass()));
        listOfParamFields.get(5).setText(String.valueOf(moveParams.getPlanetRadius()));
        listOfParamFields.get(6).setText(String.valueOf(moveParams.getAtmosphereRadius()));
        listOfParamFields.get(7).setText(String.valueOf(moveParams.getPlanetMass()));
        listOfParamFields.get(8).setText(String.valueOf(moveParams.getMaxEngineThrustX()));
        listOfParamFields.get(9).setText(String.valueOf(moveParams.getMaxEngineThrustY()));
    }

    public static void actionParametersButton(BorderPane mainPane,
                                              BorderPane borderPane,
                                              List<TextField> paramFields,
                                              List<TextField> valuesFields) {
        BorderPane parametersPane = View.addParametersPane(mainPane, borderPane, paramFields, valuesFields);
        borderPane.setLeft(parametersPane);
    }

    public static void actionValuesButton(BorderPane mainPane,
                                          BorderPane borderPane,
                                          List<TextField> paramFields,
                                          List<TextField> valuesFields) {
        BorderPane valuesPane = View.addValuesPane(mainPane, borderPane, paramFields, valuesFields);
        borderPane.setLeft(valuesPane);
    }

    private static int getComboBoxValueId(ComboBox<String> comboBox) {
        int i = 0;
        while (!comboBox.getValue().equals(comboBox.getItems().get(i))) {
            i++;
        }
        return i;
    }

    public static void actionBuildButton(BorderPane mainPane, List<ComboBox<String>> listOfComboBoxes){
        int xAxisComboBoxId = getComboBoxValueId(listOfComboBoxes.get(1));
        int yAxisComboBoxId = getComboBoxValueId(listOfComboBoxes.get(0));
        List<List<Double>> values = new ArrayList<>();
        for (State e : stateList) {
            List<Double> xy = new ArrayList<>();
            xy.add(getFieldValue(e, xAxisComboBoxId));
            xy.add(getFieldValue(e, yAxisComboBoxId));
            values.add(xy);
        }
        LineChart<Number, Number> numberLineChart = View.addSecondChart(values, String.format("%s(%s)", listOfComboBoxes.get(0).getValue(), listOfComboBoxes.get(1).getValue()));
        mainPane.setCenter(numberLineChart);
    }

    private static double getFieldValue(State state, int fieldId) {
        double value;
        switch (fieldId) {
            case 0:
                value = state.getCoordinates().getX();
                break;
            case 1:
                value = state.getCoordinates().getY();
                break;
            case 2:
                value = state.getVelocity().getX();
                break;
            case 3:
                value = state.getVelocity().getY();
                break;
            case 4:
                value = state.getForceOut().getX();
                break;
            default:
                value = state.getT();
        }
        return value;
    }

    public static void actiondomainOfDefBtn() {
        View.addHelpAlert();
    }
}
