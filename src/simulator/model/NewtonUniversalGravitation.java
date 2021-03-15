package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws {

    private double G;

    public NewtonUniversalGravitation(double G) {
        G = G;
    }

    public NewtonUniversalGravitation(JSONObject object) {
        G = object.getJSONObject("data").getDouble("G");
    }


    @Override
    public void apply(List<Body> bs) {

        for (int i = 0; i<bs.size(); i++) {
            for (int j = i + 1; i<bs.size(); i++) {

                Vector2D r = bs.get(i).pos.minus(bs.get(j).pos);

                double f =( G * bs.get(i).mass * bs.get(j).mass) / Math.pow(r.magnitude(), 2);

                Vector2D F = r.direction().scale(f);

                bs.get(i).addForce(F);
                bs.get(j).addForce(F.scale(-1.0d));


            }
        }
    }

}
