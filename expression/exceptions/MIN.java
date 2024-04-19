package expression.exceptions;

import expression.Binary;
import expression.ExpType;
import expression.MyExpression;

import java.math.BigInteger;

public class MIN extends Binary {
    public MIN(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
//        BigInteger sum = BigInteger.valueOf(l).add(BigInteger.valueOf(r));
//        if (sum.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 1 &&
//                sum.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) > -1) {
//            return l + r;
//        }
//        throw new ExpressionOverflowException("Overflow: can't add " + l + " to " + r
//                + " because of " + getLeft() + " and " + getRight());
        if (l < r) {
            return l;
        } else return r;
    }

    @Override
    public String getOperation() {
        return " min ";
    }

    @Override
    public void needLeftBracket() {
        super.leftBracket = super.childHasLowerPriority(left);
    }

    @Override
    public void needRightBracket() {
        super.rightBracket = super.childHasLowerPriority(right);
        if (right.getType().getPriority() == this.getType().getPriority() && right.getType() != getType()) {
            super.rightBracket = true;
        }
    }

    @Override
    public ExpType getType() {
        return ExpType.MIN;
    }
}
