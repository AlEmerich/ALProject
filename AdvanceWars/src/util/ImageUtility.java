package util;

import main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alaguitard on 17/01/17.
 */
public class ImageUtility {

    public Image getResource(String filename)
    {
        filename = "/"+filename;

        try
        {
            InputStream stream = Main.class.getResourceAsStream(filename);
            try {
                Image i = ImageIO.read(stream);
                return i;
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        /*
        String result = null;
        result = new File(Thread.currentThread().getContextClassLoader().getResource(filename).getPath()).getPath();

        System.err.println(result);*/
        return null;
    }
}
