package game.odyssey.engine.renderer;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.TickUpdate;
import game.odyssey.engine.levels.Entry;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.registries.Register;
import game.odyssey.engine.registries.RegistryObject;
import game.odyssey.engine.renderer.modules.BackgroundRenderModule;
import game.odyssey.engine.renderer.modules.Context;
import game.odyssey.engine.renderer.modules.PlayerRenderModule;
import game.odyssey.engine.renderer.modules.RenderModule;

import java.awt.*;
import java.util.*;

@SuppressWarnings("unused")
public class Renderer {
    public static final int TILE_PIXEL_WIDE = 32;
    public static final int TILE_PIXEL_HEIGHT = 32;
    private static Thread renderThread;
    private static boolean isSetup = false;
    private static Level level;
    private static final ArrayList<RenderModule> modules = new ArrayList<>();

    public static Thread getThread() {
        return renderThread;
    }

    public static void setup() {
        if (isSetup) return;

        modules.add(new BackgroundRenderModule());
        modules.add(new PlayerRenderModule());

        TickUpdate tickUpdate = new TickUpdate(60);

        tickUpdate.perform(() -> Game.getGameInstance().getEngine().repaint());

        renderThread = new Thread(tickUpdate);

        renderThread.start();

        isSetup = true;
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
        Context.CONTEXT.set("LEVEL", level);
        Renderer.level = level;
    }

    public static void draw(Graphics2D g) {
        if (level == null) {
            setLevel(getEntryLevel());
        }

        if (level == null) return;
        if (Game.getGameInstance() == null) return;
        if (Game.getGameInstance().getPlayer() == null) return;

        modules.forEach(m -> {
            m.update(level, Game.getGameInstance().getPlayer());
            m.render(g);
        });
    }
}