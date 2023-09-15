package challenge;

import domain.Pair;

import java.math.BigInteger;

public class BigIntChallenges {

    public static Pair<BigInteger, BigInteger> splitBigNumber(BigInteger n, BigInteger k) {
        // split n to x and y, y = x + k

        var x = n.subtract(k).divide(BigInteger.valueOf(2));
        var y = x.add(k);

        return new Pair<>(x, y);
    }

}
