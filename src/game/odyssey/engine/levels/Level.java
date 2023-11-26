package game.odyssey.engine.levels;

import game.odyssey.engine.common.Game;
import game.odyssey.engine.common.Id;
import game.odyssey.engine.entities.Player;
import game.odyssey.engine.utils.Coordinate;
import game.odyssey.engine.utils.Graph;
import game.odyssey.engine.utils.Rectangle;
import game.odyssey.engine.utils.Resource;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public abstract class Level {
    private final ArrayList<Chunk> CHUNKS = new ArrayList<>();
    private final Graph<Chunk> chunkGraph = new Graph<>();
    private Color background = Color.BLACK;
    private ImageIcon map;
    private Coordinate pivot = Coordinate.ZERO;
    private Coordinate visualPosition = Coordinate.ZERO;
    private Coordinate spawnPoint = Coordinate.ZERO;
    private Chunk currentPlayerChunk;
    private Set<Coordinate> chunkCoordinates;
    private int maxChunkRow = Integer.MIN_VALUE;
    private int minChunkRow = Integer.MAX_VALUE;
    private int maxChunkCol = Integer.MIN_VALUE;
    private int minChunkCol = Integer.MAX_VALUE;
    public Level() {

    }

    protected void addChunk(Chunk chunk, int row, int column) {
        chunk.build();
        chunk.setPosition(new Coordinate(column, -row).readOnly());

        chunkGraph.addVertex(chunk);
        CHUNKS.add(chunk);

        if (row > maxChunkRow) maxChunkRow = row;
        if (row < minChunkRow) minChunkRow = row;
        if (column > maxChunkCol) maxChunkCol = column;
        if (column < minChunkCol) minChunkCol = column;
    }

    public Chunk[] getChunks() {
        return this.CHUNKS.toArray(new Chunk[0]);
    }

    public void setCurrentChunk(Chunk currentPlayerChunk) {
        this.currentPlayerChunk = currentPlayerChunk;
    }

    public Chunk getCurrentChunk() {
        return currentPlayerChunk;
    }

    public Graph<Chunk> getChunkGraph() {
        return chunkGraph;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public ImageIcon getMap() {
        try {
            if (map == null) {
                String levelId = getId();
                map = Resource.resolve(Game.getGameInstance().GAME_ID + "/assets/levels/" + levelId + ".png");
            }

            return map;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private String getId() {
        if (this.getClass().isAnnotationPresent(Id.class))
            return this.getClass().getAnnotation(Id.class).value();
        else return "";
    }

    protected void setMapPivotInPixel(Coordinate pivot) {
        this.pivot = pivot;
    }

    protected void setMapPivotInPixel(double x, double y) {
        this.pivot = new Coordinate(x, y);
    }

    public Coordinate getPivot() {
        return this.pivot;
    }

    public Coordinate getPosition() {
        return visualPosition;
    }

    public void setPosition(Coordinate visualPosition) {
        this.visualPosition = visualPosition;
    }

    public void setSpawnPoint(Coordinate spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public void setSpawnPoint(int x, int y) {
        this.spawnPoint = new Coordinate(x, y);


    }
    public Coordinate getSpawnPoint() {
        return spawnPoint;
    }

    protected void navigate() {
        this.onLeave(Game.getGameInstance().getPlayer());
    }

    public void buildLevel() {
        onStart(Game.getGameInstance().getPlayer());

        Coordinate spawnChunk = new Coordinate();

        spawnChunk.setX(
                spawnPoint.getX() / Chunk.CHUNK_TILE_WIDTH
        );

        spawnChunk.setY(
                spawnPoint.getY() /Chunk.CHUNK_TILE_HEIGHT
        );

        minChunkCol = Math.min(spawnChunk.getIntX(), minChunkCol);
        maxChunkCol = Math.max(spawnChunk.getIntX(), maxChunkCol);
        minChunkRow = Math.min(spawnChunk.getIntY(), minChunkRow);
        maxChunkRow = Math.max(spawnChunk.getIntY(), maxChunkRow);
//        if (spawnChunk.getX() <  minChunkCol) minChunkCol = spawnChunk.getIntX();
//        if (spawnChunk.getX() > maxChunkCol) maxChunkCol = spawnChunk.getIntX();
//        if (spawnChunk.getY() < minChunkRow) minChunkRow = spawnChunk.getIntY();
//        if (spawnChunk.getY() > maxChunkRow) maxChunkRow = spawnChunk.getIntY();

//        if (0 < minChunkCol) minChunkCol = 0;
//        if (0 > maxChunkCol) maxChunkCol = 0;
//        if (0 < minChunkRow) minChunkRow = 0;
//        if (0 > maxChunkRow) maxChunkRow = 0;
        minChunkCol = Math.min(0, minChunkCol);
        maxChunkCol = Math.max(0, maxChunkCol);
        minChunkRow = Math.min(0, minChunkRow);
        maxChunkRow = Math.max(0, maxChunkRow);

        int temp = maxChunkRow;
        maxChunkRow = -minChunkRow;
        minChunkRow = -temp;

        System.out.println("spawnPoint = " + spawnPoint);

        System.out.println("spawnChunk = " + spawnChunk);

        System.out.println("maxChunkRow = " + maxChunkRow);
        System.out.println("minChunkRow = " + minChunkRow);
        System.out.println("maxChunkCol = " + maxChunkCol);
        System.out.println("minChunkCol = " + minChunkCol);

        for (int i = minChunkRow; i <= maxChunkRow; i++) {
            for (int j = minChunkCol; j <= maxChunkCol; j++) {
                populateChunkCoordinates();

                Coordinate currentCoordinate = new Coordinate(j, i);
                Chunk currentChunk = findChunkByCoordinate(currentCoordinate);

                if (currentChunk == null) {
                    currentChunk = new SimpleChunk(currentCoordinate);
                    CHUNKS.add(currentChunk);
                    chunkGraph.addVertex(currentChunk);

                }

                if (isPlayerInChunk(spawnPoint, currentCoordinate)) {
                    this.currentPlayerChunk = currentChunk;
                }

            }
        }

        populateChunkCoordinates();
        for (int i = minChunkRow; i <= maxChunkRow; i++) {
            for (int j = minChunkCol; j <= maxChunkCol; j++) {

                Coordinate currentCoordinate = new Coordinate(j, i);
                Chunk currentChunk = findChunkByCoordinate(currentCoordinate);

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue;

                        int neighborX = j + dx;
                        int neighborY = i + dy;

                        Coordinate neighborCoordinate = new Coordinate(neighborX, neighborY);
                        Chunk neighborChunk = findChunkByCoordinate(neighborCoordinate);

                        if (neighborChunk != null) {
                            //                        System.out.println("add Edge");
                            chunkGraph.addEdge(currentChunk, neighborChunk, false);
                        }
                    }
                }
            }
        }

        chunkGraph.getVertexCount();
        chunkGraph.getEdgesCount(true);
        System.out.println("Current player chunk: " + this.currentPlayerChunk);
    }

    private boolean isPlayerInChunk(Coordinate playerPosition, Coordinate chunkPosition) {
        double x = chunkPosition.getX();
        double y = -chunkPosition.getY();

        boolean isInX = (x * Chunk.CHUNK_TILE_WIDTH) <= playerPosition.getX() && playerPosition.getX() < (x + 1) * Chunk.CHUNK_TILE_WIDTH;
        boolean isInY = (y * Chunk.CHUNK_TILE_HEIGHT) >= playerPosition.getY() && playerPosition.getY() > (y - 1) * Chunk.CHUNK_TILE_HEIGHT;

        return isInX && isInY;
    }
    // Call this method once to populate chunkCoordinates
    private void populateChunkCoordinates() {
        chunkCoordinates = CHUNKS.stream().map(Chunk::getPosition).collect(Collectors.toSet());
    }

    private Chunk findChunkByCoordinate(Coordinate coordinate) {
        return chunkCoordinates.contains(coordinate) ?
                CHUNKS.stream().filter(chunk -> chunk.getPosition().equals(coordinate)).findFirst().orElse(null) : null;
    }
    private void mergeSort(int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(left, middle);
            mergeSort(middle + 1, right);
            merge(left, middle, right);
        }
    }

    private void merge(int left, int middle, int right) {
        ArrayList<Chunk> temp = new ArrayList<>(CHUNKS);

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (compareChunks(temp.get(i), temp.get(j)) <= 0) {
                CHUNKS.set(k++, temp.get(i++));
            } else {
                CHUNKS.set(k++, temp.get(j++));
            }
        }

        while (i <= middle) {
            CHUNKS.set(k++, temp.get(i++));
        }
    }

    private int compareChunks(Chunk chunk1, Chunk chunk2) {
        double x1 = chunk1.getPosition().getX();
        double y1 = chunk1.getPosition().getY();
        double x2 = chunk2.getPosition().getX();
        double y2 = chunk2.getPosition().getY();

        if (x1 == x2) {
            return Double.compare(y1, y2);
        } else {
            return Double.compare(x1, x2);
        }
    }

    public Rectangle getChunkFrame() {
        Coordinate topLeft = new Coordinate(minChunkCol, minChunkRow);
        Coordinate bottomRight = new Coordinate(maxChunkCol, maxChunkRow);

        return new Rectangle(topLeft, bottomRight);
    }

    public void onHitChunkBorder(Player player, Coordinate playerPosition, Coordinate chunkPosition) {}

    // on Register
    public abstract void onStart(Player player);

    // everytime appear on screen
    public abstract void onUpdate(Player player, Coordinate playerPosition);

    // everytime disappear on screen
    public abstract void onLeave(Player player);
}
