import java.util.Scanner;
import java.io.File;
import java.io.IOException;

class Kadai02_3
{
    static public void main(String[] av)
    {
        String fname = "test.dat";

        if ( av.length > 0 ) 
            fname = av[0];
        try
        {
            Scanner sc = new Scanner(new File(fname));
            int n  = sc.nextInt();
            System.out.println("n = " + n);
            int[] num = new int[n];
            
            for (int i = 0; i < n; i++)
            {
                num[i] = sc.nextInt();
            }
            int max;
            int max_second;
            int flag;

            max = num[0];
            max_second = num[0];
            flag = 0;
            if (n > 1)
                max_second = num[1];
            for (int i = 0; i < n; i++)
            {
                if (max < num[i])
                {
                    max_second = max;
                    max = num[i];
                }
                if (max_second < num[i] && num[i] < max)
                    max_second = num[i];
            }
            if (max != max_second)
            {
                System.out.println("max : " + max);
                System.out.println("max_second : " + max_second);
            }
            else
                System.out.println("max : " + max);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}

/*
実行結果

n = 10
max : -1
max_second : -2
*/