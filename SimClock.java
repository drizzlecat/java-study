
public class SimClock {

private static int times;


//public function
public SimClock(){
	times = 0;
}

public static void tick(){
	
	times++;
}

public static int getTime(){
	return times;
}
}
