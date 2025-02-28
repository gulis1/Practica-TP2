package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

import java.util.List;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

    public MovingTowardsFixedPointBuilder() {

        typeTag = "mtfp";
        desc = "Moving Towards Fixed Point";
    }

    @Override
    public ForceLaws createTheInstance(JSONObject info) {
        MovingTowardsFixedPoint b = null;

        try {
            b = new MovingTowardsFixedPoint(info);
        }

        catch (JSONException e) {

            if (!e.getMessage().equals("Null key."))
                throw new IllegalArgumentException();
        }

        return b;
    }


    @Override
    protected JSONObject createData() {

        return new JSONObject().put("g", "Constante de gravedad").put("c", "origen");

    }


}
