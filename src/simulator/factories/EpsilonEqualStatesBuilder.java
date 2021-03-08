package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;
import simulator.model.Body;

import java.util.List;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{
    @Override
    public StateComparator createInstance(JSONObject info) {
        EpsilonEqualStates b = null;

        try {
            b = new EpsilonEqualStates(info);
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
