package game.odyssey.engine.renderer;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.TickUpdate;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.events.Event;
import game.odyssey.engine.events.common.GameLoadingEvent;
import game.odyssey.engine.events.common.KeyPressEvent;
import game.odyssey.engine.levels.Entry;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.registries.RegistryObject;
import game.odyssey.engine.renderer.modules.*;
import game.odyssey.engine.utils.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.util.*;

@SuppressWarnings("unused")
public class Renderer {
    public static final int TILE_PIXEL_WIDTH = 48;
    public static final int TILE_PIXEL_HEIGHT = 48;
    private static final ArrayList<RenderModule> modules = new ArrayList<>();
    private static Thread renderThread;
    private static boolean isSetup = false;
    private static Level level;
    private static int drawCount = 0;
    private static final RenderModule debugModule = new DebugInformationRenderModule();
    private static boolean isDebugging = false;
    public static Thread getThread() {
        return renderThread;
    }

    public static void setup() {
        if (isSetup) return;

        modules.add(new BackgroundRenderModule());
        modules.add(new LevelRenderModule());
        modules.add(new ObjectRenderModule());
        modules.add(new PlayerRenderModule());
        modules.add(new EntityObjectRenderModule());

        TickUpdate tickUpdate = new TickUpdate(60);

        tickUpdate.perform(() -> {
//            modules.forEach(m -> m.update(level, Game.getGameInstance().getPlayer()));
            Game.getGameInstance().getRenderPanel().repaint();
        });

        tickUpdate.performEverySecond(i -> {
            drawCount = i;
            Player player = Game.getGameInstance().getPlayer();
            if (level != null) level.onUpdate(player, new Coordinate(player.getPosition()).readOnly());
        });

        renderThread = new Thread(tickUpdate);

        Event.addListener(GameLoadingEvent.class, Renderer::gameLoadingListener);
        Event.addListener(KeyPressEvent.class, Renderer::keyPressListener);

        isSetup = true;
    }

    public static int getDrawCount() {
        return drawCount;
    }

    private static Level getEntryLevel() {
        Register<Level> register = Register.createRegister(Register.Type.LEVEL);

        HashMap<String, RegistryObject<Level>> levels =  register.query();

        for (Map.Entry<String, RegistryObject<Level>> entry : levels.entrySet()) {
            Level level = entry.getValue().get();
            if (level.getClass().isAnnotationPresent(Entry.class)) {
                return level;
            }
        }

        return null;
    }

    public static void setLevel(Level level) {
//        level.buildObject();
        Context.CONTEXT.set(Context.Common.LEVEL, level);
        Renderer.level = level;
        Game.getGameInstance().getPlayer().move(level.getSpawnPoint());
    }

    public static void draw(Graphics2D g) {
        if (level == null) return;
        if (Game.getGameInstance() == null) return;
        if (Game.getGameInstance().getPlayer() == null) return;

        modules.forEach(m -> m.render(g));
    }

//    public static void drawIntro(Graphics2D g) {
//        try {
//            Resource intro = new Resource("odyssey/intro.png");
////            intro.scale();
//            intro.scale(Game.getGameInstance().getEngine().GAME_WIDTH, Game.getGameInstance().getEngine().GAME_HEIGHT);
//
//            g.drawImage(intro.getImageIcon().getImage(), 0, 0, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    private static void gameLoadingListener(GameLoadingEvent event) {
        Context.CONTEXT.set(Context.Common.CENTER,
                new Coordinate(
                        (int) ((Renderer.TILE_PIXEL_WIDTH - Game.getGameInstance().getEngine().GAME_WIDTH) / 2.0),
                        (int) ((Renderer.TILE_PIXEL_HEIGHT - Game.getGameInstance().getEngine().GAME_HEIGHT) / 2.0)
                )
        );

        renderThread.start();

        setLevel(getEntryLevel());
    }
    private static void keyPressListener(KeyPressEvent event) {
        int f3KeyCode = KeyStroke.getKeyStroke("F3").getKeyCode();
        if (event.getResult().getKeyCode() == f3KeyCode) {
            if (isDebugging) {
                modules.remove(debugModule);
            } else {
                modules.add(debugModule);
            }

            isDebugging = !isDebugging;
        }
    }
}
