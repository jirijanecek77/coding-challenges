import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringManipulationChallengesTest {

    @Test
    void testIsValidSherlock() {
        String result = StringManipulationChallenges.isValidSherlock("ibfdgaeadiaefgbhbdghhhbgdfgeiccbiehhfcggchgghadhdhagfbahhddgghbdehidbibaeaagaeeigffcebfbaieggabcfbiiedcabfihchdfabifahcbhagccbdfifhghcadfiadeeaheeddddiecaicbgigccageicehfdhdgafaddhffadigfhhcaedcedecafeacbdacgfgfeeibgaiffdehigebhhehiaahfidibccdcdagifgaihacihadecgifihbebffebdfbchbgigeccahgihbcbcaggebaaafgfedbfgagfediddghdgbgehhhifhgcedechahidcbchebheihaadbbbiaiccededchdagfhccfdefigfibifabeiaccghcegfbcghaefifbachebaacbhbfgfddeceababbacgffbagidebeadfihaefefegbghgddbbgddeehgfbhafbccidebgehifafgbghafacgfdccgifdcbbbidfifhdaibgigebigaedeaaiadegfefbhacgddhchgcbgcaeaieiegiffchbgbebgbehbbfcebciiagacaiechdigbgbghefcahgbhfibhedaeeiffebdiabcifgccdefabccdghehfibfiifdaicfedagahhdcbhbicdgibgcedieihcichadgchgbdcdagaihebbabhibcihicadgadfcihdheefbhffiageddhgahaidfdhhdbgciiaciegchiiebfbcbhaeagccfhbfhaddagnfieihghfbaggiffbbfbecgaiiidccdceadbbdfgigibgcgchafccdchgifdeieicbaididhfcfdedbhaadedfageigfdehgcdaecaebebebfcieaecfagfdieaefdiedbcadchabhebgehiidfcgahcdhcdhgchhiiheffiifeegcfdgbdeffhgeghdfhbfbifgidcafbfcd");

        assertEquals("YES", result);
    }

    @Test
    void test_read_and_find_repetitions_one() {
        int result = StringManipulationChallenges.read_and_find_repetitions("AB", "ABBAC");

        assertEquals(1, result);
    }

    @Test
    void test_read_and_find_repetitions_two_at_the_end() {
        int result = StringManipulationChallenges.read_and_find_repetitions("AB", "ABCABABACBCABAB");

        assertEquals(2, result);
    }

    @Test
    void test_read_and_find_repetitions_two() {
        int result = StringManipulationChallenges.read_and_find_repetitions("AB", "ABCABCABABX");

        assertEquals(2, result);
    }

    @Test
    void test_read_and_find_repetitions_none() {
        int result = StringManipulationChallenges.read_and_find_repetitions("AB", "XXX");

        assertEquals(0, result);
    }

    @Test
    void test_count_invalid_boxes() {
        List<List<String>> data = new ArrayList<>();
        data.add(Arrays.asList("cm", "mc"));
        data.add(Arrays.asList("cm", "cm"));
        data.add(Arrays.asList("cm", "mcc"));

        int result = StringManipulationChallenges.count_invalid_boxes(data);

        assertEquals(1, result);
    }
}