import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Represents the state of the Game.
 * INIT - set in the beginning
 * COMPLETED - set after algorithm is finished executing
 */
enum GameState {
    INIT,
    NOT_RUNNING,
    RUNNING,
    PAUSED,
    COMPLETED
}

/**
 * Represents the button events performed in the UI.
 */
enum GameEvent {
    START,
    STOP,
    RESET
}

public class TOHGame {
    private GameState currState;
    private GameEvent currEvent;
    private int noOfDisks;
    private int noOfMoves;
    private int currentMove; //Assign value in different place
    private static final int NO_OF_PEGS = 3;
    private Map<String, Peg> pegs;

    TOHGame() {}

    private void start() {
        Peg src = pegs.get("src");
        Stack<Disk> disks = new Stack<>();
        for (int i = noOfDisks; i > 0; i--) {
            disks.push(new Disk(i));
        }
        src.setDisks(disks);
    }

    private void resume() {
        runAlgorithm();
    }

    private void stop() {
        System.out.println("Game Paused");
    }

    private void reset() {
        pegs = new LinkedHashMap<>();
        pegs.put("src", new Peg("Source"));
        pegs.put("aux", new Peg("Auxiliary"));
        pegs.put("dest", new Peg("Destination"));
    }

    public void runAlgorithm() {
        System.out.println("Moves = "+ currentMove+"/"+noOfMoves);
        //System.out.println(pegs);
        if(currentMove <= noOfMoves){
            if(currentMove % NO_OF_PEGS == 1){
                moveDisks(pegs.get("src"), pegs.get("dest"));
            }
            else if(currentMove % NO_OF_PEGS == 2){
                moveDisks(pegs.get("src"), pegs.get("aux"));
            }
            else if(currentMove % NO_OF_PEGS == 0) {
                moveDisks(pegs.get("dest"), pegs.get("aux"));
            }
            currentMove++;
        }
        if(currentMove > noOfMoves) {
            //Algorithm Completed
            currState = GameState.COMPLETED;
        }
    }

    private void moveDisks(Peg fromPeg, Peg toPeg){
        try {
            Thread.sleep(2000);
            Disk disk1 = null;
            Disk disk2 = null;
            if (!fromPeg.getDisks().empty()){
                disk1 = fromPeg.popDisk();
            }
            if (!toPeg.getDisks().empty()){
                disk2 = toPeg.popDisk();
            }
            if(disk1 == null){
                fromPeg.pushDisk(disk2);
            }
            else if(disk2 == null){
                toPeg.pushDisk(disk1);
            }
            else if(disk1.getName() > disk2.getName()){
                fromPeg.pushDisk(disk1);
                fromPeg.pushDisk(disk2);
            }
            else {
                toPeg.pushDisk(disk2);
                toPeg.pushDisk(disk1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * State Machine that manages the current state of the Game
     * Needs currState and currEvent
     * Use setters to update the currState and currEvent
     */
    public void go() {
        switch (currState) {
            case INIT:
                currState = GameState.NOT_RUNNING;
                reset();
                break;
            case NOT_RUNNING:
                switch (currEvent) {
                    case START:
                        currState = GameState.RUNNING;
                        this.currentMove = 1;
                        start();
                        break;
                    case STOP:
                        currState = GameState.NOT_RUNNING;
                        break;
                    case RESET:
                        currState = GameState.NOT_RUNNING;
                        reset();
                        break;
                }
                break;
            case RUNNING:
                switch (currEvent) {
                    case START:
                        currState = GameState.RUNNING;
                        break;
                    case STOP:
                        currState = GameState.PAUSED;
                        stop();
                        break;
                    case RESET:
                        currState = GameState.NOT_RUNNING;
                        reset();
                        break;
                }
                break;
            case PAUSED:
                switch (currEvent) {
                    case START:
                        currState = GameState.RUNNING;
                        resume();
                        break;
                    case RESET:
                        currState = GameState.NOT_RUNNING;
                        reset();
                        break;
                }
                break;
            case COMPLETED:
                switch (currEvent) {
                    case START:
                        currState = GameState.COMPLETED;
                        break;
                    case RESET:
                        currState = GameState.NOT_RUNNING;
                        reset();
                        break;
                    case STOP:
                        currState = GameState.COMPLETED;
                        break;
                }
                break;
        }
    }

    public GameState getCurrState() {
        return currState;
    }

    public void setCurrState(GameState currState) {
        this.currState = currState;
    }

    public void setCurrEvent(GameEvent currEvent) {
        this.currEvent = currEvent;
    }

    public void setNoOfDisks(int noOfDisks) {
        this.noOfDisks = noOfDisks;
        this.noOfMoves = (int) (Math.pow(2,noOfDisks)-1);
    }


    public int getCurrentMove() {
        return currentMove;
    }

    public Map<String, Peg> getPegs() {
        return pegs;
    }

    public int getNoOfMoves() {
        return noOfMoves;
    }

    @Override
    public String toString() {
        return "TOHGame{" +
                "currState=" + currState +
                ", currEvent=" + currEvent +
                ", noOfDisks=" + noOfDisks +
                ", noOfMoves=" + noOfMoves +
                ", currentMove=" + currentMove +
                ", pegs=" + pegs +
                '}';
    }
}
