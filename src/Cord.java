import java.util.Objects;

/**
 * Class to represent coordinates
 */
public class Cord {

    private int x;
    private int y;

    public Cord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Cord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cord cord = (Cord) o;
        return x == cord.x &&
                y == cord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
