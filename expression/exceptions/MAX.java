package expression.exceptions;

import expression.Binary;
import expression.ExpType;
import expression.MyExpression;

import java.math.BigInteger;

public class MAX extends Binary {
    public MAX(MyExpression f, MyExpression s) {
        super(f, s);
    }

    @Override
    public int calculate(int l, int r) {
//        BigInteger sum = BigInteger.valueOf(l).add(BigInteger.valueOf(r));
//        if (sum.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 1 &&
//                sum.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) > -1) {
//            return l + r;
//        }
//        throw new ExpressionOverflowException("Overflow: can't add " + l + " to " + r
//                + " because of " + getLeft() + " and " + getRight());
//
        if (l < r) {
            return r;
        } else return l;
    }

    @Override
    public String getOperation() {
        return " max ";
    }

    @Override
    public void needLeftBracket() {
        super.leftBracket = super.childHasLowerPriority(left);
    }

    @Override
    public void needRightBracket() {
        super.rightBracket = super.childHasLowerPriority(right);
        if (right.getType().getPriority() == this.getType().getPriority() && right.getType() != getType()) {
            super.rightBracket = true;
        }
    }

    @Override
    public ExpType getType() {
        return ExpType.MAX;
    }
}

/*
__current-repo\java-solutions\expression\exceptions\ExpressionParser.java:57: error: cannot find symbol
                            left = makeNewBinary(left, parseXOR(), MAX);
                                                                   ^
  symbol:   variable MAX
  location: class MyExpressionParser
__current-repo\java-solutions\expression\exceptions\ExpressionParser.java:64: error: cannot find symbol
                            left = makeNewBinary(left, parseXOR(), MIN);
                                                                   ^
  symbol:   variable MIN
  location: class MyExpressionParser
__current-repo\java-solutions\expression\exceptions\ExpressionParser.java:193: error: an enum switch case label must be the unqualified name of an enumeration constant
                case MIN -> new MIN(left, right);
                     ^
__current-repo\java-solutions\expression\exceptions\ExpressionParser.java:194: error: an enum switch case label must be the unqualified name of an enumeration constant
                case MAX -> new MAX(left, right);
                     ^
__current-repo\java-solutions\expression\exceptions\CheckedAdd.java:36: error: leftBracket is not public in Binary; cannot be accessed from outside package
        super.leftBracket = super.childHasLowerPriority(left);
             ^
__current-repo\java-solutions\expression\exceptions\CheckedAdd.java:41: error: rightBracket is not public in Binary; cannot be accessed from outside package
        super.rightBracket = super.childHasLowerPriority(right);
             ^
__current-repo\java-solutions\expression\exceptions\CheckedDivide.java:34: error: leftBracket is not public in Binary; cannot be accessed from outside package
        super.leftBracket = super.childHasLowerPriority(left);
             ^
__current-repo\java-solutions\expression\exceptions\CheckedDivide.java:39: error: rightBracket is not public in Binary; cannot be accessed from outside package
        super.rightBracket = super.childHasLowerPriority(right);
             ^
__current-repo\java-solutions\expression\exceptions\CheckedDivide.java:41: error: rightBracket is not public in Binary; cannot be accessed from outside package
            rightBracket = true;
            ^
__current-repo\java-solutions\expression\exceptions\CheckedMultiply.java:31: error: leftBracket is not public in Binary; cannot be accessed from outside package
        leftBracket = super.childHasLowerPriority(left);
        ^
__current-repo\java-solutions\expression\exceptions\CheckedMultiply.java:36: error: rightBracket is not public in Binary; cannot be accessed from outside package
        super.rightBracket = super.childHasLowerPriority(right);
             ^
__current-repo\java-solutions\expression\exceptions\CheckedMultiply.java:38: error: rightBracket is not public in Binary; cannot be accessed from outside package
            rightBracket = true;
            ^
__current-repo\java-solutions\expression\exceptions\CheckedNegate.java:31: error: sonBracket is not public in Unary; cannot be accessed from outside package
        super.sonBracket = getSon().getType().getPriority() > getType().getPriority();
             ^
__current-repo\java-solutions\expression\exceptions\CheckedSubtract.java:27: error: leftBracket is not public in Binary; cannot be accessed from outside package
        super.leftBracket = super.childHasLowerPriority(left);
             ^
__current-repo\java-solutions\expression\exceptions\CheckedSubtract.java:32: error: rightBracket is not public in Binary; cannot be accessed from outside package
        super.rightBracket = super.childHasLowerPriority(right);
             ^
__current-repo\java-solutions\expression\exceptions\CheckedSubtract.java:34: error: rightBracket is not public in Binary; cannot be accessed from outside package
            super.rightBracket = true;
                 ^
__current-repo\java-solutions\expression\exceptions\MAX.java:36: error: leftBracket is not public in Binary; cannot be accessed from outside package
        super.leftBracket = super.childHasLowerPriority(left);
             ^
__current-repo\java-solutions\expression\exceptions\MAX.java:41: error: rightBracket is not public in Binary; cannot be accessed from outside package
        super.rightBracket = super.childHasLowerPriority(right);
             ^
__current-repo\java-solutions\expression\exceptions\MAX.java:43: error: rightBracket is not public in Binary; cannot be accessed from outside package
            super.rightBracket = true;
                 ^
__current-repo\java-solutions\expression\exceptions\MAX.java:49: error: cannot find symbol
        return ExpType.MAX;
                      ^
  symbol:   variable MAX
  location: class ExpType
__current-repo\java-solutions\expression\exceptions\MIN.java:35: error: leftBracket is not public in Binary; cannot be accessed from outside package
        super.leftBracket = super.childHasLowerPriority(left);
             ^
__current-repo\java-solutions\expression\exceptions\MIN.java:40: error: rightBracket is not public in Binary; cannot be accessed from outside package
        super.rightBracket = super.childHasLowerPriority(right);
             ^
__current-repo\java-solutions\expression\exceptions\MIN.java:42: error: rightBracket is not public in Binary; cannot be accessed from outside package
            super.rightBracket = true;
                 ^
__current-repo\java-solutions\expression\exceptions\MIN.java:48: error: cannot find symbol
        return ExpType.MIN;
                      ^
  symbol:   variable MIN
  location: class ExpType
24 errors

 */