package maclikescreenshot;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static maclikescreenshot.MacLikeScreenshot.screenShotOut;

/**
 * @author TAIBHSE
 */
public class KeyboardCommandListener
{

    //   public static int cR = 25, cG = 25, cB = 20, cA = 204, cycle = 0;
    public static KeyAdapter q;
    public static GlobalKeyListener t;

    public static boolean commandStarted = false;

    public static int x1, x2, y1, y2;

    public static void keyboardListener()
    {
        Thread keyListener = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                t = new GlobalKeyListener();

                // new GlobalKeyListener().addKeyListener(new KeyAdapter()
                //(
                q = new KeyAdapter()
                {
                    @Override
                    public void keyPressed(KeyEvent event)
                    {

                        // ctrl + c
                        //listen for regular copy
                        if (event.isCtrlPressed() && event.isShiftPressed() && event.getVirtualKeyCode() == KeyEvent.VK_S && commandStarted == false)
                        {

                            commandStarted = true;
                            System.out.println("STARTING SCREENSHOT CAPTURE");
                            x1 = MouseInfo.getPointerInfo().getLocation().x;
                            y1 = MouseInfo.getPointerInfo().getLocation().y;
                          //  System.out.println("START: " + x1 + " , " + y1);

                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent event)
                    {
                        if (event.isCtrlPressed() == false && event.isShiftPressed() == false && commandStarted == true)
                        {
                            commandStarted = false;
                            System.out.println("ENDING SCREENSHOT CAPTURE");
                            x2 = MouseInfo.getPointerInfo().getLocation().x;
                            y2 = MouseInfo.getPointerInfo().getLocation().y;
                           // System.out.println("END: " + x2 + " , " + y2);

                            try
                            {
                                   //run screenshot code here

                                TakeScreenshot.takeScreenshot(x1, y1, x2, y2);
                            } catch (AWTException | IOException ex)
                            {
                                Logger.getLogger(KeyboardCommandListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            try
                            {
                                //open folder with screenshot
                                Desktop.getDesktop().open(new File(screenShotOut));
                            } catch (IOException ex)
                            {
                                Logger.getLogger(KeyboardCommandListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                             //exit program
                            // t.removeKeyListener(q);
                            // System.exit(0);
                        }
                    }
                };

                t.removeKeyListener(q);

                t.addKeyListener(q);
                while (true)
                {
                    try
                    {
                        Thread.sleep(100);
                    } catch (InterruptedException e)
                    {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }

                }

            }
        });

        keyListener.start();
    }
}
