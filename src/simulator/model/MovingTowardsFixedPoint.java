package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws {

    final double g=9.81;
    final Vector2D origen= new Vector2D();

    @Override
    public void apply(List<Body> bs) {
        Body coso = null;
        Vector2D acc;
        double f;

        for (int i = 0; i<bs.size(); i++) {
            coso = bs.get(i);
            f = g * coso.mass;
            Vector2D r = coso.pos.minus(origen);
            coso.addForce(r.direction().scale(f));
        }
    }

}
