package expression;

import java.math.BigInteger;

public class Multiply extends Binary {
    public Multiply(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
        return l*r;
    }

    @Override
    public BigInteger calculate(BigInteger l, BigInteger r) {
        return l.multiply(r);
    }
    @Override
    public void needLeftBracket() {
        leftBracket = super.childHasLowerPriority(left);
    }

    @Override
    public void needRightBracket() {
        super.rightBracket = super.childHasLowerPriority(right);
        if (right.getType() == ExpType.DIV) {
            rightBracket = true;
        }
    }
    @Override
    public String getOperation() {
        return " * ";
    }
    @Override
    public ExpType getType() {
        return ExpType.MUL;
    }
}
