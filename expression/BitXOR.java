package expression;

import java.math.BigInteger;

public class BitXOR extends Binary{
    public BitXOR(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
        return l^r;
    }

    @Override
    public BigInteger calculate(BigInteger l, BigInteger r) {
        return l.xor(r);
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
    public String getOperation() {
        return " ^ ";
    }
    @Override
    public ExpType getType() {
        return ExpType.XOR;
    }
}
