package io;

import io.exceptions.MapMalformedException;
import io.exceptions.MapSizeMissmatchException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * FileLoader is a class implements interface FileInterface
 * This class provides a way to open the path of a map file and returns a 2D character array map.
 * @ensure The map file follows the given specifications.
 * <p>
 *  <li>Begins with the dimensions of the map (width and height).</li>
 *  <li>Followed by the map data, with and only with characters representing walls, paths, start, and end positions.</li>
 * </p>
 */
public class FileLoader implements FileInterface {
    /**
     * Transform a text map file into a 2D character map array.
     *
     * @param filename The path to the map file to be loaded.
     * @return A 2D character array representing the loaded map file.
     * @require a string which is the file path to the map file.
     * @ensure the file contains a valid map.
     * @throws MapMalformedException       If the map file is empty or in a wrong format or there is an uneven map length.
     * @throws MapSizeMissmatchException   If the size of the map doesn't match the given dimensions.
     * @throws IllegalArgumentException     If map file contain illegal characters, or the map file cannot be opened.
     * @throws FileNotFoundException        If the file is not found.
     */
    @Override
    public char[][] load(String filename) throws MapMalformedException, MapSizeMissmatchException, IllegalArgumentException, FileNotFoundException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String dimensionsLine = reader.readLine();
            if (dimensionsLine == null) {
                throw new MapMalformedException("An empty map file.");
            }

            String[] dimensions = dimensionsLine.split(" ");
            if (dimensions.length != 2) {
                throw new MapMalformedException("Map is in a wrong format, please choose a standard map file.");
            }

            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);

            if (height < 0 || width < 0) {
                throw new IllegalArgumentException("Map dimensions contain negative number.");
            }
            /**if (height % 2 == 0 || width % 2 == 0){
                throw new MapMalformedException("Map dimensions must be odd.");
            }**/
            List<String> mapLines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                mapLines.add(line);
            }

            if (mapLines.size() != height) {
                throw new MapSizeMissmatchException("Map height doesn't match the given dimension.");
            }

            char[][] map = new char[height][width];
            for (int rowNum = 0; rowNum < height; rowNum++) {
                if (mapLines.get(rowNum).length() != width) {
                    throw new MapSizeMissmatchException("Map width doesn't match the given dimension.");
                }
                map[rowNum] = mapLines.get(rowNum).toCharArray();
                for (char elements : map[rowNum]) {
                    if (elements != '#' && elements != 'P' && elements != ' '&& elements != '.' && elements != 'S' && elements != 'E') {
                        throw new IllegalArgumentException("Invalid character found in the map." );
                    }
                }
            }
            return map;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format found in map dimensions.", e);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File cannot be found.");
        } catch (IOException e) {
            throw new IllegalArgumentException("Error when reading the map file.", e);
        }
    }
}