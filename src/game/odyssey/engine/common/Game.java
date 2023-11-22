package game.odyssey.engine.common;

import game.odyssey.engine.entities.Player;
import game.odyssey.engine.events.common.GameLoadingEvent;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;

public class Game {

    public static final String GAME_PACKAGE = "game.odyssey.game";
    private static Game game;
    private static Engine engine;
    public static Game getGameInstance() {
        return game;
    }
    public final String GAME_TITLE;
    public final String GAME_ID;
    private final Player player;

    public Game(String gameTitle, String gameId) {
        this.GAME_TITLE = gameTitle;
        this.GAME_ID = gameId;
        Game.engine = new Engine(this.GAME_TITLE);
        this.player = new Player();
    }

    public Engine getEngine() {
        return engine;
    }

    public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<?> aClass = scanClassWithAnnotation();

        if (aClass == null) throw new IllegalArgumentException("Game Id is not specify.");

        String gameId = aClass.getAnnotation(Id.class).value();

        if (gameId.isEmpty()) throw new IllegalArgumentException("Game Id is not specify.");

        String gameName = aClass.getAnnotation(Id.class).name();

        if (gameName.isEmpty()) gameName = gameId;

        Game.game =  new Game(gameName, gameId.toLowerCase());

        aClass.getDeclaredConstructor().newInstance();

        new GameLoadingEvent().dispatch();
    }

    private static Class<?> scanClassWithAnnotation() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = GAME_PACKAGE.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());

                if (!directory.isDirectory()) continue;

                File[] files = directory.listFiles();

                if (files == null) continue;

                for (File file: files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = GAME_PACKAGE + '.' + file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);

                        // Check if the class has the specified annotation
                        if (clazz.isAnnotationPresent(Id.class)) {
                            return classLoader.loadClass(className);
//                            return clazz;
                        }
                    }
                }

            }
        } catch (IOException | ClassNotFoundException ignored) {
        }

        return null;
    }

}
