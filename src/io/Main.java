package io;

import io.exceptions.MapMalformedException;
import io.exceptions.MapSizeMissmatchException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    /**
     * The launcher for the Map Solving application.
     * This version uses Swing's JFileChooser with a filter for .txt files for initial map file selection.
     * @param args the command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mapFrame = new JFrame("Pandora System");
            mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mapFrame.setSize(600, 400);
            mapFrame.setLocationRelativeTo(null);
            mapFrame.setLayout(new BorderLayout());
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Initial Map Selection");
            fileChooser.setCurrentDirectory(new File("./maps"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(mapFrame);
            char[][] map = null;
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(mapFrame, "Please choose txt files in a standard format.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                try {
                    FileLoader mapLoader = new FileLoader();
                    map = mapLoader.load(selectedFile.getAbsolutePath());
                } catch (FileNotFoundException | MapMalformedException | MapSizeMissmatchException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(mapFrame, "Error loading map: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            } else {
                System.exit(0);
            }

            MapSolver mapSolver = new MapSolver(map);
            List<Point> path = mapSolver.solve();

            MapPanel solution = new MapPanel(map, path);
            JPanel fileArea = ChoosePanel.getjPanel(solution);
            mapFrame.add(fileArea, BorderLayout.NORTH);
            mapFrame.add(solution, BorderLayout.CENTER);

            mapFrame.setVisible(true);

            if (path.isEmpty()){
                UnSolvable.unSolvableAlert();
            }
        });
    }
}
