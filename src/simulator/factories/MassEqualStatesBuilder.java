package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;
import simulator.model.Body;

import java.util.List;

public class MassEqualStatesBuilder extends Builder<StateComparator> {

    public MassEqualStatesBuilder() {

        typeTag = "masseq";
        desc = "Mass Equal States";

    }

    @Override
    public StateComparator createTheInstance(JSONObject info) {
        MassEqualStates b = null;

        try {
            b = new MassEqualStates();
        } catch (JSONException e) {

            if (e.getMessage().equals("Null key."))
                b = null;
            else
                throw new IllegalArgumentException("Sad");

        }

        return b;
    }


}
