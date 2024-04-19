package expression.parser;

import expression.*;

public class Main {
    public static void main(String[] args){
        String a = ((MyExpression)new ExpressionParser().parse("l1(y) / (z + y)")).toString();
        String b = new Divide(new Negate(new Variable("y")), new Add(new Variable("y"), new Variable("y"))).toString();
//        int m1 = new Negate(new Add(new Variable("x"), new Variable("x"))).getType().getPriority();
        System.out.println(b + "\n" + a);
    }
}
