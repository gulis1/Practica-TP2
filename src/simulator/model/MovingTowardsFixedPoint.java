package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    private double g;
    private Vector2D origen;


    public MovingTowardsFixedPoint(double g, Vector2D origen) {

        this.g = g;
        this.origen = new Vector2D(origen);

    }

    public MovingTowardsFixedPoint(JSONObject data) {
        if (data.has("g"))
            g = data.getDouble("g");
        else
            g = 6.67E-11;

        origen = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
    }

    @Override
    public void apply(List<Body> bs) {
        Body coso = null;
        double f;

        for (Body b : bs) {
            coso = b;
            f = g * coso.mass;
            Vector2D r = coso.pos.minus(origen);
            coso.addForce(r.direction().scale(f));
        }
    }

}
