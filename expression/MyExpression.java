package expression;

import java.math.BigInteger;

public interface MyExpression extends Expression, TripleExpression {
    ExpType getType();

    int evaluate(int a);

    BigInteger evaluate(BigInteger a);

    String toMiniString();

    int evaluate(int x, int y, int z);

    boolean equals(Object obj);

}