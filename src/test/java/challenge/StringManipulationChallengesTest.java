package challenge;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void test_longest_common_substring() {
        int result = StringManipulationChallenges.longestStringSubsequence("HARRY", "SALLY");

        assertEquals(2, result);
    }

    @Test
    void test_firstUniqueChar_no() {
        int result = StringManipulationChallenges.firstUniqueChar("caabbc");

        assertEquals(Character.MIN_VALUE, result);
    }

    @Test
    void test_firstUniqueChar_found() {
        int result = StringManipulationChallenges.firstUniqueChar("codingminutes");

        assertEquals('c', result);
    }

    @Test
    void test_longestPalindrome() {
        assertEquals("bab", StringManipulationChallenges.longestPalindrome("babad"));
        assertEquals("bb", StringManipulationChallenges.longestPalindrome("bb"));
    }

    @Test
    void suggestedProducts() {
        assertEquals(List.of(
                        List.of("mobile", "moneypot", "monitor"), List.of("mobile", "moneypot", "monitor"),
                        List.of("mouse", "mousepad"), List.of("mouse", "mousepad"), List.of("mouse", "mousepad")
                ),
                StringManipulationChallenges.suggestedProducts(new String[]{"mobile", "mouse", "moneypot", "monitor", "mousepad"}, "mouse"));
    }

    @Test
    void gcdOfStrings() {
        assertEquals("ABC", StringManipulationChallenges.gcdOfStrings("ABCABC", "ABC"));
        assertEquals("AB", StringManipulationChallenges.gcdOfStrings("ABABAB", "ABAB"));
        assertEquals("", StringManipulationChallenges.gcdOfStrings("ABCDEF", "ABC"));
    }

    @Test
    void compress() {
        assertEquals(6, StringManipulationChallenges.compress(new char[]{'a', 'a', 'b', 'b', 'c', 'c', 'c'}));
        assertEquals(4, StringManipulationChallenges.compress(new char[]{'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'}));
        assertEquals(1, StringManipulationChallenges.compress(new char[]{'a'}));
        assertEquals(3, StringManipulationChallenges.compress(new char[]{'a', 'b', 'c'}));
        assertEquals(4, StringManipulationChallenges.compress(new char[]{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}));
    }
}