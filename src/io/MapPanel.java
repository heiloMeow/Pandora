package io;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Create a panel which draws the map using images for walls, paths, start and end points,
 * and overlays the solution path with a special image.
 */
class MapPanel extends JPanel {
    private char[][] map;
    private List<Point> path;
    // Load images for walls, paths, start, end, and solution path.
    private BufferedImage wallImage;
    private BufferedImage pathImage;
    private BufferedImage startImage;
    private BufferedImage endImage;
    private BufferedImage solutionImage;

    MapPanel(char[][] map, List<Point> path) {
        this.map = map;
        this.path = path;
        // 加载图片资源
        try {
            wallImage = ImageIO.read(new File("pic/wall.png"));       // 墙体图片
            pathImage = ImageIO.read(new File("pic/path.png"));         // 普通路径图片
            startImage = ImageIO.read(new File("pic/start.png"));       // 起点图片
            endImage = ImageIO.read(new File("pic/end.png"));           // 终点图片
            solutionImage = ImageIO.read(new File("pic/solution.png")); // 解法路径图片
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Map Loading Error！", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 更新迷宫及其求解路径。
     */
    public void setMap(char[][] map, List<Point> path) {
        this.map = map;
        this.path = path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int pixelSize = Math.min(getWidth() / map[0].length, getHeight() / map.length);

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                int x = col * pixelSize;
                int y = row * pixelSize;
                if (map[row][col] == '#') {
                    g.drawImage(wallImage, x, y, pixelSize, pixelSize, this);
                } else {
                    g.drawImage(pathImage, x, y, pixelSize, pixelSize, this);
                }
            }
        }

        for (Point point : path) {
            int x = point.column * pixelSize;
            int y = point.row * pixelSize;
            g.drawImage(solutionImage, x, y, pixelSize, pixelSize, this);
        }


        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                int x = col * pixelSize;
                int y = row * pixelSize;
                if (map[row][col] == 'S') {
                    g.drawImage(startImage, x, y, pixelSize, pixelSize, this);
                } else if (map[row][col] == 'E') {
                    g.drawImage(endImage, x, y, pixelSize, pixelSize, this);
                }
            }
        }
    }

    /**
     *  Click to set a new end point
     */
    public void enableEndpointSelection() {
        JOptionPane.showMessageDialog(this, "Click to pick a new end point", "Choose end point", JOptionPane.INFORMATION_MESSAGE);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pixelSize = Math.min(getWidth() / map[0].length, getHeight() / map.length);
                int col = e.getX() / pixelSize;
                int row = e.getY() / pixelSize;
                if (row >= 0 && row < map.length && col >= 0 && col < map[0].length) {
                    if (map[row][col] == '#' || map[row][col] == 'S') {
                        JOptionPane.showMessageDialog(MapPanel.this, "Start point or walls can't be choosen as end point", "Invalid Choice", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // 清除原有终点
                    for (int r = 0; r < map.length; r++) {
                        for (int c = 0; c < map[0].length; c++) {
                            if (map[r][c] == 'E') {
                                map[r][c] = 'P';
                            }
                        }
                    }
                    map[row][col] = 'E';
                    MapSolver solver = new MapSolver(map);
                    List<Point> newPath = solver.solve();
                    setMap(map, newPath);
                    repaint();
                    MapPanel.this.removeMouseListener(this);
                }
            }
        };
        this.addMouseListener(adapter);
    }

    /**
     *  Click to set a new start point
     */
    public void enableStartSelection() {
        JOptionPane.showMessageDialog(this, "Click to pick a new start point", "Choose start point", JOptionPane.INFORMATION_MESSAGE);
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pixelSize = Math.min(getWidth() / map[0].length, getHeight() / map.length);
                int col = e.getX() / pixelSize;
                int row = e.getY() / pixelSize;
                if (row >= 0 && row < map.length && col >= 0 && col < map[0].length) {
                    if (map[row][col] == '#' || map[row][col] == 'E') {
                        JOptionPane.showMessageDialog(MapPanel.this, "Start point or walls can't be choosen as start point", "Invalid Choice", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Clear Original Start Point
                    for (int r = 0; r < map.length; r++) {
                        for (int c = 0; c < map[0].length; c++) {
                            if (map[r][c] == 'S') {
                                map[r][c] = 'P';
                            }
                        }
                    }
                    map[row][col] = 'S';
                    MapSolver solver = new MapSolver(map);
                    List<Point> newPath = solver.solve();
                    setMap(map, newPath);
                    repaint();
                    MapPanel.this.removeMouseListener(this);
                }
            }
        };
        this.addMouseListener(adapter);
    }
}
