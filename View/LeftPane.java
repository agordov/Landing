package Landing.View;

import Landing.Control.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class LeftPane {
    public static BorderPane addParametersPane(BorderPane mainPane,
                                               BorderPane borderPane,
                                               List<TextField> paramFields,
                                               List<TextField> valuesFields) {
        BorderPane leftPane = new BorderPane();

        HBox buttonBox = addBtnBox(mainPane, borderPane, paramFields, valuesFields);
        leftPane.setTop(buttonBox);

        VBox vBox = addVBox(View.PARAMETERS, paramFields);
        leftPane.setCenter(vBox);

        vBox.getStyleClass().add("parametersPane");

        leftPane.setBottom(addParametersBtnPane(mainPane, paramFields, valuesFields));
        return leftPane;
    }
    public static BorderPane addValuesPane(BorderPane mainPane,
                                           BorderPane borderPane,
                                           List<TextField> paramFields,
                                           List<TextField> valuesFields) {
        BorderPane leftPane = new BorderPane();

        HBox buttonBox = addBtnBox(mainPane, borderPane, paramFields, valuesFields);
        leftPane.setTop(buttonBox);

        VBox vBox = addVBox(View.VALUES, valuesFields);
        leftPane.setCenter(vBox);

        vBox.getStyleClass().add("valuesPane");

        for (TextField field : valuesFields) {
            field.setEditable(false);
        }

        return leftPane;
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

        borderPane.setLeft(View.addLabelPane(labelText));
        borderPane.setRight(field);
        borderPane.setMaxWidth(320);
        borderPane.setMinWidth(320);

        return borderPane;
    }
    private static BorderPane addParametersBtnPane(BorderPane mainPane,
                                                   List<TextField> paramFields,
                                                   List<TextField> valuesFields) {
        BorderPane btnPane = new BorderPane();
        btnPane.getStyleClass().add("btnPane");

        Button calculateBtn = new Button("Calculate");
        calculateBtn.setOnAction(event -> Landing.Control.Controller.actionCalculateButton(mainPane, paramFields, valuesFields));

        Button randomBtn = new Button("Random parameters");
        randomBtn.setOnAction(event -> Landing.Control.Controller.actionRandomButton(paramFields));

        Button domainOfDefBtn = new Button("?");
        domainOfDefBtn.setOnAction(event -> Landing.Control.Controller.actiondomainOfDefBtn());
        btnPane.setCenter(domainOfDefBtn);
        btnPane.setLeft(randomBtn);
        btnPane.setRight(calculateBtn);
        return btnPane;
    }
}
