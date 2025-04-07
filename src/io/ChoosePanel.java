package io;

import io.exceptions.MapMalformedException;
import io.exceptions.MapSizeMissmatchException;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Construct a panel that allows users to select a new map file using Swing's JFileChooser,
 * with a filter that only allows .txt files, or set a new start/end point manually.
 */
public class ChoosePanel {

    public static JPanel getjPanel(MapPanel solution) {
        // BoxLayout used to put all the elements in the center
        JPanel fileArea = new JPanel();
        fileArea.setLayout(new BoxLayout(fileArea, BoxLayout.Y_AXIS));
        
        JLabel message = new JLabel("Choose a map or Set Start/End Point:");
        message.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        fileArea.add(message);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton selectButton = new JButton("Choose a Map");
        selectButton.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./maps"));
            fileChooser.setDialogTitle("Please select another standard map file");
            // Set a filter for txt files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(fileArea);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(fileArea, "Please choose txt files in a standard format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String userFile = selectedFile.getAbsolutePath();
                FileLoader fileLoader = new FileLoader();
                try {
                    char[][] userMap = fileLoader.load(userFile);
                    MapSolver mapSolver = new MapSolver(userMap);
                    List<Point> userPath = mapSolver.solve();
                    solution.setMap(userMap, userPath);
                    solution.repaint();
                    if (userPath.isEmpty()){
                        UnSolvable.unSolvableAlert();
                    }
                } catch (FileNotFoundException | IllegalArgumentException | MapMalformedException |
                         MapSizeMissmatchException ex) {
                    JOptionPane.showMessageDialog(fileArea, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton setEndpointButton = new JButton("Set Endpoint");
        setEndpointButton.addActionListener(e -> solution.enableEndpointSelection());
        
        JButton setStartButton = new JButton("Set Start");
        setStartButton.addActionListener(e -> solution.enableStartSelection());
        
        buttonPanel.add(selectButton);
        buttonPanel.add(setEndpointButton);
        buttonPanel.add(setStartButton);
        
        fileArea.add(buttonPanel);
        
        return fileArea;
    }
}
