package util;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by alaguitard on 17/01/17.
 */
public class ImageUtility {

    public static String getResource(String filename)
    {
        return (new File(ImageUtility.class.getClassLoader().getResource(filename).getPath())).getPath();
    }
}
