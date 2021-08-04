package sample;

import javafx.application.Application;
import javafx.application.Platform;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Arrays;

public class Main extends Application {

    private static double[] PrimeNumbers = {2,3,5,7,11};
    private static boolean[] ThreadDone;
    private static boolean stop = false;
    private static boolean stringStop = false;
    //public static boolean refresh = false;
    private static VBox vBox = new VBox(); //WorkAroundForUpdate

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");

        StackPane root = new StackPane();

        Label count = new Label("This is where the number of primes calculated will go");
        Label primes = new Label("This is where primes will go");
        count.setWrapText(true);
        primes.setWrapText(true);
        vBox.getChildren().add(count);
        vBox.getChildren().add(primes);
        root.getChildren().add(vBox);

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        //Platform.runLater(() -> RenderUpdate(primes,primaryStage));

    }

    @Override
    public void stop(){
        stop = true;
        /*
        for (Thread thread : Thread.getAllStackTraces().keySet()){
            //System.out.println(thread.getName());
            //System.out.println(thread.getThreadGroup());
            if (thread.getThreadGroup().getName().equals("main")) {
            //if (thread.getName().equals("main")) {
                //System.out.println(thread);
                //System.out.println("this thread belongs to main");
                thread.interrupt();
            }
        }
       */
    }

    private static void RenderUpdate(Label primes, String toPrint){
        primes.setText(toPrint);
    }

    public static class Scheduler extends Thread {
        double next_int = 12;
        double primeGoal;
        int temp;
        //double[] PrimesToPrint;
        String toPrint = "2, 3, 5, 7, 11";

        //Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        Worker[] workers = new Worker[12]; //Need to add flexible core number implementation

        Scheduler (double primeGoal) {
            this.primeGoal=primeGoal;
        }

       public void run() {
           ThreadDone = new boolean[workers.length];
           for(int i = 0; i < workers.length; i++){
               System.out.println("Creating thread "+i);
               workers[i] = new Worker(i);
               //Testing
               workers[i].Setup(next_int+next_int/workers.length*i,next_int+next_int/workers.length*(i+1),PrimeNumbers);
               workers[i].start(); //Single Threaded version
           }
            //Run until all primes to the goal are found
            while (next_int < primeGoal && !stop) {
                //try{Thread.sleep(1000);} catch (Exception ignored) {}
                //set next small goal
                next_int = next_int*2;
                /*
                for (Worker worker : workers) {
                    try {
                        worker.join();
                    } catch (Exception ignored){}
                }*/
                for (int i = 0; i < workers.length; i++) {
                    try {
                        while(!ThreadDone[i] && !stop){
                            System.out.println("Thread "+workers[i].getId()+" not yet done.");
                            //Not done yet
                        }
                        //System.out.println("Thread "+workers[i].getId()+" is already done.");
                        ThreadDone[i] = false;
                    } catch (Exception ignored){}
                }
                for (Worker worker : workers) {
                    //System.out.println("Worker "+worker+" has array of length "+worker.LocalPrimes.length);
                    if (worker.LocalPrimes.length > 0) {
                        //New better way to merge arrays
                        if(Arrays.binarySearch(worker.LocalPrimes,0)<0){
                            temp = worker.LocalPrimes.length;
                        }
                        else{
                            temp = Arrays.binarySearch(worker.LocalPrimes,0);
                        }
                        PrimeNumbers = Arrays.copyOf(PrimeNumbers,PrimeNumbers.length+temp);
                        System.arraycopy(worker.LocalPrimes, 0, PrimeNumbers,PrimeNumbers.length-temp,temp);
                        /*
                        for (int x = 0; x < worker.LocalPrimes.length; x++) {
                            PrimeNumbers = AddToArray(PrimeNumbers, worker.LocalPrimes[x]);
                            //Debugging the local primes
                            //System.out.println("Worker "+worker+" has array of length "+worker.LocalPrimes.length);
                            //System.out.println("Worker "+worker+" has prime "+worker.LocalPrimes[x]);
                        }*/
                        if(!stringStop) {
                            //Adding worker's primes to string for printing
                            toPrint = toPrint + worker.StringLocalPrimes;
                        }
                    }
                }
                //Check if string is too long then stop it
                if(toPrint.length()>50000){
                    stringStop = true;
                }
                //Create safe copy of primes to print
                //PrimesToPrint = Arrays.copyOf(PrimeNumbers,PrimeNumbers.length);
                for(int i = 0; i < workers.length; i++){
                    /*==>Debugging stretch
                    double start = next_int/2+next_int/2/workers.length*i;
                    double end = next_int/2+next_int/2/workers.length*(i+1);
                    System.out.println("Creating thread with start "+start);
                    System.out.println("Creating thread with end "+end);
                    //Debugging stretch <== */
                    workers[i].Setup(next_int+next_int/workers.length*i,next_int+next_int/workers.length*(i+1),PrimeNumbers);
                    /*
                    workers[i].start = next_int/2+next_int/2/workers.length*i;
                    workers[i].end = next_int/2+next_int/2/workers.length*(i+1);
                    workers[i].PrimeNumbers = PrimeNumbers;*/
                    //workers[i].run(); //Single Threaded version
                    workers[i].interrupt();
                }
                /*
                String toPrint = "";
                for (double primeNumber : PrimesToPrint) {
                    toPrint = toPrint + primeNumber + ", ";
                }*/
                //System.out.println("The primes up to "+next_int+" are: "+toPrint);
                try{Platform.runLater(() -> RenderUpdate((Label)vBox.getChildren().get(0), "The number of prime numbers found up to "+next_int/2+" is: "+PrimeNumbers.length+"."));} catch (Exception ignored) {}
                //if(!stringStop) {
                    final String SendtoPrint = toPrint;
                    try {
                        Platform.runLater(() -> RenderUpdate((Label) vBox.getChildren().get(1), "The latest prime number found is: " + PrimeNumbers[PrimeNumbers.length - 1] + ".\nHere are the primes: " + SendtoPrint));
                    } catch (Exception ignored) {
                    } //
                //}
            }

            //Stop threads
           stop = true;
           for (Worker worker : workers) {
               worker.interrupt();
           }

        }


    }

    public static class Worker extends Thread {

        private double[] LocalPrimes = {};
        private double[] PrivatePrimeNumbers;
        private double start;
        private double end;
        int myID;
        String StringLocalPrimes = "";

        void Setup(double start, double end, double[] primeNumbers){
            this.start = start; this.end = end; this.PrivatePrimeNumbers = primeNumbers;
        }

        public double[] getLocalPrimes (){
            return LocalPrimes;
        }

        Worker(int id){
            myID = id;
        }

        //Wait for instructions
        public void run (){
            //System.out.println("A thread started checking numbers " + start + " up to number " + end);
            StringLocalPrimes = "";
            double size = end - start;
            LocalPrimes = new double[(int) size];
            for(double i = start; i<end; i++){
                CheckIfPrime(i);
            }
            //System.out.println("The final array for this thread is "+ LocalPrimes[0]);
            LocalPrimes = CleanArray(LocalPrimes);
            //System.out.println("A thread found "+LocalPrimes.length);
            //System.out.println("this thread "+myID+" has status "+ThreadDone[myID]);

            //Creating strings to use in main thread
            if(!stringStop) {
                for (double primeNumber : LocalPrimes) {
                    StringLocalPrimes = StringLocalPrimes + ", " + primeNumber;
                }
            }
            try{
            while(true){
                //System.out.println("Thread "+myID+" is now going to sleep");
                ThreadDone[myID] = true; //Testing for multithreading
                sleep(100000000);
               // System.out.println("Thread "+myID+" is now waking up");
            }} catch (Exception e) { if(stop){yield();} else{this.run();} }
        }

        void CheckIfPrime(double NumberToCheck){
            for (double privatePrimeNumber : PrivatePrimeNumbers) {
                try {
                    if (privatePrimeNumber*privatePrimeNumber>NumberToCheck){
                        for(int x = 0; x < LocalPrimes.length; x++) {
                            if (LocalPrimes[x] == 0) {
                                LocalPrimes[x] = NumberToCheck;
                                //System.out.println(this.getId());
                                //System.out.println("this is x for the thread "+x);
                                //System.out.println("Number "+ NumberToCheck +" is a prime.");
                                return;
                            }
                        }
                    }
                    //System.out.println("Checking if number "+ NumberToCheck+" is divisible by "+ PrimeNumbers[i]);
                    if (NumberToCheck % privatePrimeNumber == 0){// || privatePrimeNumber*privatePrimeNumber>NumberToCheck) {
                        //It can be divided
                        //System.out.println("Number "+ NumberToCheck +"is not a prime.");
                        return;
                    }
                    //System.out.println("Number "+ NumberToCheck +" cannot be divided by "+ PrimeNumbers[i]);
                } catch (Exception ignored) {
                }
            }
            //LocalPrimes = AddToArray(LocalPrimes,NumberToCheck);
            for(int x = 0; x < LocalPrimes.length; x++) {
                if (LocalPrimes[x] == 0) {
                    LocalPrimes[x] = NumberToCheck;
                    //System.out.println(this.getId());
                    //System.out.println("this is x for the thread "+x);
                    //System.out.println("Number "+ NumberToCheck +" is a prime.");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scheduler MainScheduler = new Scheduler(2000000000);
        MainScheduler.start();
        launch(args);
    }

    private static double[] AddToArray(double[] Array, double ToAdd)
    {
        double[] NewArray = Arrays.copyOf(Array,Array.length+1);
        NewArray[Array.length] = ToAdd;
        return NewArray;
    }

    private static double[] CleanArray(double[] Array)
    {
        for(int i = 0; i<Array.length; i++){
            if(Array[i]==0){
                return Arrays.copyOf(Array,i);
            }
        }
        return Array;
    }

}
