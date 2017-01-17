package util;

import java.io.File;

/**
 * Created by alaguitard on 17/01/17.
 */
public class ImageUtility {

    public static String getResource(String filename)
    {
        //System.err.println(filename);
        return (new File(ImageUtility.class.getClassLoader().getResource(filename).getPath())).getPath();
    }
}
