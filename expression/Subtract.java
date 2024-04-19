package expression;
import java.math.BigInteger;

public class Subtract extends Binary {
    public Subtract(MyExpression f, MyExpression s){
        super(f, s);
    }
    @Override
    public int calculate(int l, int r) {
        return l-r;
    }

    @Override
    public BigInteger calculate(BigInteger l, BigInteger r) {
        return l.subtract(r);

    }@Override
    public void needLeftBracket() {
        super.leftBracket = super.childHasLowerPriority(left);
    }
    @Override
    public void needRightBracket() {
        super.rightBracket = super.childHasLowerPriority(right);
        if (right.getType().getPriority() == this.getType().getPriority()){
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
