package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Builder;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

public class Controller {

    private final PhysicsSimulator simulator;

    private final Factory<Body> factory;

    public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory){

        this.simulator=simulator;
        this.factory=bodyFactory;

    }

    public void loadBodies(InputStream in) {
        JSONObject jsonInput = new JSONObject(new JSONTokener(in));
        JSONArray lista = jsonInput.getJSONArray("bodies");

        for (int i = 0; i<lista.length(); i++) {
            simulator.addBody(factory.createInstance(lista.getJSONObject(i)));
        }

    }


    public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) {


        JSONObject nombrependiente=new JSONObject();
        JSONArray arraypendiente=new JSONArray();

        for (int i = 0; i<n; i++) {
            simulator.advance();
            arraypendiente.put(simulator.getState());
        }

        nombrependiente.put("states",arraypendiente);

        try {
            out.write(nombrependiente.toString().getBytes());
        }

        catch (Exception s) {
            System.out.println("No va");
        }


        /*if (cmp.equal(simulator.getState(), new JSONObject(new JSONTokener(expOut))))
            System.out.println("SUUUUU");
        else
            System.out.println("NOP");*/


    }

}
