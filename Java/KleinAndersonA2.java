/* This is a simple Java program to search and sort arrays.
Call this file "KleinAndersonA2.java". Use it by typing java KleinAndersonA2 in the terminal. */

//Importing the necessary scanner library
import java.util.Scanner;

class KleinAndersonA2 {
    //The Main Function
    public static void main(String args[]) {

        //What the program says when you exit
        String Exit_text = "Program Completed";

        //Creating the input detector
        Scanner input = new Scanner(System.in);

        //Flag for user input loop
        int flag_input_complete = 0;

        //Program startup message
        System.out.println();
        System.out.print("Please enter length of an Array: ");

        //Loop until user gave proper size for the array
        int array_size = get_valid_natural_number_input();

        System.out.println();
        System.out.format("Please enter %d elements for the array: ",array_size);

       int unsorted_array[] = new int[array_size];

        //Get user input for each number in the array
        for(int i = 0; i < array_size; i++) {

            //validate input for a given number in the array
            unsorted_array[i] = get_valid_integer_number_input(0);

            //Feedback on each input
            if(i<array_size-1) {
                //System.out.println();
                System.out.format("The element %d is %d. Next element: ", i, unsorted_array[i]);
            }
        }

        //Converting array to string so it can be outputed
        String unsorted_array_string = stringify_array(array_size,unsorted_array);

        //Outputing array to the user
        System.out.println();
        System.out.println("The input array is: ["+unsorted_array_string+"].");
        System.out.println();

        while(flag_input_complete == 0) {

            //Requesting number to be searched
            System.out.print("Please enter the number to be searched: ");

            //Checking if user gave proper integer as number to be searched
            int Number_to_be_searched = get_valid_integer_number_input(1);

            //Finding position of given element in array
            int location_of_element = indexOfInt(unsorted_array, Number_to_be_searched);

            //Check if element was found
            if(location_of_element == -1){
                System.out.println();
                System.out.println("The element was not found in the array.");
            }
            else {
                //Send feedback out to the user
                System.out.println();
                System.out.println("The element was found at index: " + location_of_element);
                flag_input_complete = 1;
            }
        }

        //Sorting the array
        int sorted_array[] = sortArrayOfInts(unsorted_array);

        //Stringifying output
        String sorted_array_string = stringify_array(array_size,sorted_array);

        //Showing output
        //System.out.println();
        System.out.println("The sorted array is: ["+sorted_array_string+"]");

        //Removing duplicates
        int duplicate_free_array[] = removeDuplicateInts(sorted_array);

        //Stringifying output
        array_size = duplicate_free_array.length;
        String duplicate_free_array_string = stringify_array(array_size,duplicate_free_array);

        //System.out.println();
        System.out.println("The array after removing duplicate elements is: ["+duplicate_free_array_string+"]");

        //Program is complete
        System.out.println();
        System.out.println(Exit_text);

    }

    //Function to remove duplicates
    private static int[] removeDuplicateInts(int unsorted_array[]) {

        //Generating array_size
        int array_size = unsorted_array.length;

        //Create new array for modifying
        int duplicate_free_array[] = unsorted_array.clone();

        //Next duplicate to remove
        int int_to_remove = -1;

        //Checking if any of the variables has a duplicate
        for(int i = 0; i < array_size; i++) {
            int_to_remove = findDuplicateInt(duplicate_free_array,duplicate_free_array[i]);
            if (int_to_remove != -1) {
                duplicate_free_array = popInt(duplicate_free_array, int_to_remove);
                array_size = duplicate_free_array.length;
                i = i-1;
            }
        }

        //Returning the duplicate free array
        return duplicate_free_array;
    }

    //Function to sort arrays
    private static int[] sortArrayOfInts(int unsorted_array[]) {

        //Generating array_size
        int array_size = unsorted_array.length;

        int sorted_array[] = unsorted_array.clone();

        int min_int;
        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            min_int = findMinInt(unsorted_array);
            sorted_array[i] = min_int;
            unsorted_array = popInt(unsorted_array,indexOfInt(unsorted_array,min_int));
        }

