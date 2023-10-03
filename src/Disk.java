import java.awt.*;

public class Disk extends Rectangle {
    private int name;

    public Disk(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "name=" + name +
                '}';
    }
}
