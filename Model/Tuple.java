package Landing.Model;

public class Tuple<L, R> {
    private L x;
    private R y;

    Tuple(L x, R y) {
        this.x = x;
        this.y = y;
    }

    public void setX(L x) {
        this.x = x;
    }

    public void setY(R y) {
        this.y = y;
    }

    public L getX() {
        return x;
    }

    public R getY() {
        return y;
    }
}
