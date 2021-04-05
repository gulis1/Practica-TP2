package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.MassLosingBody;

import java.util.List;

public class MassLosingBodyBuilder extends Builder<Body> {


    public MassLosingBodyBuilder() {
        typeTag = "mlb";
        desc = "MassLosingBody";

    }

    @Override
    public Body createTheInstance(JSONObject info) {
        MassLosingBody b = null;

        try {
            b = new MassLosingBody(info);
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
        JSONObject b = super.createData();

        b.put("id", "id del cuerpo");
        b.put("p", "vector de posicion");
        b.put("v", "vector de velocidad");
        b.put("m", "masa");
        b.put("freq", "frecuencia de perdida de masa");
        b.put("facto", "factor de perdida de masa");

        return b;
    }

}
