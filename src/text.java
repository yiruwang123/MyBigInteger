import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class text {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a="123";
		String b="321234";
		List<Double> d = new ArrayList<Double>();
		d.add(1.);
		d.add(2.);
		d.add(3.);
		for(Double q:d) {
			System.out.println(q);
		}
		d.clear();
		for(Double q:d) {
			System.out.println(q);
		}
		//char[] a1=s.toCharArray();
		
		//for(int i=0;i<a.length;i++){
		//}

		//MyBigInteger t=new MyBigInteger(s);
		//MyBigInteger t2=new MyBigInteger(s2);
		//MyBigInteger t3=t.add(t2);
		//System.out.print(Integer.valueOf(""+"00002132132100"));
		//int a=Integer.valueOf("00034");
		//System.out.print(t3);
		Random r = new Random();
		 int opernum = r.nextInt(3);
		 float o=r.nextFloat();
		 System.out.print(o);
	}

}
