public class NoteNormal {
    // fields
    private String noteType;
    private double xCoordinate;
    protected double yCoordinate;
    protected int initialFrame;
    // constants
    private static final double speed = 2; // pixels per frame
    private static final double refY = 657; // y-coordinate of ref note
    private static final int windowHeight = 768;

    // methods
    // Constructor
    public NoteNormal(String noteType, double xCoordinate, int initialFrame) {
        this.noteType = noteType;
        this.xCoordinate = xCoordinate;
        initializeYCoordinate();  // initialize the local constants
        this.initialFrame = initialFrame;
    }

    protected void initializeYCoordinate() {
        this.yCoordinate = 100; // initialize the y-coordinate of the NoteNormal as 100
    }

    public double currentY(int currentFrame) {
        return yCoordinate + (currentFrame - initialFrame) * speed;
    }

    public double distance(int currentFrame) {
        return currentY(currentFrame) - refY;
    }
    // frame when the note exits
    public int exit() {
        return initialFrame + (int) ((refY + 200 - yCoordinate) / speed);
    }
    // grade the player's performance
    public int grade(int currentFrame) {
        double dist = Math.abs(distance(currentFrame));
        if (dist <= 15) return 10; // PERFECT
        else if (dist <= 50) return 5; // GOOD
        else if (dist <= 100) return -1; // BAD
        else if (dist <= 200 || currentFrame > exit()) return -5; // MISS
        return 0; // In case it doesn't fit any category
    }

    // Getters
    public String getNoteType() {
        return noteType;
    }
    public double getXCoordinate() {
        return xCoordinate;
    }
    public double getYCoordinate() {
        return yCoordinate;
    }
    public double getRefY() {
        return refY;
    }
    public double getSpeed() {
        return speed;
    }
    public int getGrade(int currentFrame) {
        return grade(currentFrame);
    }

    protected static int getWindowHeight() {
        return windowHeight;
    }

    // setters
    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setInitialFrame(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }


}