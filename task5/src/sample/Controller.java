package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Controller {

    @FXML
    private TextArea inputArea;

    @FXML
    private void solve(){
        String text = inputArea.getText();

        try{
            Tree tree = new Tree();
            tree.load(text);
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\BULIZHNIK\\Desktop\\graph.dot"));
            writer.write(tree.save());
            writer.close();
            System.out.println(tree.min());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
