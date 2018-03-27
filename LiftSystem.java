
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amit
 */
public class LiftSystem {
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        System.out.println("------- Welcome  to  Lift ---------");
        Thread inputThread = new Thread(new InputThread());
        Thread processingThread = new Thread(new ProcessingThread());
        
        Elevator.getInstance().setThread(processingThread);
        
        inputThread.start();
        processingThread.start();
        
    }
    
}

class InputThread implements Runnable
{

    @Override
    public void run() {
        while(true){
            String inputFloor = null;
            
            try{
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                inputFloor = bufferedReader.readLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            
            int input = Integer.parseInt(inputFloor);
            if(inputFloor.length()<=2 && input<=50){
                System.out.println("You have pressed   :   "+input);
                Elevator elevator = Elevator.getInstance();
                elevator.addNewFloor(input);
            }
            else{
                System.out.println("---------Invalid Floor Number-------");
            }
        }
    }
}



class ProcessingThread implements Runnable
{

    @Override
    public void run() {
        while(true){
            
            Elevator elevator = Elevator.getInstance();
            int nextFloor = elevator.nextFloor();
            int currentFloor = elevator.getCurrentFloor();
            try{
                if(nextFloor != -1 && currentFloor>nextFloor){
                    while(currentFloor>nextFloor){
                        --currentFloor;
                        elevator.setCurrentFloor(currentFloor);
                    }
                }
                else if(nextFloor!=-1 && currentFloor<nextFloor){
                    while(currentFloor<nextFloor){
                        ++currentFloor;
                        elevator.setCurrentFloor(currentFloor);
                    } 
                }
                   
                System.out.println("Welocome to the Floor : "+currentFloor);
            }
            catch(InterruptedException e){
                if(elevator.getCurrentFloor()!=nextFloor){
                    elevator.getHashset().add(nextFloor);
                }
            }
            
        }
    }
    
}