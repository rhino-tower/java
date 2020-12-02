import java.util.*;
import java.io.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.collections.*;

public class Kadai06_1 extends Application{
    private Button bt;
	private Label lb;
	private static String fname = "input.txt";
	private int[][] value = new int[9][9];
    private ArrayList<ComboBox<String>> cb_list= new ArrayList<ComboBox<String>>();
    private TextField[][] tf = new TextField[9][9];

	public static void main(String[] args)
	{
		if (args.length > 0)
            fname = args[0];
		launch(args);
    }
    

	public void start(Stage stage) throws Exception{
		Scanner scn = new Scanner(new File(fname));
        ObservableList<String> ol = FXCollections.observableArrayList("1", "2", "3", "4", "5","6","7","8","9");
		try{
			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){
					value[i][j] = scn.nextInt();
					ComboBox<String> cb = new ComboBox<String>(); 	
					cb.setItems(ol);
					cb.setOnAction(new InputEventHandler());
					cb_list.add(cb);
                    if (1 <= value[i][j] && value[i][j] <= 9)
                    { 
						tf[i][j] = new TextField(String.valueOf(value[i][j]));
						tf[i][j].setEditable(false);
						tf[i][j].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
						tf[i][j].setFont(Font.font("MonoSpace", 18));
						tf[i][j].setMaxWidth(55);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			System.exit(0);
		}

		GridPane[][] sub_gp = new GridPane[3][3];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				sub_gp[i][j] = new GridPane();
				sub_gp[i][j].setHgap(5);
				sub_gp[i][j].setVgap(5);
                for (int k = 0; k < 3; k++){
					for (int l = 0; l < 3; l++){
						if (1 <= value[i * 3 + k][j * 3 + l])
							sub_gp[i][j].add(tf[i * 3 + k][j * 3 + l], l, k);
						else
							sub_gp[i][j].add(cb_list.get((i * 3 + k) * 9 + (j * 3 + l)), l, k);
                    }
                }
			}
		}


		GridPane gp = new GridPane();
		gp.setHgap(15);
		gp.setVgap(15);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
                gp.add(sub_gp[i][j], j, i);
		gp.setAlignment(Pos.CENTER);

        lb = new Label("各コンボボックスで数字を選択してください");

		bt = new Button("リセット");
		bt.setOnAction(new ResetButtonHandler());
		GridPane gp_bt = new GridPane();
		gp_bt.add(bt, 0, 0);
		gp_bt.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		bp.setTop(gp);
		bp.setCenter(lb);
		bp.setBottom(gp_bt);

		Scene sc = new Scene(bp, 600, 450);
		stage.setScene(sc);
		stage.setTitle("数独");
		stage.show();
	}

	public boolean is_clear(){
		try{
			int[] check_x = new int[9];
			int[] check_y = new int[9];
            int[] check_square = new int [9];
            Arrays.fill(check_x, 0);
            Arrays.fill(check_y, 0);
            Arrays.fill(check_square, 0);

			for (int i = 0; i < 9; i++)
			{
				
				for (int j = 0; j < 9; j++)
				{   
                    if (1 <= value[i][j] && value[i][j] <= 9)
                    {
						check_x[value[i][j] - 1] = 1;
						check_y[value[i][j] - 1] = 1;
                    }
                    else
                    {
						int index1 = Integer.parseInt(cb_list.get(i * 9 + j).getValue().toString())-1;
						int index2 = Integer.parseInt(cb_list.get(i * 9 + j).getValue().toString())-1;
						check_x[index1] = 1;
						check_y[index2]= 1;
					}
				}
				if (Arrays.stream(check_x).sum() < 9 || Arrays.stream(check_y).sum() < 9){
					return false;
				}
            }
            for (int i = 0; i < 3; i ++)
            {
                for (int j = 0; j < 3; j++)
                {
                    Arrays.fill(check_square, 0);
                    for (int k = 0; k < 3; k++)
                    {
                        for (int l = 0; l < 3; l++)
                        {
                            if (1 <= value[i * 3 + k][j * 3 + l] && value[i * 3 + k][j * 3 + l] <= 9)
                            { 
								check_square[value[i * 3 + k][j * 3 + l] - 1] = 1;
                            }
                            else
                            {								
                                check_square[Integer.parseInt(cb_list.get((i * 3 + k) * 9 + ( j * 3 + l)).getValue().toString()) - 1] = 1;
							}
						}
					}
					if (Arrays.stream(check_square).sum() < 9)
						return false;
				}
			}
			return true;
		}catch (Exception e){ 
            return false;
        }
	}

	class InputEventHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
            if (is_clear()){
                lb.setText("ゲームクリア！おめでとうございます！！");
            }else{
                lb.setText("各コンボボックスで数字を選択してください");
            }
		}
	}

	class ResetButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++)
					if (!(1 <= value[i][j] && value[i][j] <= 9))
						cb_list.get(i * 9 + j).setValue(null);
			lb.setText("各コンボボックスで数字を選択してください");
		}
    }
    
}