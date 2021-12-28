public class IndexOutOfRangeException extends RuntimeException {
    public String message;

    public IndexOutOfRangeException (String message) {
        this.message = message;
    }

    public String toString () {
        return "IndexOutOfRange: '" + message + "'";
    }

    /**
     * Check if index belongs to defined range
     * @param index index
     * @param start start of array
     * @param end end of array
     * @throws IndexOutOfRangeException if index is out of range
     * */
    public static void checkRange(int index, int start, int end) {
        if (index < start || index > end) {
            throw new IndexOutOfRangeException("Index " + index + " out of range <" + start + ", " + end + ">");
        }
    }
}
