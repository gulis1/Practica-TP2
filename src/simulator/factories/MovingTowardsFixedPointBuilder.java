package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

import java.util.List;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

    public MovingTowardsFixedPointBuilder(){

        typeTag="mtcp";
        desc="Moving Towards Fixed Point";
    }

    @Override
    public ForceLaws createTheInstance(JSONObject info) {
        MovingTowardsFixedPoint b = null;

        try {
            b = new MovingTowardsFixedPoint(info);
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
    protected JSONObject createData() {

        return new JSONObject().put("g","Constante de gravedad").put("c","origen");

    }


}
