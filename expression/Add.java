package expression;

import java.math.BigInteger;

public class Add extends Binary {
    public Add(MyExpression f, MyExpression s) {
        super(f, s);
    }
    @Override
    public int calculate(int l, int r) {
        return l+r;
    }

    @Override
    public BigInteger calculate(BigInteger l, BigInteger r) {
        return l.add(r);
    }

    @Override
    public String getOperation() {
        return " + ";
    }

    @Override
    public void needLeftBracket() {
        super.leftBracket = super.childHasLowerPriority(left);
    }

    @Override
    public void needRightBracket() {
        super.rightBracket = super.childHasLowerPriority(right);
    }

    @Override
    public ExpType getType() {
        return ExpType.ADD;
    }
}
