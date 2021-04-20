package simulator.model;

import org.json.JSONObject;

import java.util.List;

public class NoForce implements ForceLaws {

    public NoForce() {
    }

    @Override
    public void apply(List<Body> bs) {
        // UwU
    }

    @Override
    public String toString(){
        return "No Force";
    }
}
