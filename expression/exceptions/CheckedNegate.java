package expression.exceptions;

import expression.Unary;
import expression.ExpType;
import expression.MyExpression;

import java.math.BigInteger;

public class CheckedNegate extends Unary {

    public CheckedNegate(MyExpression son) {
        super(son);
    }

    @Override
    public ExpType getType() {
        return ExpType.NEG;
    }

    @Override
    public int calculate(int son) {
        try {
            return Math.negateExact(son);
        } catch (ArithmeticException e) {
            throw new ExpressionOverflowException("Overflow: can't negate " + son);
        }
    }


    public void needSonsBracket() {
        super.sonBracket = getSon().getType().getPriority() > getType().getPriority();
    }

    @Override
    public String getOperation() {
        return "-";
    }

}
