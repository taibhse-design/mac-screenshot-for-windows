package maclikescreenshot;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static maclikescreenshot.MacLikeScreenshot.format;
import static maclikescreenshot.MacLikeScreenshot.screenShotName;
import static maclikescreenshot.MacLikeScreenshot.screenShotOut;

/**
 * @author TAIBHSE
 */
public class TakeScreenshot
{

    public static int offsetX = 0, offsetY = 0;

    public static void takeScreenshot(int x1, int y1, int x2, int y2) throws AWTException, IOException
    {

        Robot robot = new Robot();

        //#####################################################################
        //account for all attached screens
        Rectangle virtualBounds = new Rectangle();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        for (int j = 0; j < gs.length; j++)
        {
            GraphicsDevice gd = gs[j];
            GraphicsConfiguration[] gc = gd.getConfigurations();
            for (int i = 0; i < gc.length; i++)
            {
                virtualBounds = virtualBounds.union(gc[i].getBounds());
                if (gc[i].getBounds().x < 0)
                {
                    offsetX += gc[i].getBounds().width;
                }

                if (gc[i].getBounds().y < 0)
                {
                    offsetY += gc[i].getBounds().height;
                }
            }
            gd = null;
            gc = null;
        }

        
        //#####################################################################
        try
        {

           // save full screenshot to be cropped
            javaxt.io.Image out = new javaxt.io.Image(screenShotOut + "FULL" + "." + format);
            out.addImage(robot.createScreenCapture(virtualBounds), 0, 0, true);
            out.setOutputQuality(1000);
             out.saveAs(new File(screenShotOut + "FULL" + "."  + format));

            
          //  ImageIO.write(robot.createScreenCapture(virtualBounds), "JPG", new File(screenShotOut + "FULL" + ".JPG"));
         // ImageIO.write(robot.createScreenCapture(virtualBounds.intersection(new Rectangle(screenShotTopL.getLocation().x + (screenShotTopL.getWidth()/2),screenShotTopL.getLocation().y + (screenShotTopL.getHeight()/2),screenW,screenH))), "jpg", new File("G:\\My Documents\\GitHub\\test.jpg"));

            javaxt.io.Image image = new javaxt.io.Image(screenShotOut + "FULL" + "."  + format);
            image.setOutputQuality(1000);

            image.crop(x1 + offsetX, y1 + offsetY, ((x2 + offsetX) - (x1 + offsetX)), ((y2 + offsetY) - (y1 + offsetY)));

            image.saveAs(new File(screenShotOut + screenShotName + "."  + format));

            
            //delete full screenshot when done with
           // new File(screenShotOut + "FULL" + "."  + format).delete();
        } catch (IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(null, "PICTURE CAN NOT BE TAKE \nWITH AN INVERSED FRAME!", "WARNING", 0);
        }
    }
}
