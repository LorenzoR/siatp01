import java.util.ArrayList;
import java.util.Random;


public class FnImplement implements FnInterface {

	public double decode( ArrayList<Boolean> chromosome ){		
 		double decode = 0;
        int pow = 0;
        
        for ( int i = chromosome.size() - 1; i >= 0; i--, pow++ ) {
                if ( chromosome.get(i) ) {
                        decode += Math.pow(2, pow);
                }
        }
        
        decode = decode * ( 1 / (Math.pow(2,chromosome.size()) - 1) );        
        return decode;		 
	}
	
	public ArrayList<Boolean> encode( double fenotype, int chromosomeSize ){	
		//FALTA COMPLETAR: Esta codificacion sigue el ejemplo de Lore		
		Random number = new Random();
		ArrayList<Boolean> chromosome = new ArrayList<Boolean>(chromosomeSize);
		for (int i = 0; i < chromosomeSize; i++)
			chromosome.add(i, number.nextBoolean());		
		
		return chromosome;		
	}
	
	public double maxFn(ArrayList<Boolean> chromosome) {
		double x = decode(chromosome);
		return fn(x);
	}

	public double minFn(ArrayList<Boolean> chromosome) {
		double x = decode(chromosome);
		return UPPER_BOUND - fn(x);
	}
	
	//Funcion que queremos minimizar y maximizar
	private double fn( double x){
		return A + A * Math.sin( x * B * Math.PI ) + C * x;
	}
}
