package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class Body {

    protected String id;
    protected Vector2D vel, force, pos;
    protected double mass;

    public Body(String id, Vector2D vel, Vector2D pos, double mass) {
        this.id = id;
        this.vel = vel;
        this.force = new Vector2D();
        this.pos = pos;
        this.mass = mass;
    }

    public void move(double t) {
        Vector2D a;

        if (mass == 0)
            a = new Vector2D();

        else
            a = force.scale(1.0d/mass);

        pos = pos.plus(vel.scale(t).plus(a.scale(0.5d*t*t)));
        vel = vel.plus(a.scale(t));
    }

    public void resetForce() {
        force = new Vector2D();
    }

    public String getId() {
        return id;
    }

    public Vector2D getVel() {
        return vel;
    }

    public Vector2D getForce() {
        return force;
    }

    public Vector2D getPos() {
        return pos;
    }

    public double getMass() {
        return mass;
    }

    public JSONObject getState() {
        return new JSONObject().put("id", id).put("m", mass).put("p", pos.asJSONArray()).put("v",vel.asJSONArray()).put("f",force.asJSONArray());

    }

    /* { "id": id, "m": mass, "p": pos, "v": vel, "f": force }*/

    public String toString() {
        return getState().toString();
    }
}
