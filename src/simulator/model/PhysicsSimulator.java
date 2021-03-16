package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

    private final double dt;
    private final ForceLaws law;
    private double t;
    private List<Body> bs;

    public PhysicsSimulator(double dt, ForceLaws law) {

        this.dt=dt;
        this.law=law;
        t = 0.0d;
        this.bs=new ArrayList<Body>();

    }

    public void advance() {

        for (Body body : bs)
            body.resetForce();

        law.apply(bs);

        for (Body body : bs)
            body.move(dt);

        t += dt;
    }

    public void addBody(Body b) throws IllegalArgumentException {

        if (!bs.contains(b))
            bs.add(b);
        else
            throw new IllegalArgumentException();

    }

    public JSONObject getState() {

        JSONObject state = new JSONObject();
        JSONArray bodies = new JSONArray();

        state.put("time", t);

        for (Body body: bs)
            bodies.put(body.getState());

        state.put("bodies", bodies);

        return state;
    }




}
