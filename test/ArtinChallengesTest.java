import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtinChallengesTest {

    @Test
    void test1() {
        int[] data = {2, 4, 10, -11, -8};
        int result = ArtinChallenges.getClosestToZero(data);
        assertEquals(2, result);
    }


    @Test
    void test2() {
        int[] data = {5, 4, 0, 0, -1, 0, 2};
        int result = ArtinChallenges.countChunks(data);
        assertEquals(3, result);
    }

    @Test
    void test3() {
        List<ArtinChallenges.Node> children2 = new ArrayList<>();
        children2.add(new ArtinChallenges.NodeC(5, null));
        children2.add(new ArtinChallenges.NodeC(6, null));

        List<ArtinChallenges.Node> children = new ArrayList<>();
        children.add(new ArtinChallenges.NodeC(2, children2));
        children.add(new ArtinChallenges.NodeC(3, null));


        ArtinChallenges.NodeC root = new ArtinChallenges.NodeC(1, children);

        int result = ArtinChallenges.getLevelSum(root, 2);
        assertEquals(11, result);
    }

    @Test
    void test4() {
        int[] data = {11, 14, 12, 13};
        List<Integer> result = ArtinChallenges.getReversalsToSort(data);
        assertEquals(3, result.size());
        assertEquals(2, result.get(0));
        assertEquals(4, result.get(1));
        assertEquals(3, result.get(2));
    }

    @Test
    void testAmazonFindMax2DigitNumberInString() {
        int result = ArtinChallenges.amazonFindMax2DigitNumberInString("54326254610");
        assertEquals(62, result);
    }

    @Test
    void testAmazonFindMax2DigitNumberInString_empty() {
        int result = ArtinChallenges.amazonFindMax2DigitNumberInString("");
        assertEquals(0, result);
    }

    @Test
    void testAmazonFindMax2DigitNumberInString_one() {
        int result = ArtinChallenges.amazonFindMax2DigitNumberInString("1");
        assertEquals(1, result);
    }

    @Test
    void testAmazonGraph() {
        int[] A = {1, 3, 2, 4};
        int[] B = {4, 1, 3, 2};

        assertTrue(ArtinChallenges.amazonGraph(A, B));
    }
}
