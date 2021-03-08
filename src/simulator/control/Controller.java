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

    private PhysicsSimulator simulator;

    private Factory<Body> fabriquita;

    public Controller(PhysicsSimulator xD, Factory<Body> Xd){

        this.simulator=xD;
        this.fabriquita=Xd;

    }

    public void loadBodies(InputStream in) {
        JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
        JSONArray listita = jsonInupt.getJSONArray("bodies");

        for (int i = 0; i<listita.length(); i++) {
            simulator.addBody(fabriquita.createInstance(listita.getJSONObject(i)));
        }

    }


    public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) {

        for (int i = 0; i<n; i++) {
            simulator.advance();
            try {
                out.write(simulator.getState().toString().getBytes());
            }

            catch (Exception s) {
                System.out.println("No va");
            }

        }

        if (cmp.equal(simulator.getState(),  new JSONObject(new JSONTokener(expOut))))
            System.out.println("SUUUUU");
        else
            System.out.println("NOP");


    }

}
