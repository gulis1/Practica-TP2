package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.FartLaw;
import simulator.model.ForceLaws;

import java.util.List;

public class FartLawBuilder extends Builder<ForceLaws> {

    public FartLawBuilder() {

        typeTag = "fl";
        desc = "Fart Law";
    }

    @Override
    public ForceLaws createTheInstance(JSONObject info) {
        FartLaw b = null;

        try {
            b = new FartLaw();
        } catch (JSONException e) {

            if (e.getMessage().equals("Null key."))
                b = null;
            else
                throw new IllegalArgumentException("Sad");

        }

        return b;
    }

}
