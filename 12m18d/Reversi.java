import java.awt.*;
import java.awt.event.*;
import javax.lang.model.util.ElementScanner14;
import javax.swing.*;
import java.util.stream.*;
import java.util.*;

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

    void doReverse()
    {
        if (obverse == black)
            obverse = white;
        else if (obverse == white)
            obverse = black;
    }
}

class Board
{
    private Stone[][] board = new Stone[8][8];
    public int num_grid_black = 0;
    public int num_grid_white = 0;
    private Point[] direction = new Point[8];
    public int[][] eval_black = new int[8][8];
    public int[][] eval_white = new int[8][8];

    Board()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                board[i][j] = new Stone();
                //board[i][j].setObverse(0);
                if ((i == 3 && j == 3) || (i == 4 && j == 4))
                    board[i][j].setObverse(1);
                else if ((i == 3 && j == 4) || (i == 4 && j == 3))
                    board[i][j].setObverse(2);
            }
        }
        direction[0] = new Point(1, 0);
        direction[1] = new Point(1, 1);
        direction[2] = new Point(0, 1);
        direction[3] = new Point(-1, 1);
        direction[4] = new Point(-1, 0);
        direction[5] = new Point(-1, -1);
        direction[6] = new Point(0, -1);
        direction[7] = new Point(1, -1);
    }

    ArrayList<Integer> getLine(int x, int y, Point d)
    {
        ArrayList<Integer> line = new ArrayList<Integer>();
        int cx = x + d.x;
        int cy = y + d.y;
        while (isOnBoard(cx, cy) && board[cx][cy].getObverse() != 0)
        {
            line.add(board[cx][cy].getObverse());
            cx += d.x;
            cy += d.y;
        }
        return line;
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
                board[i][j].paint(g, p, unit_size * 80 / 100);
            }
        }
    }

    void setStone(int x, int y, int s)
    {
        board[x][y].setObverse(s);
    }
    
    boolean isOnBoard(int x, int y)
    {
        return (0 <= x && x < 8) && (0 <= y && y < 8);
    }

    int countStone(int s)
    {
        int cnt;

        cnt = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j].getObverse() == s)
                    cnt++;
        return (cnt);
    }

    int countReverseStone(int x, int y, int s)
    {
        if (board[x][y].getObverse() != 0)
            return (-1);
        int cnt = 0;
        for (int d = 0; d < 8; d++)
        {
            ArrayList<Integer> line = new ArrayList<Integer>();
            line = getLine(x, y, direction[d]);
            int n = 0;
            while (n < line.size() && line.get(n) != s)
                n++;
            if (1 <= n && n < line.size())
                cnt += n;
        }
        return (cnt);
    }

    void evaluateBoard()
    {
        num_grid_black = 0;
        num_grid_white = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                eval_black[i][j] = countReverseStone(i, j, 1);
                if (eval_black[i][j] > 0)
                    num_grid_black++;
                eval_white[i][j] = countReverseStone(i, j, 2);
                if (eval_white[i][j] > 0)
                    num_grid_white++;
            }
        }
    }

    void printBoard()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
                System.out.printf("%2d ", board[j][i].getObverse());
            System.out.println("");
        }
    }

    void printEval()
    {
        System.out.println("Black(1):");
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
                System.out.printf("%2d ", eval_black[j][i]);
            System.out.println("");
        }
        System.out.println("White(2):");
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
                System.out.printf("%2d ", eval_white[j][i]);
            System.out.println("");
        }
    }

    void setStoneAndReverse(int x, int y, int s)
    {
        //石がおける場所なら
        if (countReverseStone(x, y, s) > 0)
        {
            setStone(x, y, s);
            for (int d = 0; d < 8; d++)
            {
                ArrayList<Integer> line = new ArrayList<Integer>();
                line = getLine(x, y, direction[d]);
                int n = 0;
                while (n < line.size() && line.get(n) != s)
                    n++;
                if (1 <= n && n < line.size())
                {
                    int x_cnt = x;
                    int y_cnt = y;
                    for (int i = 0; i < n; i++)
                    {
                        x_cnt += direction[d].x;
                        y_cnt += direction[d].y;
                        board[x_cnt][y_cnt].doReverse();
                    }
                }
            }
        }
    }
}

