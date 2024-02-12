package steps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CharacterOccurences {
    public static void main(String[] args) {

        String str = "Hello World";
        Map<Character, Integer> occurrences = new HashMap<>();

        for(char ch : str.toCharArray()){
            occurrences.put(ch, occurrences.getOrDefault(ch, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : occurrences.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        for (char c = 'a'; c <= 'Z'; c++){
            System.out.print(c + " ");
        }

//        int[] numbers = {0,2,4,9,12,24,11,11};
//        for (int number : numbers)
//            System.out.print(number + " ");
//        Arrays.sort(numbers);
//        System.out.println(Arrays.toString(numbers));

//        String str = "level";
//        String reverseStr = "";
//        for (int i = (str.length() - 1); i >= 0; i--)
//            reverseStr = reverseStr + str.charAt(i);
//
//        System.out.println(reverseStr);
//
//        if (str.equals(reverseStr))
//            System.out.println("Palindrome");
//        else
//            System.out.println("Not a Palindrome");
        int[] numbers = {1,1,0,3,4,5,4,6,7,3};

        HashSet<Integer> set = new HashSet<>();
        for (int number : numbers){
            set.add(number);
        }

        // Create a new array with a size equal to the hashSet
        int[] uniqueNumbers = new int[set.size()];
        int index = 0;

        for (int element : set)
            uniqueNumbers[index++] = element;
        System.out.println(Arrays.toString(uniqueNumbers));
    }
}
