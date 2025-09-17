package utils;

public class CommonUtils {
    public static String[] pushFromLeft(String[] array, String... newElements) {

        for (String newElement : newElements) {
            for (int i = array.length - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }

            array[0] = newElement;
        }

        return array;
    }
}
