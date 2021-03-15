package simulator.factories;

import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import simulator.model.Body;

import java.util.List;

public abstract class Builder <T>  {

    protected String typeTag, desc;

    public JSONObject getBuilderInfo () {
        JSONObject obj = new JSONObject().put("type",typeTag).put("desc",desc);

        obj.put("data",this.createData());

        return obj;

    }

    protected T createInstance(JSONObject info) throws IllegalArgumentException {


        if (info.getString("type").equals(typeTag))
            return createTheInstance(info.getJSONObject("data"));

        else
            return null;

    }

    public abstract T createTheInstance(JSONObject info);

    protected JSONObject createData(){

        return new JSONObject();
    }
}
