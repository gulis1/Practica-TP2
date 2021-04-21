//package simulator.view;
//
//import simulator.misc.Vector2D;
//import simulator.model.Body;
//
//import java.awt.*;
//
//public class Viewer extends JComponent implements SimulatorObserver {
//    // ...
//    private int _centerX;
//    private int _centerY;
//    private double _scale;
//    private List<Body> _bodies;
//    private boolean _showHelp;
//    private boolean _showVectors;
//
//    Viewer(Controller ctrl) {
//        initGUI();
//        ctrl.addObserver(this);
//    }
//
//    private void initGUI() {
//// TODO add border with title
//        _bodies = new ArrayList<>();
//        _scale = 1.0;
//        _showHelp = true;
//        _showVectors = true;
//        addKeyListener(new KeyListener() {
//            // ...
//            @Override
//            public void keyPressed(KeyEvent e) {
//                switch (e.getKeyChar()) {
//                    case ’-’:
//                        _scale = _scale * 1.1;
//                        repaint();
//                        break;
//                    case ’+’:
//                        _scale = Math.max(1000.0, _scale / 1.1);
//                        repaint();
//                        break;
//                    case ’=’:
//                        autoScale();
//                        repaint();
//                        break;
//                    case ’h’:
//                        _showHelp = !_showHelp;
//                        repaint();
//                        break;
//                    case ’v’:
//                        _showVectors = !_showVectors;
//                        repaint();
//                        reak;
//                    default:
//                }
//            }
//        });
//        addMouseListener(new MouseListener() {
//            // ...
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                requestFocus();
//            }
//        });
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//// use ’gr’ to draw not ’g’ --- it gives nicer results
//        Graphics2D gr = (Graphics2D) g;
//        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//// calculate the center
//        _centerX = getWidth() / 2;
//        _centerY = getHeight() / 2;
//// TODO draw a cross at center
//// TODO draw bodies (with vectors if _showVectors is true)
//// TODO draw help if _showHelp is true
//    }
//
//    // other private/protected methods
//// ...
//    private void autoScale() {
//        double max = 1.0;
//        for (Body b : _bodies) {
//            Vector2D p = b.getpublic class BodiesTable extends JPanel {
//                BodiesTable(Controller ctrl) {
//                    setLayout(new BorderLayout());
//                    setBorder(BorderFactory.createTitledBorder(
//                            BorderFactory.createLineBorder(Color.black, 2),
//                            "Bodies",
//                            TitledBorder.LEFT, TitledBorder.TOP));
//// TODO complete
