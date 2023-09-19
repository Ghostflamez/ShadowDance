import java.util.List;

public class NoteFactory {

    public static NoteRepository makeNotes(List<List<String>> refSet, List<List<String>> popOrder) {

        NoteRepository repository = new NoteRepository();

        for (List<String> order : popOrder) {
            String direction = order.get(0);
            String type = order.get(1);
            double xCoord = Double.parseDouble(getCoordForDirection(direction, refSet));
            int initialFrame = Integer.parseInt(order.get(2));

            if (type.equals("Normal")) {
                NoteNormal note = new NoteNormal(direction, xCoord, initialFrame);
                repository.addNote(note);
            } else if (type.equals("Hold")) {
                NoteHold note = new NoteHold(direction, xCoord, initialFrame);
                repository.addNote(note);
            }
        }

        return repository;
    }

    private static String getCoordForDirection(String direction, List<List<String>> refSet) {
        for (List<String> ref : refSet) {
            if (ref.get(0).equals(direction)) {
                return ref.get(1);
            }
        }
        return null;
    }

}
