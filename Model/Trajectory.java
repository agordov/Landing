package Landing.Model;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {

    private List<State> trajectory = new ArrayList<>();

    public Trajectory(State startState, PIDController startPid, MoveParams startParams) {
        State state = new State(startState);
        generateTrajectory(state, startPid, startParams.getdT(), startParams.getPlanetRadius(), startParams.getAtmosphereRadius(), startParams.getAirK(), startParams.getG());
    }


    private void generateTrajectory(State startState, PIDController startPid, double dt, double radiusOfPlanet, double radiusOfAtmosphere, double airK, double g) { // стоит ли мне dt передавать отдельно, или занести в статус?
        double dr = Math.sqrt(Math.pow(startState.getCoordinates().getY() - radiusOfPlanet, 2) + Math.pow(startState.getCoordinates().getX(), 2)); // типа приземляюсь в точку (0, R)
        trajectory.add(startState);
        while(dr > 0) {
            State state = new State(trajectory.get(trajectory.size() - 1));
            state.setForceIn(startPid.countForces(state.getCoordinates().getX(), Math.abs(state.getCoordinates().getY() - radiusOfPlanet)));
            double distance = Math.sqrt(Math.pow(startState.getCoordinates().getY(), 2) + Math.pow(startState.getCoordinates().getX(), 2));
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

            dr = Math.sqrt(Math.pow(startState.getCoordinates().getY() - radiusOfPlanet, 2) + Math.pow(startState.getCoordinates().getX(), 2)); // типа приземляюсь в точку (0, R)
            trajectory.add(state);

        }
    }

}
