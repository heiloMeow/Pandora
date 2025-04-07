package io;

import javax.swing.*;

/**
 * Represent a window which informs the users that the maze they choose is not solvable.
 * <p>
 *     The users can close the window and nothing would happen to the application itself
 * </p>
 */
public class UnSolvable {
    /**
     * Create an Alert window when the maze is unsolvable.
     */
    public static void unSolvableAlert(){
        JFrame unsolvable = new JFrame("Unsolvable Route");
        unsolvable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        unsolvable.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Sorry, the we can't reach the place you want to go.");
        label.setSize(30,100);
        panel.add(label);
        unsolvable.add(panel);
        unsolvable.pack();
        unsolvable.setVisible(true);
    }
}
