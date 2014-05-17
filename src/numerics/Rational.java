/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package numerics;

/**
 *
 * @author blue
 */
public class Rational {

    int num;
    int den;

    public Rational(int n) {
        num = n;
        den = 1;
    }

    public Rational(int n, int d) {
        num = n;
        den = d;
    }

    @Override
    public String toString() {
        if ( den == 1 ) {
            return num + "";
        } else {
            return num + "/" + den;
        }
    }

    public double value() {
        return ( num * 1.0) / ( den * 1.0 );
    }

    public void divide(int n) {
        den = den * n;
        reduce();
    }

    public void times(int n) {
        num = num * n;
        reduce();
    }

    public Rational subtract(Rational r) {
        Rational s = this;
        int rm = r.den;
        int sm = s.den;
        r.den = r.den*sm;
        r.num = r.num*sm;
        s.den = s.den*rm;
        s.num = s.num*rm;
        Rational result = new Rational(s.num-r.num,s.den);
        result.reduce();
        s.reduce();
        r.reduce();
        return result;
    }

    private void reduce() {
        if ( ( num % 2 == 0 ) & ( den % 2 == 0 ) ) {
            num = num / 2; den = den / 2; reduce();
        } else if ( ( num % 3 == 0 ) & ( den % 3 == 0 ) ) {
            num = num / 3; den = den / 3; reduce();
        } else if ( ( num % 5 == 0 ) & ( den % 5 == 0 ) ) {
            num = num / 5; den = den / 5; reduce();
        } else if ( ( num % 7 == 0 ) & ( den % 7 == 0 ) ) {
            num = num / 7; den = den / 7; reduce();
        } else if ( ( num % 11 == 0 ) & ( den % 11 == 0 ) ) {
            num = num / 11; den = den / 11; reduce();
        } else if ( ( num % 13 == 0 ) & ( den % 13 == 0 ) ) {
            num = num / 13; den = den / 13; reduce();
        } else if ( ( num % 17 == 0 ) & ( den % 17 == 0 ) ) {
            num = num / 17; den = den / 17; reduce();
        } else if ( ( num % 19 == 0 ) & ( den % 19 == 0 ) ) {
            num = num / 19; den = den / 19; reduce();
        }
    }

    public boolean one() {
       return ( ( num == 1 ) & ( den == 1 ) );
    }

    public boolean two() {
       return ( ( num == 2 ) & ( den == 1 ) );
    }

    public boolean onehalf() {
       return ( ( num == 1 ) & ( den == 2 ) );
    }

    public boolean three() {
       return ( ( num == 3 ) & ( den == 1 ) );
    }

    public boolean onethird() {
       return ( ( num == 1 ) & ( den == 3 ) );
    }

    public boolean four() {
       return ( ( num == 4 ) & ( den == 1 ) );
    }

    public boolean onefourth() {
       return ( ( num == 1 ) & ( den == 4 ) );
    }

    public boolean five() {
       return ( ( num == 5 ) & ( den == 1 ) );
    }

    public boolean onefifth() {
       return ( ( num == 1 ) & ( den == 5 ) );
    }

    public boolean twofifths() {
       return ( ( num == 2 ) & ( den == 5 ) );
    }

    public boolean fivehalves() {
       return ( ( num == 5 ) & ( den == 2 ) );
    }

    public boolean six() {
       return ( ( num == 6 ) & ( den == 1 ) );
    }

    public boolean onesixth() {
       return ( ( num == 1 ) & ( den == 6 ) );
    }

    public boolean seven() {
       return ( ( num == 7 ) & ( den == 1 ) );
    }

    public boolean oneseventh() {
       return ( ( num == 1 ) & ( den == 7 ) );
    }

    public boolean eight() {
       return ( ( num == 8 ) & ( den == 1 ) );
    }

    public boolean oneeighth() {
       return ( ( num == 1 ) & ( den == 8 ) );
    }

    public boolean nine() {
       return ( ( num == 9 ) & ( den == 1 ) );
    }

    public boolean onenineth() {
       return ( ( num == 1 ) & ( den == 9 ) );
    }

    public boolean twothirds() {
       return ( ( num == 2 ) & ( den == 3 ) );
    }

