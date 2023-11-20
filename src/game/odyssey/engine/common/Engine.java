package game.odyssey.engine.common;

import game.odyssey.engine.entities.event.PlayerMoveEvent;
import game.odyssey.engine.events.Event;
import game.odyssey.engine.events.common.*;
import game.odyssey.engine.events.common.FocusEvent;
import game.odyssey.engine.renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("FieldCanBeLocal")
public class Engine extends JFrame implements KeyListener, MouseListener {
    public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
    public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

    private static void setupCommonEvent() {
        Event.createEvent(KeyPressEvent.class);
        Event.createEvent(KeyReleaseEvent.class);
        Event.createEvent(KeyTypeEvent.class);
        Event.createEvent(PlayerMoveEvent.class);

        Event.createEvent(MousePressEvent.class);
        Event.createEvent(MouseReleaseEvent.class);
        Event.createEvent(MouseClickEvent.class);
        Event.createEvent(FocusEvent.class);
        Event.createEvent(BlurEvent.class);
    }

    public final double GAME_WIDTH;
    public final double GAME_HEIGHT;
    private final Component CONTENT_PANE;
    private final TickUpdate TICK_UPDATER;
    private final Thread SERVER_THREAD;

    Engine(String title) {
        super(title);
        this.CONTENT_PANE = this.getContentPane();
        this.TICK_UPDATER = new TickUpdate(20);
        this.SERVER_THREAD = new Thread(this.TICK_UPDATER);

//        this.TICK_UPDATER.perform(this::cycle);
        this.setSize(Engine.SCREEN_WIDTH, Engine.SCREEN_HEIGHT);
        this.setResizable(false);

        final double[] FRAME_SIZE = new double[] { this.getSize().getWidth(), this.getSize().getHeight() };

        this.GAME_WIDTH = FRAME_SIZE[0];
        this.GAME_HEIGHT = FRAME_SIZE[1];

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        Engine.setupCommonEvent();

        Renderer.setup();
        this.SERVER_THREAD.start();

        this.addKeyListener(this);
        this.addMouseListener(this);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Renderer.draw((Graphics2D) g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        new KeyTypeEvent(e).dispatch();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        new KeyPressEvent(e).dispatch();
        new PlayerMoveEvent(e).dispatch(System.out::println);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        new KeyReleaseEvent(e).dispatch();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new MouseClickEvent(e).dispatch();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        new MousePressEvent(e).dispatch();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        new MouseReleaseEvent(e).dispatch();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        new FocusEvent(e).dispatch();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        new BlurEvent(e).dispatch();
    }
}
