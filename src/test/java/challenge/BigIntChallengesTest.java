package challenge;

import domain.Pair;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigIntChallengesTest {
    @Test
    void splitBigNumber() {
        assertEquals(new Pair<>(
                        new BigInteger("500000002176726726726726675000002117115000000000"),
                        new BigInteger("500000002176726726726727675000002117119234200000")
                ),
                BigIntChallenges.splitBigNumber(
                        new BigInteger("1000000004353453453453454350000004234234234200000"),
                        new BigInteger("1000000000000004234200000")
                )
        );
    }

}