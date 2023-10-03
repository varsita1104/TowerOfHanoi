import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Stack;

public class PegPanel extends JPanel {
    private Map<String, Peg> pegs = null;
    private static final int PEG_WIDTH = 15;
    private static final int PEG_HEIGHT = 300;
    private static final int PEG_X = 250;
    private static final int PEG_Y = 100;

    public PegPanel() {
    }

    public void setPegs(Map<String, Peg> pegs) {
        this.pegs = pegs;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int i = 1;
        for (Peg peg : pegs.values()) {
            if (peg.getDisks() != null) {
                peg.setBounds(PEG_X * i, PEG_Y, PEG_WIDTH, PEG_HEIGHT);
                int peg_disk_x = PEG_X * i;
                for (int j = 0; j < peg.getDisks().size(); j++) {
                    peg.getDisks().get(j).setBounds(peg_disk_x - (175 + peg.getDisks().get(j).getName() * 25) / 2, 400 - (j + 1) * 25, 175 + peg.getDisks().get(j).getName() * 25, 25);
                }
            }
            i++;
        }
        paintPegs(g2d);
    }

    private void paintPegs(Graphics2D g2d) {
        for (Peg peg : pegs.values()) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect((int) peg.getX(), (int) peg.getY(), (int) peg.getWidth(), (int) peg.getHeight());
            paintDisks(g2d, peg.getDisks());
        }
    }

    private void paintDisks(Graphics2D g2d, Stack<Disk> disks) {
        for (Disk disk : disks) {
            g2d.setColor(Color.red);
            g2d.fillRoundRect((int) disk.getX(), (int) disk.getY(), (int) disk.getWidth(), (int) disk.getHeight(), 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect((int) disk.getX(), (int) disk.getY(), (int) disk.getWidth(), (int) disk.getHeight(), 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString(" " + (disk.getName()), (int) disk.getX() + 90 + Integer.parseInt(String.valueOf(disk.getName())) * 12, (int) disk.getY() + 15);
        }
    }
}