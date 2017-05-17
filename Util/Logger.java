package Landing.Util;

import Landing.Model.State;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Logger {

    private static final String DATE_FORMAT = "yy.MM.dd HH-mm-ss";

    private static final String[] header = new String[] {
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
                file.write(String.format("%.3g", state.getCoordinates().getX()));
                file.write(",");
                file.write(String.format("%.3g", state.getCoordinates().getY()));
                file.write(",");

                file.write(String.format("%.3g", state.getVelocity().getX()));
                file.write(",");
                file.write(String.format("%.3g", state.getVelocity().getY()));
                file.write(",");

                file.write(String.format("%.3g", state.getAcceleration().getX()));
                file.write(",");
                file.write(String.format("%.3g", state.getAcceleration().getY()));
                file.write(",");

                file.write(String.format("%.3g", state.getForceIn().getX()));
                file.write(",");
                file.write(String.format("%.3g", state.getForceIn().getY()));
                file.write(",");
            
                file.write(String.format("%.3g", state.getForceOut().getX()));
                file.write(",");
                file.write(String.format("%.3g", state.getForceOut().getY()));
                file.write("\n");
            }
            file.flush();
            file.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}

