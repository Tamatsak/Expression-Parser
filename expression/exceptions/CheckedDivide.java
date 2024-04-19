package expression.exceptions;

import java.math.BigInteger;

import expression.Binary;
import expression.ExpType;
import expression.MyExpression;

public class CheckedDivide extends Binary {
    public CheckedDivide(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
        if (r == 0) {
            throw new ExpressionDivisionByZeroException("division by zero because of " + getLeft() + " and " + getRight());
        }
        try {
            return Math.divideExact(l, r);
        } catch (ArithmeticException e) {
            throw new ExpressionOverflowException("Overflow: can't divide " + l + " to " + r +
                    " because of " + getLeft() + " and " + getRight());
        }
    }

    @Override
    public BigInteger calculate(BigInteger l, BigInteger r) {
        return l.divide(r);
    }

    @Override
    public void needLeftBracket() {
        super.leftBracket = super.childHasLowerPriority(left);
    }

    @Override
    public void needRightBracket() {
        super.rightBracket = super.childHasLowerPriority(right);
        if (right.getType().getPriority() == getType().getPriority()) {
            rightBracket = true;
        }
    }

    @Override
    public String getOperation() {
        return " / ";
    }

    @Override
    public ExpType getType() {
        return ExpType.DIV;
    }
}
