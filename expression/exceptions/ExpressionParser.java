package expression.exceptions;

import expression.ExpType;
import expression.MyExpression;
import expression.TripleExpression;
import expression.parser.BasedParser;
import expression.parser.CharSource;
import expression.parser.StringSource;


import static expression.ExpType.*;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) {
        return new ExpressionParser.MyExpressionParser(new StringSource(expression)).parse();

    }


    private static class MyExpressionParser extends BasedParser {
        private CharSource expression;
        private int recLevel = 0;

        MyExpressionParser(CharSource expression) {
            super(expression);
            this.expression = expression;
            //System.err.println(expression.toString());
        }

        public MyExpression parse() {
            return parseExpr();
        }

        private MyExpression parseExpr() {
            MyExpression left = parseOR();
            while (true) {
                if (!take(')')) {
                    if (eof() && recLevel == 0) {
                        return left;
                    }
                    if (!test('m')) {
                        throw new ParserSyntaxException("Unexpected symbol while parsing \"" + this.expression +
                                "\", found \"" + take() + "\", expected any operation");
                    }
                    if (take('m') && (take('i') && test('n')) ||
                            take('a') && test('x')) {
                        if (take('x')) {
                            if (!Character.isWhitespace(test())) {
                                //System.out.println(this.expression);
                            }
                            if (between('a', 'z') || between('0', '9')) {
                                throw new ParserSyntaxException("No Whitespace between max and \"" + take() + "\"");
                            }
                            left = makeNewBinary(left, parseXOR(), MAX);
                            continue;
                        }
                        if (take('n')) {
                            if (between('a', 'z') || between('0', '9')) {
                                throw new ParserSyntaxException("No Whitespace between min and \"" + take() + "\"");
                            }
                            left = makeNewBinary(left, parseXOR(), MIN);
                            continue;
                        }
                        throw new ParserSyntaxException("Expected WhiteSpaces while parsing \"" + this.expression +
                                "\": found \"" + take() + "\"");
                    }
                } else {
                    if (recLevel == 0) {
                        throw new ParserSyntaxException("No opening parenthesis in \"" + this.expression + "\"");
                    }
                }
                recLevel--;
                skipWhitespace();
                return left;
            }
        }

        private MyExpression parseOR() {
            MyExpression left = parseXOR();
            while (!(endTest() && recLevel == 0) && !test(')')) {

                if (test('|')) {
                    take();
                    left = makeNewBinary(left, parseXOR(), OR);
                } else return left;
            }
            return left;
        }

        private MyExpression parseXOR() {
            MyExpression left = parseAND();
            while (!(endTest() && recLevel == 0) && !test(')')) {

                if (test('^')) {
                    take();
                    left = makeNewBinary(left, parseAND(), XOR);
                } else return left;
            }
            return left;
        }

        private MyExpression parseAND() {
            MyExpression left;
            left = parseAddSub();
            while (!(endTest() && recLevel == 0) && !test(')')) {

                if (test('&')) {
                    take();
                    left = makeNewBinary(left, parseAddSub(), AND);
                } else return left;
            }
            return left;
        }

        private MyExpression parseAddSub() {
            MyExpression left;
            left = parseMulDiv();
            do {
                if (test('+')) {
                    take();
                    left = makeNewBinary(left, parseMulDiv(), ADD);
                } else if (test('-')) {
                    take();
                    left = makeNewBinary(left, parseMulDiv(), SUB);
                } else return left;
            } while (!(endTest() && recLevel == 0) && !test(')'));
            return left;
        }

        private MyExpression parseMulDiv() {
            MyExpression left;
            left = parseUnary();

            do {
                if (test('*')) {
                    take();
                    left = makeNewBinary(left, parseUnary(), MUL);
                } else if (test('/')) {
                    take();
                    left = makeNewBinary(left, parseUnary(), DIV);
                } else return left;
            } while (!(endTest() && recLevel == 0) && !test(')'));
            return left;
        }

        private MyExpression parseUnary() {
            skipWhitespace();

            if (take('(')) {
                recLevel++;
                return parseExpr();
            }

            if (test('t') || test('l')) {
                return parseTLOnes();
            }

            if (test('x') || test('y') || test('z')) {
                return parseVariable();
            }

            if (between('0', '9')) {
                return parseNumber();
            }

            if (take('-')) {
                return parseNegate();
            }
            if (isValidAfterVariable()) {
                if (endTest()) {
                    throw new ParserSyntaxException("Unexpected end of file in \"" + this.expression + "\"");
                }
                throw new ParserSyntaxException("Unexpected symbol while parsing \"" + this.expression +
                        "\": found \"" + take() + "\", expected argument or \"(\"");
            }
            throw new ParserSyntaxException("Illegal symbol while parsing \"" + this.expression +
                    "\": found \"" + take() + "\", expected correct variable, number or \"(\"");

        }

        private MyExpression makeNewBinary(MyExpression left, MyExpression right, ExpType op) {
            return switch (op) {
                case OR -> new expression.BitOR(left, right);
                case AND -> new expression.BitAND(left, right);
                case XOR -> new expression.BitXOR(left, right);
                case ADD -> new CheckedAdd(left, right);
                case SUB -> new CheckedSubtract(left, right);
                case MUL -> new CheckedMultiply(left, right);
                case DIV -> new CheckedDivide(left, right);
                case MIN -> new MIN(left, right);
                case MAX -> new MAX(left, right);
                default -> throw new ParserSyntaxException("Illegal operation: " + op);
            };
        }

        private boolean isValidAfterVariable() {
            return test(')') || test('+') || test('*')
                    || test('&') || test('-') || test('/')
                    || test('^') || (endTest() && recLevel == 0); //eof
        }

        private MyExpression parseTLOnes() {
            String op = Character.toString(take());
            if (take('1')) {
                op += "1";
                skipWhitespace();
                if (op.equals("t1")) {
                    if (take('(')) {
                        recLevel++;
                        return new expression.T1(parseExpr());
                    }
                    return new expression.T1(parseUnary());
                }
                if (take('(')) {
                    recLevel++;
                    return new expression.L1(parseExpr());
                }
                return new expression.L1(parseUnary());
            }
            throw new ParserSyntaxException("Found illegal operation while parsing \"" + this.expression +
                    "\": found \"" + op + take() + "\", expected \"" + op + "1\"");
        }

        private MyExpression parseVariable() {
            String var = Character.toString(take());
            if (!Character.isWhitespace(test())) {
                if (!isValidAfterVariable()) {
                    if (test('m')) {
                        throw new ParserSyntaxException("Unexpected symbol while parsing \"" + this.expression +
                                "\": found \"" + take() + "\" after variable");
                    }
                    if (endTest() && recLevel != 0) {
                        throw new ParserSyntaxException("Unexpected end of file in \"" + this.expression + "\"");
                    }
                    throw new ParserSyntaxException("Found illegal operation while parsing \"" + this.expression +
                            "\": found \"" + take() + "\" after variable");
                }
                return new expression.Variable(var);
            }
            skipWhitespace();
            if (!isValidAfterVariable()) {
                if (endTest() && recLevel != 0) {
                    throw new ParserSyntaxException("Unexpected end of file in \"" + this.expression + "\"");
                }
                if (!test('m') && !test('t') & !test('l')) {
                    throw new ParserSyntaxException("Found illegal operation while parsing \"" + this.expression +
                            "\": found \"" + take() + "\" after variable");
                }
                return new expression.Variable(var);
            }
            return new expression.Variable(var);
        }

        private MyExpression parseNegate() {
            if (between('0', '9')) {
                StringBuilder sb = new StringBuilder();
                sb.append('-');
                do {
                    sb.append(take());
                } while (between('0', '9'));
                if (test('m')) {
                    throw new ParserSyntaxException("Unexpected symbol while parsing \"" + this.expression +
                            "\": found \"" + take() + "\" after number");
                }
                skipWhitespace();

                try {
                    return new expression.Const(Integer.parseInt(sb.toString()));
                } catch (NumberFormatException e) {
                    throw new ParserValueException("Constant overflow for " + sb.toString());
                }
            }
            skipWhitespace();
            if (take('(')) {
                recLevel++;
                return new CheckedNegate(parseExpr());
            }
            return new CheckedNegate(parseUnary());
        }

        private MyExpression parseNumber() {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(take());
            } while (between('0', '9'));
            if (test('m')) {
                throw new ParserSyntaxException("Unexpected symbol while parsing \"" + this.expression +
                        "\": found \"" + take() + "\" after number");
            }
            skipWhitespace();
            if (sb.charAt(0) == '0' && sb.length() > 1) {
                throw new ParserValueException("Unexpected symbol: number " + sb + " started with zero");
            }
            try {
                return new expression.Const(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException e) {
                throw new ParserValueException("Constant overflow for " + sb.toString());
            }
        }
    }
}
