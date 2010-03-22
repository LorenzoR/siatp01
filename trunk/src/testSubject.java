import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class testSubject {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int individualSize = 4;
		int frequency = 10;
		boolean eq;
		
		Individual ind1 = new Individual(individualSize);
		Individual ind2 = new Individual(individualSize);
		
		Subject s1 = new Subject(ind1, frequency);
		
		Subject s2 = new Subject(ind1, frequency+20);
		
		System.out.println("S1 = " + s1);
		System.out.println("S2 = " + s2);
		
		eq = s1.equals(s1);
		System.out.println("S1 == S1 ==>" + eq);
		
		eq = s1.equals(s2);
		System.out.println("S1 == S2 ==>" + eq);
				
		Subject s3 = new Subject(ind1, frequency);
		Subject s4 = new Subject(ind2, frequency);
		System.out.println("S3 = " + s3);
		System.out.println("S4 = " + s4);
		eq = s3.equals(s4);
		System.out.println("S3 == S4 ==>" + eq);
		
		ArrayList<Subject>subjectList = new ArrayList<Subject>(4);
		s1.setFrequency(50);
		s3.setFrequency(80);
		subjectList.add(s1);
		subjectList.add(s2);
		subjectList.add(s3);
		subjectList.add(s4);
		System.out.println("List = \n" + subjectList);
		
		List list = Collections.synchronizedList(subjectList); 
        Collections.sort(list);
        System.out.println("Ordered List = \n" + list);
	}

}
