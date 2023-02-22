/* COP 4520C Assignment 2 
This program was written by: Pedro Henrique Sotto-Mayor Pereira da Silva */ 

import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Scanner;

public class BirthdayParty{
    //keep track of guests in the labyrinth and if the cupcake is served
    static int GUESTS = 0;
    static int count = 0;
    static Boolean cupcakeServed = Boolean.TRUE;
    static Boolean[] chosenGuests;
    static Random random = new Random();
    static Lock lock = new ReentrantLock();

    //track what thread was selected to go into the labyrinth
    static int currentThread;

    //guest goes into the labyrinth
    public static void goIntoLabyrinth(int index){
        while(count < GUESTS){
            lock.lock();
            try
            {
                if(currentThread == index && chosenGuests[index] == Boolean.FALSE && cupcakeServed == Boolean.TRUE){
                    cupcakeServed = Boolean.FALSE;
                    chosenGuests[index] = Boolean.TRUE;
                    System.out.println("Cupcake eaten by Guest #" + index);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }
        }
    }

    //let the first guest be the one counting
    //how many guests entered the labyrinth
    public static void countCupcakes (){
        while(count < GUESTS){
            lock.lock();
            try
            {
                if(currentThread == 0){
                    if(cupcakeServed == Boolean.FALSE){
                        count++;
                        cupcakeServed = Boolean.TRUE;
                    }

                    if(chosenGuests[0] == Boolean.FALSE && cupcakeServed == Boolean.TRUE){
                        count++;
                        cupcakeServed = Boolean.TRUE;
                        chosenGuests[0] = Boolean.TRUE;
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }
        }
    }

    public static void main (String[] args) throws Exception{
        //get the number of guests
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of guests: ");
        GUESTS = scanner.nextInt();
        chosenGuests = new Boolean [GUESTS];

        //get start time
        long startTime = System.currentTimeMillis();
        
        Arrays.fill(chosenGuests, Boolean.FALSE);

        //spawn and join threads and
        //randomly select guests to go into the labyrinth
        Thread myThreads[] = new Thread[GUESTS];
        for (int i=0; i<GUESTS; i++){
            myThreads[i] = new Thread(new Guests(i));
            myThreads[i].start();
        }
        while(count < GUESTS){
            currentThread = random.nextInt(GUESTS+1);
        }
        for (int i=0; i<GUESTS; i++){
            myThreads[i].join();
        }

        //get end time and calculate the time taken (end - start)
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime);
        System.out.println('\n' + "All " + chosenGuests.length + " guests were in the labyrinth at least once");
        System.out.println('\n' + "Time taken: " + timeTaken + "ms");

    }

}

class Guests implements Runnable{
    //thread number
    final int index;

    public Guests(int i){
        //initialize thread number
        index=i;
    }

    public void run(){
        for(int i=0; i<BirthdayParty.GUESTS; i++){
            //make the first thread the one that counts how many
            //guests visited and eaten the cupcake. It will update
            //the counter as needed
            if(i%BirthdayParty.GUESTS == index){
                if(index == 0){
                    BirthdayParty.countCupcakes();
                }
                else{
                    BirthdayParty.goIntoLabyrinth(index);
                }
            }
        }
    }
}