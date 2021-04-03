package simulator.control;

import org.json.JSONObject;

;

public class DiferentStatesException extends Exception {

    public DiferentStatesException(JSONObject estado1, JSONObject estado2, int paso) {
        super(String.format("No coincide el estado %d:\n%s\n%s", paso, estado1.toString(), estado2.toString()));
    }

}
