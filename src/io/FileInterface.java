package io;

import io.exceptions.MapMalformedException;
import io.exceptions.MapSizeMissmatchException;

import java.io.FileNotFoundException;

/**
 * Represents an interface for map file operations.
 * This interface provides a contract for loading maps from files and converting them into a 2D character array.
 * The loaded maps should comply with the given specifications.
 * <p>
 * A typical map file:
 * - Begins with the dimensions of the map (width and height).
 * - Followed by the map data, with characters representing walls, paths, start, and end positions.
 * </p>
 * <p>
 * Note: Implementing classes should handle various validation scenarios and throw the appropriate exceptions as detailed in the {@link #load(String)} method.
 * </p>
 */
public interface FileInterface {

    /**
     * Loads a map from the specified filename and converts it into a 2D character array.
     * <p>
     * This method attempts to read a map file with the following expectations:
     * - The first line should contain the map's dimensions, separated by a space (e.g., "10 15").
     * - Subsequent lines should provide the map data with specific characters representing the map elements.
     * </p>
     * <p>
     * Exception Handling:
     * - Throws {@link MapMalformedException} if the map data doesn't match the given format.
     * - Throws {@link MapSizeMissmatchException} if the map data doesn't match the specified dimensions.
     * - Throws {@link IllegalArgumentException} for other general validation errors, such as invalid characters.
     * - Throws {@link FileNotFoundException} if the specified map file is not found.
     * </p>
     *
     * @param filename The path to the map file to be loaded.
     * @return A 2D character array representing the loaded map.
     * @throws MapMalformedException      If the map data is not correctly formatted.
     * @throws MapSizeMissmatchException  If the map dimensions do not match the provided size.
     * @throws IllegalArgumentException     For other validation errors.
     * @throws FileNotFoundException        If the map file is not found.
     */
    public char[][] load(String filename) throws MapMalformedException, MapSizeMissmatchException, IllegalArgumentException, FileNotFoundException;

}
