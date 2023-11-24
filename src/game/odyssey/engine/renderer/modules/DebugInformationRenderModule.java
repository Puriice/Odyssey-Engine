package game.odyssey.engine.renderer.modules;

import com.sun.management.OperatingSystemMXBean;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.levels.Level;

import java.awt.*;
import java.lang.management.ManagementFactory;

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

        g2d.drawString("CPU Usage: " + cpuUsage, 10, 50);
        g2d.drawString("Total Memory: " + totalMemory, 10, 75);
        g2d.drawString("Free Memory: " + freeMemory, 10, 100);
        g2d.drawString("Used Memory: " + usedMemory, 10, 125);
    }
}
