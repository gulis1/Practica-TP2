package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    private double g;
    private Vector2D origen;


    public MovingTowardsFixedPoint(double g, Vector2D origen) {

        g = g;
        origen = new Vector2D(origen);

    }

    public MovingTowardsFixedPoint(JSONObject object) {
        if (object.has("g"))
            g = object.getJSONObject("data").getDouble("g");
        else
            g = 6.67E-11;

        origen = new Vector2D(object.getJSONObject("data").getJSONArray("c").getDouble(0), object.getJSONObject("data").getJSONArray("c").getDouble(1));
    }

    @Override
    public void apply(List<Body> bs) {
        Body coso = null;
        double f;

        for (int i = 0; i<bs.size(); i++) {
            coso = bs.get(i);
            f = g * coso.mass;
            Vector2D r = coso.pos.minus(origen);
            coso.addForce(r.direction().scale(f));
        }
    }

}
