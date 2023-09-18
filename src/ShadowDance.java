import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2023
 * Please enter your name below
 * @Author (hongjianz1)
 */
public class ShadowDance extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";


    /**
     * This set of constants are used as flags to indicate the status of the game
     */
    private boolean showInitialInterface = true; // flags to indicate the current scene

    private boolean isPaused = false; //Add a flag to track the pause status

    /**
     * The following constant declarations cannot be set as static
     * If they are declared as static, these resources may be shared among all instances of the application
     */
    //load main interface
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    //set messages font
    private final Font TITLE_FONT = new Font("res/FSO8BITR.ttf", 60); // assume 48 is the font size temporarily
    private final Font INSTRUCTION_FONT = new Font("res/FSO8BITR.ttf", 24); // assume 24 is the font size temporarily

    //set messages
    private final String TITLE = "SHADOW DANCE";
    private final String INSTRUCTION1 = "PRESS SPACE TO START";
    private final String INSTRUCTION2 = "USE ARROW KEYS TO PLAY";

    //set messages options
    private final DrawOptions TITLE_OPTIONS = new DrawOptions().setBlendColour(1.0, 1.0, 1.0);
    private final DrawOptions INSTRUCTION_OPTIONS = new DrawOptions().setBlendColour(1.0, 1.0, 1.0);
    private final Font SCORE_FONT = new Font("res/FSO8BITR.ttf", 35); // Assuming the font is located in the 'res' folder
    private final double Y_POSITION = 384.0;

    // Variables for the game state
    private int score = 0; // The player's score

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV();
    }

    /**
     * Method used to read file
     * initialize the reference image
     * store pop order of notes in an array
     */
    private ArrayList<String[]> refSet;
    private ArrayList<String[]> popOrder;
    private boolean isLaneType(String value) { // check if the value is a lane type
        return "Left".equals(value) || "Right".equals(value) || "Up".equals(value) || "Down".equals(value);
    }
    private void readCSV() {
        refSet = new ArrayList<>();
        popOrder = new ArrayList<>();
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
                    refSet.add(new String[]{refType, String.valueOf(refxCoordinate), "lane" + refType + ".png"});
                } else if (isLaneType(data[0])) {
                    popOrder.add(data);
                } else {
                    throw new IOException("Unexpected value in the first column on line " + lineCount);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //draw initial interface
    private void drawInitialInterface(){
        // draw title
        TITLE_FONT.drawString(TITLE, 220.0, 250.0, TITLE_OPTIONS);

        // draw instructions
        INSTRUCTION_FONT.drawString(INSTRUCTION1, 320.0, 440.0, INSTRUCTION_OPTIONS);
        INSTRUCTION_FONT.drawString(INSTRUCTION2, 320.0, 490.0, INSTRUCTION_OPTIONS);
    }

    private void drawPlayingInterface() {
        // Drawing the score
        SCORE_FONT.drawString("SCORE  " + score, 20, 50); // 10, 10 为左上角坐标

        // Drawing images based on refSet data
        for (String[] data : refSet) {
            double xPosition = Double.parseDouble(data[1]); // Convert the string to a double
            String imageName = data[2]; // Get the image name
            Image img = new Image("res/" + imageName); // load the image
            img.draw(xPosition, Y_POSITION);
        }
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
            //  draw pause message
            return;
        }

        if (input.wasPressed(Keys.SPACE)) {
            showInitialInterface = false; //press"space" to switch the interface
        }
        if (showInitialInterface) {
            drawInitialInterface();
        }
        else {
            drawPlayingInterface();
            if (input.isDown(Keys.LEFT)) {
                // Add logical judgment statement
            }
            if (input.isDown(Keys.RIGHT)) {
            // Add logical judgment statement
            }
            if (input.isDown(Keys.DOWN)) {
            // Add logical judgment statement
            }
            if (input.wasPressed(Keys.ESCAPE)) {
            // Add logical judgment statement
            }
        }
    }
}
