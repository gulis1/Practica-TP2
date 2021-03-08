package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;
import simulator.model.Body;

import java.util.List;

public class MassEqualStatesBuilder extends Builder<StateComparator> {
    @Override
    public StateComparator createInstance(JSONObject info) {
        MassEqualStates b = null;

        try {
            b = new MassEqualStates();
        }

        catch (JSONException e) {

            if (e.getMessage() == "Null key.")
                b = null;
            else
                throw new IllegalArgumentException("Sad");

        }

        return b;
    }

    @Override
    public List<JSONObject> getInfo() {
        return null;
    }
}
