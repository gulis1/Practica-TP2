package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Builder;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

import javax.sound.midi.Soundbank;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

public class Controller {

    private final PhysicsSimulator simulator;

    private final Factory<Body> factory;

    public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory) {

        this.simulator = simulator;
        this.factory = bodyFactory;

    }

    public void loadBodies(InputStream in) {
        JSONObject jsonInput = new JSONObject(new JSONTokener(in));
        JSONArray lista = jsonInput.getJSONArray("bodies");

        for (int i = 0; i < lista.length(); i++) {
            simulator.addBody(factory.createInstance(lista.getJSONObject(i)));
        }

    }


    public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) {

        int i = 0;
        boolean igual = true;
        JSONObject obj = new JSONObject();
        JSONArray arrayEstados = new JSONArray();
        JSONArray expStates = null;

        // Se pone el estado inicial
        arrayEstados.put(simulator.getState());

        if (expOut != null) {
            expStates = new JSONObject(new JSONTokener(expOut)).getJSONArray("states");

            if (n + 1 != expStates.length()) {
                igual = false;
                System.out.println("Different number of states.");
            }

        }


        while (i < n && igual) {
            simulator.advance();
            arrayEstados.put(simulator.getState());

            if (expOut != null && !cmp.equal(arrayEstados.getJSONObject(i), expStates.getJSONObject(i))) {
                System.out.println(String.format("El estado %d no coincide ", i));
                igual = false;
            }
            i++;
        }

        obj.put("states", arrayEstados);

        if (expOut == null) {
            try {
                out.write(obj.toString().getBytes());
            } catch (Exception s) {
                System.out.println("No va");
            }
        } else {
            if (igual)
                System.out.println("SUUUUUUUUUU");
            else
                System.out.println("NOOOOOOOOOOOOOO");
        }


    }

}
