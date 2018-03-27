
import java.util.HashSet;
import java.util.Iterator;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amit
 */
class Elevator {

    private static Elevator elevator = null;
    private HashSet hashset = new HashSet<>();
    private int currentFloor =0;
    private int direction = 1;  // 1 for up and -1 for down
    private Thread threadForElevator;

    static Elevator getInstance() { 
        if (elevator == null) { 
            elevator = new Elevator(); 
        } 
        return elevator; 
    } 
    
    public Elevator(){}

    /**
     * @return the hashset
     */
    public HashSet getHashset() {
        return hashset;
    }

    /**
     * @param hashset the hashset to set
     */
    public void setHashset(HashSet hashset) {
        this.hashset = hashset;
    }

    /**
     * @return the currentFloor
     */
    
        /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    
    /**
     * @return the thread
     */
    public Thread getThread() {
        return threadForElevator;
    }

    /**
     * @param thread the thread to set
     */
    public void setThread(Thread thread) {
        this.threadForElevator = thread;
    }
    
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * @param currentFloor the currentFloor to set
     */
    public void setCurrentFloor(int currentFloor) throws InterruptedException {
        this.currentFloor = currentFloor;
        
        if(this.currentFloor>currentFloor){
            setDirection(-1);
        }
        else
        {
            setDirection(1);
        }
        
        System.out.println("You are at Floor :  "+currentFloor);

        Thread.sleep(2000);
    }

    
    public synchronized void addNewFloor(int floor){
        getHashset().add(floor);
//        System.out.println(getThread());
        if(threadForElevator.getState() == Thread.State.WAITING){
            notify();
        }
        else{
            threadForElevator.interrupt();
        }
        
    }
    
    public synchronized int nextFloor(){
        int floor = -1;
        Pair pair = getMaxMin();
        int nextUpperFloor = (int) pair.getKey();
        int prevLowerFloor = (int) pair.getValue();
        if(direction ==1){
            if(nextUpperFloor != 100){
                floor = nextUpperFloor;
            }
            else{
                floor = prevLowerFloor;
            }
        }
        else{
            if(prevLowerFloor != -1){
                floor = prevLowerFloor;
            }
            else{
                floor = nextUpperFloor;
            }
        }
        if(floor==100){
            floor=-1;
        }
        if(floor == -1){
            System.out.println("Currently Waiting at floor : "+ getCurrentFloor());
            try{
                wait();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            hashset.remove(floor);
        }
        return floor;
    }
    
    public Pair getMaxMin(){
        int maxValue=100;
        int minValue=-1;
        
        Iterator itr = hashset.iterator();
        while(itr.hasNext()){
            int value = (int)itr.next();
            if(currentFloor<=value && maxValue>=value){
                maxValue=value;
            }
            if(currentFloor>=value && minValue<=value){
                minValue=value;
            }
        }
        return new Pair(maxValue,minValue);
    }

    
}
