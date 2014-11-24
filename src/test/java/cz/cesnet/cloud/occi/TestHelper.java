package cz.cesnet.cloud.occi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestHelper {

    public static String readFile(String filename) throws IOException {
        File f = new File(filename);
        String fileContent = new String(Files.readAllBytes(f.toPath()));
        return fileContent;
    }
}
