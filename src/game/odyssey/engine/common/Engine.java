package game.odyssey.engine.common;

import game.odyssey.engine.entities.event.PlayerMoveEvent;
import game.odyssey.engine.events.Event;
import game.odyssey.engine.events.common.*;
import game.odyssey.engine.events.common.FocusEvent;
import game.odyssey.engine.renderer.RenderPanel;
import game.odyssey.engine.renderer.Renderer;
import game.odyssey.engine.servers.ServerTick;
import game.odyssey.engine.utils.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class Engine extends JFrame implements KeyListener, MouseListener {
    public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
    public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();
    public final double GAME_WIDTH;
    public final double GAME_HEIGHT;
    private final Container CONTENT_PANE;
    private final ServerTick SERVER_TICK;
    private final Thread SERVER_THREAD;
    private final RenderPanel renderPanel;
    private boolean isReady = false;

    Engine(String title) {
        super(title);

        this.CONTENT_PANE = this.getContentPane();
        this.SERVER_TICK = new ServerTick();
        this.SERVER_THREAD = new Thread(this.SERVER_TICK);

        this.setSize(Engine.SCREEN_WIDTH, Engine.SCREEN_HEIGHT);
        this.setResizable(false);

        final double[] FRAME_SIZE = new double[] { this.getSize().getWidth(), this.getSize().getHeight() };

        this.GAME_WIDTH = FRAME_SIZE[0];
        this.GAME_HEIGHT = FRAME_SIZE[1];
        this.setLayout(null);

        try {
            Resource intro = new Resource("odyssey/intro.png");

            double scale = GAME_HEIGHT / intro.getImageIcon().getIconHeight();
            intro.scale(scale);

            JLabel image = new JLabel(intro.getImageIcon());

            image.setBounds(0, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT);

            this.CONTENT_PANE.add(image);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.setVisible(true);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.renderPanel = new RenderPanel(GAME_WIDTH, GAME_HEIGHT);
        this.CONTENT_PANE.add(this.renderPanel);

        Event.setupCommonEvent();

        Renderer.setup();

        this.SERVER_THREAD.start();

        this.addKeyListener(this);
        this.addMouseListener(this);

    }

    public RenderPanel getRenderPanel() {
        return renderPanel;
    }

    void setServerCycle(Runnable cycle) {
        this.SERVER_TICK.perform(cycle);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        new KeyTypeEvent(e).dispatch();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        PlayerMoveEvent.setDirection(e);
        new KeyPressEvent(e).dispatch();
        new PlayerMoveEvent(e).dispatch();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        PlayerMoveEvent.removeDirection(e);
        new KeyReleaseEvent(e).dispatch();
        new PlayerMoveEvent(e).dispatch();
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

    private void onReady(GameLoadingEvent event) {
        isReady = true;
        SERVER_THREAD.start();
        this.requestFocus();
    }

}
