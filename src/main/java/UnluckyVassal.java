import java.util.*;

public class UnluckyVassal {
    public static final Inhabitant KING = new Inhabitant("король");

    public void printReportForKing(List<String> pollResults) {
        Map<String, Inhabitant> allInhabitants = new HashMap<>();
        Set<Inhabitant> kingSubordinates = new HashSet<>();
        try {
            for (String s : pollResults) {
                String[] temp1 = s.split(": ");
                Inhabitant owner = getInhabitant(temp1[0], allInhabitants);
                if (owner.getOwner() == null) {
                    owner.setOwner(KING);
                    kingSubordinates.add(owner);
                }
                if (temp1.length > 1) {
                    String[] temp2 = temp1[1].split(", ");
                    for (String subName : temp2) {
                        Inhabitant sub = getInhabitant(subName, allInhabitants);
                        owner.addSubordinate(sub);
                        sub.setOwner(owner);
                        kingSubordinates.remove(sub);
                    }
                }
            }
            for (Inhabitant sub : kingSubordinates)
                KING.addSubordinate(sub);
            System.out.println(KING.toReportString(0).toString());
        } catch (OwningLoopException e) {
            System.out.println(e.getMessage());
        }
    }

    private Inhabitant getInhabitant(String name, Map<String, Inhabitant> inhabitantMap) {
        if (inhabitantMap.containsKey(name))
            return inhabitantMap.get(name);
        else {
            Inhabitant newInhabitant = new Inhabitant(name);
            inhabitantMap.put(name, newInhabitant);
            return newInhabitant;
        }
    }


}
