import java.awt.*;
import javax.swing.*;

class Stone
{
    public final static int black = 1;
    public final static int white = 2;
    private int obverse;

    Stone()
    {
        obverse = 0;
    }

    void setObverse(int color)
    {
        if (color == black || color == white)
            obverse = color;
        else
            System.out.println("黒か白でなければいけません");
    }

    void paint(Graphics g, Point p, int rad)
    {
        if (obverse == black)
        {
            g.setColor(Color.black);
            g.fillOval(p.x, p.y, rad, rad);
        }
        else if (obverse == white)
        {
            g.setColor(Color.white);
            g.fillOval(p.x, p.y, rad, rad);
        }
    }
}

class Board 
{
    Stone[][] stones = new Stone[8][8];
    Board()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                stones[i][j] = new Stone();
                if ((i == 3 && j == 3) || (i == 4 && j == 4))
                    stones[i][j].setObverse(1);
                else if ((i == 3 && j == 4) || (i == 4 && j == 3))
                    stones[i][j].setObverse(2);
            }
        }
    }
    void paint(Graphics g ,int unit_size)
    {
        // 背景
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 800);
        // 盤面
        g.setColor(new Color(0, 85, 0));
        g.fillRect(unit_size, unit_size, 800 - unit_size*2, 800 - unit_size*2);
        g.setColor(Color.black);
        // 横線
        for (int i = 0; i < 9; i++)
            g.drawLine(unit_size , unit_size * (i + 1), 800 - unit_size, unit_size * (i + 1));
        // 縦線
        for (int i = 0; i < 9; i++)
            g.drawLine(unit_size * (i + 1), unit_size, unit_size * (i + 1), 800 - unit_size);
        // 目印
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                g.fillRect(unit_size * (3 + i * 4) - 5, unit_size * (3 + j * 4) - 5, 10, 10);        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Point p = new Point();
                p.x = (int)(unit_size * (i + 1.1));
                p.y = (int)(unit_size * (j + 1.1));
                stones[i][j].paint(g, p, unit_size * 80 / 100);
            }
        }
    }
}

public class Reversi extends JPanel
{
    public final static int UNIT_SIZE = 80;
    Board board = new Board();
    
    public Reversi()
    {
        setPreferredSize(new Dimension(800, 800));
    }

    public void paintComponent(Graphics g)
    {
        board.paint(g, UNIT_SIZE);
    }

    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new Reversi());
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
