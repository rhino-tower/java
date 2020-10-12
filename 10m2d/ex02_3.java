import java.util.Scanner;
import java.io.File;
import java.io.IOException;

class ex02_3
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
            for (int i = 0; i < n; i++)
            {
                int v = sc.nextInt();
                System.out.println("v = " + v);
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}