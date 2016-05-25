/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maclikescreenshot;

import java.io.File;
import static maclikescreenshot.KeyboardCommandListener.keyboardListener;

/**
 *
 * @author TAIBHSE
 */
public class MacLikeScreenshot
{

    public static String screenShotOut = "./";
    public static String screenShotName = "screenshot_54882472512";
    public static String format = "JPG";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        //use ctrl shift s to screenshot
        keyboardListener();

    }

}
