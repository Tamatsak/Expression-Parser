package expression.exceptions;

import expression.Binary;
import expression.ExpType;
import expression.MyExpression;

import java.math.BigInteger;

public class CheckedAdd extends Binary {
    public CheckedAdd(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
        try {
            return Math.addExact(l, r);
        } catch (ArithmeticException e) {
            throw new ExpressionOverflowException("Overflow: can't add " + l + " to " + r
                    + " because of " + getLeft() + " and " + getRight());
        }
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
