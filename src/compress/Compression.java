package compress;

/**
 * The interface Compression.
 */
public interface Compression {
    /**
     * Gets content of the input file
     */
    void getContent();

    /**
     * Compress the input file
     */
    void compress();
}
