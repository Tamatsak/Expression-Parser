package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class Unary implements MyExpression {
    MyExpression son;
    protected boolean sonBracket;

    public Unary(MyExpression son) {
        this.son = son;
        needSonsBracket();
    }

    @Override
    public ExpType getType() {
        return null;
    }

    public String getOperation(){
        return null;
    }

    public void needSonsBracket(){
    }

    @Override
    public String toString(){
        return (getOperation() + "(" + son.toString() + ")");
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getOperation());
        if (sonBracket) {
            sb.append("(").append(getSon().toMiniString()).append(")");
        } else {
            sb.append(" ").append(getSon().toMiniString());
        }
        return sb.toString();
    }
    public MyExpression getSon(){
        return son;
    }


    @Override
    public int hashCode(){
        return Objects.hash(getSon(), getType()) * 137;
    }

    @Override
    public int evaluate (int a) {
        return calculate(son.evaluate(a));
    }
    @Override
    public int evaluate (int x, int y, int z){
        return calculate(son.evaluate(x, y, z));
    }
    @Override
    public BigInteger evaluate (BigInteger a) {
        return null;
    }
    public int calculate (int son){
        return 0;
    }
}

