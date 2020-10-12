import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;


public class Kadai03_1 extends Application
{
    private Label lb;

    public static void main(String[] args)
    {
        launch(args);
    }
    public void start(Stage stage)throws Exception
    {
        Calendar now = new GregorianCalendar();
        String daytime = now.getTime().toString();
        StringBuffer str = new StringBuffer();
        Formatter fmt = new Formatter(str);
        fmt.format("%02d:%02d:%02d", now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), now.get(Calendar.SECOND));
        lb = new Label();
        lb.setText(str.toString());
        BorderPane bp = new BorderPane();
        bp.setCenter(lb);
        Scene sc = new Scene(bp, 300, 200);
        stage.setScene(sc);
        stage.setTitle("サンプル");
        stage.show();
    }
}