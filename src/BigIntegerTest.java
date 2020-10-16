
import java.util.Random;


import java.math.*;

public class BigIntegerTest {
    private int count;
    private int size;
    private String[] first;
    private String[] second;
    private String[] operator;
    private String[] results; 

    class Stopwatch { 

        private final long start;

        public Stopwatch() {
            start = System.currentTimeMillis();
        } 

        public double elapsedTime() {
            long now = System.currentTimeMillis();
            double time = (now - start) / 1000.0;
            return time;
        }

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public String[] getResults() {
        return results;
    }

    public void setResults(String[] results) {
        this.results = results;
    }

    public String[] getFirst() {
        return first;
    }

    public void setFirst(String[] first) {
        this.first = first;
    }

    public String[] getSecond() {
        return second;
    }

    public void setSecond(String[] second) {
        this.second = second;
    }

    public String[] getOperator() {
        return operator;
    }

    public void setOperator(String[] operator) {
        this.operator = operator;
    }
    
    private int nextInt(Random r, int low, int high)
    {
        int result = r.nextInt(high-low);
        result += low;
        return result;
    }
    public BigIntegerTest()
    {
        
        this(100,10000);
    }
    public BigIntegerTest(int count, int size)
    {
        this.count = count;
        this.size = size;
        first = new String[count];
        second = new String[count];
        operator = new String[count];
        results = new String[count];
        
        Random r = new Random();
        for(int i=0; i< count; i++)
        {
            int rsize = nextInt(r, 1, size);
            boolean isminus = r.nextBoolean();
            first[i] = new String(isminus?"-":"");
            for(int j=0; j<rsize; j++)
            {
                int temp = 0;
                if(j==0)
                    temp = nextInt(r, 1, 10);
                else
                    temp = nextInt(r, 0, 10);
                first[i] += String.valueOf(temp);
            }
            rsize = nextInt(r, 1, size);
            isminus = r.nextBoolean();
            second[i] = new String(isminus?"-":"");
            for(int j=0; j<rsize; j++)
            {
                int temp = 0;
                if(j==0)
                    temp = nextInt(r, 1, 10);
                else
                    temp = nextInt(r, 0, 10);
                second[i] += String.valueOf(temp);
            }
            
            int opernum = r.nextInt(3);
            if(opernum == 0) operator[i] = "+";
            else if(opernum == 1) operator[i] = "-";
            else 
            operator[i] = "*";
        }
    }
    
    public double compute()
    {
        double stdtime = 0;
        for(int i=0; i<count; i++)
        {
            Stopwatch eachtime = new Stopwatch();
            BigInteger firstbi = new BigInteger(this.first[i]);
            BigInteger secondbi = new BigInteger(this.second[i]);
            if(operator[i] == "+")
               results[i] = firstbi.add(secondbi).toString();
           else if(operator[i] == "-")
                results[i] = firstbi.subtract(secondbi).toString();
            else if(operator[i] == "*")
                results[i] = firstbi.multiply(secondbi).toString();
            double time = eachtime.elapsedTime();
            stdtime += time;
        }
        return stdtime;
    }
    
    public float test()
    {
        double stdtime = compute();
        float r = 0;
        float errorcount = 0;
        double alltime = 0;
        for(int i=0; i<count; i++)
        {
            Stopwatch eachtime = new Stopwatch();
            String s = null;
            MyBigInteger firstbi = new MyBigInteger(this.first[i]);
            MyBigInteger secondbi = new MyBigInteger(this.second[i]);
            if(operator[i] == "+")
                s = firstbi.add(secondbi).toString();
            else if(operator[i] == "-")
                s = firstbi.subtract(secondbi).toString();
            else if(operator[i] == "*")
                s = firstbi.multiply(secondbi).toString();
            double time = eachtime.elapsedTime();
            alltime += time;
            if(!s.equals(results[i]))
            {
                System.out.println("the " + (i+1) + "th result is error, use " + time + " second");
                System.out.println("first integer is: " + first[i]);
                System.out.println(operator[i]);
                System.out.println("second integer is: " + second[i]);
                System.out.println("your result is: " + s);
                System.out.println("true result is: " + results[i]);
                errorcount++;
            }
            else
            {
                System.out.println("the " + (i+1) + "th result is correct, use " + time + " second");
            }
            System.out.println("-------------------------------------------------------------------");
        }
        
        r = (count-errorcount)/count;
        System.out.println("your precision is: " + r*100 + "%, use " + alltime + " seconds, the standard time is " + stdtime + " seconds");
        return r;
    }
    
    public float testmake()
    {
        double stdtime = compute();
        float r = 0;
        float errorcount = 0;
        double alltime = 0;
        for(int i=0; i<count; i++)
        {
            Stopwatch eachtime = new Stopwatch();
            String s = null;
            MyBigInteger firstbi = new MyBigInteger(this.first[i]);
            MyBigInteger secondbi = new MyBigInteger(this.second[i]);
           if(operator[i] == "+")
              s = firstbi.add(secondbi).toString();
            else if(operator[i] == "-")
                s = firstbi.subtract(secondbi).toString();
            else if(operator[i] == "*")
                s = firstbi.multiply(secondbi).toString();
            double time = eachtime.elapsedTime();
            alltime += time;
        }
        
        r = (count-errorcount)/count;
        System.out.println("" + this.count + "\t"  + this.size + "\t" + r*100 + "%\t" + alltime + "\t" + stdtime);
        return r;
    }
    
    
    public static void main(String[] args) {
 
            BigIntegerTest test = new BigIntegerTest();
            test.test();
        
            //BigIntegerTest test = new BigIntegerTest(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            //test.testmake();
         }

}
