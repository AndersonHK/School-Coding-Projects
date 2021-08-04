/* This is a simple Java program to check whether 3 given positive integers are a Pythagorean triplet.
Call this file "KleinAndersonA1.java". Use it by typing java KleinAndersonA1 3 4 5 in the terminal. */
class KleinAndersonA1
{
    //The Main Function
    public static void main(String args[]){

        //Sending an extra new line so it looks nicer
        System.out.println();

        //Setting Variables
        String Exit_text = "Program Completed";

        //Safeguard in case user gave wrong type of input
        try {
            //Convert string arguments into integers
            int Numbers[] = new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])};

            //Checking if user put more than 3 inputs
            try {
                args[3] = args[3];
                //If it doesn't jump to the catch, the user gave at least one parameter too many.
                System.out.println("You gave too many parameters. This program takes only 3.");
                System.out.println("Write them as ( KleinAndersonA1 3 4 5 ) in the terminal.");
                System.out.println(Exit_text);
                return;
            }
            catch (Exception e)
            {
                //Horray, there is exactly 3 numbers.
            }

            //Check if numbers are valid, it passes, it sounds like the user gave valid numbers
            if (Numbers[0] > 0 && Numbers[1] > 0 && Numbers[2] > 0) {

                //Calling a simple function to check if given numbers are Pythagorean triplets
                boolean Is_it_a_pythagorean_triplet_boolean = is_this_a_Pythagorean_triplet(Numbers);

                //Using the above boolean to give proper feedback to user
                if (Is_it_a_pythagorean_triplet_boolean == true) {
                    System.out.format("The given triplet (%d,%d,%d) is a Pythagorean triplet.", Numbers[0], Numbers[1], Numbers[2]);
                    System.out.println();
                } else {
                    System.out.format("The given triplet (%d,%d,%d) is not a Pythagorean triplet.", Numbers[0], Numbers[1], Numbers[2]);
                    System.out.println();
                }

                //Program execution completed
                System.out.println(Exit_text);
            }
            else {
                //If it didn't pass, the user probably gave wrong input like negative numbers or letters...
                System.out.println("You did not give the proper parameters.");
                System.out.println("Arguments were: " + args[0] + " " + args[1] + " " + args[2]);
                System.out.println("Write them as ( KleinAndersonA1 3 4 5 ) in the terminal.");
                System.out.println(Exit_text);
            }
        }
        //Catch wrong type of input
        catch (Exception e)
        {
            System.out.println("You did not give the proper parameters.");
            System.out.println("Write them as ( KleinAndersonA1 3 4 5 ) in the terminal.");
            System.out.println(Exit_text);
        }
    }
    //This function is called to determine whether an array of 3 positive integers is a valid Pythagorean_triplet
    private static boolean is_this_a_Pythagorean_triplet(int Three_Valid_Numbers[]) {
        if (Three_Valid_Numbers[0]*Three_Valid_Numbers[0] + Three_Valid_Numbers[1]*Three_Valid_Numbers[1]== Three_Valid_Numbers[2]*Three_Valid_Numbers[2]) {
            return true;
        } else {
            return false;
        }
    }
}

/*===============================================================================================================
Sources are:
How to make a basic java program: https://www.geeksforgeeks.org/beginning-java-programming-with-hello-world-example/
How to use IntelliJ: https://www.javatpoint.com/intellij-idea-first-java-program
How to use arrays: https://www.tutorialspoint.com/Java-command-line-arguments
How to convert strings into ints: https://www.mkyong.com/java/java-convert-string-to-int/
How to create new complex arrays: https://www.geeksforgeeks.org/arrays-in-java/
Documentation on string_format: https://docs.oracle.com/javase/6/docs/api/java/util/Formatter.html#syntax
Tutorial on string_format: https://stackoverflow.com/questions/7641288/using-strings-and-variables-in-println-statements
Dictionary on Java Exceptions: https://www.w3schools.com/java/java_try_catch.asp
===============================================================================================================*/