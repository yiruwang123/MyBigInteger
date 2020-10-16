
public class ff {

		 private static final Complex ZERO = new Complex(0, 0);
		 public static Complex[] fft(Complex[] arrA) {
		        
		
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
		    		Complex tmp = arrA[i];
		            arrA[i] = arrA[j];
		            arrA[j] = tmp;
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
		        
		        return arrA;
		    }
		 
		 public static Complex[] ifft(Complex[] x) {
		        int n = x.length;
		        Complex[] y = new Complex[n];

		        // take conjugate
		        for (int i = 0; i < n; i++) {
		            y[i] = x[i].conjugate();
		        }

		        // compute forward FFT
		        y = fft(y);

		        // take conjugate again
		        for (int i = 0; i < n; i++) {
		            y[i] = y[i].conjugate();
		        }

		        // divide by n
		        for (int i = 0; i < n; i++) {
		            y[i] = y[i].scale(1.0 / n);
		        }

		        return y;

		    }
		 
		 public static Complex[] cconvolve(Complex[] x, Complex[] y) {

		        // should probably pad x and y with 0s so that they have same length
		        // and are powers of 2
		        if (x.length != y.length) {
		            throw new IllegalArgumentException("Dimensions don't agree");
		        }

		        int n = x.length;

		        // compute FFT of each sequence
		        Complex[] a = fft(x);
		        Complex[] b = fft(y);

		        // point-wise multiply
		        Complex[] c = new Complex[n];
		        for (int i = 0; i < n; i++) {
		            c[i] = a[i].times(b[i]);
		        }

		        // compute inverse FFT
		        return ifft(c);
		    }
		 
		 public static Complex[] convolve(Complex[] x, Complex[] y) {
		        Complex[] a = new Complex[2*x.length];
		        for (int i = 0; i < x.length; i++)
		            a[i] = x[i];
		        for (int i = x.length; i < 2*x.length; i++)
		            a[i] = ZERO;

		        Complex[] b = new Complex[2*y.length];
		        for (int i = 0; i < y.length; i++)
		            b[i] = y[i];
		        for (int i = y.length; i < 2*y.length; i++)
		            b[i] = ZERO;

		        return cconvolve(a, b);
		    }
		 
		 private static void show(Complex[] x, String title) {
		        System.out.println(title);
		        System.out.println("-------------------");
		        for (int i = 0; i < x.length; i++) {
		        	System.out.println(x[i]);
		        }
		        System.out.println();
		    }
		 
		 public static void main(String[] args) { 
		        int n = Integer.parseInt(args[0]);
		        Complex[] x = new Complex[n];

		        

		        // FFT of original data
		        Complex[] y = fft(x);
		        show(y, "y = fft(x)");

		        // take inverse FFT
		        Complex[] z = ifft(y);
		        show(z, "z = ifft(y)");

		        // circular convolution of x with itself
		    

		}
		 
	}



