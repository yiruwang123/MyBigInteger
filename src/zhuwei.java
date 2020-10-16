
public class zhuwei {

	public static void main(String[] args) {
		String a1="-12314823749812";
		String b1="219874931749819284";
		MyBigInteger ap=new MyBigInteger(a1);
		MyBigInteger bp=new MyBigInteger(b1);
		System.out.println(ap.multiply(bp));
		
		int n=4;
		
		int[] a=new int[(int)(Math.ceil(a1.length()/(n*1.0)))];
		int[] b=new int[(int)(Math.ceil(b1.length()/(n*1.0)))];
		a[0]=Integer.valueOf(a1.substring(a1.length()-n));		
		for(int i=1;i<a.length-1;i++){
			a[i]=Integer.valueOf(a1.substring(a1.length()-n*(i+1),a1.length()-n*(i+1)+n));
		}
		a[a.length-1]=Integer.valueOf(a1.substring(0,a1.length()-n*(a.length-1)));
		
		

		b[0]=Integer.valueOf(b1.substring(b1.length()-n));		
		for(int i=1;i<b.length-1;i++){
			b[i]=Integer.valueOf(b1.substring(b1.length()-n*(i+1),b1.length()-n*(i+1)+n));
		}
		b[b.length-1]=Integer.valueOf(b1.substring(0,b1.length()-n*(b.length-1)));
		
		
		int[] c=new int[a.length+b.length];
		
		for(int i=0;i<a.length;i++) {
			for(int j=0;j<b.length;j++) {
				c[i+j]+=a[i]*b[j];
			}
		}
		for(int i=0;i<c.length-1;i++) {
			c[i+1]+=c[i]/10000;
			c[i]=c[i]%10000;
		}
		String str="";
		for(int i=0;i<c.length;i++) {
			String ss=""+c[i];
			int sslength=ss.length();
			if(ss.length()<n) {
				for (int p=0;p<n-sslength;p++) {
					ss="0"+ss;
				}
			}
			str=ss+str;
		}
		int p=str.length();
		for(int i=0;i<p;i++) {
			if(str.substring(0,1).equals("0")) {
				str=str.substring(1);
			}else {
				break;
			}
		}
		
		System.out.println(str);
		

		

	}

}
