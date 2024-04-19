package expression;

import java.math.BigInteger;

public class L1 extends Unary{
    public L1(MyExpression son){
        super(son);
    }
    @Override
    public ExpType getType() {
        return ExpType.L1;
    }

    @Override
    public int calculate(int son) {
        return Integer.numberOfLeadingZeros(~son) ;
    }


    public void needSonsBracket() {
        super.sonBracket = getSon().getType().getPriority()> getType().getPriority();
    }


    @Override
    public String getOperation(){
        return "l1";
    }
}
