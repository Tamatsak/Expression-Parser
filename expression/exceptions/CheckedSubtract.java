package expression.exceptions;

import java.math.BigInteger;

import expression.Binary;
import expression.ExpType;
import expression.MyExpression;

public class CheckedSubtract extends Binary {
    public CheckedSubtract(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
        try {
            return Math.subtractExact(l, r);
        } catch (ArithmeticException e) {
            throw new ExpressionOverflowException("Overflow: can't subtract " + l + " - " + r
                    + " because of " + getLeft() + " and " + getRight());
        }
    }


    @Override
    public void needLeftBracket() {
        super.leftBracket = super.childHasLowerPriority(left);
    }

    @Override
    public void needRightBracket() {
        super.rightBracket = super.childHasLowerPriority(right);
        if (right.getType().getPriority() == this.getType().getPriority()) {
            super.rightBracket = true;
        }
    }

    @Override
    public String getOperation() {
        return " - ";
    }

    @Override
    public ExpType getType() {
        return ExpType.SUB;
    }
}
