package simulator.factories;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T> {

    List<Builder<T>> lista;

    public BuilderBasedFactory(List<Builder<T>> builders) {
        lista = builders;

    }


    @Override
    public T createInstance(JSONObject info) throws IllegalArgumentException {
        T instancia = null;
        int i = 0;

        while (i < lista.size() && instancia == null) {
            instancia = lista.get(i).createInstance(info);
            i++;
        }

        if (instancia == null)
            throw new IllegalArgumentException();

        return instancia;
    }

    @Override
    public List<JSONObject> getInfo() {

        ArrayList<JSONObject> list = new ArrayList<JSONObject>();

        for (Builder<T> e : lista) {
            list.add(e.getBuilderInfo());
        }

        return list;
    }
}
