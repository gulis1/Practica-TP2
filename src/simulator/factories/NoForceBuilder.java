package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

import java.util.List;

public class NoForceBuilder extends Builder<ForceLaws> {

    public NoForceBuilder() {

        typeTag = "nf";
        desc = "No Force Law";
    }


    @Override
    public ForceLaws createTheInstance(JSONObject info) {
        NoForce b = null;

        try {
            b = new NoForce();
        } catch (JSONException e) {

            if (!e.getMessage().equals("Null key."))
                throw new IllegalArgumentException();

        }

        return b;
    }

}
