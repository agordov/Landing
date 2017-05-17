package Landing.Model;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {

    private List<State> trajectory = new ArrayList<>();

    public Trajectory(PIDController startPid, MoveParams startParams) {
        generateTrajectory(startPid, startParams);
    }

    private void generateTrajectory(PIDController startPid, MoveParams startParams) {
        double radiusOfAtmosphere = startParams.getAtmosphereRadius();
        double g = startParams.getG();
        double airK = startParams.getAirK();
        double dt = startParams.getdT();
        double planetRadius = startParams.getPlanetRadius();
        trajectory.add(new State(new Tuple<>(startParams.getX(), startParams.getY()), new Tuple<>(startParams.getVx(),
                startParams.getVy()), new Tuple<>(0d, 0d), new Tuple<>(0d, 0d),new Tuple<>(0d, 0d), startParams.getProbeMass(), dt));

        double distance = Math.sqrt(Math.pow(startParams.getY(), 2) + Math.pow(startParams.getX(), 2));
        double fOutX;
        double fOutY;

        while(distance > planetRadius && distance < 2e8) {

            State state = new State(trajectory.get(trajectory.size() - 1));
            distance = Math.sqrt(Math.pow(state.getCoordinates().getY(), 2) + Math.pow(state.getCoordinates().getX(), 2));

            fOutX = g * state.getM() * (-Math.signum(state.getCoordinates().getX())) * Math.pow(planetRadius, 2)
                    / Math.pow(distance, 2) * Math.abs(getCos(state.getCoordinates().getX(), state.getCoordinates().getY()));
            fOutY = g * state.getM() * (-Math.signum(state.getCoordinates().getY())) * Math.pow(planetRadius, 2)
                    / Math.pow(distance, 2) * Math.abs(getSin(state.getCoordinates().getX(), state.getCoordinates().getY()));

            if(distance < radiusOfAtmosphere) {
                fOutX -= Math.signum(state.getVelocity().getX()) * airK * state.getVelocity().getX() * state.getVelocity().getX();
                fOutY -= Math.signum(state.getVelocity().getY()) * airK * state.getVelocity().getY() * state.getVelocity().getY();
            }

            state.setForceIn(startPid.countForces(-state.getCoordinates().getX(), -state.getCoordinates().getY()));
            state.setForceOut(new Tuple<>(fOutX, fOutY));

            double aX = (state.getForceIn().getX() + state.getForceOut().getX()) / state.getM();
            double aY = (state.getForceIn().getY() + state.getForceOut().getY()) / state.getM();;
            state.setAcceleration(new Tuple<>(aX, aY));

            double vX = state.getVelocity().getX() + state.getAcceleration().getX() * dt;
            double vY = state.getVelocity().getY() + state.getAcceleration().getY() * dt;
            vX = Math.min(vX, MoveParams.getMaxVx());
            vY = Math.min(vY, MoveParams.getMaxVy());
            state.setVelocity(new Tuple<>(vX, vY));
            double x = state.getCoordinates().getX() + state.getVelocity().getX() * dt;
            double y = state.getCoordinates().getY() + state.getVelocity().getY() * dt;

            state.setCoordinates(new Tuple<>(x, y));
            state.setT(state.getT() + dt);

            trajectory.add(state);
        }
    }

    private double getCos(double x, double y) {
        return x / Math.sqrt(x * x + y * y);
    }

    private double getSin(double x, double y) {
        return y / Math.sqrt(x * x + y * y);
    }

    public List<State> getTrajectory() {
        return trajectory;
    }
}
