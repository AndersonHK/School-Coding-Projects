/* The answers to problems 1 and 2 are here. This file compiles into the answer to problem 3.

Problem 1:
When compiled this program produces the output "1 1 2 2 3 3" in new lines.
However, it is not inherently deterministic, as the threads are not syncronized and thus
fall prey to a race condition. It is possible, however unlikely in this case, that the
output of the program would go out of sinc into something like "1 1 2 3 2 3".
This kind of failture becomes more likely if the program was to be expanded in execution duration
or in number of threads.
*/

/*
Problem 2:
Here's the fixed runnable version of the code in problem 2.
It is running as close to the original as possible without errors.
To run it, just copy it into a a blank java file named KleinAndersonA6P2.java and
write "javac KleinAndersonA6P2" to compile then "java KleinAndersonA6P2" to execute.

NOTE: The variables seemed like a section of dead code, but I created constructors for them regardless.
They are never used though, as the original code did not show how they were intended to be used.
NOTE2: I assumed .run and .start were the same and were just misyped since run() is never implemented
and start() seems to be doing the job run() usually does.

Expected OUTPUT:
Print 1, 2 and 3. Then print 1 1, 2 2, 3 3. It is not guaranteed to run synchroniously for the same reason as
the program in problem 1.

class KleinAndersonA6P2{
    public static class TestJoinMethod1 extends Thread {
        private int a,b,c;
        private String d;
        private char f;

        //Constructors
        TestJoinMethod1(int a,int b)
        {
            this.a = a;
            this.b = b;
        }
        TestJoinMethod1(int a,int b,int c)
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        TestJoinMethod1(String d,int a,char f)
        {
            this.a = a;
            this.d = d;
            this.f = f;
        }

        //Start function
        public void run() {
            for (int i = 1; i <= 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println(i);
            }
        }
    }

    public static void main(String args[]){
        TestJoinMethod1 t1=new TestJoinMethod1(1,2);
        TestJoinMethod1 t2=new TestJoinMethod1(2,4,5);
        TestJoinMethod1 t3=new TestJoinMethod1("sys",1,'a');
        t1.start();
        try{
            t1.join();
        }catch(Exception e){System.out.println(e);}
        t2.start();
        t3.start();
    }
}

  */

/*
Problem 3, the program below will be compiled from the contents of this java file.
Compile it as "javac KleinAndersonA6" and run it as "java KleinAndersonA6"
The use of this program is to simulate a race. The player starts by setting length.
Then the player names each of the two racers, one at a time on prompt.
Racers will then start racing each on it's own thread, leaping every 1 second by a random amount.
Type the name of a player to make it tumble and lose it's "turn". It will restart it's 1s countdown.
No error checking for user input was implemented.
 */

import java.util.Scanner;


class KleinAndersonA6 {
    //Setting up public variable
    public static String winner = "false";

    //Creating the input detector
    public static Scanner input = new Scanner(System.in);

    //A simple thread that creates a nice effect on the ending text
    public static class Epilog extends Thread {
        String endMessage = "And so ends the race.";
        public void run(){
            for(int i=0;i<endMessage.length();i++){
                try {
                    Thread.sleep(75);
                    System.out.print(endMessage.substring(i, i + 1));
                } catch (Exception e){}
            }
        }
    }

    //The thread that controls player actions past setup
    public static class PlayerThread extends Thread{

        //Setting thread names it imports
        private Thread racer1,racer2;

        //receiving thread names from main
        PlayerThread(Thread racer1,Thread racer2){
            this.racer1=racer1;
            this.racer2=racer2;
        }

        //Running loop that lets player
        public void run() {
            String temp;
            while (winner == "false") {
                try {
                    temp = input.nextLine();

                    if(temp.equals(racer1.getName())){
                        racer1.interrupt();
                    }
                    if(temp.equals(racer2.getName())){
                        racer2.interrupt();
                    }
                } catch (Exception e) {
                    //game over, next lap will return false and finish this thread
                    this.yield();
                }
            }
        }
    }

    public static class MyThread extends Thread{

        //How much this racer has ran
        double distance_ran,distance_goal;

        //Constructor to get goal
        MyThread(double distance_goal){
            this.distance_goal=distance_goal;
        }

        //Running the race
        public void run(){
            while (distance_ran < distance_goal && winner == "false") {
                try {
                    //Wait some time and run some distance
                    Thread.sleep(1000);
                    distance_ran = distance_ran+Math.random()*50;
                    //Have we won?
                    if(distance_ran>distance_goal && winner == "false"){
                        System.out.println("Racer " + this.getName() + " has won the " + distance_goal + " yards race!!!");
                        winner = Thread.currentThread().getName();
                    }
                    //Race not over?
                    if(winner == "false"){
                        System.out.println("Racer " + this.getName() + " has run " + distance_ran + " yards.");
                        this.yield();
                    }
                    //Race if over, yield and finish execution
                    else {
                        this.yield();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Runner "+this.getName()+" has stumbled and lost it's momentum");
                    this.yield();
                }
            }
        }
    }

    //The Main Function
    public static void main(String args[]) {

        System.out.println();
        System.out.println("Welcome to the race app");
        System.out.print("Please select the race length in yards(RECOMMENDED: 500-2000): ");
        double distance_goal = input.nextDouble();
        //discard next line and add space
        input.nextLine();
        System.out.println();

        //Creating racers
        System.out.println("What should racer one be called?");
        MyThread racer1 = new MyThread(distance_goal);
        racer1.setName(input.nextLine());
        System.out.println();
        System.out.println("What should racer two be called?");
        MyThread racer2 = new MyThread(distance_goal);
        racer2.setName(input.nextLine());

        //Tutorial message
        System.out.println();
        System.out.println("Once the race starts, type the name of a racer to make it stumble");
        System.out.println();

        try{Thread.sleep(2000);} catch(Exception e){}

        //This section of code will now run in parallel to main
        System.out.println("Let the race begin!");
        racer1.start();
        racer2.start();

        //Start player thread and don't proceed until game over.
        PlayerThread player = new PlayerThread(racer1,racer2);
        player.start();
        try{
            racer1.join();
            racer2.join();
        } catch (Exception e) {}
        //Stop player thread once race is over.
        System.out.println("Press enter to proceed");
        try{
            player.join();
        } catch (Exception e) {}

        //Being epilog
        Epilog the_end = new Epilog();
        the_end.start();
        try {
            the_end.join();
        } catch (Exception e){}

        //After epilog is over, let user know execution is done
        System.out.println();
        System.out.println();
        System.out.println("Program completed");

    }
}

/*===============================================================================================================
Sources are:
Naming threads: https://www.javatpoint.com/naming-a-thread
Interupting threads: https://dzone.com/articles/understanding-thread-interruption-in-java
===============================================================================================================*/