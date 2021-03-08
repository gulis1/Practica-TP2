package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

import java.util.List;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{
    @Override

    public ForceLaws createInstance(JSONObject info) {
        NewtonUniversalGravitation b = null;

        try {
            b = new NewtonUniversalGravitation(info);
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
