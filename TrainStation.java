
public class TrainStation {
private int[] totalDestinationRequests;
private int[] arrivedPassengers;
private int[] passengerRequests;
private int approachingtrain;

public TrainStation(){
	 totalDestinationRequests = new int[5];
	 arrivedPassengers = new int[5];
	 passengerRequests = new int[5];
	 approachingtrain = -1;
}
public int[] getTotalDestinationRequests() {
	return totalDestinationRequests;
}
public void setTotalDestinationRequests(int[] totalDestinationRequests) {
	this.totalDestinationRequests = totalDestinationRequests;
}
public int[] getArrivedPassengers() {
	return arrivedPassengers;
}
public void setArrivedPassengers(int[] arrivedPassengers) {
	this.arrivedPassengers = arrivedPassengers;
}
public int[] getPassengerRequests() {
	return passengerRequests;
}
public void setPassengerRequests(int[] passengerRequests) {
	this.passengerRequests = passengerRequests;
}
public int getApproachingtrain() {
	return approachingtrain;
}
public void setApproachingtrain(int approachingtrain) {
	this.approachingtrain = approachingtrain;
}
}
