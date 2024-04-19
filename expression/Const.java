package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Const implements MyExpression {
    private BigInteger c;

    public Const(int c) {
        this.c = (BigInteger.valueOf(c));
    }

    public Const(BigInteger c) {
        this.c = c;
    }
    public BigInteger getValue(){
        return c;
    }

    @Override
    public int evaluate(int a) {
        return getValue().intValue();
    }

    @Override
    public BigInteger evaluate(BigInteger a) {
        return getValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getValue().intValue();
    }


    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public String toMiniString() {
        return getValue().toString();
    }

    @Override
    public ExpType getType() {
        return ExpType.ARG;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
            Const ebj = (Const) obj;
            return (Objects.equals(getValue(), ebj.getValue()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue()) * 137;

    }
}
