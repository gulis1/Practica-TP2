package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class MassLosingBody extends Body {

    protected double lossFactor, lossFrequency;
    protected double c;

    public MassLosingBody(String id, Vector2D vel, Vector2D pos, double mass, double lossFactor, double lossFrequency) {
        super(id, vel, pos, mass);
        this.lossFactor = lossFactor;
        this.lossFrequency = lossFrequency;
        this.c = 0.0d;

    }

    public MassLosingBody(JSONObject data) {
        super(data);
        this.lossFrequency = data.getDouble("freq");
        this.lossFactor = data.getDouble("factor");

    }

    @Override
    public void move(double t) {
        super.move(t);
        c += t;
        if (c >= lossFrequency) {
            mass *= (1.0d - lossFactor);
            c = 0.0d;
        }




    }

}
