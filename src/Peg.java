import java.awt.*;
import java.util.Stack;

public class Peg extends Rectangle {
    private String name;
    private Stack<Disk> disks;

    public Peg(String name) {
        this.name = name;
        this.disks = new Stack<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stack<Disk> getDisks() {
        return disks;
    }

    public void setDisks(Stack<Disk> disks) {
        this.disks = disks;
    }

    public void pushDisk(Disk disk){
        this.disks.push(disk);
    }

    public Disk popDisk(){
        return this.disks.pop();
    }

    @Override
    public String toString() {
        return "Peg{" +
                "name='" + name +
                ", disks=" + disks +
                '}';
    }
}
