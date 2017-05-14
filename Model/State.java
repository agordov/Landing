package Landing.Model;

/*
*************************

 */

public class State {

    private Tuple<Double, Double> coordinates;
    private Tuple<Double, Double> velocity;
    private Tuple<Double, Double> acceleration;
    private Tuple<Double, Double> forceOut;
    private Tuple<Double, Double> forceIn;
    double t;
    double m; //если мы всё же запилим с изменяющейся массой


    public State(State other) {
        this(other.getCoordinates(), other.getVelocity(), other.getAcceleration(), other.getForceOut(), other.getForceIn());
    }

    public State(Tuple<Double, Double> coordinates, Tuple<Double, Double> velocity, Tuple<Double, Double> acceleration, Tuple<Double, Double> forceOut, Tuple<Double, Double> forceIn) {
        this.coordinates = coordinates;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.forceOut = forceOut;
        this.forceIn = forceIn;
    }

    public void setCoordinates(Tuple<Double, Double> coordinates) {
        this.coordinates = coordinates;
    }

    public void setVelocity(Tuple<Double, Double> velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Tuple<Double, Double> acceleration) {
        this.acceleration = acceleration;
    }

    public void setForceOut(Tuple<Double, Double> forceOut) {
        this.forceOut = forceOut;
    }

    public void setForceIn(Tuple<Double, Double> forceIn) {
        this.forceIn = forceIn;
    }

    public Tuple<Double, Double> getCoordinates() {
        return coordinates;
    }

    public Tuple<Double, Double> getVelocity() {

        return velocity;
    }

    public Tuple<Double, Double> getAcceleration() {
        return acceleration;
    }

    public Tuple<Double, Double> getForceIn() {
        return forceIn;
    }

    public Tuple<Double, Double> getForceOut() {

        return forceOut;
    }

    public double getM() {
        return m;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getT() {
        return t;
    }
}
