package Landing.Model;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {

    private final double G = 6.67 * 1e-11;
    //надо подумать над названиями переменных, а то жопа


    public Trajectory(double x, double y, double vX, double vY, double aX, double aY, double fOutX, double fOutY, double t, double dt, double massOfPlanet, double radiusOfPlanet, double airK) {
        //State state = new State(x, y, vX, vY, aX, aY, fOutX, fOutY, 0, 0, t);
    }

    private List<State> trajectory = new ArrayList<>();

    //а тут я захерачу конструктор из которого буду вызывать generateTrajectory

    private void generateTrajectory(State startState, double dt, double massOfPlanet, double radiusOfPlanet, double airK) { // стоит ли мне dt передавать отдельно, или занести в статус?
        double dr = Math.sqrt((startState.getCoordinates().getRight() - radiusOfPlanet) * (startState.getCoordinates().getRight() - radiusOfPlanet) + startState.getCoordinates().getLeft() * startState.getCoordinates().getLeft()); // типа приземляюсь в точку (R, 0)
        // чё делать с 1-ым силами тяги? сразу у Лёхи брать или 0 сделать изначально
        trajectory.add(startState);
        while(dr > 0) {
            State state = new State(trajectory.get(trajectory.size() - 1));

            double fInX = 1;// беру у Лёхи
            double fInY = 1;//
            double fOutX = G * massOfPlanet * state.getM() / (state.getCoordinates().getLeft() * state.getCoordinates().getLeft() + state.getCoordinates().getRight() * state.getCoordinates().getRight()) - airK * state.getCoordinates().getRight() * state.getCoordinates().getRight();
            double fOutY = G * massOfPlanet * state.getM() / (state.getCoordinates().getLeft() * state.getCoordinates().getLeft() + state.getCoordinates().getRight() * state.getCoordinates().getRight()) - airK * state.getCoordinates().getLeft() * state.getCoordinates().getLeft();
            double aX = (state.getForceIn().getRight() + state.getForceOut().getRight()) / state.getM();
            double aY = (state.getForceIn().getLeft() + state.getForceOut().getLeft()) / state.getM();;
            double vX = state.getVelocity().getRight() + state.getAcceleration().getRight() * dt;
            double vY = state.getVelocity().getLeft() + state.getAcceleration().getLeft() * dt;;
            double x = state.getCoordinates().getRight() + state.getVelocity().getRight() * dt / 2;
            double y = state.getCoordinates().getLeft() + state.getVelocity().getLeft() * dt / 2;

            state.setCoordinates(new Tuple<Double, Double>(x, y));
            state.setVelocity(new Tuple<Double, Double>(vX, vY));
            state.setAcceleration(new Tuple<Double, Double>(aX, aY));
            state.setForceIn(new Tuple<Double, Double>(fInX, fInY));
            state.setForceOut(new Tuple<Double, Double>(fOutX, fOutY));
            state.setT(state.getT() + dt);

            dr = Math.sqrt((state.getCoordinates().getRight() - radiusOfPlanet) * (state.getCoordinates().getRight() - radiusOfPlanet) + state.getCoordinates().getLeft() * state.getCoordinates().getLeft());
            trajectory.add(state);

        }
    }


    //а тут я захерачу методы чтобы возвращать в контролер нужные данные в нужном формате
}
