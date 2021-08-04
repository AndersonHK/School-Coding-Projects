/* This is a simple Java program to split sentences and return whether they match and some more stuff
Call this file "KleinAndersonA3.java". Use it by typing java KleinAndersonA3 in the terminal. */

//Importing the necessary scanner library
import java.util.Scanner;

class KleinAndersonA3 {
    //The Main Function
    public static void main(String args[]) {
        //Setting up string variables
        String string1 = "I am a student from Santa Clara University.";
        String string2 = "I am a Graduate student.";
        String string3 = "The little drops of water, little grains of sand, Make the mighty ocean and the beauteous land. And the little moments, Humble though they be, Make the mighty ages of eternity. so our little errors lead the soul away.";

        //What the program says when you exit
        String Exit_text = "Program Completed";

        //Error meesage
        String Error_message = "Please enter valid start index and end index such that start index < end index and end index <= length of the ";

        //Creating the input detector
        Scanner input = new Scanner(System.in);

        //Flag for user input loop
        int flag_input_complete = 0;

        //Program startup message
        System.out.println();
        System.out.println("String 1 is: " + string1);
        System.out.println("String 2 is: " + string2);

        //Requesting user for input
        System.out.println();
        int string1_start = get_valid_natural_number_input_within_bounds("Enter start index for string1 - ",Error_message+"string1.",0,string1.length()-1);
        int string1_end = get_valid_natural_number_input_within_bounds("Enter end index for string1 - ",Error_message+"string1.",string1_start,string1.length());
        int string2_start = get_valid_natural_number_input_within_bounds("Enter start index for string2 - ",Error_message+"string2.",0,string2.length()-1);
        int string2_end = get_valid_natural_number_input_within_bounds("Enter end index for string2 - ",Error_message+"string2.",string2_start,string2.length());

        //Generating Substrings
        String substring1 = string1.substring(string1_start, string1_end);
        String substring2 = string2.substring(string2_start, string2_end);

        //Giving back results
        System.out.println();
        System.out.println("Substring1 is: " + substring1);
        System.out.println("Substring2 is: " + substring2);

        //Showing whether stirngs are equal
        System.out.println("Are the two substrings equal: "+substring1.equalsIgnoreCase(substring2));

        //Counting how many times little shows up in string 3
        String string3_words[] = Wordify_string(string3);


        //Counting the instances of little in string3
        int instances_of_little = count_words_in_array(string3_words,"little");
        System.out.println("Occurrences of little in string3: "+instances_of_little);

        //Find the longest word in string3
        int position_of_longest_word = find_longest_word(string3_words);
        System.out.println("Longest Word in string 3: "+string3_words[position_of_longest_word]);

        //Create a string version of the paragraph, but with words reversed and captalized
        String puncutation = ",.!?;:";
        String reversed_string3 = "";
        for(int i = 0; i < string3_words.length; i++){
            if(puncutation.indexOf(string3_words[i])!=-1){
                reversed_string3 = string3_words[i].toUpperCase()+reversed_string3;
            }
            else {
                reversed_string3 = string3_words[i].toUpperCase()+" "+reversed_string3;
            }
        }
        System.out.println("Reversed Capital Paragraph: "+reversed_string3);

        //Print exit text before exiting
        System.out.println();
        System.out.println(Exit_text);

    }

    //Finds longest word in an array of strings
    private static int find_longest_word (String arrayToSearch[]){

        //Generating array_size
        int array_size = arrayToSearch.length;

        //Longest variable to date
        int longest_word_position = 0;
        String longest_word = arrayToSearch[0];

        //Main loop
        for(int i = 1; i < array_size; i++) {
            if(longest_word.length() < arrayToSearch[i].length()) {
                longest_word_position = i;
                longest_word = arrayToSearch[i];
            }
        }

        //returning the position of the longest world
        return longest_word_position;
    }

