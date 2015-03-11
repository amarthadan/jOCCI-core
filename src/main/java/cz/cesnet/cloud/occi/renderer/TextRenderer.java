package cz.cesnet.cloud.occi.renderer;

/**
 * Helper class for rendering specific methods.
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class TextRenderer {

    /**
     * Surrounds given string with prefix '="' and suffix '";'.
     *
     * @param string string to apply prefix and suffix to
     * @return modified string
     */
    public static String surroundString(String string) {
        return surroundString(string, "=\"", "\";");
    }

    /**
     * Surround given string with given prefix and suffix.
     *
     * @param string string to apply prefix and suffix to
     * @param prefix prefix to apply
     * @param suffix suffix to apply
     * @return modified string
     */
    public static String surroundString(String string, String prefix, String suffix) {
        StringBuilder sb = new StringBuilder(string);
        sb.append(suffix);
        sb.insert(0, prefix);

        return sb.toString();
    }
}
