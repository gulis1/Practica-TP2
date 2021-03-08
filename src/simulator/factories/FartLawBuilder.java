package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.FartLaw;
import simulator.model.ForceLaws;

import java.util.List;

public class FartLawBuilder extends Builder<ForceLaws> {
    @Override
    public ForceLaws createInstance(JSONObject info) {
        FartLaw b = null;

        try {
            b = new FartLaw();
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
