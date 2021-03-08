package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;

import java.util.List;

public class BasicBodyBuilder extends Builder<Body> {

    @Override
    public Body createInstance(JSONObject info) throws IllegalArgumentException {
        Body b = null;

        try {
            b = new Body(info);
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
