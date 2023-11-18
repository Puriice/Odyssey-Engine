package game.odyssey.engine.utils;

import java.awt.*;
import java.util.Objects;

@SuppressWarnings("unused")
public class Coordinate {
    public static final Coordinate ZERO = new Coordinate().readOnly();
    private double x;
    private double y;
    private boolean isReadOnly = false;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Point p) {
        this.x = p.getX();
        this.y = p.getX();
    }

    public Coordinate(Coordinate c) {
        this.x = c.x;
        this.y = c.y;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public Coordinate readOnly() {
        isReadOnly = true;
        return this;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if (isReadOnly) return;
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        if (isReadOnly) return;
        this.y = y;
    }

    public void move(double x, double y) {
        if (isReadOnly) return;
        this.x = x;
        this.y = y;
    }

    public void move(Coordinate other) {
        this.move(other.x, other.y);
    }

    public void translate(double dx, double dy) {
        if (isReadOnly) return;
        this.x += dx;
        this.y += dy;
    }

    public void translate(Coordinate other) {
        this.translate(other.x, other.y);
    }
    public void rotate(double angle) {
        if (isReadOnly) return;
        double radians = Math.toRadians(angle);

        double newX = x * Math.cos(radians) - y * Math.sin(radians);
        double newY = x * Math.sin(radians) + y * Math.cos(radians);

        x = newX;
        y = newY;
    }

    public double distance(Coordinate other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
