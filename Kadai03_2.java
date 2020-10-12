import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import java.util.*;

public class Kadai03_2 extends Application 
{
    private Label lb;
    private Button bt;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage)throws Exception
    {
        StringBuffer str = new StringBuffer();
        Formatter fmt = new Formatter(str);
        Calendar now = new GregorianCalendar();
        String daytime = now.getTime().toString();
        fmt.format("%02d:%02d:%02d", now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
        lb = new Label();
        bt = new Button("更新");
        lb.setText(str.toString());

        BorderPane bp = new BorderPane();

        bp.setTop(lb);
        bp.setCenter(bt);
        bt.setOnAction(new SampleEventHandler());

        Scene sc = new Scene(bp, 300, 200);

        stage.setScene(sc);
        stage.setTitle("現在時刻");
        stage.show();
    }

    class SampleEventHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            StringBuffer str = new StringBuffer();
            Formatter fmt = new Formatter(str);
            Calendar now = new GregorianCalendar();
            String daytime = now.getTime().toString();
            fmt.format("%02d:%02d:%02d", now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
            lb.setText(str.toString());
        }
    }
}