    public boolean threehalves() {
       return ( ( num == 3 ) & ( den == 2 ) );
    }

    public boolean threesevenths() {
       return ( ( num == 3 ) & ( den == 7 ) );
    }

    public boolean seventhirds() {
       return ( ( num == 7 ) & ( den == 3 ) );
    }

    public boolean threefifths() {
       return ( ( num == 3 ) & ( den == 5 ) );
    }

    public boolean fivethirds() {
       return ( ( num == 5 ) & ( den == 3 ) );
    }

    public boolean fiveeighths() {
       return ( ( num == 5 ) & ( den == 8 ) );
    }

    public boolean eightfifths() {
       return ( ( num == 8 ) & ( den == 5 ) );
    }

    public boolean threefourths() {
       return ( ( num == 3 ) & ( den == 4 ) );
    }

    public boolean fourthirds() {
       return ( ( num == 4 ) & ( den == 3 ) );
    }

    public boolean ninehalves() {
       return ( ( num == 9 ) & ( den == 2 ) );
    }

    public boolean thirteenthirds() {
       return ( ( num == 13 ) & ( den == 3 ) );
    }

    public boolean threethirteenths() {
       return ( ( num == 3 ) & ( den == 13 ) );
    }

    public boolean fifteenhalves() {
       return ( ( num == 15 ) & ( den == 2 ) );
    }

    public boolean twofifteenths() {
       return ( ( num == 2 ) & ( den == 15 ) );
    }

    public boolean thirteenfifths() {
       return ( ( num == 13 ) & ( den == 5 ) );
    }

    public boolean fivethirteenths() {
       return ( ( num == 5 ) & ( den == 13 ) );
    }

    public boolean eq(String rn) {
        if ( rn.equals("1") ) {
            return one();
        } else if ( rn.equals("2") ) {
            return two();
        } else if ( rn.equals("1/2") ) {
            return onehalf();
        } else if ( rn.equals("3") ) {
            return three();
        } else if ( rn.equals("1/3") ) {
            return onethird();
        } else if ( rn.equals("4") ) {
            return four();
        } else if ( rn.equals("1/4") ) {
            return onefourth();
        } else if ( rn.equals("5") ) {
            return five();
        } else if ( rn.equals("1/5") ) {
            return onefifth();
        } else if ( rn.equals("2/5") ) {
            return twofifths();
        } else if ( rn.equals("5/2") ) {
            return fivehalves();
        } else if ( rn.equals("6") ) {
            return six();
        } else if ( rn.equals("1/6") ) {
            return onesixth();
        } else if ( rn.equals("7") ) {
            return seven();
        } else if ( rn.equals("1/7") ) {
            return oneseventh();
        } else if ( rn.equals("8") ) {
            return eight();
        } else if ( rn.equals("1/8") ) {
            return oneeighth();
        } else if ( rn.equals("2/3") ) {
            return twothirds();
        } else if ( rn.equals("3/2") ) {
            return threehalves();
        } else if ( rn.equals("3/7") ) {
            return threesevenths();
        } else if ( rn.equals("7/3") ) {
            return seventhirds();
        } else if ( rn.equals("3/5") ) {
            return threefifths();
        } else if ( rn.equals("5/3") ) {
            return fivethirds();
        } else if ( rn.equals("5/8") ) {
            return fiveeighths();
        } else if ( rn.equals("8/5") ) {
            return eightfifths();
        } else if ( rn.equals("3/4") ) {
            return threefourths();
        } else if ( rn.equals("4/3") ) {
            return fourthirds();
        } else if ( rn.equals("9") ) {
            return nine();
        } else if ( rn.equals("1/9") ) {
            return onenineth();
        } else if ( rn.equals("9/2") ) {
            return ninehalves();
        } else if ( rn.equals("13/3") ) {
            return thirteenthirds();
        } else if ( rn.equals("3/13") ) {
            return threethirteenths();
        } else if ( rn.equals("15/2") ) {
            return fifteenhalves();
        } else if ( rn.equals("2/15") ) {
            return twofifteenths();
        } else if ( rn.equals("13/5") ) {
            return thirteenfifths();
        } else if ( rn.equals("5/13") ) {
            return fivethirteenths();
        } else {
            return false;
        }
    }

}
