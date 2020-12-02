import java.awt.*;
import java.awt.event.*;

import javax.lang.model.util.ElementScanner14;
import javax.swing.*;
import java.util.stream.*;

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

    int getObverse()
    {
        return (obverse);
    }
}

class Board 
{
    private Stone[][] stones = new Stone[8][8];
    public int num_grid_black = 0;
    public int num_grid_white = 0;
    
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
    
    void setStone(int x, int y, int s)
    {
        stones[x][y].setObverse(s);
    }
    
    boolean isOnBoard(int x, int y)
    {
        return (0 <= x && x <= 9) && (0 <= y && y <= 9);
    }

    /*
    int countStone(int s)
    {
        int cnt;

        cnt = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j< 8; j++)
                if (stones[i][j].getObverse() == s)
                    cnt++;
        return (cnt);
    }

    void evaluateBoard()
    {
        num_grid_black = 64 - countStone(1) - countStone(2);
        num_grid_white = 64 - countStone(1) - countStone(2);
    }
    */
}

public class Reversi extends JPanel
{
    public final static int UNIT_SIZE = 80;
    Board board = new Board();
    int x;
    int y;
    static int[][] count = new int[8][8];
    
    public Reversi()
    {
        setPreferredSize(new Dimension(800, 800));
        addMouseListener(new MouseProc());
    }

    public void paintComponent(Graphics g)
    {
        board.paint(g, UNIT_SIZE);
    }

    class MouseProc extends MouseAdapter
    {
        public void mouseClicked(MouseEvent me)
        {
            Point point = me.getPoint();
            int btn = me.getButton();

            System.out.println("(" + point.x + ", " + point.y + ")");
            x = point.x / UNIT_SIZE - 1;
            y = point.y / UNIT_SIZE - 1;
            if (board.isOnBoard(x, y))
            {
                if (btn == MouseEvent.BUTTON1)
                    board.setStone(x, y, 1);
                else if (btn == MouseEvent.BUTTON3)
                    board.setStone(x, y, 2);
            }
            repaint();

            /*
            board.evaluateBoard();
            if (board.num_grid_black == 0 && board.num_grid_white == 0)
                EndMessageDialog();
            */
        }
    }
    /*
    void EndMessageDialog()
    {
        String  str;
        int     white_cnt;
        int     black_cnt;

        black_cnt = board.countStone(1);
        white_cnt = board.countStone(2);
        str = "[黒:" + black_cnt + ",白" + white_cnt + "]で";
        if (black_cnt < white_cnt)
            str += "白の勝ち";
        else if (black_cnt > white_cnt)
            str += "黒の勝ち";
        else
            str += "引き分け";
        JOptionPane.showMessageDialog(this, str, "ゲーム終了", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    */

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
