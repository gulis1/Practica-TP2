package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class PhysicsSimulator {

    private double dt;
    private ForceLaws law;
    private double t;
    private List<Body> bs;
    private List<SimulatorObserver> observerList;


    public PhysicsSimulator(double dt, ForceLaws law) {

        this.dt = dt;
        this.law = law;
        t = 0.0d;
        this.bs = new ArrayList<Body>();
        this.observerList = new ArrayList<SimulatorObserver>();

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

        if (!bs.contains(b)) {

            bs.add(b);

            for(SimulatorObserver observer: observerList )
                observer.onBodyAdded(bs, b);
        }

        else
            throw new IllegalArgumentException();



    }

    public JSONObject getState() {

        JSONObject state = new JSONObject();
        JSONArray bodies = new JSONArray();

        state.put("time", t);

        for (Body body : bs)
            bodies.put(body.getState());

        state.put("bodies", bodies);

        return state;
    }

    public void reset() {

        t = 0;
        bs = new ArrayList<Body>();

        for(SimulatorObserver observer : observerList )
            observer.onReset(bs, t, dt, law.toString());
    }

    public void setDt(double dt) throws IllegalArgumentException {

        if (dt<0)
            throw new IllegalArgumentException();

        else {

            this.dt = dt;
            for(SimulatorObserver observer : observerList )
                observer.onDeltaTimeChanged(dt);
        }




    }

    public void setForceLaw(ForceLaws law) throws IllegalArgumentException {

        if (law == null)
            throw new IllegalArgumentException();

        else{
            this.law = law;
            for(SimulatorObserver observer : observerList )
                observer.onForceLawsChanged(law.toString());

        }

    }

    public void addObserver(SimulatorObserver o) {
        o.onRegister(bs, t, dt, law.toString());

        if(!observerList.contains(o))
             observerList.add(o);

    }

}
