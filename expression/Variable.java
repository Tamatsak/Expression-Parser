package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Variable implements MyExpression {
    String var;
    public final ExpType type = ExpType.ARG;

    public Variable(String var) {
        this.var = var;
    }

    public int evaluate(int a) {
        return a;
    }

    @Override
    public BigInteger evaluate(BigInteger a) {
        return a;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (var) {
            case ("x"):
                return x;
            case ("y"):
                return y;
            case ("z"):
                return z;
            default:
                return 0;

        }
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public String toMiniString() {
        return var;
    }

    @Override
    public ExpType getType() {
        return this.type;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        MyExpression ebj = (MyExpression) obj;
        if (ebj.getType() == ExpType.ARG) {
            return toString().equals(ebj.toString());
        } else {
            return false;
        }

    }


    @Override
    public int hashCode() {
        return Objects.hash(var) * 137;
    }
}
