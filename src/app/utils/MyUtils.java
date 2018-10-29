package app.utils;

public class MyUtils {

    public static String repeatString(String string, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(string);
        }
        return sb.toString();
    }
}
