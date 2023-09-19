import java.util.List;
import java.util.ArrayList;


public class NoteRepository {

    private List<NoteNormal> repository;
    private List<NoteNormal> normal;
    private List<NoteHold> hold;

    private int[] normalCount;
    private int[] holdCount;

    private boolean valid;

    public NoteRepository() {
        repository = new ArrayList<>();
        normal = new ArrayList<>();
        hold = new ArrayList<>();

        normalCount = new int[4];
        holdCount = new int[4];

        valid = true;
    }

    public void addNote(NoteNormal note) {
        repository.add(note);
        if (note instanceof NoteHold) {
            hold.add((NoteHold) note);
            updateNormalCount(note.getNoteType());
        } else if (note instanceof NoteNormal) {
            normal.add((NoteNormal) note);
            updateHoldCount(note.getNoteType());
        }
        checkValidity();
    }

    private void updateNormalCount(String type) {
        if (type.equals("left")) {
            normalCount[0]++;
        } else if (type.equals("right")) {
            normalCount[1]++;
        } else if (type.equals("up")) {
            normalCount[2]++;
        } else if (type.equals("down")) {
            normalCount[3]++;
        }
    }

    private void updateHoldCount(String type) {
        if (type.equals("left")) {
            holdCount[0]++;
        } else if (type.equals("right")) {
            holdCount[1]++;
        } else if (type.equals("up")) {
            holdCount[2]++;
        } else if (type.equals("down")) {
            holdCount[3]++;
        }
    }

    private void checkValidity() {
        for (int count : normalCount) {
            if (count >= 100) {
                valid = false;
                return;
            }
        }

        for (int count : holdCount) {
            if (count >= 20) {
                valid = false;
                return;
            }
        }
    }


    public List<NoteNormal> getRepository() {
        return repository;
    }
    public List<NoteNormal> getNormal() {
        return normal;
    }

    public List<NoteHold> getHold() {
        return hold;
    }

    public boolean isValid() {
        return valid;
    }


}
