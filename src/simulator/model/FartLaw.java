package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;
import java.util.Random;

public class FartLaw implements ForceLaws {

    public FartLaw() {

    }

    @Override
    public void apply(List<Body> bs) {
        fart(bs);
    }

    // El body se tira un pedo en una direccion aleatoria, el cual produce una fuerza en sentido contrario.
    public void fart(List<Body> bs) {
        System.out.println("Pffffffffffffffffffffffff");
        Random rng = new Random();
        Body body = null;

        for (Body b : bs) {
            body = b;
            Vector2D f = new Vector2D(-1.0d + 2.0d * rng.nextDouble(), -1.0d + 2.0d * rng.nextDouble());
            b.addForce(f.scale(body.mass));
        }
    }
}
