import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class TrainSimulation  {
	private TrainSystemManager manager;
	private int totalSimulationTime;
	private int simulatedSecondRate;
	private ArrayList<ArrayList<PassengerArrival>> passengerBehaviorEachS;
	private Train train0;
	private Train train1;
	private Train train2;
	private Train train3;
	private Train train4;
	
	
	public TrainSimulation(){
		passengerBehaviorEachS = new ArrayList<ArrayList<PassengerArrival>>();
		 manager = new TrainSystemManager();
		 train0 = new Train(0,manager);
		 train1 = new Train(1,manager);
		 train2 = new Train(2,manager);
		 train3 = new Train(3,manager);
		 train4 = new Train(4,manager);
		 
		try{
			File myInFile = new File("src/Lab4in.txt"); 
		       //Constructs an instance of a file object for myFile.txt
		    Scanner s = new Scanner(myInFile);
			s.useDelimiter("\r\n"); // Sets the delimiter for the s scanner instance to a newline
			String tempString = ""; // declares and initializes an empty string variable
			String[] temp; // declares an empty string array variable
			String[] result;
			totalSimulationTime = Integer.parseInt(s.next());
			simulatedSecondRate = Integer.parseInt(s.next());
			
			while (s.hasNext()){ // checks my input file for remaining lines
				tempString = s.next(); //pulls the "token" or stream of data up to the delimiter from the file into tempString
				temp = tempString.split(";"); //parse tempString using ; and stores each data parsed value into the string array
				ArrayList<PassengerArrival> array = new ArrayList<PassengerArrival>();
				for (int i = 0; i < temp.length; i++){
					result = temp[i].split(" ");
					PassengerArrival passengerarrival = new PassengerArrival();
					passengerarrival.setNumPassengers(Integer.parseInt(result[0]));
					passengerarrival.setDestinationTrainStation(Integer.parseInt(result[1]));
					passengerarrival.setTimePeriod(Integer.parseInt(result[2]));
					array.add(passengerarrival);
				}
				passengerBehaviorEachS.add(array);
			}
	        					
			s.close(); //closes the scanner stream
		}
		
		catch(FileNotFoundException e){
			System.out.println("File not found!");
		}
	}
	
    public void start() throws Exception{
    	long startTime = System.currentTimeMillis();
    
    	train0.start();
    	train1.start();
    	train2.start();
    	train3.start();
    	train4.start();
    		
    	while( SimClock.getTime() <= totalSimulationTime){
    	long currentTime =  System.currentTimeMillis() ;
    	
    	if(currentTime - startTime >= simulatedSecondRate){
    		SimClock.tick();
//    		System.out.println("Current Time: " + Integer.toString(SimClock.getTime()));
    		startTime = currentTime;
    		
    		//generate new passenger
    		for(int station =0; station < 5; station++){
    			for(int groupPeople = 0; groupPeople < passengerBehaviorEachS.get(station).size(); groupPeople++){
    				int timePeriod = passengerBehaviorEachS.get(station).get(groupPeople).getTimePeriod();
    				if(SimClock.getTime() != 0 && SimClock.getTime()%timePeriod == 0){
    					int destination = passengerBehaviorEachS.get(station).get(groupPeople).getDestinationTrainStation();
    					manager.getStation()[station].getPassengerRequests()[destination] += passengerBehaviorEachS.get(station).get(groupPeople).getNumPassengers();
    					manager.getStation()[station].getTotalDestinationRequests()[destination] += passengerBehaviorEachS.get(station).get(groupPeople).getNumPassengers();
    				}	
    			}
    		}
    	}
    }
    	   
}		

    
    	
    
    public void printTrainState(){ 
    	try{
    	File myOutFile = new File("src/Lab4out.txt");
		PrintWriter pw = new PrintWriter(myOutFile);
		
    	//For each train
    	pw.println("For each train:");
    	pw.println("Train0:");
    	pw.println("total number of passengers that entered the train :"+  + train0.getTotalLoadedPassengers());
    	pw.println("total number of passengers that exited this train on a specific train station :" + train0.getTotalUnloadedPassengers());
    	pw.println("current number of passengers heading to  train station: " + train0.getNumPassengers()+ "\n");
 		
    	pw.println("Train1:");
    	pw.println("total number of passengers that entered the train :"+  + train1.getTotalLoadedPassengers());
    	pw.println("total number of passengers that exited this train on a specific train station :" + train1.getTotalUnloadedPassengers());
    	pw.println("current number of passengers heading to  train station: " + train1.getNumPassengers()+ "\n");
   		
    	pw.println("Train2:");
    	pw.println("total number of passengers that entered the train :"+  + train2.getTotalLoadedPassengers());
    	pw.println("total number of passengers that exited this train on a specific train station :" + train2.getTotalUnloadedPassengers());
    	pw.println("current number of passengers heading to  train station: " + train2.getNumPassengers()+ "\n");
   		
    	pw.println("Train3:");
    	pw.println("total number of passengers that entered the train :"+  + train3.getTotalLoadedPassengers());
    	pw.println("total number of passengers that exited this train on a specific train station :" + train3.getTotalUnloadedPassengers());
    	pw.println("current number of passengers heading to  train station: " + train3.getNumPassengers()+ "\n");
   		
    	pw.println("Train4:");
    	pw.println("total number of passengers that entered the train :"+  + train4.getTotalLoadedPassengers());
    	pw.println("total number of passengers that exited this train on a specific train station :" + train4.getTotalUnloadedPassengers());
    	pw.println("current number of passengers heading to  train station: " + train4.getNumPassengers()+ "\n");
    	//For each train station
    	
    	for(int i =0; i < 5; ++i){
    		int result1 = 0;
        	int result2 = 0;
    		pw.println("For each TrainStation" + i +": ");
    		for(int j = 0; j < 5; ++j){
    			 //total number of passengers requesting a trains ride on the station
    			if(manager.getStation()[i].getTotalDestinationRequests()[j] != 0)
    				result1+= manager.getStation()[i].getTotalDestinationRequests()[j];
    				
                   // current number of passengers waiting for a train on the train station	
    			if(manager.getStation()[i].getPassengerRequests()[j] != 0)
    				result2+= manager.getStation()[i].getPassengerRequests()[j];
    				
    			}
    		pw.println("total number of passengers that exited a train on the train station:" +  +manager.getStation()[0].getArrivedPassengers()[i]);
    		pw.println("total number of passengers requesting a trains ride on the station: " + result1);
    		pw.println("current number of passengers waiting for a train on the train station: " + result2);
    		pw.println("train"+manager.getStation()[i].getApproachingtrain() +" currently heading towards the train station"+ i +" for passenger pickup\n");
    		}
    	pw.close();
    	}catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
    	
}	
    

	public int getTotalSimulationTime() {
		return totalSimulationTime;
	}

	public void setTotalSimulationTime(int totalSimulationTime) {
		this.totalSimulationTime = totalSimulationTime;
	}

	public int getSimulatedSecondRate() {
		return simulatedSecondRate;
	}

	public void setSimulatedSecondRate(int simulatedSecondRate) {
		this.simulatedSecondRate = simulatedSecondRate;
	}

	public ArrayList<ArrayList<PassengerArrival>> getPassengerBehaviorEachS() {
		return passengerBehaviorEachS;
	}

	public void setPassengerBehaviorEachS(ArrayList<ArrayList<PassengerArrival>> passengerBehaviorEachS) {
		this.passengerBehaviorEachS = passengerBehaviorEachS;
	}

}
