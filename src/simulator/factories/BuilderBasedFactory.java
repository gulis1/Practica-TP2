package simulator.factories;

import org.json.JSONObject;

import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T> {

    List<Builder<T>> lista;

    public BuilderBasedFactory(List<Builder<T>> builders) {
        lista = builders;

    }


    @Override
    public T createInstance(JSONObject info) throws IllegalArgumentException {
        T xD=null;
        int i = 0;

        while(i < lista.size()) {
            xD = lista.get(i).createInstance(info);
            i++;
        }

        if (xD==null)
            throw new IllegalArgumentException();

        return xD;
    }

    @Override
    public List<JSONObject> getInfo() {
        return null;
    }
}
