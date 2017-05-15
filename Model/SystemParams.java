package Landing.Model;

public class SystemParams {

    private double planetMass;
    private double dT;
    private double planetRadius;
    private double atmosphereRadius;
    private double airK;

    public SystemParams(MoveParams moveParams) {
        this.planetMass = moveParams.getPlanetMass();
        this.dT = moveParams.getdT();
        this.atmosphereRadius = moveParams.getAtmosphereRadius();
        this.planetRadius = moveParams.getPlanetRadius();
        this.airK = moveParams.getAirK();
    }

    public SystemParams(double planetMass, double planetRadius, double atmosphereRadius, double dT, double airK) {
        this.planetMass = planetMass;
        this.dT = dT;
        this.atmosphereRadius = atmosphereRadius;
        this.planetRadius = planetRadius;
        this.airK = airK;
    }

}
