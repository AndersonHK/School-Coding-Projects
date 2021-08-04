/* This is a simple Java program check whether a given box is valid for US postal services
Call this file "KleinAndersonA4.java". Use it by typing java KleinAndersonA4 in the terminal. */

class KleinAndersonA4 {

    //Box Class
    public static class Box {

        //setting private variables
        private double length;
        private double heigth;
        private double width;
        private double weight;

        //Creating a cube box (no mass)
        public Box(double side) {
            length = side;
            heigth = side;
            width = side;
            weight = 0;
        }

        //Creating a cube box
        public Box(double side, double weight) {
            length = side;
            heigth = side;
            width = side;
            this.weight = weight;
        }

        //Creating box with 3 inputs (no weight)
        public Box(double length, double heigth, double width) {

            this.length = length;
            this.heigth = heigth;
            this.width = width;
            weight = 0;

        }

        //Creating box variation with 4 inputs and in line code for rearrangingDim()
        public Box(double dimension1, double dimension2, double dimension3, double weight) {

            double dimensions[] = new double[]{dimension1, dimension2, dimension3};
            dimensions = sortArrayOfDoubles(dimensions);

            length = dimensions[2];
            heigth = dimensions[1];
            width = dimensions[0];
            this.weight = weight;

        }

        public Box setWeight(double weight) {
            this.weight = weight;

            return this;
        }

        //Call with no parameters, just reorder internal variables
        public void rearrangeDim()
        {
            double dimensions[] = new double[]{length, heigth, width};
            dimensions = sortArrayOfDoubles(dimensions);

            length = dimensions[2];
            heigth = dimensions[1];
            width = dimensions[0];

        }

        //Calculate perimeter around height/width
        public double calculateGirth()
        {
            double Girth = heigth*2+width*2;

            return Girth;
        }

        //Validating package for US postal rules
        public int validatePackage()
        {
            int validation = 0;

            //Check for weight
            if(weight>70){
                validation++;
            }

            //Check for size
            if(this.calculateGirth()+length>100){
                validation = validation+2;
            }

            return validation;
        }

        //Returns proper text for validation code
        public void print()
        {

            int validation = this.validatePackage();

            //Match code with text
            String codes[] = new String[]{"Package is acceptable","Package is too heavy","Package is too large","Package is too large and too heavy"};

            //Return validation response
            System.out.println();
            System.out.println(codes[validation]+".");
            if(validation == 2 || validation == 3)
            {
                System.out.println("The length of package="+length);
                System.out.println("The girth of package="+this.calculateGirth());
            }
            if(validation == 1 || validation == 3)
            {
                System.out.println("The weight of package="+weight);
            }

        }

    }

    //The main class
    public static void main(String args[]) {

        //What the program says when you exit
        String Exit_text = "Program Completed";

        double Weight[] = new double[4];

        //Safeguard in case user gave wrong type of input
        try {
            //Convert string arguments into integers
            Weight[0] = Double.parseDouble(args[0]);
            Weight[1] = Double.parseDouble(args[1]);
            Weight[2] = Double.parseDouble(args[2]);
            Weight[3] = Double.parseDouble(args[3]);

            //Checking if user put more than 3 inputs
            try {
                args[4] = args[4];
                //If it doesn't jump to the catch, the user gave at least one parameter too many.
                System.out.println();
                System.out.println("You gave too many parameters. This program takes only 4.");
                System.out.println("Write them as ( KleinAndersonA4 40 47.6 90.5 102.0 ).");
                System.out.println(Exit_text);
                return;
            } catch (Exception e) {
                //Horray, there is exactly 4 numbers.
            }

            //creating 4 hardcoded objects with 3 parameters
            Box obj1 = new Box(5, 13.5, 2);
            Box obj2 = new Box(15.7, 25, 54);
            Box obj3 = new Box(13, 10.9, 21);
            Box obj4 = new Box(43, 45, 10.8);

            //Ordering dimmentions and adding weight
            obj1.setWeight(Weight[0]).rearrangeDim();
            obj2.setWeight(Weight[1]).rearrangeDim();
            obj3.setWeight(Weight[2]).rearrangeDim();
            obj4.setWeight(Weight[3]).rearrangeDim();

            //Print validation text
            obj1.print();
            obj2.print();
            obj3.print();
            obj4.print();
            System.out.println();
            System.out.println(Exit_text);

        } catch (Exception e) {
            System.out.println();
            System.out.println("You did not give the proper parameters (4 doubles).");
            System.out.println("Write the weigth of the packages in pounds as ( KleinAndersonA4 40 47.6 90.5 102.0 ).");
            System.out.println(Exit_text);
        }

    }

    //Function to sort arrays
    private static double[] sortArrayOfDoubles(double unsorted_array[]) {

        //Generating array_size
        int array_size = unsorted_array.length;

        double sorted_array[] = unsorted_array.clone();

        double min_double;
        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            min_double = findMinDouble(unsorted_array);
            sorted_array[i] = min_double;
            unsorted_array = popDouble(unsorted_array,indexOfDouble(unsorted_array,min_double));
        }

        //the array is now sorted
        return sorted_array;
    }

    //Function to remove double from array
    private static double[] popDouble(double old_array[],double double_to_remove) {

        //Generating array_size
        int array_size = old_array.length;

        //Create the new array with one less double
        double new_array[] = new double[array_size-1];

        //Have we removed the double?
        boolean flag = false;

        //Testing each element
        for(int i = 0; i < array_size; i++) {
            if(i!=double_to_remove){
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

        //Return array without double to remove
        return new_array;
    }

    //Function to find mininum element in an array
    private static double findMinDouble(double arrayToSearch[]) {

        //Generating array_size
        int array_size = arrayToSearch.length;

        //Minimum variable to date
        double min_variable = arrayToSearch[0];

        //Main loop
        for(int i = 1; i < array_size; i++) {
            if(min_variable > arrayToSearch[i]) {
                min_variable = arrayToSearch[i];
            }
        }

        //Return the minimum variable
        return min_variable;

    }

    //Function to find location of element in array
    private static double indexOfDouble(double unsorted_array[],double double_to_search) {

        //Generating array_size
        int array_size = unsorted_array.length;

        //Testing each variable
        for(int i = 0; i < array_size; i++) {
            if(unsorted_array[i] == double_to_search){
                return i;
            }
        }

        //int was not found
        return -1;

    }

}

/*===============================================================================================================
Sources are:
Doubles in Java: https://www.geeksforgeeks.org/double-parsedouble-method-in-java-with-examples/
Constructors in Java: https://www.w3schools.com/java/java_constructors.asp
===============================================================================================================*/