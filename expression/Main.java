package expression;

public class Main {
    public static void main(String[] args){
        Multiply exp = new Multiply(new Add(new Variable("x"), new Const(-2)), new Variable("x"));
        ToMiniString exp1 = new Add(new Const(2), new Variable("x"));
        System.out.println(exp1.toMiniString());
    }
}
