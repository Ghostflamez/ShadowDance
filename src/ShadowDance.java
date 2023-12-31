import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import bagel.AbstractGame;
import java.util.List;
import java.util.Arrays;
/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2023
 * Please enter your name below
 * @Author (hongjianz1)
 */
public class ShadowDance extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";


    // game status flags
    private boolean showInitialInterface = true; // flags to indicate the current interface
    private boolean isPaused = false; //flag to track the pause status
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean upReleased = false;
    boolean downReleased = false;
    boolean leftReleased = false;
    boolean rightReleased = false;

    /**
     * Constants for the notes
     * The following constant declarations cannot be set as static
     * If they are declared as static, these resources may be shared among all instances of the application
     */

    //load main interface
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    //set messages font
    private final Font TITLE_FONT = new Font("res/FSO8BITR.ttf", 60);
    private final Font INSTRUCTION_FONT = new Font("res/FSO8BITR.ttf", 24);
    private final Font SCORE_FONT = new Font("res/FSO8BITR.ttf", 35);
    private final Font ENDING_FONT = new Font("res/FSO8BITR.ttf", 60);


    //set messages content
    private final String TITLE = "SHADOW DANCE";
    private final String INSTRUCTION1 = "PRESS SPACE TO START";
    private final String INSTRUCTION2 = "USE ARROW KEYS TO PLAY";

    //set messages options
    private final DrawOptions TITLE_OPTIONS = new DrawOptions().setBlendColour(1.0, 1.0, 1.0);
    private final DrawOptions INSTRUCTION_OPTIONS = new DrawOptions().setBlendColour(1.0, 1.0, 1.0);
    private final DrawOptions SCORE_OPTIONS = new DrawOptions().setBlendColour(1.0, 1.0, 1.0);
    private final DrawOptions ENDING_OPTIONS = new DrawOptions().setBlendColour(1.0, 1.0, 1.0);


    // Variables for the game state
    public int currentFrame = 0; // The current frame number
    public int score = 0; // The player's current score
    // Create NoteRepository instance
    NoteRepository notions = new NoteRepository();

    public int maxExitFrame = 0;

    // constants for the game
    private final double Y_POSITION_REFS = 384.0; // y-coordinate of the reference Notes

    // preloaded data from csv
    private List<List<String>> refSet; // store the reference notes data
    private List<List<String>> popOrder; // store the pop order of notes


    List<NoteNormal> currentNormal = new ArrayList<>();
    List<NoteHold> currentHold = new ArrayList<>();

    //construct a method for easy use
    private boolean isLaneType(String value) { // check if the value is a lane type
        return "Left".equals(value) || "Right".equals(value) || "Up".equals(value) || "Down".equals(value);
    }

    /**
     * Constructor for the game
     */
    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);

        // Read data
        readCSV();

    }
    /**
     * Method used to read file
     * initialize the reference image
     * store pop order of notes in an array
     */
    private void readCSV() {
        refSet = new ArrayList<List<String>>(); // An interface (List) cannot be instantiated directly.
        popOrder = new ArrayList<List<String>>();
        String path = "G:\\SWEN_project1\\hongjianz1-project-1\\res\\test1.csv"; // find the path of the file
        int lineCount = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            // Step 1: Preliminary check
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 3) {
                    throw new IOException("Invalid number of columns on line " + lineCount);
                }
                lineCount++;
            }

            if (lineCount < 4) {
                throw new IOException("Insufficient number of lines in the CSV. Expected at least 4 but got " + lineCount);
            }

            // Resetting the BufferedReader to read the file again
            br.close();
            br = new BufferedReader(new FileReader(path));
            lineCount = 0;

            // Step 2: Process the data
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] data = line.split(",");

                if ("Lane".equals(data[0])) {
                    String refType = data[1];
                    double refxCoordinate = Double.parseDouble(data[2].trim());
                    refSet.add(Arrays.asList(refType, String.valueOf(refxCoordinate), "lane" + refType + ".png"));
                } else if (isLaneType(data[0])) {
                    popOrder.add(Arrays.asList(data));
                } else {
                    throw new IOException("Unexpected value in the first column on line " + lineCount);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notions = NoteFactory.makeNotes(refSet, popOrder);

        for (NoteNormal note : notions.getRepository()) {
            int exitFrame = note.exit();
            if (exitFrame > maxExitFrame) {
                maxExitFrame = exitFrame;
            }
        }
    }

    /**
     * this is how to play two phases of the interface
     */

    //draw initial interface
    private void drawInitialInterface(){
        // draw title
        TITLE_FONT.drawString(TITLE, 220.0, 250.0, TITLE_OPTIONS);

        // draw instructions
        INSTRUCTION_FONT.drawString(INSTRUCTION1, 320.0, 440.0, INSTRUCTION_OPTIONS);
        INSTRUCTION_FONT.drawString(INSTRUCTION2, 320.0, 490.0, INSTRUCTION_OPTIONS);
    }

    //draw playing interface
    private void drawPlayingInterface() {
        currentFrame++;

        // Drawing the score
        SCORE_FONT.drawString("SCORE  " + score, 20.0, 50.0, SCORE_OPTIONS);
        // Drawing images based on refSet data
        for (List<String> data : refSet) {
            double xPosition = Double.parseDouble(data.get(1)); // Convert the string to a double
            String imageName = data.get(2); // Get the image name
            Image img = new Image("res/" + imageName); // load the image
            img.draw(xPosition, Y_POSITION_REFS);
        }
        // drawing normal notes
        List<NoteNormal> normalNotes = new ArrayList<>();

        normalNotes = notions.getNormal();

        for (NoteNormal note : normalNotes) {
                if (note.exit() > currentFrame && note.initialFrame < currentFrame) {
                    Image img = new Image("res/note" + note.getNoteType() + ".png");

                    double yCoordinate = note.currentY(currentFrame);
                    double xCoordinate = note.getXCoordinate();

                    img.draw(xCoordinate, yCoordinate);
                    currentNormal.add(note);
                }
            }

        // drawing hold notes
        List<NoteHold> holdNotes = new ArrayList<>();

        holdNotes = notions.getHold();
        for (NoteHold note : holdNotes) {
            if (note.exit() > currentFrame && note.initialFrame < currentFrame) {

                Image img = new Image("res/holdNote" + Character.toUpperCase(note.getNoteType().charAt(0)) + note.getNoteType().substring(1) + ".png");

                double yCoordinate = note.currentY(currentFrame);
                double xCoordinate = note.getXCoordinate();

                img.draw(xCoordinate, yCoordinate);
                currentHold.add(note);
            }
        }
    }

    private void drawEndingInterface(){
        String ENDING = "";
        // draw title
        if (score >= 150 ){
            ENDING = "CLEAR!" + "\n" + " SCORE " + score;
        }   else {
            ENDING = "TRY AGAIN!" + "\n" + " SCORE " + score;
        }

        ENDING_FONT.drawString(ENDING, 300.0, 350.0, ENDING_OPTIONS);

    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        //keyboard input
        //press tab to pause
        if (input.wasPressed(Keys.TAB)) {
            isPaused = !isPaused; // switch the pause status
        }
        if (isPaused) {
            //don't know how to pause the game
            return;
        }

        if (input.wasPressed(Keys.SPACE)) {
            showInitialInterface = false; //press"space" to switch the interface
        }

        if (showInitialInterface) {
            drawInitialInterface();
        }
        else {
            if (maxExitFrame > currentFrame) {
                drawPlayingInterface();

                //detect keyboard press
                if (input.wasPressed(Keys.LEFT)) {
                    leftPressed = true;
                }
                if (input.wasPressed(Keys.RIGHT)) {
                    rightPressed = true;
                }
                if (input.wasPressed(Keys.UP)) {
                    // 设置按下标志
                    upPressed = true;
                }
                if (input.wasPressed(Keys.DOWN)) {
                    downPressed = true;
                }

                //detect keyboard release
                if (input.wasReleased(Keys.LEFT)) {
                    upReleased = true;
                }
                if (input.wasReleased(Keys.RIGHT)) {
                    rightReleased = true;
                }
                if (input.wasReleased(Keys.UP)) {
                    upReleased = true;
                }
                if (input.wasReleased(Keys.DOWN)) {
                    downReleased = true;
                }
                // score calculation
                /**
                 * The following code is used to calculate the score after press action
                 */
                // up
                if (upPressed && !upReleased) {
                    int normalUpScore = 0;
                    for (NoteNormal note : currentNormal) {
                        if (note.getNoteType().equals("Up")) {
                            normalUpScore = note.getGrade(currentFrame);
                        }
                    }

                    int holdUpScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Up")) {
                            holdUpScore = note.lowerGrade(currentFrame);
                        }
                    }

                    int totalUpScore = normalUpScore + holdUpScore;
                    score += totalUpScore;
                    upPressed = false;
                }
                if (upReleased) {
                    upReleased = false;
                }
                //down
                if (downPressed && !downReleased) {
                    int normalDownScore = 0;
                    for (NoteNormal note : currentNormal) {
                        if (note.getNoteType().equals("Down")) {
                            normalDownScore = note.getGrade(currentFrame);
                        }
                    }

                    int holdDownScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Down")) {
                            holdDownScore = note.lowerGrade(currentFrame);
                        }
                    }

                    int totalDownScore = normalDownScore + holdDownScore;
                    score += totalDownScore;
                    downPressed = false;
                }
                if (downReleased) {
                    downReleased = false;
                }
                //left
                if (leftPressed && !leftReleased) {
                    int normalLeftScore = 0;
                    for (NoteNormal note : currentNormal) {
                        if (note.getNoteType().equals("Left")) {
                            normalLeftScore = note.getGrade(currentFrame);
                        }
                    }

                    int holdLeftScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Left")) {
                            holdLeftScore = note.lowerGrade(currentFrame);
                        }
                    }

                    int totalLeftScore = normalLeftScore + holdLeftScore;
                    score += totalLeftScore;
                    leftPressed = false;
                }
                if (leftReleased) {
                    leftReleased = false;
                }
                //right
                if (rightPressed && !rightReleased) {
                    int normalRightScore = 0;
                    for (NoteNormal note : currentNormal) {
                        if (note.getNoteType().equals("Right")) {
                            normalRightScore = note.getGrade(currentFrame);
                        }
                    }

                    int holdRightScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Right")) {
                            holdRightScore = note.lowerGrade(currentFrame);
                        }
                    }

                    int totalRightScore = normalRightScore + holdRightScore;
                    score += totalRightScore;
                    rightPressed = false;
                }
                if (rightReleased) {
                    rightReleased = false;
                }
                /**
                 * The following code is used to calculate the score after release action
                 */
                // up
                if (upReleased) {
                    int holdUpScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Up")) {
                            holdUpScore += note.upperGrade(currentFrame);
                        }
                    }
                    score += holdUpScore;
                    upReleased = false;
                }
                //down
                if (downReleased) {
                    int holdDownScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Down")) {
                            holdDownScore += note.upperGrade(currentFrame);
                        }
                    }
                    score += holdDownScore;
                    downReleased = false;
                }
                //left
                if (leftReleased) {
                    int holdLeftScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Left")) {
                            holdLeftScore += note.upperGrade(currentFrame);
                        }
                    }
                    score += holdLeftScore;
                    leftReleased = false;
                }
                //right
                if (rightReleased) {
                    int holdRightScore = 0;
                    for (NoteHold note : currentHold) {
                        if (note.getNoteType().equals("Right")) {
                            holdRightScore += note.upperGrade(currentFrame);
                        }
                    }
                    score += holdRightScore;
                    rightReleased = false;
                }

            } else {
                drawEndingInterface();
            }
        }
    }
}
