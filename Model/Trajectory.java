package Landing.Model;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {

    private List<State> trajectory = new ArrayList<>();

    public Trajectory(PIDController startPid, MoveParams startParams) {
        generateTrajectory(startPid, startParams);
    }


    private void generateTrajectory(PIDController startPid, MoveParams startParams) { // стоит ли мне dt передавать отдельно, или занести в статус?
        double dr = Math.sqrt(Math.pow(startParams.getY() - startParams.getPlanetRadius(), 2) + Math.pow(startParams.getX(), 2)); // типа приземляюсь в точку (0, R)
        trajectory.add(new State(new Tuple<>(startParams.getX(), startParams.getY()), new Tuple<>(startParams.getVx(), startParams.getVy()), new Tuple<>(0d, 0d), new Tuple<>(0d, 0d),new Tuple<>(0d, 0d), startParams.getZondMass()));

        double radiusOfAtmosphere = startParams.getAtmosphereRadius();
        double g = startParams.getG();
        double airK = startParams.getAirK();
        double dt = startParams.getdT();


        while(dr > 0 && dr < 1e15) {
            State state = new State(trajectory.get(trajectory.size() - 1));
            state.setForceIn(startPid.countForces(state.getCoordinates().getX(), Math.abs(state.getCoordinates().getY() - startParams.getPlanetRadius())));
            double distance = Math.sqrt(Math.pow(startParams.getY(), 2) + Math.pow(startParams.getX(), 2));
            double fOutX;
            double fOutY;


            if(distance < radiusOfAtmosphere) {
                fOutX = g * state.getM() - airK * state.getVelocity().getX() * state.getVelocity().getX();
                fOutY = g * state.getM() - airK * state.getVelocity().getY() * state.getVelocity().getY();
            }
            else {
                fOutX = g * state.getM();
                fOutY = g * state.getM();
            }

            double aX = (state.getForceIn().getX() + state.getForceOut().getX()) / state.getM();
            double aY = (state.getForceIn().getY() + state.getForceOut().getY()) / state.getM();;
            double vX = state.getVelocity().getX() + state.getAcceleration().getX() * dt;
            double vY = state.getVelocity().getY() + state.getAcceleration().getY() * dt;;
            double x = state.getCoordinates().getX() + state.getVelocity().getX() * dt / 2;
            double y = state.getCoordinates().getY() + state.getVelocity().getY() * dt / 2;

            state.setCoordinates(new Tuple<>(x, y));
            state.setVelocity(new Tuple<>(vX, vY));
            state.setAcceleration(new Tuple<>(aX, aY));
            state.setForceOut(new Tuple<>(fOutX, fOutY));
            state.setT(state.getT() + dt);

            dr = Math.sqrt(Math.pow(state.getCoordinates().getY() - startParams.getPlanetRadius(), 2) + Math.pow(state.getCoordinates().getX(), 2)); // типа приземляюсь в точку (0, R)
            trajectory.add(state);

        }
    }

    public List<State> getTrajectory() {
        return trajectory;
    }
}
