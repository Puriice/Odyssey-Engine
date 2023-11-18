package game.odyssey.engine.utils;

import game.odyssey.engine.Game;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.URL;

/**
 * A class for loading resources from the classpath.
 */
@SuppressWarnings("UnusedReturnValue")
public class Resource implements Serializable {
    public static final boolean WIDTH = false;
    public static final boolean HEIGHT = true;
    public static ImageIcon resolve(String path) throws FileNotFoundException {
        URL url = Resource.class.getResource("/" + path);

        if (url == null) throw new FileNotFoundException("Resource not found");

        return new ImageIcon(url);
    }

    private final URL path;
    private ImageIcon imageIcon;
    /**
     * Constructs a new Resource object with the specified resource path.
     *
     * @param path the path to the resource to load
     */
    public Resource(String path) throws FileNotFoundException {
        this.path = getClass().getClassLoader().getResource(path);

        if (this.path == null) throw new FileNotFoundException("Resource not found");

        this.imageIcon = new ImageIcon(this.path);
    }

    /**
     * Returns the URL of the loaded resource.
     *
     * @return the URL of the loaded resource
     */
    public URL getPath() {
        return path;
    }

    /**
     * Returns an {@link ImageIcon} object for the loaded resource.
     *
     * @return an ImageIcon object for the loaded resource
     */
    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    public Resource scale() {
        double scale = Game.getGameInstance().getEngine().GAME_WIDTH / this.imageIcon.getIconWidth();

        return this.scale(scale);
    }

    public Resource scale(boolean scaleBy) {
        if (scaleBy == Resource.HEIGHT) {
            double scale = Game.getGameInstance().getEngine().GAME_HEIGHT / this.imageIcon.getIconHeight();

            return this.scale(scale);
        }

        return this.scale();
    }

    public Resource scale(double multiplier) {
        return this.scale(this.imageIcon.getIconWidth() * multiplier, this.imageIcon.getIconHeight() * multiplier);
    }

    public Resource scale(double width, double height) {
        Image image = this.imageIcon.getImage();
        Image scaledImage = image.getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);

        this.imageIcon = new ImageIcon(scaledImage);

        return this;
    }
}
