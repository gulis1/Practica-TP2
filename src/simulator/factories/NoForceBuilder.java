package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

import java.util.List;

public class NoForceBuilder extends Builder<ForceLaws> {
    @Override
    public ForceLaws createInstance(JSONObject info) {
        NoForce b = null;

        try {
            b = new NoForce();
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
