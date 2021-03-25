package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator {

    public boolean equal(JSONObject s1, JSONObject s2) {
        boolean iguales = true;
        int i = 0;

        JSONArray l1, l2;
        l1 = s1.getJSONArray("bodies");
        l2 = s2.getJSONArray("bodies");

        if (s1.getDouble("time") != s2.getDouble("time"))
            iguales = false;


        else if (l1.length() != l2.length())
            iguales = false;


        while (i < l1.length() && iguales) {
            if (l1.getJSONObject(i).getDouble("m") != l2.getJSONObject(i).getDouble("m") || !l1.getJSONObject(i).getString("id").equals(l2.getJSONObject(i).getString("id")))
                iguales = false;

            i++;
        }


        return iguales;
    }

}
