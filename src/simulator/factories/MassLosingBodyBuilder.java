package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.MassLosingBody;

import java.util.List;

public class MassLosingBodyBuilder extends Builder<Body>{

    @Override
    public Body createInstance(JSONObject info) {
        MassLosingBody b = null;

        try {
            b = new MassLosingBody(info);
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
