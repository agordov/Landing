package Landing.Model;

public class Tuple<L, R> {
    private L left;
    private R right;

    Tuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public void setRight(R right) {
        this.right =right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
