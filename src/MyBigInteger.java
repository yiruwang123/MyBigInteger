import java.util.Arrays;
import java.util.Vector;

//后面有补充的fft类，和复数类。
public class MyBigInteger {
	public int[] num;
	public String ori; 
	public boolean isnegative=false;
	
	public MyBigInteger(String s,int a) {
		
		if(s=="") {s="0";}
		ori=s;
		if(s.substring(0, 1).equals("-")) {
			isnegative=true;
		}
	}
	public MyBigInteger(String s){
		if(s=="") {s="0";}
		ori=s;
		//char[] a=s.toCharArray();
		if(s.substring(0, 1).equals("-")) {
			isnegative=true;
			s=s.substring(1);
		}
		num=new int[(int)(Math.ceil(s.length()/(9.0)))];
		if(num.length>1) {
			num[0]=Integer.valueOf(s.substring(s.length()-9));
			for(int i=1;i<num.length-1;i++){
				num[i]=Integer.valueOf(s.substring(s.length()-9*(i+1),s.length()-9*(i+1)+9));
			}
			num[num.length-1]=Integer.valueOf(s.substring(0,s.length()-9*(num.length-1)));
			}
		else {
			num[0]=Integer.valueOf(s);
		}
		
		
	}
	
	public MyBigInteger add(MyBigInteger operand) {
		if(isnegative&(!operand.isnegative)) {
			MyBigInteger res=subtractmethod(operand);
			if(res.isnegative) {
				return new MyBigInteger(res.ori.substring(1),1);
			}else {
				return new MyBigInteger("-"+res.ori,1);
			}
			}
		else if(operand.isnegative&(!isnegative)) {
			return subtractmethod(operand);
		}else if(isnegative&&operand.isnegative) {
			return new MyBigInteger("-"+addmethod(operand).ori,1);
		}
		
		else{
			return addmethod(operand);
		}
	}
	
	public MyBigInteger subtract(MyBigInteger operand) {
		if((!isnegative)&operand.isnegative) {
			return addmethod(operand);
		}
		else if(isnegative&(!operand.isnegative)) {
			MyBigInteger res=addmethod(operand);
			if(res.isnegative) {
				return new MyBigInteger(res.ori.substring(1),1);
			}else {
				return new MyBigInteger("-"+res.ori,1);
			}
		}else if(isnegative&operand.isnegative) {
			MyBigInteger res=subtractmethod(operand);
			if(res.isnegative) {
				return new MyBigInteger(res.ori.substring(1),1);
			}else {
				return new MyBigInteger("-"+res.ori,1);
			}
		}else {
			return subtractmethod(operand);
		}
		
	}
	
	public MyBigInteger multiply(MyBigInteger operand){
		if(ori=="0"||operand.ori=="0") {
			return new MyBigInteger("0",1);
		}
		if(ori.length()<3&&operand.ori.length()<3) {
			return new MyBigInteger(Integer.valueOf(ori)*Integer.valueOf(operand.ori)+"",1);
		}
		if(ori.length()<=19000&&operand.ori.length()<=19000) {
			if((!isnegative)&&operand.isnegative) {
				String str=operand.ori.substring(1);
				MyBigInteger n=new MyBigInteger(str,1);
				return new MyBigInteger("-"+multiplymethod(n).ori,1);
			}else if(isnegative&&(!operand.isnegative)){
				String str=ori.substring(1);
				MyBigInteger n=new MyBigInteger(str,1);
				return new MyBigInteger("-"+n.multiplymethod(operand).ori,1);
			}
			else if(isnegative&&operand.isnegative) {
				String str1=ori.substring(1);
				String str2=operand.ori.substring(1);
				MyBigInteger n1=new MyBigInteger(str1,1);
				MyBigInteger n2=new MyBigInteger(str2,1);
				return n1.multiplymethod(n2);
			}
			else {
				return multiplymethod(operand);
			}
		}
		else {
		if((!isnegative)&&operand.isnegative) {
			String str=operand.ori.substring(1);
			MyBigInteger n=new MyBigInteger(str,1);
			return new MyBigInteger("-"+multiplyfu(n).ori,1);
		}else if(isnegative&&(!operand.isnegative)){
			String str=ori.substring(1);
			MyBigInteger n=new MyBigInteger(str,1);
			return new MyBigInteger("-"+n.multiplyfu(operand).ori,1);
		}
		else if(isnegative&&operand.isnegative) {
			String str1=ori.substring(1);
			String str2=operand.ori.substring(1);
			MyBigInteger n1=new MyBigInteger(str1,1);
			MyBigInteger n2=new MyBigInteger(str2,1);
			return n1.multiplyfu(n2);
		}
		else {
			return multiplyfu(operand);
		}}
	}
	
	
	
