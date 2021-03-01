package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator {

   public boolean equal(JSONObject s1, JSONObject s2){
        boolean iguales=true;
        int i=0;

       JSONArray l1, l2;
       l1 = s1.getJSONArray("bodies");
       l2 = s2.getJSONArray("bodies");

        if(s1.get("time")!= s2.get("time")){
            iguales=false;
        }

        else if(l1.length() !=  l2.length()){
            iguales=false;

        }

        while (i<l1.length() && iguales){
            if(l1.getJSONObject(i).get("mass")!=l2.getJSONObject(i).get("mass") || l1.getJSONObject(i).get("id")!=l2.getJSONObject(i).get("id"))
                iguales=false;

            i++;
        }


    return iguales;
   }

}
