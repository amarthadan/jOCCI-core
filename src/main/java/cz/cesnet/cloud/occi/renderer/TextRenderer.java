package cz.cesnet.cloud.occi.renderer;

public class TextRenderer {

    public static String surroundString(String string) {
        return surroundString(string, "=\"", "\";");
    }

    public static String surroundString(String string, String prefix, String suffix) {
        StringBuilder sb = new StringBuilder(string);
        sb.append(suffix);
        sb.insert(0, prefix);

        return sb.toString();
    }
}
