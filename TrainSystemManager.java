
public class TrainSystemManager {
private TrainStation station[];
private int[] lockPeople;

public TrainSystemManager(){
	station = new TrainStation[5];
	for(int i =0; i < 5; ++i)
	station[i] = new TrainStation();
	
	lockPeople = new int[5];
	for(int j =0; j <5; ++j){
		lockPeople[j] = -1;
	}
}

public String backString(String string){
	return string;
}

public synchronized int findStation(int currentTrainStation,int trainID){//decide go to which station to pick up people
	int checkstation = currentTrainStation;
	int destination = currentTrainStation;
	boolean mode1 = true;
	Boolean mode2 = true;
    for(int k = 0; k < 5; ++k){
    	for(int i =0; i < 5; ++i){// find the destination
    	    if( station[checkstation].getApproachingtrain() == -1  && station[checkstation].getPassengerRequests()[destination] != 0){
    			getStation()[checkstation].setApproachingtrain(trainID);
    			lockPeople[destination] = trainID;
    			return checkstation;
    		}
    		
    		else{
    			if(destination == 4)
    			 mode1 = false;
    			else if(destination == 0)
    			 mode1 = true;
    			
    			if(mode1)
        			destination++;
        		else
        			destination--;
    		    }
    		}
    	
    	if(checkstation == 4)
			 mode2 = false;
			else if(checkstation == 0)
			 mode2 = true;
		
		if(mode2)
			checkstation++;
		else
			checkstation--;
		
    	}
    return -1;
}


public synchronized int pickup(int trainID, int currentTrainStation){//decide pickup which group of people(decide the destination)
	int destination = currentTrainStation;
	Boolean mode = true;
	for(int i =0; i < 5; ++i){
		 if(lockPeople[i] == trainID){
			return i;
		}
	}
	for(int i =0; i < 5; ++i){
		  if(lockPeople[destination] == -1 && station[currentTrainStation].getPassengerRequests()[destination] != 0){
				lockPeople[destination] = trainID;
				return destination;
			}
			
		
    	if(destination == 4)
			 mode = false;
			else if(destination == 0)
			 mode = true;
		
		if(mode)
			destination++;
		else
			destination--;
	}
	return -1;
}

public synchronized int loadpassengers(int trainID, int currentTrainStation, int destination){
	int numPassengers = getStation()[currentTrainStation].getPassengerRequests()[destination];
	getStation()[currentTrainStation].getPassengerRequests()[destination] = 0;//people now in the train
	getStation()[currentTrainStation].setApproachingtrain(-1);
	lockPeople[destination] = -1;
	return numPassengers;
}




public TrainStation[] getStation() {
	return station;
}

public void setStation(TrainStation station[]) {
	this.station = station;
}
}
