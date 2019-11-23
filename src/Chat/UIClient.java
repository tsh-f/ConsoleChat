package Chat;

import javax.swing.*;

public class UIClient {
    private JFrame frame;
    private JTextArea text;
    private JTextField textField;
    private JButton submit;

    UIClient(){
        frame = new JFrame();
        text = new JTextArea(20, 30);
        textField = new JTextField(30);
        submit = new JButton("Ввод");


    }
}
