package simulator.model;

import org.json.JSONObject;

import java.util.List;

public class NoForce implements ForceLaws {

    public NoForce() {
    }

    @Override
    public void apply(List<Body> bs) {
        // UwU
        System.out.printf("nothing happend ...:(");
    }
}
