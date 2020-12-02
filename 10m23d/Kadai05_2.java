import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;

public class Kadai05_2 extends Application
{
    private TextField[][] tf = new TextField[9][9];
    private Button bt = new Button("リセット");
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage)throws Exception
    {
        int[][] board = new int [9][9];
        String fname = "input.txt";
        List<String> list = getParameters().getRaw();
        if (list.size() > 0)
            fname = list.get(0);
        try
        {
            Scanner sc = new Scanner(new File(fname));
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    board[i][j] = sc.nextInt();
                    if (board[i][j] < 0 || 9 < board[i][j]) board[i][j] = 0;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] == 0)
                    tf[i][j] = new TextField();
                else
                {
                    tf[i][j] = new TextField(String.valueOf(board[i][j]));
                    tf[i][j].setEditable(false);
                    tf[i][j].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                }
                tf[i][j].setMaxWidth(40);
                tf[i][j].setFont(Font.font("MonoSpace", 20));
            }
        }

        GridPane[][] gp_set = new GridPane[3][3];

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                gp_set[i][j] = new GridPane();
                gp_set[i][j].setHgap(5);
                gp_set[i][j].setVgap(5);
            }
        }

        int index_i, index_j;

        index_i = 0;
        index_j = 0;
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (i < 3)
                    index_i = 0;
                else if (i < 6)
                    index_i = 1;
                else
                    index_i = 2;

                if (j < 3)
                    index_j = 0;
                else if (j < 6)
                    index_j = 1;
                else
                    index_j = 2;

                gp_set[index_i][index_j].add(tf[i][j], j % 3, i % 3);
            }
            gp_set[index_i][index_j].setAlignment(Pos.CENTER);
        }

        GridPane gp = new GridPane();
        gp.setHgap(15);
        gp.setVgap(15);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                gp.add(gp_set[i][j], j, i);
        gp.setAlignment(Pos.CENTER);

        GridPane gp_bt = new GridPane();
        gp_bt.add(bt, 0, 0);
        gp_bt.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setCenter(gp);
        bp.setBottom(gp_bt);
        Scene sc = new Scene(bp, 600, 600);
        stage.setScene(sc);
        stage.setTitle("数独");
        stage.show();
    }
}