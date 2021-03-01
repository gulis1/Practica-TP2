package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;

public class Body {

    protected String id;
    protected Vector2D vel, force, pos,ace;
    protected double mass;

    public Body(String id, Vector2D vel, Vector2D pos, double mass) {
        this.id = id;
        this.vel = vel;
        this.force = new Vector2D();
        this.pos = pos;
        this.mass = mass;
    }

    //Constructor que he hecho por si hiciera falta para leer un body del json
    /*public Body(JSONObject obj) {
        this.id = obj.getString("id");
        this.mass = obj.getDouble("mass");
        this.vel = new Vector2D(obj.getJSONArray("v").getDouble(0), obj.getJSONArray("v").getDouble(1));
        this.pos = new Vector2D(obj.getJSONArray("p").getDouble(0), obj.getJSONArray("p").getDouble(1));
        this.force = new Vector2D(obj.getJSONArray("f").getDouble(0), obj.getJSONArray("f").getDouble(1));
    }*/

    public void move(double t) {
        Vector2D ace;

        if (mass == 0)
            ace = new Vector2D();

        else
            ace = force.scale(1.0d/mass);

        pos = pos.plus(vel.scale(t).plus(ace.scale(0.5d*t*t)));
        vel = vel.plus(ace.scale(t));
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

    public  void addForce(Vector2D f){

        this.force=this.force.plus(f);

    }

    public JSONObject getState() {
        return new JSONObject().put("id", id).put("m", mass).put("p", pos.asJSONArray()).put("v",vel.asJSONArray()).put("f",force.asJSONArray());

    }

    /* { "id": id, "m": mass, "p": pos, "v": vel, "f": force }*/

    public String toString() {
        return getState().toString();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean equalsEpsilon(Body b2, double eps) {

        return Math.abs(mass - b2.getMass()) <= eps &&
                pos.distanceTo(b2.getPos()) <= eps &&
                vel.distanceTo(b2.getVel()) <= eps &&
                force.distanceTo(b2.getForce()) <= eps;

    }
}
