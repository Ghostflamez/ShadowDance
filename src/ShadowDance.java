import bagel.*;

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

    //draw initial interface
    private void drawInitialInterface(){
        // draw title
        TITLE_FONT.drawString(TITLE, 220.0, 250.0, TITLE_OPTIONS);

        // draw instructions
        INSTRUCTION_FONT.drawString(INSTRUCTION1, 320.0, 440.0, INSTRUCTION_OPTIONS);
        INSTRUCTION_FONT.drawString(INSTRUCTION2, 320.0, 490.0, INSTRUCTION_OPTIONS);
    }

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV() {

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
