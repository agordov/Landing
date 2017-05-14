package Landing.Model;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {

    private static final double G = 6.67 * 1e-11;
    //надо подумать над названиями переменных, а то жопа

    public Trajectory(double x, double y, double vX, double vY, double aX, double aY, double fOutX, double fOutY, double t, double dt, double massOfPlanet, double radiusOfPlanet, double airK) {
        //State state = new State(x, y, vX, vY, aX, aY, fOutX, fOutY, 0, 0, t);
    }

    private List<State> trajectory = new ArrayList<>();

    //а тут я захерачу конструктор из которого буду вызывать generateTrajectory

    private void generateTrajectory(State startState, double dt, double massOfPlanet, double radiusOfPlanet, double airK) { // стоит ли мне dt передавать отдельно, или занести в статус?
        double dr = Math.sqrt((startState.getCoordinates().getY() - radiusOfPlanet) * (startState.getCoordinates().getY() - radiusOfPlanet) + startState.getCoordinates().getX() * startState.getCoordinates().getX()); // типа приземляюсь в точку (R, 0)
        // чё делать с 1-ым силами тяги? сразу у Лёхи брать или 0 сделать изначально
        trajectory.add(startState);
        while(dr > 0) {
            State state = new State(trajectory.get(trajectory.size() - 1));

            double fInX = 1;// беру у Лёхи
            double fInY = 1;//
            double fOutX = G * massOfPlanet * state.getM() / (state.getCoordinates().getX() * state.getCoordinates().getX() + state.getCoordinates().getY() * state.getCoordinates().getY()) - airK * state.getCoordinates().getY() * state.getCoordinates().getY();
            double fOutY = G * massOfPlanet * state.getM() / (state.getCoordinates().getX() * state.getCoordinates().getX() + state.getCoordinates().getY() * state.getCoordinates().getY()) - airK * state.getCoordinates().getX() * state.getCoordinates().getX();
            double aX = (state.getForceIn().getY() + state.getForceOut().getY()) / state.getM();
            double aY = (state.getForceIn().getX() + state.getForceOut().getX()) / state.getM();;
            double vX = state.getVelocity().getY() + state.getAcceleration().getY() * dt;
            double vY = state.getVelocity().getX() + state.getAcceleration().getX() * dt;;
            double x = state.getCoordinates().getY() + state.getVelocity().getY() * dt / 2;
            double y = state.getCoordinates().getX() + state.getVelocity().getX() * dt / 2;

            state.setCoordinates(new Tuple<>(x, y));
            state.setVelocity(new Tuple<>(vX, vY));
            state.setAcceleration(new Tuple<>(aX, aY));
            state.setForceIn(new Tuple<>(fInX, fInY));
            state.setForceOut(new Tuple<>(fOutX, fOutY));
            state.setT(state.getT() + dt);

            dr = Math.sqrt((state.getCoordinates().getY() - radiusOfPlanet) * (state.getCoordinates().getY() - radiusOfPlanet) + state.getCoordinates().getX() * state.getCoordinates().getX());
            trajectory.add(state);

        }
    }


    //а тут я захерачу методы чтобы возвращать в контролер нужные данные в нужном формате
}
