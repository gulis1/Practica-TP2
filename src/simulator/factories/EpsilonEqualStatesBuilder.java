package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;
import simulator.model.Body;

import java.util.List;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {

    public EpsilonEqualStatesBuilder() {

        typeTag = "epseq";
        desc = " Epsilon Equal States";
    }

    @Override
    public StateComparator createTheInstance(JSONObject info) {
        EpsilonEqualStates b = null;

        try {
            b = new EpsilonEqualStates(info);
        }

        catch (JSONException e) {

            if (!e.getMessage().equals("Null key."))
                throw new IllegalArgumentException();

        }

        return b;
    }

    @Override
    protected JSONObject createData() {
        return super.createData().put("eps", "epsilon");
    }
}
