import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;

public class Kadai07_2 extends Application 
{
    private TextField tf;
    private Button[][] bt = new Button[4][5];
    
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage)throws Exception
    {
        tf = new TextField();
        tf.setEditable(false);
        tf.setMaxWidth(380);
        tf.setFont(Font.font("MonoSpace", 40));
        tf.setAlignment(Pos.CENTER_RIGHT);

        String[][] bt_str = {{"CE", "C", "BS", "/"},
                             {"7", "8", "9", "*"}, 
                             {"4", "5", "6", "-"},
                             {"1", "2", "3", "+"},
                             {"±", "0", ".", "="}};
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                bt[i][j] = new Button(bt_str[j][i]);
                bt[i][j].setPrefWidth(95);
                bt[i][j].setPrefHeight(95);
                bt[i][j].setFont(Font.font("MonoSpace", 30));
                bt[i][j].setOnAction(new ButtonEventHandler());
            }
        }
        GridPane gp = new GridPane();
        gp.setHgap(2);
        gp.setVgap(2);
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                gp.add(bt[i][j], i, j);
            }
        }
        gp.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setTop(tf);
        bp.setAlignment(tf, Pos.CENTER);
        bp.setCenter(gp);

        Scene sc = new Scene(bp, 400, 600);
        stage.setScene(sc);
        stage.setTitle("電卓");
        stage.show();
    }

    class ButtonEventHandler implements EventHandler<ActionEvent>
    {
        public void handle (ActionEvent e)
        {
            String in = ((Button)e.getSource()).getText();
            StringBuffer stb = new StringBuffer(tf.getText());
            if (in == "BS")
            {
                if (stb.length() - 1 >= 0)
                    stb.deleteCharAt(stb.length() - 1);
            }
            else if (in == "±")
            {
                if ('0' <= stb.charAt(0) && stb.charAt(0) <= '9')
                    stb.insert(0, '-');
            }
            else if (in != "=")
                stb.append(in);
            String str = new String(stb.toString());
            String regex = "[+\\-]?[0-9]+.?[0-9]*[+\\-\\*/]{1}[0-9]+.?[0-9]*";
            tf.setText(str);
            if (str.matches(regex) && in == "=")
            {
                String result;
                String regrex2 = "(?=[+\\-\\*/])";
                String[] operands = str.toString().split(regrex2);
                double op1 = Double.parseDouble(operands[0]);
                double op2 = Double.parseDouble(operands[1].substring(1));
                
                if (operands[1].charAt(0) == '+')
                    result = String.valueOf(op1 + op2);
                else if (operands[1].charAt(0) == '-')
                    result = String.valueOf(op1 - op2);
                else if (operands[1].charAt(0) == '*')
                    result = String.valueOf(op1 * op2);
                else
                    result = String.valueOf(op1 / op2);
                tf.setText(result);
            }
            if (in == "C" || in == "CE")
                tf.clear();
        }
    }
}
