import java.util.ArrayList;


public class FnImplement implements FnInterface {

	public double maxFn(ArrayList<Boolean> chromosome) {
		// TODO Auto-generated method stub
		//return 0;
		double sum = 0;
		
		for ( int i = 0; i < chromosome.size(); i++ ) {
			if ( chromosome.get(i))
				sum++;
		}
		
		return sum;
	}

	public double minFn(ArrayList<Boolean> chromosome) {
		// TODO Auto-generated method stub
		//return 0;
		double sum = 0;
		
		for ( int i = 0; i < chromosome.size(); i++ ) {
			if ( chromosome.get(i))
				sum++;
		}
		
		return sum;
	}

}
