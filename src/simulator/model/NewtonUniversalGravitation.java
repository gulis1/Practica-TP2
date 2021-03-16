package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws {

    private double G;

    public NewtonUniversalGravitation(double G) {
        this.G = G;
    }

    public NewtonUniversalGravitation(JSONObject data) {
        if (data.has("G"))
            G = data.getDouble("G");
        else
            G = 6.67E-5;
    }


    @Override
    public void apply(List<Body> bs) {

        for (int i = 0; i<bs.size(); i++) {
            for (int j = i + 1; j<bs.size(); j++) {

                Vector2D r = bs.get(i).pos.minus(bs.get(j).pos);

                double f =( G * bs.get(i).mass * bs.get(j).mass) / Math.pow(r.magnitude(), 2);

                Vector2D F = r.direction().scale(f);

                bs.get(i).addForce(F.scale(-1.0d));
                bs.get(j).addForce(F);


            }
        }
    }

}
