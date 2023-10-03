import javax.swing.*;
import java.awt.*;

public class TOHGameGui extends JFrame {
    final int WIDTH=1000;
    final int HEIGHT=600;

    private static final String TITLE = "Tower of Hanoi";

    private TOHPanel tohPanel = new TOHPanel();


    public TOHGameGui() {
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);

        add(tohPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
