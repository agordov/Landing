package Landing.Control;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import Landing.View.View;

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
    public static void actionCalculateButton(){
        try{
            //LineChart<Number, Number> numberLineChart = View.addChart(values);
            //borderPane.setCenter(numberLineChart);
            //listOfConstantFields.get(0).setText(String.valueOf(calcTrajectory.calculatePathLength()));
            //listOfConstantFields.get(1).setText(String.valueOf(calcTrajectory.fallingTime()));
            //listOfConstantFields.get(2).setText(String.valueOf(calcTrajectory.calculateMaxHeight()));

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
    public static void actionParametersButton(BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        BorderPane parametersPane = View.addParametersPane(borderPane, listOfParamFields, listOfValuesFields);
        borderPane.setLeft(parametersPane);

    }
    public static void actionValuesButton(BorderPane borderPane, List<TextField> listOfParamFields, List<TextField> listOfValuesFields){
        BorderPane valuesPane = View.addValuesPane(borderPane, listOfParamFields, listOfValuesFields);
        borderPane.setLeft(valuesPane);
    }
}
