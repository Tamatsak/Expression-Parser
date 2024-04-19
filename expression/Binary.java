package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class Binary implements MyExpression {
    public MyExpression left, right;
    protected boolean leftBracket;
    protected boolean rightBracket;

    public Binary(MyExpression f, MyExpression s) {
        left = f;
        right = s;
        needLeftBracket();
        needRightBracket();
    }
    public String getOperation(){
        return null;
    }

    @Override
    public String toString() {
        //теперь это просто строчки, потому что для 5 строк оптимизации за счет stringbuilder нет
        return ("(" + left.toString() + getOperation() + right.toString() + ")");
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        if (leftBracket) {
            sb.append("(").append(left.toMiniString()).append(")");
        } else {
            sb.append(left.toMiniString());
        }
        sb.append(getOperation());

        if (rightBracket) {
            sb.append("(").append(right.toMiniString()).append(")");
        } else {
            sb.append(right.toMiniString());
        }
        return sb.toString();
    }

    public void needLeftBracket() {
    }

    public boolean childHasLowerPriority(MyExpression child) {
        return (child.getType().getPriority() > this.getType().getPriority());
    }

    public void needRightBracket() {
    }

    @Override
    public ExpType getType() {
        return null;
    }

    public MyExpression getLeft() {
        return left;
    }

    public MyExpression getRight() {
        return right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Binary ebj = (Binary) obj;
        return Objects.equals(ebj.getLeft(), (this.getLeft())) &&
                    Objects.equals(ebj.getRight(), (this.getRight()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, getOperation()) * 137;
    }
    @Override
    public int evaluate (int a) {
        return calculate(left.evaluate(a), right.evaluate(a));
    }
    @Override
    public int evaluate (int x, int y, int z){
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
    @Override
    public BigInteger evaluate (BigInteger a) {
        return calculate(left.evaluate(a), right.evaluate(a));
    }
    public int calculate (int left, int right){
        return 0;
    }
    public BigInteger calculate(BigInteger a, BigInteger b) {
        return null;
    }
}
