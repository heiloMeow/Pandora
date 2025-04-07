package io;
import java.util.*;
import java.util.List;

/**
 * Create a MapSolver to find the shortest solution for the map.
 * It will return a Point List which represents the list of points in the solution.
 * <li>
 *     map : A 2D character array map
 * </li>
 * <li>
 *     pointsVisited : A 2D boolean array for the search history of the solver.
 * </li>
 */
public class MapSolver {
    public char[][] map;
    private boolean[][] pointsVisited;

    /**
     * Construct a MapSolver with the map to be solved.
     * @param map A 2D character array map.
     */
    public MapSolver(char[][] map) {
        this.map = map;
    }

    /**
     * Solve the map, find the shortest way from the start point to the end.
     * <p>BFS is used in the method (An algorithm to calculate the shortest way to solve the map.
     * reference:"<a href="https://blog.csdn.net/g11d111/article/details/76169861">...</a>"
     * </p>
     *
     * @return A List of Points which contains all the points in the shortest solution for the map.
     */
    public List<Point> solve() {
        int rowSize = map.length;
        int colSize = map[0].length;
        Point start = null, end = null;

        mapSolving:
        for (int rowNumber = 0; rowNumber < rowSize; rowNumber++) {
            for (int colNumber = 0; colNumber < colSize; colNumber++) {
                if (map[rowNumber][colNumber] == 'S') start = new Point(rowNumber, colNumber, null);
                if (map[rowNumber][colNumber] == 'E') end = new Point(rowNumber, colNumber, null);
                if (start != null && end != null) break mapSolving;
            }
        }

        if (start == null || end == null) {
            return Collections.emptyList();
        }

        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rowSize][colSize];
        queue.add(start);
        visited[start.row][start.column] = true;

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            if (point.row == end.row && point.column == end.column) {
                this.pointsVisited = visited;
                return constructPath(point);
            }

            for (int[] dir : dirs) {
                int newRow = point.row + dir[0];
                int newCol = point.column + dir[1];

                if (newRow >= 0 && newRow < rowSize && newCol >= 0 && newCol < colSize &&
                    !visited[newRow][newCol] && map[newRow][newCol] != '#') {
                    queue.add(new Point(newRow, newCol, point));
                    visited[newRow][newCol] = true;
                }
            }
        }
        this.pointsVisited = visited;
        return Collections.emptyList();
    }

    /**
     * Track the history points the map solver has searched.
     *
     * @return a List of points that has been visited by the map solver.
     */
    public List<Point> visitedPath() {
        List<Point> visited = new ArrayList<>();
        if (pointsVisited == null) return Collections.emptyList();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (pointsVisited[i][j]) {
                    visited.add(new Point(i, j, null));
                }
            }
        }
        return visited;
    }

    /**
     * Give a list of points to show the solution to the map.
     *
     * @param end The end point in the map.
     * @return A List of points which shows how the start point came to the end.
     */
    private static List<Point> constructPath(Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;
        while (current != null) {
            path.add(current);
            current = current.lastPoint;
        }
        Collections.reverse(path);
        return path;
    }
}
