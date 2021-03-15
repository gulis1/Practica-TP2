package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;

import java.util.List;

public class BasicBodyBuilder extends Builder<Body> {

    public BasicBodyBuilder() {
        typeTag = "basic";
        desc = "Basic Body";
    }




    @Override
    public Body createTheInstance(JSONObject data) {
        Body b = null;

        try {
            b = new Body(data);
        }

        catch (JSONException e) {

            if (e.getMessage().equals("Null key."))
                b = null;
            else
                throw new IllegalArgumentException();

        }

        return b;
    }


    @Override
    protected JSONObject createData() {

      return new JSONObject().put("id","bl").put("p","vector de posicion").put("v","vector de velocidad").put("m","double de masa");

    }
}
