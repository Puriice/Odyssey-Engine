package game.odyssey.engine.levels;

import game.odyssey.engine.entities.Player;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.DoubleConsumer;

@SuppressWarnings("unused")
public class SimpleChunk extends Chunk{
    private Runnable build;
    private DoubleConsumer<Player, Coordinate> start;
    private DoubleConsumer<Player, Coordinate> destroy;

    public SimpleChunk() {
    }

    public SimpleChunk(Runnable build) {
        this.build = build;
    }

    public SimpleChunk(DoubleConsumer<Player, Coordinate> start, DoubleConsumer<Player, Coordinate> destroy) {
        this.start = start;
        this.destroy = destroy;
    }

    public SimpleChunk(Runnable build, DoubleConsumer<Player, Coordinate> start, DoubleConsumer<Player, Coordinate> destroy) {
        this.build = build;
        this.start = start;
        this.destroy = destroy;
    }

    @Override
    protected void build() {
        if (build == null) return;

        build.run();
    }

    @Override
    protected void start(Player player, Coordinate playerCoordinate) {
        if (start == null) return;

        start.accept(player, playerCoordinate);
    }

    @Override
    protected void destroy(Player player, Coordinate playerCoordinate) {
        if (destroy == null) return;

        destroy.accept(player, playerCoordinate);
    }
}
