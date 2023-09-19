public class NoteHold extends NoteNormal {
    private static final double holdLength = 164; // Sum of 82 above and 82 below the center

    public NoteHold(String noteType, double xCoordinate, int initialFrame) {
        super(noteType, xCoordinate, initialFrame);
        initializeYCoordinate() ;
    }

    @Override
    protected void initializeYCoordinate() {
        this.yCoordinate = 24;
    }
    @Override
    public int exit() {
        // Note's center Y-coordinate when it reaches 200 pixels below the refY
        double targetY = getYCoordinate() + 200;
        return initialFrame + (int) ((getRefY() + 200 - yCoordinate + holdLength / 2) / getSpeed());
    }

    private double currentUpperY(int currentFrame) {
        return currentY(currentFrame) - holdLength / 2; // Subtracting 82 (holdLength/2) from the center Y-coordinate
    }

    private double currentLowerY(int currentFrame) {
        return currentY(currentFrame) + holdLength / 2; // Adding 82 (holdLength/2) to the center Y-coordinate
    }

    private double upperDistance(int currentFrame) {
        return Math.abs(currentUpperY(currentFrame) - getRefY());
    }

    private double lowerDistance(int currentFrame) {
        return Math.abs(currentLowerY(currentFrame) - getRefY());
    }

    @Override
    public int grade(int currentFrame) {
        int upperScore = gradeBasedOnDistance(upperDistance(currentFrame));
        int lowerScore = gradeBasedOnDistance(lowerDistance(currentFrame));
        return upperScore + lowerScore;
    }

    private int gradeBasedOnDistance(double distance) {
        if (distance <= 15) return 10; // PERFECT
        else if (distance <= 50) return 5; // GOOD
        else if (distance <= 100) return -1; // BAD
        else if (distance <= 200) return -5; // MISS
        return 0; // In case it doesn't fit any category
    }

    // Disallow calling of distance from outside this class and its subclasses
    @Override
    public double distance(int currentFrame) {
        throw new UnsupportedOperationException("distance method is break into upperDistance and lowerDistance in NoteHold");
    }
}
