package expression.exceptions;

import expression.Binary;
import expression.ExpType;
import expression.MyExpression;

import java.math.BigInteger;

public class CheckedMultiply extends Binary {
    public CheckedMultiply(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
        try {
            return Math.multiplyExact(l, r);
        } catch (ArithmeticException e) {
            throw new ExpressionOverflowException("Overflow: can't multiply " + l + " times " + r +
                    " because of " + getLeft() + " and " + getRight());
        }
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
