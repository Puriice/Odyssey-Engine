package game.odyssey.engine.renderer.modules;

import com.sun.management.OperatingSystemMXBean;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Chunk;
import game.odyssey.engine.levels.Level;
import game.odyssey.engine.renderer.Context;
import game.odyssey.engine.renderer.Renderer;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Rectangle;

import java.awt.*;
import java.lang.management.ManagementFactory;

import static game.odyssey.engine.common.Game.getGameInstance;

public class DebugInformationRenderModule extends RenderModule{
    private static final Runtime runtime = Runtime.getRuntime();
    private static final OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    @Override
    public void update(Level level, Player player) {

    }

    @Override
    public void render(Graphics2D g2d) {
        double cpuUsage = osBean.getProcessCpuLoad() * 100.0;
        double totalMemory = runtime.totalMemory() / (1024.0 * 1024.0);
        double freeMemory = runtime.freeMemory() / (1024.0 * 1024.0);
        double usedMemory = totalMemory - freeMemory;
        int fps = Renderer.getDrawCount();

        g2d.setColor(new Color(0,0,0,75));

        Level level = (Level) getContext().get(Context.Common.LEVEL);
//        int rowCount = (int) Math.ceil(getGameInstance().getEngine().GAME_HEIGHT / Renderer.TILE_PIXEL_HEIGHT);
//        int colCount = (int) Math.ceil(getGameInstance().getEngine().GAME_WIDTH / Renderer.TILE_PIXEL_WIDTH);
        Coordinate visualPosition = (Coordinate) getContext().get(Context.Common.VISUAL);
        Coordinate center = (Coordinate) getContext().get(Context.Common.CENTER);

        int r, c, i = 0;

        do {
            r = (int) (visualPosition.getY() + i*Renderer.TILE_PIXEL_HEIGHT);
            g2d.drawLine(0, r, (int) getGameInstance().getEngine().GAME_WIDTH, r);
            i++;
        } while (r < getGameInstance().getEngine().GAME_HEIGHT);

        i = 0;

        do {
            c = (int) ( -visualPosition.getX() + i*Renderer.TILE_PIXEL_WIDTH);
            g2d.drawLine(c, 0, c, (int) getGameInstance().getEngine().GAME_HEIGHT);
            i++;
        } while (c < getGameInstance().getEngine().GAME_WIDTH);

        i = 0;

        g2d.setColor(Color.YELLOW);
        int[] ChunkSize = new int[] { Chunk.CHUNK_TILE_WIDTH*Renderer.TILE_PIXEL_WIDTH, Chunk.CHUNK_TILE_HEIGHT*Renderer.TILE_PIXEL_HEIGHT };


        do {
            r = (int) (-center.getY() + 6 + visualPosition.getY() + i*ChunkSize[1]);
            g2d.drawLine(0, r, (int) getGameInstance().getEngine().GAME_WIDTH, r);
            i++;
        } while (r < getGameInstance().getEngine().GAME_HEIGHT);

        i = 0;

        do {
            c = (int) (-center.getX() + 24 -visualPosition.getX() + i*ChunkSize[0]);

            g2d.drawLine(c, 0, c, (int) getGameInstance().getEngine().GAME_HEIGHT);
            i++;
        } while (c < getGameInstance().getEngine().GAME_WIDTH);

        Coordinate playerPosition = getGameInstance().getPlayer().getPosition();

        g2d.setColor(Color.RED);

        Rectangle chunkFrame = level.getChunkFrame();

        r = (int) (-center.getY() + 6 + visualPosition.getY() + (chunkFrame.getTopLeft().getY() + 1) *ChunkSize[1]);

        g2d.drawLine(0, r, (int) getGameInstance().getEngine().GAME_WIDTH, r);

        r = (int) (-center.getY() + 6 + visualPosition.getY() + chunkFrame.getBottomLeft().getY()*ChunkSize[1]);

        g2d.drawLine(0, r, (int) getGameInstance().getEngine().GAME_WIDTH, r);

        c = (int) (-center.getX() + 24 - visualPosition.getX() + chunkFrame.getTopLeft().getX()*ChunkSize[0]);

        g2d.drawLine(c, 0, c, (int) getGameInstance().getEngine().GAME_HEIGHT);

        c = (int) (-center.getX() + 24 - visualPosition.getX() + (chunkFrame.getTopRight().getX() + 1)*ChunkSize[0]);

        g2d.drawLine(c, 0, c, (int) getGameInstance().getEngine().GAME_HEIGHT);

        g2d.setColor(new Color(0,0,0,75));

        g2d.fillRect(10,10, 200, 145);
        g2d.fillRect(225, 10,200, 75);
        g2d.fillRect(450, 10,200, 75);

        g2d.setColor(Color.WHITE);

        g2d.fillOval((int) (-center.getX() - 2.5), (int) (-center.getY()), 5, 5);
//        g2d.fillOval(100, 100, 20, 20);

        g2d.drawString("FPS: " + fps, 25, 35);
        g2d.drawString("CPU Usage: " + String.format("%.2f", cpuUsage), 25, 60);
        g2d.drawString("Total Memory: " + String.format("%.2f", totalMemory), 25, 85);
        g2d.drawString("Free Memory: " + String.format("%.2f", freeMemory), 25, 110);
        g2d.drawString("Used Memory: " + String.format("%.2f", usedMemory), 25, 135);


        g2d.drawString("Visual Position", 240, 35);
        g2d.drawString("X: " + String.format("%.2f", -visualPosition.getX()) + " Y: " + String.format("%.2f", visualPosition.getY()), 240, 60);
        g2d.drawString("Player Position", 465, 35);
        g2d.drawString("X: " + String.format("%f", playerPosition.getX()) + " Y: " + String.format("%f", playerPosition.getY()), 465, 60);

    }
}
