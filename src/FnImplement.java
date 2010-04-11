import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	/*
	public ArrayList<Boolean> encode( double fenotype, int chromosomeSize ){	
		//FALTA COMPLETAR: Esta codificacion sigue el ejemplo de Lore		
		Random number = new Random();
		ArrayList<Boolean> chromosome = new ArrayList<Boolean>(chromosomeSize);
		for (int i = 0; i < chromosomeSize; i++)
			chromosome.add(i, number.nextBoolean());		
		
		return chromosome;		
	}
	*/
	
	
	public SyntaxTree setSyntaxTree(String[] terminals, String[] functions, int[] arities) {
		Map<String, Integer> functionSet = new HashMap<String, Integer>();
		/*
		functionSet.put("AND", 2);
		functionSet.put("OR", 2);
		functionSet.put("NOT", 1);
		*/
		for( int i=0 ; i< functions.length ; i++ ){
			functionSet.put(functions[i], arities[i]);
		}
		

		Map<String, Boolean> terminalSet = new HashMap<String, Boolean>();
		Random r = new Random();
		
		for( int i=0 ; i< terminals.length ; i++ ){
			terminalSet.put(terminals[i], r.nextBoolean());
			
		}
		/*
		terminalSet.put("A0", r.nextBoolean());
		terminalSet.put("A1", r.nextBoolean());
		//terminalSet.put("A2", r.nextBoolean());
		//terminalSet.put("A3", r.nextBoolean());
		terminalSet.put("B0", r.nextBoolean());
		terminalSet.put("B1", r.nextBoolean());
		//terminalSet.put("B2", r.nextBoolean());
		//terminalSet.put("B3", r.nextBoolean());
		*/
		SyntaxTree st = new SyntaxTree(terminalSet, functionSet);
		
		return st;
	}
	public Fitness setFitnessFunction(String[] terminals) {
		// Seteo las mascaras de los Terminales (posiciones de los bits de entrada)
		Map<String, Integer> terminalMask = new HashMap<String, Integer>();
		/*
		terminalMask.put("A3", 128);	// bit7: 2^7
		terminalMask.put("A2", 64);
		terminalMask.put("A1", 32);
		terminalMask.put("A0", 16);
		terminalMask.put("B3", 8);
		terminalMask.put("B2", 4);
		terminalMask.put("B1", 2);
		 */
		/*
		terminalMask.put("A1", 8);
		terminalMask.put("A0", 4);
		terminalMask.put("B1", 2);
		terminalMask.put("B0", 1);		// bit0: 2^0
		*/
		int pow = 1;
		for( int i=0 ; i< terminals.length ; i++ ){
			terminalMask.put(terminals[i], pow);
			pow <<= 1;
		}
	/*
		int cantBitsEntrada = 2;	// 1 bit para A y 1 bit para B
		int cantBitsSalida = 7;
		int cantEntradas = 2;
	*/
		int cantBitsEntrada = BITS_PER_INPUT;	
		int cantBitsSalida = BITS_PER_OUTPUT;
		int cantEntradas = CANT_INPUTS;
		
		
		Fitness f = new Fitness(cantBitsEntrada, cantBitsSalida, cantEntradas, terminalMask);

		return f;
	}
}
