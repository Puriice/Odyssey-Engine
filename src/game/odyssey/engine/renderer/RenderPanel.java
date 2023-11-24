package game.odyssey.engine.renderer;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {
    public RenderPanel(double width, double height) {
        this.setBounds(0, 0, (int) width, (int) height);
        this.setLayout(null);
    }

    @Override
    public void paint(Graphics g) {
        Renderer.draw((Graphics2D) g);
    }
}
