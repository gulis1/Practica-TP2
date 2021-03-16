package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.Body;

public class EpsilonEqualStates implements StateComparator {

    private final double eps;

    public EpsilonEqualStates(double eps) {
        this.eps = eps;
    }

    public EpsilonEqualStates(JSONObject data) {
        if(data.has("eps")){
            this.eps = data.getDouble("eps");
        }
        else
           this.eps=0.0d;

    }

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        boolean iguales = true;

        JSONArray l1, l2;
        l1 = s1.getJSONArray("bodies");
        l2 = s2.getJSONArray("bodies");

        if (s1.get("time") != s2.get("time"))
            iguales = false;

        else if (l1.length() != l2.length())
            iguales = false;

        else {
            int i = 0;
            Body aux1, aux2;
            while (i<s1.length() && iguales) {

                // No estoy seguro de que esto se pueda hacer. Si no funciona, usar el constructor de Body(JSONObject) que esta comentado.
                aux1 = new Body(l1.getJSONObject(i));
                aux2 = new Body(l2.getJSONObject(i));

                if (!aux1.equals(aux2) || !aux1.equalsEpsilon(aux2, eps))
                    iguales = false;

                i++;
            }

        }

        return iguales;
    }
}
