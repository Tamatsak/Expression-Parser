package expression;
public enum ExpType {
    DIV(2), SUB(3), ADD(3), MUL(2),
    ARG(0), OR(6), XOR(5), AND(4),
    NEG(1), L1(1), T1(1), MIN(7), MAX(7);
    private final int priority;
    ExpType(int priority){
        this.priority = priority;
    }
    public int getPriority(){
        return this.priority;
    }
}
