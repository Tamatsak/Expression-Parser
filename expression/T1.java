package expression;

public class T1 extends Unary{
    public T1(MyExpression son){
        super(son);
    }
    @Override
    public ExpType getType() {
        return ExpType.T1;
    }

    @Override
    public int calculate(int son) {
        return Integer.numberOfTrailingZeros(~son) ;
    }


    public void needSonsBracket() {
        super.sonBracket = getSon().getType().getPriority()> getType().getPriority();
    }


    @Override
    public String getOperation(){
        return "t1";
    }
}

