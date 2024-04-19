package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Negate extends Unary {

    public Negate(MyExpression son){
        super(son);
    }
    @Override
    public ExpType getType() {
        return ExpType.NEG;
    }

    @Override
    public int calculate(int son) {
        return -1 * son;
    }


    public void needSonsBracket() {
        super.sonBracket = getSon().getType().getPriority()> getType().getPriority();
    }

    @Override
    public String getOperation(){
        return "-";
    }

}
