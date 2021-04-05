package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

import java.util.List;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {


    public NewtonUniversalGravitationBuilder() {

        typeTag = "nlug";
        desc = "Newton Universal Gravitation Law";


    }

    @Override
    public ForceLaws createTheInstance(JSONObject info) {
        NewtonUniversalGravitation b = null;

        try {
            b = new NewtonUniversalGravitation(info);
        } catch (JSONException e) {

            if (e.getMessage().equals("Null key."))
                b = null;
            else
                throw new IllegalArgumentException("Sad");

        }

        return b;
    }

    @Override
    protected JSONObject createData() {

        return new JSONObject().put("G", "Constate de gravitacion");

    }


}
