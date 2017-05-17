package Landing.Util;

import java.util.List;
import java.text.SimpleDateFormat;
import java.nio.file.*;
import java.io.*;
import java.util.Date;

import Landing.Model.State;

public class Logger {
    public static final String DATE_FORMAT = "yy.MM.dd HH:mm:ssZ";

    public static final String[] header = new String[] {
        "Position by X",
        "Position by Y",
        "Velocity by X",
        "Velocity by Y",
        "Acceleration by X",
        "Acceleration by Y",
        "Control force by X",
        "Control force by Y",
        "Outer force by X",
        "Outer force by Y"
    };

    public static boolean save(List<State> states) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        
        try {
            BufferedWriter file = Files.newBufferedWriter(Paths.get(formatter.format(date)));
            file.write(String.join(",", header) + "\n");

            for (State state : states) {
                file.write(Double.toString(state.getCoordinates().getX()));
                file.write(",");
                file.write(Double.toString(state.getCoordinates().getY()));
                file.write(",");

                file.write(Double.toString(state.getVelocity().getX()));
                file.write(",");
                file.write(Double.toString(state.getVelocity().getY()));
                file.write(",");

                file.write(Double.toString(state.getAcceleration().getX()));
                file.write(",");
                file.write(Double.toString(state.getAcceleration().getY()));
                file.write(",");

                file.write(Double.toString(state.getForceIn().getX()));
                file.write(",");
                file.write(Double.toString(state.getForceIn().getY()));
                file.write(",");
            
                file.write(Double.toString(state.getForceOut().getX()));
                file.write(",");
                file.write(Double.toString(state.getForceOut().getY()));
                file.write("\n");
            }

            file.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}

