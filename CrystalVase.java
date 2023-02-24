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

public class CrystalVase{
    //keep track of guests in the labyrinth and if the cupcake is served
    static int THREADS = 0;
    static int GUESTS = 0;
    static int count = 0;
    static NavigableSet<Integer> chosenGuests = new TreeSet<Integer>();
    static Boolean roomAvailable = Boolean.FALSE;
    static Random random = new Random();
    static Lock lock = new ReentrantLock();

    //guest will see the vase
    public static void seeVase (int index){
        //make sure the room is available before entering
        //if not, simply keep trying
        while(!chosenGuests.contains(index)){
        try
        {
            //go see the vase
            lock.lock();
            if(roomAvailable == Boolean.TRUE && !chosenGuests.contains(index)){
                roomAvailable = Boolean.FALSE;
                chosenGuests.add(index);
                Thread.sleep(random.nextInt(1));
                roomAvailable = Boolean.TRUE;
                count++;
                System.out.println("Guest #" + index + " looked at the vase");
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
        THREADS = GUESTS;

        //get start time
        long startTime = System.currentTimeMillis();

        //spawn and join threads
        Thread myThreads[] = new Thread[THREADS];
        for (int i=0; i<THREADS; i++){
            myThreads[i] = new Thread(new Guests(i));
            myThreads[i].start();
        }
        roomAvailable=Boolean.TRUE;
        for (int i=0; i<THREADS; i++){
            myThreads[i].join();
        }

        //get end time and calculate the time taken (end - start)
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime);
        System.out.println('\n' + "All " + count + " guests looked at the vase");
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
        for(int i=0; i<CrystalVase.GUESTS; i++){
            if(i%CrystalVase.THREADS == index){
                CrystalVase.seeVase(i);
            }
        }
    }
}