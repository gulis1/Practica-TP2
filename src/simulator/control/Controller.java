package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Builder;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

import javax.sound.midi.Soundbank;
import java.io.*;
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


    public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws DiferentStatesException {

        int i = 0;
        boolean igual = true;
        JSONObject estadoActual;
        JSONArray expStates = null;

        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");

        // Se pone el estado inicial
        p.println(simulator.getState().toString());


        if (expOut != null) {
            expStates = new JSONObject(new JSONTokener(expOut)).getJSONArray("states");

            if (n + 1 != expStates.length()) {
                igual = false;
                System.out.println("Different number of states.");
            }

        }


        while (i < n && igual) {

            estadoActual = simulator.getState();
            if (expOut != null && !cmp.equal(estadoActual, expStates.getJSONObject(i))) {

                throw new DiferentStatesException(estadoActual, expStates.getJSONObject(i), i);

            }

            simulator.advance();
            p.println("," + estadoActual.toString());

            i++;
        }

        if (expOut != null)
            System.out.println("Outputs are equal");

        p.println("]");
        p.println("}");

    }

}
