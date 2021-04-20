package simulator.launcher;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.control.StateComparator;
import simulator.factories.*;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // default values for some parameters
    //
    private final static Double _dtimeDefaultValue = 2500.0;
    private final static Integer _defaultSteps = 150;
    private final static String _forceLawsDefaultValue = "nlug";
    private final static String _stateComparatorDefaultValue = "epseq";
    private final static String _modeDefaultValue = "batch";

    // some attributes to stores values corresponding to command-line parameters
    //
    private static Double _dtime = null;
    private static String _inFile = null;
    private static String _expFile = null;
    private static JSONObject _forceLawsInfo = null;
    private static JSONObject _stateComparatorInfo = null;

    // factories
    private static Factory<Body> _bodyFactory;
    private static Factory<ForceLaws> _forceLawsFactory;
    private static Factory<StateComparator> _stateComparatorFactory;

    private static OutputStream out;
    private static InputStream in;
    private static InputStream expIn;
    private static int stepNum;
    private static String mode;

    private static void init() {
        //  initialize the bodies factory
        ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
        bodyBuilders.add(new BasicBodyBuilder());
        bodyBuilders.add(new MassLosingBodyBuilder());
        _bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);

        //  initialize the force laws factory

        ArrayList<Builder<ForceLaws>> forceBuilders = new ArrayList<>();
        forceBuilders.add(new NewtonUniversalGravitationBuilder());
        forceBuilders.add(new MovingTowardsFixedPointBuilder());
        forceBuilders.add(new NoForceBuilder());
        _forceLawsFactory = new BuilderBasedFactory<ForceLaws>(forceBuilders);

        // initialize the state comparator
        ArrayList<Builder<StateComparator>> compBuilders = new ArrayList<>();
        compBuilders.add(new MassEqualStatesBuilder());
        compBuilders.add(new EpsilonEqualStatesBuilder());
        _stateComparatorFactory = new BuilderBasedFactory<StateComparator>(compBuilders);
    }

    private static void parseArgs(String[] args) {

        // define the valid command line options
        //
        Options cmdLineOptions = buildOptions();

        // parse the command line as provided in args
        //
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(cmdLineOptions, args);

            parseModeOption(line);

            parseHelpOption(line, cmdLineOptions);
            parseInFileOption(line);
            // add support of -o, -eo, and -s (define corresponding parse methods)

            parseDeltaTimeOption(line);
            parseForceLawsOption(line);
            parseStateComparatorOption(line);
            parseStepsOption(line);
            parseExpectedOutFileOption(line);
            parseOutPutFileOption(line);


            // if there are some remaining arguments, then something wrong is
            // provided in the command line!
            //
            String[] remaining = line.getArgs();
            if (remaining.length > 0) {
                StringBuilder error = new StringBuilder("Illegal arguments:");
                for (String o : remaining)
                    error.append(" ").append(o);
                throw new ParseException(error.toString());
            }

        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }

    }

    private static Options buildOptions() {
        Options cmdLineOptions = new Options();

        // help
        cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

        // input file
        cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

        //  add support for -o, -eo, and -s (add corresponding information to cmdLineOptions)
        cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Bodies JSON Output file.").build());

        cmdLineOptions.addOption(Option.builder("eo").longOpt("expectedOutput").hasArg().desc("Expected output JSON file.").build());

        cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg()
                .desc("An integer representing the number of steps  Default value: "
                        + _defaultSteps + ".")
                .build());

        // delta-time
        cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
                .desc("A double representing actual time, in seconds, per simulation step. Default value: "
                        + _dtimeDefaultValue + ".")
                .build());

        // force laws
        cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
                .desc("Force laws to be used in the simulator. Possible values: "
                        + factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
                        + "'.")
                .build());

        // gravity laws
        cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
                .desc("State comparator to be used when comparing states. Possible values: "
                        + factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
                        + _stateComparatorDefaultValue + "'.")
                .build());

        cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg()
                .desc(" Execution Mode. Possible values: 'batch'(Batch Mode), 'gui' (Graphical User Interface mode)" + "Default value: '" + _modeDefaultValue + "'.")
                .build());



        return cmdLineOptions;
    }

    public static String factoryPossibleValues(Factory<?> factory) {
        if (factory == null)
            return "No values found (the factory is null)";

        String s = "";

        for (JSONObject fe : factory.getInfo()) {
            if (s.length() > 0) {
                s = s + ", ";
            }
            s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
        }

        s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
        return s;
    }

    private static void parseModeOption(CommandLine line) throws ParseException {

        String modo = line.getOptionValue("m");

        if (modo == null)
            mode = _modeDefaultValue;

        else if (!modo.equals("gui") && !modo.equals("batch"))
            throw new ParseException("Invalid mode: " + modo + ".");

        else
            mode = modo;

    }

    private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
        if (line.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
            System.exit(0);
        }
    }

    private static void parseInFileOption(CommandLine line) throws ParseException {
        _inFile = line.getOptionValue("i");
        if (_inFile == null && mode.equals("batch")) {
            throw new ParseException("In batch mode an input file of bodies is required");
        }

        else if (_inFile != null) {
            try {
                in = new FileInputStream(new File(_inFile));
            } catch (FileNotFoundException e) {
                System.out.println("archivo de bodies no encontrado");

            }
        }


    }

    private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
        String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
        try {
            _dtime = Double.parseDouble(dt);
            assert (_dtime > 0);
        } catch (Exception e) {
            throw new ParseException("Invalid delta-time value: " + dt);
        }
    }

    private static void parseOutPutFileOption(CommandLine line) {

        String o = line.getOptionValue("o");
        if (o == null) {
            out = System.out;
        } else {
            try {
                out = new FileOutputStream(new File(o));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseExpectedOutFileOption(CommandLine line) throws ParseException {
        _expFile = line.getOptionValue("eo");

        if (_expFile == null) {
            expIn = null;
        } else {
            try {
                expIn = new FileInputStream(new File(_expFile));
            } catch (FileNotFoundException e) {
                System.out.println("Expected input file not found");
                ;
            }
        }
    }

    private static void parseStepsOption(CommandLine line) throws ParseException {
        String s = line.getOptionValue("s", _defaultSteps.toString());
        try {
            stepNum = Integer.parseInt(s);
            assert (stepNum > 0);
        } catch (Exception e) {
            throw new ParseException("Invalid Steps value: " + stepNum);
        }
    }

    private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

        // the value of v is either a tag for the type, or a tag:data where data is a
        // JSON structure corresponding to the data of that type. We split this
        // information
        // into variables 'type' and 'data'
        //
        int i = v.indexOf(":");
        String type = null;
        String data = null;
        if (i != -1) {
            type = v.substring(0, i);
            data = v.substring(i + 1);
        } else {
            type = v;
            data = "{}";
        }

        // look if the type is supported by the factory
        boolean found = false;
        for (JSONObject fe : factory.getInfo()) {
            if (type.equals(fe.getString("type"))) {
                found = true;
                break;
            }
        }

        // build a corresponding JSON for that data, if found
        JSONObject jo = null;
        if (found) {
            jo = new JSONObject();
            jo.put("type", type);
            jo.put("data", new JSONObject(data));
        }
        return jo;

    }

    private static void parseForceLawsOption(CommandLine line) throws ParseException {
        String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
        _forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
        if (_forceLawsInfo == null) {
            throw new ParseException("Invalid force laws: " + fl);
        }
    }

    private static void parseStateComparatorOption(CommandLine line) throws ParseException {
        String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
        _stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
        if (_stateComparatorInfo == null) {
            throw new ParseException("Invalid state comparator: " + scmp);
        }
    }

    private static void startBatchMode() throws Exception {
        // complete this method
        PhysicsSimulator simulator = new PhysicsSimulator(_dtime, _forceLawsFactory.createInstance(_forceLawsInfo));
        Controller controller = new Controller(simulator, _bodyFactory, _forceLawsFactory);

        controller.loadBodies(in);
        controller.run(stepNum, out, expIn, _stateComparatorFactory.createInstance(_stateComparatorInfo));

    }

    private static void startGuiMode() throws Exception {

        PhysicsSimulator simulator = new PhysicsSimulator(_dtime, _forceLawsFactory.createInstance(_forceLawsInfo));
        Controller controller = new Controller(simulator, _bodyFactory, _forceLawsFactory);

        SwingUtilities.invokeAndWait(() -> new MainWindow(controller));

    }

    private static void start(String[] args) throws Exception {
        parseArgs(args);

        if (mode.equals("batch"))
            startBatchMode();
        else if (mode.equals("gui"))
            startGuiMode();
    }

    public static void main(String[] args) {
        try {

            init();
            start(args);
        } catch (Exception e) {
            System.err.println("Something went wrong ...");
            System.err.println();
            e.printStackTrace();
        }
    }
}
