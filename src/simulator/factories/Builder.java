package simulator.factories;

import org.json.JSONObject;

import java.util.List;

public abstract class Builder <T> implements Factory{

    String typeTag, desc;


    public abstract T createInstance(JSONObject info);

    public abstract List<JSONObject> getInfo();
}