    //Convert string into array of words
    private static String[] Wordify_string (String raw_text){

        //setting variables
        String Words[] = new String[0];
        String Words_Old[] = new String[0];
        int next_punctuation = 0;
        int next_space = 0;

        //while text lasts do:
        while(raw_text.length()>0){

            //look for the next punctuation
            int next_punctuation_array[] = {raw_text.indexOf("."),raw_text.indexOf(";"),raw_text.indexOf(","),raw_text.indexOf(":"),raw_text.indexOf("!"),raw_text.indexOf("?")};
            next_punctuation = findMinIntModified(next_punctuation_array);

            //Look for next space
            next_space = raw_text.indexOf(" ");

            //if not found disconsider for next check and set flag
            if(next_punctuation == -1){
                next_punctuation = 999999;
            }
            if(next_space == -1){
                next_space = 999999;
            }

            //check flag if this is the last word in the document
            if(next_punctuation == 999999 && next_space == 999999) {
                Words_Old = Words.clone();
                Words = new String[Words_Old.length+1];
                for(int i=0; i<Words_Old.length; i++){
                    Words[i] = Words_Old[i];
                }
                Words[Words.length-1] = raw_text;

                //Return the finalized array
                return Words;
            }

            //Don't add blank values
            if(next_space==0){
                raw_text = raw_text.substring(1);
            }

            //Add words between spaces
            else {
                if (next_punctuation > next_space) {
                    Words_Old = Words.clone();
                    Words = new String[Words_Old.length + 1];
                    for (int i = 0; i < Words_Old.length; i++) {
                        Words[i] = Words_Old[i];
                    }
                    Words[Words.length - 1] = raw_text.substring(0, next_space);
                    raw_text = raw_text.substring(next_space + 1);
                }

                //Add words between punctuations
                else {
                    Words_Old = Words.clone();
                    Words = new String[Words_Old.length + 2];
                    for (int i = 0; i < Words_Old.length; i++) {
                        Words[i] = Words_Old[i];
                    }
                    Words[Words.length - 2] = raw_text.substring(0, next_punctuation);
                    Words[Words.length - 1] = raw_text.substring(next_punctuation, next_punctuation + 1);
                    raw_text = raw_text.substring(next_punctuation + 1);
                }
            }
        }

        //Should only ever get here if the array is null
        return Words;
    }

    //This function will get the proper input for index
    private static int get_valid_natural_number_input_within_bounds(String try_again, String error_message, int lower_bound, int upper_bound) {

        //Size of Array
        int natural_number = 0;

        //Flag for user input loop
        int flag_input_complete = 0;

        //Creating the input detector
        Scanner input = new Scanner(System.in);

        while(flag_input_complete == 0) {

            //Trying to get user input
            try {

                //Get user input
                System.out.print(try_again);
                natural_number = input.nextInt();

                //Check if it is within bounds
                while (natural_number < lower_bound || natural_number > upper_bound){

                    System.out.println();
                    System.out.println(error_message);
                    System.out.print(try_again);
                    natural_number = input.nextInt();

                }

                //Horray, we got our array size as a valid number for sure!
                flag_input_complete = 1;

            }

            //This will show up if the user gives improper input
            catch (Exception e) {

                //Clear user input buffer so it waits for next input
                input.next();

                //The user typed something other than an integer, give back error message
                System.out.println();
                System.out.println("This is not an integer, please try again.");

            }
        }

        //Returning the validated user input
        return natural_number;
    }

    //Function to find mininum variable in an array
    private static int findMinIntModified(int arrayToSearch[]) {

        //Generating array_size
        int array_size = arrayToSearch.length;

        //Minimum variable to date
        int min_variable = arrayToSearch[0];
        if(min_variable == -1){
            min_variable = 999999;
        }

        //Main loop
        for(int i = 1; i < array_size; i++) {
            if(min_variable > arrayToSearch[i] && arrayToSearch[i] != -1) {
                min_variable = arrayToSearch[i];
            }
        }

        //Return the minimum variable
        return min_variable;

    }

    //Function to count instances of words in an array
    private static int count_words_in_array(String unsorted_array[], String word_to_remove) {

        //Generating array_size
        int array_size = unsorted_array.length;

        //Create new array for modifying
        String clean_array[] = unsorted_array.clone();

        //Next duplicate to remove
        int string_to_remove = -1;

        int instances_of_word = 0;

        //Checking if any of the variables has a duplicate
        for(int i = 0; i < array_size; i++) {
            string_to_remove = indexOfString(clean_array,word_to_remove);
            if (string_to_remove != -1) {
                clean_array = popString(clean_array, string_to_remove);
                array_size = clean_array.length;
                i = i-1;
                instances_of_word++;
            }
        }

        //Returning the duplicate free array
        return instances_of_word;
    }

    //Function to remove int from array
    private static String[] popString(String old_array[],int string_to_remove) {

        //Generating array_size
        int array_size = old_array.length;

        //Create the new array with one less int
        String new_array[] = new String[array_size-1];

        //Have we removed the int?
        boolean flag = false;

        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            if(i!=string_to_remove){
                if(flag==false){
                    new_array[i] = old_array[i];
                }
                else{
                    new_array[i-1] = old_array[i];
                }
            }
            else{
                flag = true;
            }
        }

        //Return array without int to remove
        return new_array;
    }
    //Function to find location of element in array
    private static int indexOfString(String unsorted_array[],String string_to_search) {

        //Generating array_size
        int array_size = unsorted_array.length;

        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            if(unsorted_array[i].equalsIgnoreCase(string_to_search)){
                return i;
            }
        }

        //int was not found
        return -1;

    }
}

/*===============================================================================================================
Sources are:
Substring in Java: https://beginnersbook.com/2013/12/java-string-substring-method-example/
Length in Java: https://www.guru99.com/string-length-method-java.html
Compare in Java: https://www.geeksforgeeks.org/compare-two-strings-in-java/
String arrays in Java: https://javadevnotes.com/java-string-array-examples
Strings to uppercase in Java: https://www.javatpoint.com/java-string-touppercase
===============================================================================================================*/