package cz.cesnet.cloud.occi.renderer;

/**
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class TextRenderer {

    /**
     *
     * @param string
     * @return
     */
    public static String surroundString(String string) {
        return surroundString(string, "=\"", "\";");
    }

    /**
     *
     * @param string
     * @param prefix
     * @param suffix
     * @return
     */
    public static String surroundString(String string, String prefix, String suffix) {
        StringBuilder sb = new StringBuilder(string);
        sb.append(suffix);
        sb.insert(0, prefix);

        return sb.toString();
    }
}
