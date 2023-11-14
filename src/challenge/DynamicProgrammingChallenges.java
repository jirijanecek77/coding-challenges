package challenge;

public class DynamicProgrammingChallenges {

    public static boolean abbreviation(String a, String b) {

        int indexA = 0, indexB = 0;
        while (indexA < a.length() && indexB < b.length()) {
            if (Character.toUpperCase(a.charAt(indexA)) == b.charAt(indexB)) {
                indexA++;
                indexB++;
            } else if (Character.isLowerCase(a.charAt(indexA))) {
                indexA++;
            } else {
                return false;
            }
        }
        return indexB == b.length() && a.substring(indexA).chars().allMatch(Character::isLowerCase);
    }
}
