package expression.parser;

import expression.*;
import expression.exceptions.TripleParser;

import static expression.ExpType.*;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) {
//        System.err.println(expression);
        return new ExpressionParser.MyExpressionParser(new StringSource(expression)).parse();

    }


    private static class MyExpressionParser extends BasedParser {

        MyExpressionParser(CharSource expression) {
            super(expression);
        }

        public MyExpression parse() {
            return parseExpr();
        }

        private MyExpression parseExpr() {
            MyExpression left = parseXOR();
            while (true) {
                if (!eof() && !take(')')) {
                    //System.err.println(test());
                    expect('|');
                    left = makeNewBinary(left, parseXOR(), OR);
                    continue;
                }
                skipWhitespace();
                return left;
            }
        }

        private MyExpression parseXOR() {
            MyExpression left = null;
            left = parseAND();
            while (!endTest() && !test(')')) {

                if (test('^')) {
                    take();
                    left = makeNewBinary(left, parseAND(), XOR);
                } else return left;
            }
            return left;
        }

        private MyExpression parseAND() {
            MyExpression left = null;
            left = parseAddSub();
            while (!endTest() && !test(')')) {

                if (test('&')) {
                    take();
                    left = makeNewBinary(left, parseAddSub(), AND);
                } else return left;
            }
            return left;
        }

        private MyExpression parseAddSub() {
            MyExpression left = null;
            if (!endTest() && !test(')')) {
                left = parseMulDiv();
            }
            do {
                if (test('+')) {
                    take();
                    left = makeNewBinary(left, parseMulDiv(), ADD);
                } else if (test('-')) {
                    take();
                    left = makeNewBinary(left, parseMulDiv(), SUB);
                } else return left;
            } while (!endTest() && !test(')'));
            return left;
        }

        private MyExpression parseMulDiv() {
            MyExpression left = null;
            if (!endTest() && !test(')')) {

                left = parseUnary();

            }
            do {
                if (test('*')) {
                    take();
                    left = makeNewBinary(left, parseUnary(), MUL);
                } else if (test('/')) {
                    take();
                    left = makeNewBinary(left, parseUnary(), DIV);
                } else return left;
            } while (!endTest() && !test(')'));
            return left;
        }

        private MyExpression parseUnary() {
            skipWhitespace();

            if (take('(')) {
                return parseExpr();
            }
            if (test('t') || test('l')) {
                String op = Character.toString(take());
                if (take('1')) {
                    op += "1";
                    if (op.equals("t1")) {
                        if (take('(')) {
                            return new expression.T1(parseExpr());
                        }
                        return new expression.T1(parseUnary());
                    }
                    if (take('(')) {
                        return new expression.L1(parseExpr());
                    }
                    return new expression.L1(parseUnary());
                }
                if (between('a', 'z') || between('A', 'Z')) {
                    StringBuilder sb = new StringBuilder(op);
                    do {
                        sb.append(take());
                    } while (between('a', 'z') || between('A', 'Z'));
                    skipWhitespace();
                    return new expression.Variable(sb.toString());
                }
                throw new RuntimeException("illegal variable/number");
            }
            if (between('a', 'z') || between('A', 'Z')) {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(take());
                } while (between('a', 'z') || between('A', 'Z'));
                skipWhitespace();
                return new expression.Variable(sb.toString());
            }

            if (between('0', '9')) {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(take());
                } while (between('0', '9'));
                skipWhitespace();
                return new expression.Const(Integer.parseInt(sb.toString()));
            }
            if (take('-')) {
                if (between('0', '9')) {
                    StringBuilder sb = new StringBuilder();
                    sb.append('-');
                    do {
                        sb.append(take());
                    } while (between('0', '9'));
                    skipWhitespace();
                    return new expression.Const(Integer.parseInt(sb.toString()));
                }
                skipWhitespace();
                if (take('(')) {
                    return new expression.Negate(parseExpr());
                }
                return new expression.Negate(parseUnary());
            } else {
                throw new RuntimeException("invalid variable/const");
            }
        }

        private MyExpression makeNewBinary(MyExpression left, MyExpression right, ExpType op) {
            return switch (op) {
                case OR -> new expression.BitOR(left, right);
                case AND -> new expression.BitAND(left, right);
                case XOR -> new expression.BitXOR(left, right);
                case ADD -> new expression.Add(left, right);
                case SUB -> new expression.Subtract(left, right);
                case MUL -> new expression.Multiply(left, right);
                case DIV -> new expression.Divide(left, right);
                default -> throw new RuntimeException("illegal operation");
            };
//            if (op == OR) return new expression.BitOR(left, right);
//            if (op == AND) return new expression.BitAND(left, right);
//            if (op == XOR) return new expression.BitXOR(left, right);
//            if (op == ADD) return new expression.Add(left, right);
//            if (op == SUB) return new expression.Subtract(left, right);
//            if (op == MUL) return new expression.Multiply(left, right);
//            if (op == DIV) return new expression.Divide(left, right);
//            throw new RuntimeException("illegal operation");
        }
    }
}
