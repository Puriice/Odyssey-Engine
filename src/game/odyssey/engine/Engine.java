package game.odyssey.engine;

import game.odyssey.engine.events.bus.IEventBus;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

@SuppressWarnings("FieldCanBeLocal")
public class Engine extends JFrame {
    public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
    public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

    public final double GAME_WIDTH;
    public final double GAME_HEIGHT;
    private final Component CONTENT_PANE;
    private final TickUpdate TICK_UPDATER;
    private final Thread SERVER_THREAD;

    Engine(String title) throws HeadlessException {
        super(title);
        this.CONTENT_PANE = this.getContentPane();
        this.TICK_UPDATER = new TickUpdate(20);
        this.SERVER_THREAD = new Thread(this.TICK_UPDATER);

        this.setSize(Engine.SCREEN_WIDTH, Engine.SCREEN_HEIGHT);
        this.setResizable(false);

        final double[] FRAME_SIZE = new double[] { this.getSize().getWidth(), this.getSize().getHeight() };

        this.GAME_WIDTH = FRAME_SIZE[0];
        this.GAME_HEIGHT = FRAME_SIZE[1];

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        Renderer.setup();

        this.setVisible(true);
    }


    IEventBus getEventBus() {
        return null;
    }

//    private void build() {
//        Register<Level> levelRegister = Register.createRegister(Register.Type.LEVEL);
//
////        HashMap<String, Level> levels = levelRegister.query();
//    }

    @Override
    public void paint(Graphics g) {
//        Graphics2D g2D = (Graphics2D) g;
        Renderer.draw((Graphics2D) g);
    }
}
