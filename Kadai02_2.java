import java.util.Scanner;

class kadai02_2
{
    static public void main(String[] av)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("n : ");
        int n = sc.nextInt();
        int num[] = new int[n];
        int max;
        int min;

        for (int i = 0; i < n; i++)
        {
            System.out.print(i + " : ");
            num[i] = sc.nextInt();
        }
        min = num[0];
        max = num[1];
        for (int i = 0; i < n; i++)
        {
            if (max < num[i])
                max = num[i];
            if (min > num[i])
                min = num[i];
        }
        System.out.println("max : " + max);
        System.out.println("min : " + min);
    }
}

/*
実行結果

n : 6
0 : -124
1 : 5
2 : 3
3 : 120
4 : 43
5 : 3
max : 120
min : -124

*/