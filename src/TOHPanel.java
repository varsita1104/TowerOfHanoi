import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TOHPanel extends JPanel {
    private TOHGame game = null;
    private PegPanel pegPanel = null;

    private final JLabel inputLabel = new JLabel();
    private final JButton startButton=new JButton("Start");
    private final JButton stopButton=new JButton("Stop");
    private final JButton resetButton=new JButton("Reset");

    public TOHPanel() {
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        addPegPanel();
        addInfoPanel();
        addActionPanel();
    }

    private void addInfoPanel() {
        JPanel infoPanel = new JPanel();
        inputLabel.setText("Enter Number of Disk(s)");
        infoPanel.add(inputLabel);
        SpinnerModel spinnerModel = new SpinnerNumberModel(3, 3,10, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        game = new TOHGame();
        game.setNoOfDisks(Integer.parseInt(spinner.getValue().toString()));
        game.setCurrState(GameState.INIT);
        game.go();
        pegPanel.setPegs(game.getPegs());
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                game.setNoOfDisks(Integer.parseInt(spinner.getValue().toString()));
                game.setCurrState(GameState.INIT);
                game.go();
                pegPanel.setPegs(game.getPegs());
            }
        });
        infoPanel.add(spinner);
        add(infoPanel, BorderLayout.NORTH);
    }

    private void addPegPanel() {
        pegPanel = new PegPanel();
        add(pegPanel, BorderLayout.CENTER);
    }

    private void addActionPanel() {
        JPanel actionsPanel = new JPanel();
        actionsPanel.add(startButton);
        actionsPanel.add(stopButton);
        actionsPanel.add(resetButton);
        add(actionsPanel, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable startRunnable = new Runnable() {
                    @Override
                    public void run() {
                        startLogic();
                    }
                };
                Thread startThread = new Thread(startRunnable);
                startThread.start();
                System.out.println(startThread.getStackTrace());
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(Thread.getAllStackTraces());
                game.setCurrEvent(GameEvent.STOP);
                game.go();
                pegPanel.setPegs(game.getPegs());
                pegPanel.repaint();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setCurrEvent(GameEvent.RESET);
                game.go();
                pegPanel.setPegs(game.getPegs());
                pegPanel.repaint();
            }
        });
    }

    private void startLogic() {
        if(game.getCurrState() != GameState.RUNNING) {
            game.setCurrEvent(GameEvent.START);
            game.go();
            pegPanel.setPegs(game.getPegs());
            pegPanel.repaint();
            for (int i = game.getCurrentMove(); i <= game.getNoOfMoves(); i++) {
                if (game.getCurrState() == GameState.RUNNING) {
                    game.runAlgorithm();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            pegPanel.setPegs(game.getPegs());
                            pegPanel.repaint();
                        }
                    });
                }
            }
        } else {
            System.out.println("Game is already Running");
        }
    }
}
