import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Inhabitant implements Comparable<Inhabitant> {

    private static final String SPACE = "     ";

    private String name;
    private Inhabitant owner;
    private SortedSet<Inhabitant> subordinates;

    public Inhabitant(String name) {
        this.name = name;
        this.subordinates = new TreeSet<>();
    }

    public void addSubordinate(Inhabitant sub){
        subordinates.add(sub);
    }

    public void setOwner(Inhabitant owner) throws OwningLoopException {
        this.owner = owner;
        checkLoopException();
    }

    private void checkLoopException() throws OwningLoopException {
        Inhabitant temp = this;
        while (true) {
            temp = temp.owner;
            if (temp == null)
                return;
            if (temp.equals(this))
                throw new OwningLoopException("Ошибка: Циклическое подчинение");
        }
    }

    public String getName() {
        return name;
    }

    public Inhabitant getOwner() {
        return owner;
    }

    public StringBuilder toReportString(int level) {
        StringBuilder res = new StringBuilder(SPACE.repeat(level)).append(name).append("\r\n");
        for (Inhabitant sub: subordinates)
            res.append(sub.toReportString(level+1));
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inhabitant inhabitant = (Inhabitant) o;
        return name.equals(inhabitant.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Inhabitant o) {
        return this.name.compareTo(o.name);
    }

}