        //the array is now sorted
        return sorted_array;
    }

    //Function to remove int from array
    private static int[] popInt(int old_array[],int int_to_remove) {

        //Generating array_size
        int array_size = old_array.length;

        //Create the new array with one less int
        int new_array[] = new int[array_size-1];

        //Have we removed the int?
        boolean flag = false;

        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            if(i!=int_to_remove){
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
    private static int indexOfInt(int unsorted_array[],int int_to_search) {

        //Generating array_size
        int array_size = unsorted_array.length;

        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            if(unsorted_array[i] == int_to_search){
                return i;
            }
        }

        //int was not found
        return -1;

    }

    //Function to find location of duplicate element in array
    private static int findDuplicateInt(int unsorted_array[],int int_to_search) {

        //Generating array_size
        int array_size = unsorted_array.length;

        //Is it a duplicate?
        boolean flag = false;

        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            if(flag == true && unsorted_array[i] == int_to_search) {
                return i;
            }
            if(unsorted_array[i] == int_to_search){
                flag = true;
            }
        }

        //int was not found
        return -1;

    }

    //Function to find mininum variable in an array
    private static int findMinInt(int arrayToSearch[]) {

        //Generating array_size
        int array_size = arrayToSearch.length;

        //Minimum variable to date
        int min_variable = arrayToSearch[0];

        //Main loop
        for(int i = 1; i < array_size; i++) {
            if(min_variable > arrayToSearch[i]) {
                min_variable = arrayToSearch[i];
            }
        }

        //Return the minimum variable
        return min_variable;

    }

    //This function will get the proper input for the array size
    private static int get_valid_natural_number_input() {

        //Size of Array
        int array_size = 0;

        //Flag for user input loop
        int flag_input_complete = 0;

        //Creating the input detector
        Scanner input = new Scanner(System.in);

        while(flag_input_complete == 0) {

            //Trying to get user input
            try {

                //Get user input
                array_size = input.nextInt();

                //Checking if number is positive, can't have negative number of items in array
                while (array_size < 1) {

                    //Trying to get user input
                    System.out.println();
                    System.out.println("You have entered a negative number or 0.");
                    System.out.print("Please enter a positive integer for the length of the Array: ");
                    array_size = input.nextInt();
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
                System.out.print("Please enter length of an Array as a natural number: ");

            }
        }

        //Returning the validated user input
        return array_size;
    }

    //This function will get the proper input for the array size
    private static int get_valid_integer_number_input(int mode) {

        //Size of Array
        int array_elements = 0;

        //Flag for user input loop
        int flag_input_complete = 0;

        //Creating the input detector
        Scanner input = new Scanner(System.in);

        while(flag_input_complete == 0) {

            //Trying to get user input
            try {

                //Get user input
                array_elements = input.nextInt();

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
                if(mode == 0){
                    System.out.print("Please enter the next of an Array as a integer number: ");
                }
                else{
                    System.out.print("Please enter the integer element to be searched: ");
                }

            }
        }

        //Returning the validated user input
        return array_elements;
    }

    //Simple function to "stringify" an array into a string
    private static String stringify_array(int array_size,int unsorted_array[]) {

        //Creating blank string
        String new_string = "";

        //Add each element of array into the string
        for(int i = 0; i < array_size; i++) {
            if(i==0){
                new_string = new_string + Integer.toString(unsorted_array[i]);
            }
            else {
                new_string = new_string + ", " + Integer.toString(unsorted_array[i]);
            }
        }

        //return the string
        return new_string;

    }
}

/*===============================================================================================================
Sources are:
Loops in Java: https://www.w3schools.com/java/java_for_loop.asp
Getting new input on scan: https://stackoverflow.com/questions/5333110/checking-input-type-how
Scan utility functions: https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html
Length function in arrays: https://www.edureka.co/blog/array-length-in-java/
How to properly clone arrays: https://www.baeldung.com/java-array-copy
===============================================================================================================*/