	public MyBigInteger addmethod(MyBigInteger operand){
		if(ori=="0") {return operand;}
		if(operand.ori=="0") {return new MyBigInteger(ori,1);}
		int len=0;
		int[] def;
		String str=""; //用于创建返回的大整数
		
		if(num.length==operand.num.length) {
			len=operand.num.length;
			def=new int[len];//复制长的那个大整数
			for(int i=0;i<len;i++){
		    	def[i]=num[i];
			}
			
			for(int i=0;i<len-1;i++){
			    def[i]=def[i]+operand.num[i];
			    if(def[i]>=1000000000){
			    	def[i+1]=1+def[i+1];
			    	def[i]=def[i]-1000000000;
			    }
			}
			def[len-1]=def[len-1]+operand.num[len-1];			
				
			for(int i=0;i<def.length-1;i++){
				String ss=""+def[i];
				int sslength=ss.length();
				if(ss.length()<9) {
					for (int p=0;p<9-sslength;p++) {
						ss="0"+ss;
					}
				}
				str=ss+str;
			}
			if(def[def.length-1]!=0) {
			str=def[def.length-1]+str;}
			
			return new MyBigInteger(str,1);	
				
		}
		
		else if(num.length>operand.num.length) {
			len=operand.num.length;
			def=new int[num.length];//复制长的那个大整数
			for(int i=0;i<def.length;i++){
		    	def[i]=num[i];
			}
			
			for(int i=0;i<len;i++){
			    def[i]=def[i]+operand.num[i];
			    if(def[i]>=1000000000){
			    	def[i+1]=1+def[i+1];
			    	def[i]=def[i]-1000000000;
			    }
			}
			
			for(int i=0;i<def.length-1;i++){
				String ss=""+def[i];
				int sslength=ss.length();
				if(ss.length()<9) {
					for (int p=0;p<9-sslength;p++) {
						ss="0"+ss;
					}
				}
				str=ss+str;
			}
			if(def[def.length-1]!=0) {
				str=def[def.length-1]+str;}

					return new MyBigInteger(str,1);
				
		}
		else {
			len=num.length;
			def=new int[operand.num.length];//复制长的那个大整数
			for(int i=0;i<def.length;i++){
		    	def[i]=operand.num[i];
			}
			
			for(int i=0;i<len;i++){
			    def[i]=def[i]+num[i];
			    if(def[i]>=1000000000){
			    	def[i+1]=1+def[i+1];
			    	def[i]=def[i]-1000000000;
			    }
			}
			
			for(int i=0;i<def.length-1;i++){
				String ss=""+def[i];
				int sslength=ss.length();
				if(ss.length()<9) {
					for (int p=0;p<9-sslength;p++) {
						ss="0"+ss;
					}
				}
				str=ss+str;
			}
			if(def[def.length-1]!=0) {
				str=def[def.length-1]+str;}
			
					return new MyBigInteger(str,1);
				
		}
	}
	
	
	public MyBigInteger subtractmethod(MyBigInteger operand){
		String ope="";
		if(operand.isnegative) {
			ope=operand.ori.substring(1);
		}else {
			ope=operand.ori;
		}
		String tor="";
		if(isnegative) {
			tor=ori.substring(1);
		}else {
			tor=ori;
		}
		int len=0;
		int[] def;
		String str="";
		
		if(num.length>operand.num.length){
			len=operand.num.length;
			def=new int[num.length];//复制长的那个大整数
			for(int i=0;i<def.length;i++){
		    	def[i]=num[i];
			}
			
			for(int i=0;i<len;i++){
			    def[i]=def[i]-operand.num[i];
			    if(def[i]<0){
			    	def[i+1]=def[i+1]-1;
			    	def[i]=def[i]+1000000000;
			    }
			}
			
			for(int i=0;i<def.length-1;i++){
				String ss=def[i]+"";
				int sslength=ss.length();
				if(ss.length()<9) {
					for (int p=0;p<9-sslength;p++) {
						ss="0"+ss;
					}
				}
				str=ss+str;
			}
			if(def[def.length-1]!=0) {
				str=def[def.length-1]+str;}
			return new MyBigInteger(str,1);
			}
		else if(num.length<operand.num.length) {
			len=num.length;
			def=new int[operand.num.length];//复制长的那个大整数
			for(int i=0;i<def.length;i++){
		    	def[i]=operand.num[i];
			}
			
			for(int i=0;i<len;i++){
			    def[i]=def[i]-num[i];
			    if(def[i]<0){
			    	def[i+1]=def[i+1]-1;
			    	def[i]=def[i]+1000000000;
			    }
			}
			
			for(int i=0;i<def.length-1;i++){
				String ss=def[i]+"";
				int sslength=ss.length();
				if(ss.length()<9) {
					for (int p=0;p<9-sslength;p++) {
						ss="0"+ss;
					}
				}
				str=ss+str;
			}
			if(def[def.length-1]!=0) {
				str=def[def.length-1]+str;}
			
			return new MyBigInteger("-"+str,1);
	}
		else {
			if(tor.length()>ope.length()) {
				len=num.length;
				def=new int[num.length];//复制长的那个大整数
				for(int i=0;i<def.length;i++){
			    	def[i]=num[i];
				}
				
				for(int i=0;i<len-1;i++){
				    def[i]=def[i]-operand.num[i];
				    if(def[i]<0){
				    	def[i+1]=def[i+1]-1;
				    	def[i]=def[i]+1000000000;
				    }
				}
				
				def[len-1]=def[len-1]-operand.num[len-1];
				for(int i=0;i<def.length-1;i++){
					String ss=def[i]+"";
					int sslength=ss.length();
					if(ss.length()<9) {
						for (int p=0;p<9-sslength;p++) {
							ss="0"+ss;
						}
					}
					str=ss+str;
				}
				if(def[def.length-1]!=0) {
					str=def[def.length-1]+str;}
				return new MyBigInteger(str,1);				
			}
			else if(tor.length()<ope.length()) {
				len=num.length;
				def=new int[num.length];//复制长的那个大整数
				for(int i=0;i<def.length;i++){
			    	def[i]=operand.num[i];
				}
				
				for(int i=0;i<len-1;i++){
				    def[i]=def[i]-num[i];
				    if(def[i]<0){
				    	def[i+1]=def[i+1]-1;
				    	def[i]=def[i]+1000000000;
				    }
				}
				
				def[len-1]=def[len-1]-num[len-1];
				for(int i=0;i<def.length-1;i++){
					String ss=def[i]+"";
					int sslength=ss.length();
					if(ss.length()<9) {
						for (int p=0;p<9-sslength;p++) {
							ss="0"+ss;
						}
					}
					str=ss+str;
				}
				if(def[def.length-1]!=0) {
					str=def[def.length-1]+str;}
				return new MyBigInteger("-"+str,1);	
			}
			else {
				int dx=tor.compareTo(ope);
				if(dx==0) {
					def=new int[num.length];//复制长的那个大整数
					for(int i=0;i<def.length;i++){
				    	def[i]=0;
					}
					for(int i=0;i<def.length-1;i++){
						String ss=def[i]+"";
						int sslength=ss.length();
						if(ss.length()<9) {
							for (int p=0;p<9-sslength;p++) {
								ss="0"+ss;
							}
						}
						str=ss+str;
					}
					if(def[def.length-1]!=0) {
						str=def[def.length-1]+str;}
					return new MyBigInteger(str,1);
				}else if(dx>0) {
					len=num.length;
					def=new int[num.length];//复制长的那个大整数
					for(int i=0;i<def.length;i++){
				    	def[i]=num[i];
					}
					
					for(int i=0;i<len-1;i++){
					    def[i]=def[i]-operand.num[i];
					    if(def[i]<0){
					    	def[i+1]=def[i+1]-1;
					    	def[i]=def[i]+1000000000;
					    }
					}
					
					def[len-1]=def[len-1]-operand.num[len-1];
					for(int i=0;i<def.length-1;i++){
						String ss=def[i]+"";
						int sslength=ss.length();
						if(ss.length()<9) {
							for (int p=0;p<9-sslength;p++) {
								ss="0"+ss;
							}
						}
						str=ss+str;
					}
					if(def[def.length-1]!=0) {
						str=def[def.length-1]+str;}
					return new MyBigInteger(str,1);
				}
				else {
					len=num.length;
					def=new int[num.length];//复制长的那个大整数
					for(int i=0;i<def.length;i++){
				    	def[i]=operand.num[i];
					}
					
					for(int i=0;i<len-1;i++){
					    def[i]=def[i]-num[i];
					    if(def[i]<0){
					    	def[i+1]=def[i+1]-1;
					    	def[i]=def[i]+1000000000;
					    }
					}
					
					def[len-1]=def[len-1]-num[len-1];
					for(int i=0;i<def.length-1;i++){
						String ss=def[i]+"";
						int sslength=ss.length();
						if(ss.length()<9) {
							for (int p=0;p<9-sslength;p++) {
								ss="0"+ss;
							}
						}
						str=ss+str;
					}
					if(def[def.length-1]!=0) {
						str=def[def.length-1]+str;}
					return new MyBigInteger("-"+str,1);	
				}		
			}
		}			
	}
	
	
	//采用快速傅里叶变换计算乘法
    public MyBigInteger multiplyfu(MyBigInteger operand){
    	Fft fu=new Fft(ori);
    	
		return new MyBigInteger(fu.compute(operand.ori),1);
	}
    
	
    //用逐位法计算乘法
	public MyBigInteger multiplymethod(MyBigInteger operand){
		String a1=ori;
		String b1=operand.ori;
		
		int[] a=new int[(int)(Math.ceil(a1.length()/(3.0)))];
		int[] b=new int[(int)(Math.ceil(b1.length()/(3.0)))];
		
		if(a.length>1) {
		a[0]=Integer.valueOf(a1.substring(a1.length()-3));		
		for(int i=1;i<a.length-1;i++){
			a[i]=Integer.valueOf(a1.substring(a1.length()-3*(i+1),a1.length()-3*(i+1)+3));
		}
		a[a.length-1]=Integer.valueOf(a1.substring(0,a1.length()-3*(a.length-1)));
		}else {
			a[0]=Integer.valueOf(a1);
		}
		
		if(b.length>1) {
		b[0]=Integer.valueOf(b1.substring(b1.length()-3));		
		for(int i=1;i<b.length-1;i++){
			b[i]=Integer.valueOf(b1.substring(b1.length()-3*(i+1),b1.length()-3*(i+1)+3));
		}
		b[b.length-1]=Integer.valueOf(b1.substring(0,b1.length()-3*(b.length-1)));
		}
		else {
			b[0]=Integer.valueOf(b1);
		}
		
		
		int[] c=new int[a.length+b.length];
		
		for(int i=0;i<a.length;i++) {
			for(int j=0;j<b.length;j++) {
				c[i+j]+=a[i]*b[j];
				//System.out.println(c[i]);
			}
		}
		for(int i=0;i<c.length-1;i++) {
			c[i+1]+=c[i]/1000;
			c[i]=c[i]%1000;
		}
		String str="";
		
		for(int i=0;i<c.length;i++) {
			String ss=""+c[i];
			int sslength=ss.length();
			if(ss.length()<3) {
				for (int p=0;p<3-sslength;p++) {
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
		
		return new MyBigInteger(str,1);
}
	
	
	public String toString(){
		return ori;
	}
}



//fft类
 class Fft {
    private int maxn;
    public String ss;
    public Fft(String s){
    	ss=s;
    }
    
    public String compute(String s) {
        String n1=ss;
        String n2=s;
        int len =n1.length()+n2.length();
        maxn = 0;
        double temp = log2(len);
        double floortemp = Math.floor(temp);
        if(floortemp == temp){
            maxn = len;
        }else {
            maxn = (int)Math.pow(2,floortemp+1);
        }
        Complex[] arra = createArray(maxn);
        Complex[] arrb =createArray(maxn);
        Complex[] arrc =createArray(maxn);
        Complex[] arrA = createArray(maxn);
        Complex[] arrB = createArray(maxn);
        Complex[] arrC = createArray(maxn);

        char[] a = n1.toCharArray();
        int j = 0;
        for(int i = a.length - 1; i >= 0; i--){
            arra[j++] = new Complex(a[i] - '0');
        }
        j = 0;
        char []b = n2.toCharArray();
        for(int i = b.length - 1; i >= 0; i--){
            arrb[j++] = new Complex(b[i] - '0');
        }
        
        //invert(arra);
        //invert(arrb);
        //FFt(arra);
        //FFt(arrb);
        arrA = fft(arra);

        arrB = fft(arrb);

        for(int i = 0; i < arrc.length; i++){
            arrC[i] = arrA[i].times(arrB[i]);
        }
        
         arrc=ifft(arrC);
        Vector<Integer> vector = new Vector();
        
        vector = convert(arrc);
        String str = "";
        
        char vectorCHar[] = new char[vector.size()];
        for(int i = 0; i < vectorCHar.length; i++){
            vectorCHar[i] = (char)(vector.get(i) + '0');
        }
        str =String.valueOf(vectorCHar);
        str = new StringBuffer(str).reverse().toString();
        //for(int i=0;i<vector.size();i++) {
        //	str=(int)vector.get(i)+str;
        //}

        str = trim(str);
        
        return str;
        

    }
    
    private String trim(String value) {

        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();    /* avoid getfield opcode */

        while ((st < len) && (val[st] <= '0')) {
            st++;
        }
           // while ((st < len) && (val[len - 1] <= ' ')) {
             //   len--;
            //}
        return value.substring(st, len);

    }
    
    private Vector<Integer> convert(Complex[] arrc) {
    	Vector<Integer> result = new Vector();
    	   int n = arrc.length;
           int arrayTemp [] = new int[n+1];
           for(int i = 0; i < arrc.length; i++){
               arrayTemp[i] = (int)(arrc[i].re()+0.5);
           }
           int i;
           arrayTemp[n] = 0;
           for(i = 0; i < n; i++){
               result.add(arrayTemp[i]%10);
               arrayTemp[i+1] += arrayTemp[i] / 10;
           }
           int t = arrayTemp[n];
           while(t > 0){
               result.add(t % 10);
               t/= 10;
           }
           return result;
       }

    private Complex[] ifft(Complex[] arrC) {
        int n = arrC.length;
        Complex arrayResult[] = new Complex[n];
        for(int i = 0; i < arrC.length; i++){
            arrC[i] = arrC[i].conjugate();
        }
        arrayResult= fft(arrC);
        for(int i = 0; i < arrayResult.length; i++){
            arrayResult[i] = arrayResult[i].conjugate().divides(new Complex(n));
        }
        return arrayResult;
    }
    
    
    private void FFt(Complex[] arrA) {
    	int n=arrA.length;
    	int m=(int)(Math.log(n)/Math.log(2));
    	
    	int j=0;
    	int n1;
    	int n2=n/2;
    	for(int i=1;i<n-1;i++) {
    		n1=n2;
    	while(j>=n1) {
    		j=j-n1;
    		n1=n1/2;
    	}
    	j=j+n1;
    	if(i<j) {
    		swap(arrA,i,j);
    	}
    	}
    	for(int len=2;len<=n;len*=2) {
    		Complex wn=new Complex(Math.cos(2.0*Math.PI/len),Math.sin(2.0*Math.PI/len));
    		for(int st=0;st<n;st+=len) {
    			Complex W=new Complex(1.0,0.0);
    			for(int i=st;i<st+(len/2);i++,W=W.times(wn)) {
    				Complex x=arrA[i];
    				Complex y=W.times(arrA[i+len/2]);
    				arrA[i]=x.plus(y);
    				arrA[i+len/2]=x.minus(y);
    			}
    		}
    	}
    }

    private Complex[] fft(Complex[] arrA) {
        int N = arrA.length;
        if(N == 1){
            return arrA;
        }
        Complex [] arrayEven = new Complex[(int)(Math.ceil(N/2.0))];
        Complex [] arrayOdd = new Complex[N/2];
        if(N%2==0) {
        for(int i = 0; i < arrayOdd.length; i++){
            arrayEven[i] = arrA[2*i];
            arrayOdd[i] = arrA[2*i+1];
        }}else {
        	for(int i = 0; i < arrayOdd.length; i++){
                arrayEven[i] = arrA[2*i];
                arrayOdd[i] = arrA[2*i+1];}
        	arrayEven[arrayEven.length-1]=arrA[arrA.length-1];
        }
        arrayEven = fft(arrayEven);
        arrayOdd = fft(arrayOdd);
        Complex[] arrayResult = new Complex[N];
        Complex wn=new Complex(Math.cos(2.0*Math.PI/N),Math.sin(2.0*Math.PI/N));
        Complex W=new Complex(1.0,0.0);
        for(int i = 0; i*2 < N; i++){
            arrayResult[i] = arrayEven[i].plus(arrayOdd[i].times(W));
            arrayResult[i+N/2] = arrayEven[i].minus(arrayOdd[i].times(W));
            W=W.times(wn);
        }
        
        return arrayResult;
    }



    private void swap(Complex[] arra, int i, int j) {
        Complex tmp = arra[i];
        arra[i] = arra[j];
        arra[j] = tmp;
    }
    private Complex[] createArray(int maxn) {
        Complex[] result =new Complex[maxn];
        for(int i=0;i<maxn;i++)
            result[i]=new Complex(0,0);
        return result;
    }

    public double log2(int n){
        return Math.log(n)/Math.log(2);
    }
}


//复数类
class Complex {
	private double re;   
    private  double im; 
    
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public Complex(double re) {
        this.re = re;
        this.im = 0;
    }
    
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }
    public double abs() {
        return Math.hypot(re, im);
    }
    
    public double phase() {
        return Math.atan2(im, re);
    }
    
    public Complex plus(Complex that) {
        double real = this.re + that.re;
        double imag = this.im + that.im;
        return new Complex(real, imag);
    }
    
    public Complex minus(Complex that) {
        double real = this.re - that.re;
        double imag = this.im - that.im;
        return new Complex(real, imag);
    }

    public Complex times(Complex that) {
        double real = this.re * that.re - this.im * that.im;
        double imag = this.re * that.im + this.im * that.re;
        return new Complex(real, imag);
    }

    public Complex scale(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }
    
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }
    
    public Complex conjugate() {
        return new Complex(re, -im);
    }
    
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }
    
    public double re() {
        return re;
    }
    
    public double im() {
        return im;
    }
    
    public Complex divides(Complex that) {
        return this.times(that.reciprocal());
    }
    
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }
    
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }
    
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }
    
    public Complex tan() {
        return sin().divides(cos());
    }
}
