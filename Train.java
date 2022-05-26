
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

//import java.applet.Applet;
//import java.awt.Graphics;
//import java.awt.Color;
//import java.awt.Font;

public class Train implements Runnable{
	
	private int trainID;
	private int currentTrainStation;
	private int numPassengers;
	private int totalLoadedPassengers;
	private int totalUnloadedPassengers;
	private ArrayList<TrainEvent> moveQueue;
	private int[] passengerDestinations;
	private TrainSystemManager manager;
	private Thread train;
	
	public Train(int trainID, TrainSystemManager manager){
		currentTrainStation = 0;
		numPassengers = 0;
		totalLoadedPassengers = 0;
		totalUnloadedPassengers = 0;
		passengerDestinations = new int[5];
		moveQueue = new  ArrayList<TrainEvent>();
	    this.trainID = trainID;
		this.manager = manager;
		
	}
	//helper method
	
	public void unloadpassengers() throws FileNotFoundException{
		totalUnloadedPassengers += numPassengers;
		
		manager.getStation()[0].getArrivedPassengers()[currentTrainStation] += numPassengers; 
		numPassengers = 0;
	}
	 
	public int updateMove(int destination, int currentTime, int time) throws FileNotFoundException{
		TrainEvent trainEvent = new TrainEvent();
		trainEvent.setDestination(destination);
		manager.getStation()[destination].setApproachingtrain(trainID);
		int simulatedTime = Math.abs(5*(destination-currentTrainStation))+time;
		int endTime = currentTime + simulatedTime;
		trainEvent.setExpectedArrival(simulatedTime);
		moveQueue.add(trainEvent);
		return endTime;
	}
	
	public void start(){
		System.out.println(Integer.toString(trainID) + " Start thread");
		 if (train == null){
    		 Thread t = new Thread (this, Integer.toString(trainID));
    		 t.setPriority(Thread.MAX_PRIORITY);
    		 t.start ();
		 }
	}
	
	public void run(){
		boolean Mode = true;
		int endTime = -1;
		
		try {
			
		while(true){
		    int currentTime = SimClock.getTime();
	
		    if(Mode){
		    		int destination;
					int pickupstation;
				
					if(currentTime% 5 !=0)
						currentTime++;
		    		if(numPassengers ==0){
		    				//check whether there is people want to go up and decide next destination
		    			destination = manager.pickup(trainID,currentTrainStation);
		    			if(destination != -1){
		    				numPassengers =  manager.loadpassengers(trainID,currentTrainStation,destination);
		    				String string = "Time: " + currentTime + " " + trainID + " picked up number of passengers:" + numPassengers+ " to the destination" + destination;
		    				System.out.println(string);
		    				
		    				totalLoadedPassengers += numPassengers;
		    				passengerDestinations[destination] += numPassengers;
			    			endTime = updateMove(destination,currentTime,10);
			    			Mode = false;
		    			}
		    			else{
		    				pickupstation = manager.findStation(currentTrainStation,trainID);
			    			if(pickupstation != -1){
			    				String string ="Time: " + currentTime+ " Train" + trainID + " Find passengers at station" + pickupstation+ " Start from Station:" + currentTrainStation;
			    				System.out.println(string);
			    				endTime = updateMove(pickupstation,currentTime,0);
			    				Mode = false;
			    			}
		    			}		
		    		}else{
		    			String string1 = "Time: " + currentTime + " " + trainID + " Unloaded "+ numPassengers + " people at station " + currentTrainStation;
		    			System.out.println(string1);
		    			
		    			unloadpassengers();
		    			destination = manager.pickup(trainID,currentTrainStation);
		    			if(destination != -1){
		    				numPassengers = manager.loadpassengers(trainID,currentTrainStation,destination);
		    				String string = "Time: " + currentTime + " " + trainID + " picked up number of passengers:" + numPassengers+ " to the destination" + destination;
		    				System.out.println(string);
		    				
		    				totalLoadedPassengers += numPassengers;
		    				passengerDestinations[destination] += numPassengers;
			    			endTime = updateMove(destination,currentTime,20);
			    			Mode = false;
			    		}	
		    		}
		    } 
		    if(currentTime == endTime){
				currentTrainStation = moveQueue.get(moveQueue.size()-1).getDestination();
				String string = "Time: " + currentTime+ " " + "Train"+trainID + " Reached the destination " + currentTrainStation;
				System.out.println(string);
				manager.getStation()[currentTrainStation].setApproachingtrain(-1);
				endTime = 0;
				Mode = true;	
			}
		}	
//		pw.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
}

	

	public int getTrainID() {
		return trainID;
	}

	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}

	public int getCurrentTrainStation() {
		return currentTrainStation;
	}

	public void setCurrentTrainStation(int currentTrainStation) {
		this.currentTrainStation = currentTrainStation;
	}

	public int getNumPassengers() {
		return numPassengers;
	}

	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}

	public int getTotalLoadedPassengers() {
		return totalLoadedPassengers;
	}

	public void setTotalLoadedPassengers(int totalLoadedPassengers) {
		this.totalLoadedPassengers = totalLoadedPassengers;
	}

	public int getTotalUnloadedPassengers() {
		return totalUnloadedPassengers;
	}

	public void setTotalUnloadedPassengers(int totalUnloadedPassengers) {
		this.totalUnloadedPassengers = totalUnloadedPassengers;
	}

	public int[] getPassengerDestinations() {
		return passengerDestinations;
	}

	public void setPassengerDestinations(int[] passengerDestinations) {
		this.passengerDestinations = passengerDestinations;
	}

	public TrainSystemManager getManager() {
		return manager;
	}

	public void setManager(TrainSystemManager manager) {
		this.manager = manager;
	}

	public ArrayList<TrainEvent> getMoveQueue() {
		return moveQueue;
	}

	public void setMoveQueue(ArrayList<TrainEvent> moveQueue) {
		this.moveQueue = moveQueue;
	}
	

}
