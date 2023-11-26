package game.odyssey.engine.utils;

/**
 * Represents a rectangle with a top-left corner and bottom-right corner.
 */
@SuppressWarnings("unused")
public class Rectangle {
    private Coordinate topLeft;
    private Coordinate bottomRight;

    /**
     * Constructs a new Rectangle object with the specified top-left and bottom-right coordinates.
     *
     * @param coordinate1 the first coordinate that defines the rectangle
     * @param coordinate2 the second coordinate that defines the rectangle
     */
    public Rectangle(Coordinate coordinate1, Coordinate coordinate2) {
        double x1 = coordinate1.getX();
        double y1 = coordinate1.getY();
        double x2 = coordinate2.getX();
        double y2 = coordinate2.getY();

        double topLeftX = Math.min(x1, x2);
        double topLeftY = Math.max(y1, y2);
        double bottomRightX = Math.max(x1, x2);
        double bottomRightY = Math.min(y1, y2);

        this.topLeft = new Coordinate(topLeftX, topLeftY);
        this.bottomRight = new Coordinate(bottomRightX, bottomRightY);
    }

    /**
     * Returns the top-left coordinate of this rectangle.
     *
     * @return the top-left coordinate of this rectangle
     */
    public Coordinate getTopLeft() {
        return topLeft;
    }

    /**
     * Sets the top-left coordinate of this rectangle to the specified value.
     *
     * @param topLeft the new top-left coordinate of this rectangle
     */
    public void setTopLeft(Coordinate topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Returns the bottom-right coordinate of this rectangle.
     *
     * @return the bottom-right coordinate of this rectangle
     */
    public Coordinate getBottomRight() {
        return bottomRight;
    }

    /**
     * Returns the top-right coordinate of this rectangle.
     *
     * @return the top-right coordinate of this rectangle
     */
    public Coordinate getTopRight() {
        return new Coordinate(this.topLeft.getX() + this.getWidth(), this.topLeft.getY());
    }

    /**
     * Returns the bottom-left coordinate of this rectangle.
     *
     * @return the bottom-left coordinate of this rectangle
     */
    public Coordinate getBottomLeft() {
        return new Coordinate(this.topLeft.getX(), this.topLeft.getY() - this.getHeight());
    }

    /**
     * Sets the bottom-right coordinate of this rectangle to the specified value.
     *
     * @param bottomRight the new bottom-right coordinate of this rectangle
     */
    public void setBottomRight(Coordinate bottomRight) {
        this.bottomRight = bottomRight;
    }

    /**
     * Returns the width of this rectangle.
     *
     * @return the width of this rectangle
     */
    public double getWidth() {
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    /**
     * Returns the height of this rectangle.
     *
     * @return the height of this rectangle
     */
    public double getHeight() {
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    /**
     * Returns the area of this rectangle.
     *
     * @return the area of this rectangle
     */
    public double getArea() {
        return this.getWidth() * this.getHeight();
    }

    /**
     * Moves this rectangle up by the specified amount.
     *
     * @param amount the amount to move the rectangle up by
     */
    public void moveUp(double amount) {
        this.topLeft.addY(amount);
        this.bottomRight.addY(amount);
    }

    /**
     * Moves this rectangle down by the specified amount.
     *
     * @param amount the amount to move the rectangle down by
     */
    public void moveDown(double amount) {
        this.topLeft.addY(-amount);
        this.bottomRight.addY(-amount);
    }

    /**
     * Moves this rectangle left by the specified amount.
     *
     * @param amount the amount to move the rectangle left by
     */
    public void moveLeft(double amount) {
        this.topLeft.addX(-amount);
        this.bottomRight.addX(-amount);
    }

    /**
     * Moves this rectangle right by the specified amount.
     *
     * @param amount the amount to move the rectangle right by
     */
    public void moveRight(double amount) {
        this.topLeft.addX(amount);
        this.bottomRight.addX(amount);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "topLeft=" + topLeft +
                ", bottomRight=" + bottomRight +
                '}';
    }
}