class Player
{
    public final static int type_human = 0;
    public final static int type_computer = 1;
    private int color;
    private int type;

    Player (int c, int t)
    {
        if (c == Stone.black || c == Stone.white)
            color = c;
        else
        {
            System.out.println("プレイヤーの石は黒か白でなければいけません:" + c);
            System.exit(0);
        }
        if (t == type_human || t == type_computer)
            type = t;
        else 
        {
            System.out.println("プレイヤーは人間かコンピュータでなければいけません:" + t);
            System.exit(0);
        }
    }

    int getColor()
    {
        return (color);
    }

    int getType()
    {
        return (type);
    } 

    Point tactics(Board bd, int pattern)
    {
        // pattern1
        if (pattern == 1)
        {
            Point points[] = new Point[64];
            Random random = new Random();
            int rand_index;
            int cnt = 0;

            if (color == Stone.black)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (bd.eval_black[i][j] > 0)
                        {
                            points[cnt] = new Point(i, j);
                            cnt++;
                        }
                    }
                }
                if (cnt > 0)
                {
                    rand_index = random.nextInt(cnt);
                    return (points[rand_index]);            
                }
            }
            else if (color == Stone.white)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (bd.eval_white[i][j] > 0)
                        {
                            points[cnt] = new Point(i, j);
                            cnt++;
                        }
                    }
                }
                if (cnt > 0)
                {
                    rand_index = random.nextInt(cnt);
                    return (points[rand_index]);
                }
            }
            return (new Point(-1, -1));
        }
        // pattern2
        else if (pattern == 2)
        {
            Point p = new Point();
            Point max_p = new Point();
            int cnt = 0;

            max_p.x = 0;
            max_p.y = 0;
            if (color == Stone.black)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (bd.eval_black[i][j] > 0 && 
                        bd.eval_black[max_p.x][max_p.y] < bd.eval_black[i][j])
                        {
                            cnt = 1;
                            max_p.x = i;
                            max_p.y = j;
                        }
                    }
                } 
            }
            else if (color == Stone.white)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (bd.eval_white[i][j] > 0 &&
                        bd.eval_white[max_p.x][max_p.y] < bd.eval_white[i][j])
                        {
                            cnt = 1;
                            max_p.x = i;
                            max_p.y = j;
                        }
                    }
                }
            }
            if (cnt == 1)
                return (max_p);
            return (new Point(-1, -1));
        }
        else if (pattern == 4)
        {
            Point p = new Point();
            Point opt_p = new Point();
            int cnt = 0;

            opt_p.x = 0;
            opt_p.y = 0;
            if (color == Stone.black)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (bd.eval_black[i][j] > 0 && ((i == 1 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 1)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_black[i][j] > 0 && ((i == 0 && j == 6) || (i == 1 && j == 7) || (i == 1 && j == 6)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_black[i][j] > 0 && ((i == 6 && j == 7) || (i == 7 && j == 6) || (i == 7 && j == 7)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_black[i][j] > 0 && ((i == 6 && j == 0) || (i == 7 && j == 1) || (i == 6 && j == 1)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_black[i][j] > 0 && i % 7 == 0 && j % 7 == 0)
                        {
                            opt_p.x = i;
                            opt_p.y = j;
                            return (opt_p);
                        }
                        else if (bd.eval_black[i][j] > 0 &&
                        bd.eval_black[opt_p.x][opt_p.y] < bd.eval_black[i][j])
                        {
                            cnt++;
                            opt_p.x = i;
                            opt_p.y = j;
                        }
                    }
                }
                if (cnt == 0)
                    return (p);
            }
            else if (color == Stone.white)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        if (bd.eval_white[i][j] > 0 && ((i == 1 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 1)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_white[i][j] > 0 && ((i == 0 && j == 6) || (i == 1 && j == 7) || (i == 1 && j == 6)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_white[i][j] > 0 && ((i == 6 && j == 7) || (i == 7 && j == 6) || (i == 7 && j == 7)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_white[i][j] > 0 && ((i == 6 && j == 0) || (i == 7 && j == 1) || (i == 6 && j == 1)))
                        {
                            p.x = i;
                            p.y = j;
                        }
                        else if (bd.eval_white[i][j] > 0 && i % 7 == 0 && j % 7 == 0)
                        {
                            opt_p.x = i;
                            opt_p.y = j;
                            return (opt_p);
                        }
                        else if (bd.eval_white[i][j] > 0 &&
                        bd.eval_white[opt_p.x][opt_p.y] < bd.eval_white[i][j])
                        {
                            cnt++;
                            opt_p.x = i;
                            opt_p.y = j;
                        }
                    }
                }
                if (cnt == 0)
                    return (p);
            }
            if (cnt > 0)
                return (opt_p);
            return (new Point(-1, -1));
        }

        // pattern3(default)
        Point p = new Point();
        Point opt_p = new Point();
        int cnt = 0;

        opt_p.x = 0;
        opt_p.y = 0;
        if (color == Stone.black)
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (bd.eval_black[i][j] > 0 && i % 7 == 0 && j % 7 == 0)
                    {
                        opt_p.x = i;
                        opt_p.y = j;
                        return (opt_p);
                    }
                    if (bd.eval_black[i][j] > 0 &&
                    bd.eval_black[opt_p.x][opt_p.y] < bd.eval_black[i][j])
                    {
                        cnt = 1;
                        opt_p.x = i;
                        opt_p.y = j;
                    }
                }
            }
        }
        else if (color == Stone.white)
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (bd.eval_white[i][j] > 0 && i % 7 == 0 && j % 7 == 0)
                    {
                        opt_p.x = i;
                        opt_p.y = j;
                        return (opt_p);
                    }
                    if (bd.eval_white[i][j] > 0 &&
                    bd.eval_white[opt_p.x][opt_p.y] < bd.eval_white[i][j])
                    {
                        cnt = 1;
                        opt_p.x = i;
                        opt_p.y = j;
                    }
                }
            } 
        }
        if (cnt == 1)
            return (opt_p);
        return (new Point(-1, -1));
    }

    Point nextMove(Board bd, Point p, int pattern)
    {
        if (type == type_human)
            return (p);
        else if (type == type_computer)
            return (tactics(bd, pattern));
        return (new Point(-1, -1));
    }
}

public class Reversi extends JPanel
{
    public final static int UNIT_SIZE = 80;
    private Board board = new Board();
    static int[][] count = new int[8][8];
    private int turn;
    private Player[] player = new Player[2];
    static int pattern;
    static int computer1_tactics;
    static int computer2_tactics;
    static int computer_cnt;
    
    public Reversi()
    {
        setPreferredSize(new Dimension(800, 800));
        addMouseListener(new MouseProc());
        if (computer_cnt == 0)
        {
            player[0] = new Player(Stone.black, Player.type_human);
            player[1] = new Player(Stone.white, Player.type_human);
        }
        else if (computer_cnt == 1)
        {
            player[0] = new Player(Stone.black, Player.type_human);
            player[1] = new Player(Stone.white, Player.type_computer);
        }
        else if (computer_cnt == 2)
        {
            player[0] = new Player(Stone.black, Player.type_computer);
            player[1] = new Player(Stone.white, Player.type_computer);
        }
        else
        {
            System.out.println("コンピュータの数(computer_cnt)は 0,1,2 のどれかです");
            System.exit(0);
        }
        turn = Stone.black;
        if (computer_cnt == 2)
            auto_battle();
    }

    public void paintComponent(Graphics g)
    {
        String msg1 = "";
        board.paint(g, UNIT_SIZE);
        g.setColor(Color.white);
        if (turn == Stone.black)
            msg1 = "黒の番です";
        else
            msg1 = "白の番です";
        if (player[turn - 1].getType() == Player.type_computer)
            msg1 += "(考えています)";
        String msg2 = "[黒:" + board.countStone(Stone.black) +
                    ", 白" + board.countStone(Stone.white) + "]";
        g.drawString(msg1, UNIT_SIZE / 2, UNIT_SIZE / 2);
        g.drawString(msg2, UNIT_SIZE / 2, 19 * UNIT_SIZE / 2);
    }

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
        if (black_cnt < white_cnt)
            System.out.println(black_cnt + " : " + white_cnt + "  で戦略④の勝ち");
        else
            System.out.println(black_cnt + " : " + white_cnt + "  で戦略③の勝ち");
        JOptionPane.showMessageDialog(this, str, "ゲーム終了", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    void MessageDialog(String str)
    {
        JOptionPane.showMessageDialog(this, str, "情報", JOptionPane.INFORMATION_MESSAGE);
    }

    void changeTurn()
    {
        if (turn == Stone.black)
            turn = Stone.white;
        else if (turn == Stone.white)
            turn = Stone.black;
    }

    class TacticsThread extends Thread
    {
        public void run()
        {
            try {
                Thread.sleep(1000);
                if (computer_cnt == 2)
                {
                    if (turn == Stone.black)
                        pattern = computer1_tactics;
                    else
                        pattern = computer2_tactics;
                }
                Point nm = player[turn - 1].nextMove(board, new Point(-1, -1), pattern);
                //if (nm.x == -1 && nm.y == -1)
                //    MessageDialog("相手はパスです");
                //else
                if (nm.x != -1 && nm.y != -1)
                    board.setStoneAndReverse(nm.x, nm.y, player[turn - 1].getColor());
                board.evaluateBoard();
                repaint();
                changeTurn();
                addMouseListener(new MouseProc());
                if (board.num_grid_black == 0 && board.num_grid_white == 0)
                    EndMessageDialog();
                if (computer_cnt == 2)
                    auto_battle();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    void auto_battle()
    {
        Thread th = new TacticsThread();
        th.start();
        board.evaluateBoard();
    }

    class MouseProc extends MouseAdapter
    {
        public void mouseClicked(MouseEvent me)
        {
            Point point = me.getPoint();
            int btn = me.getButton();
            Point gp = new Point();

            board.evaluateBoard();
            gp.x = point.x / UNIT_SIZE - 1;
            gp.y = point.y / UNIT_SIZE - 1;
            if (!board.isOnBoard(gp.x, gp.y))
                return;
            removeMouseListener(this);
            // プレイヤー（人間）の手番
            if (player[turn - 1].getType() == Player.type_human)
            {
                if ((player[turn - 1].getColor() == Stone.black
                    && board.num_grid_black == 0) || 
                    (player[turn - 1].getColor() == Stone.white
                    && board.num_grid_white == 0))
                {
                    MessageDialog("あなたはパスです");
                    changeTurn();
                    repaint();
                }
                else if ((player[turn - 1].getColor() == Stone.black
                        && board.eval_black[gp.x][gp.y] > 0) ||
                        (player[turn - 1].getColor() == Stone.white
                        && board.eval_white[gp.x][gp.y] > 0))
                {
                    Point nm = player[turn - 1].nextMove(board, gp, pattern);
                    board.setStoneAndReverse(nm.x, nm.y, player[turn - 1].getColor());
                    changeTurn();
                    repaint();
                    //board.evaluateBoard();
                    if (board.num_grid_black == 0 && board.num_grid_white == 0)
                        EndMessageDialog();
                }
                if (player[turn - 1].getType() == Player.type_human)
                    addMouseListener(this);
            }
            // コンピュータ（自動）の手番
            if (player[turn - 1].getType() == Player.type_computer)
            {
                Thread th = new TacticsThread();
                th.start();
            }
            board.evaluateBoard();
        }
    }
    
    public static void main(String[] args)
    {
        computer_cnt = args.length;
        if (computer_cnt== 2)
        {
            computer1_tactics = Integer.parseInt(args[0]);
            computer2_tactics = Integer.parseInt(args[1]);
        }
        else if (computer_cnt == 1)
            pattern = Integer.parseInt(args[0]);
        else
            pattern = 3;
        if (computer_cnt == 2)
            System.out.println("computer1(戦略" + computer1_tactics + ") と computer2(戦略" + computer2_tactics + ") の対決です");
        else if (computer_cnt == 1)
            System.out.println("pattern" + pattern + "と戦います");
        else if (computer_cnt == 0)
            System.out.println("人同士の対決です");
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new Reversi());
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}