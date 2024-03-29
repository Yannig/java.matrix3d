package testMatrix;

/*
 * @(#)DemoImages.java	1.9 00/06/19
 *
 * Copyright (c) 1998, 1999 by Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */


import java.awt.*;
import java.awt.image.*;
import java.util.Hashtable;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * A cache of all the demo images found in the images directory.
 */
public class DemoImages extends Component {

    private String[] names = 
    { 
        "savon.gif","Yann.jpg"
    };
    private static Hashtable cache;


    public DemoImages() {
        cache = new Hashtable(names.length);
        for (int i = 0; i < names.length; i++) {
            cache.put(names[i], getImage(names[i], this));
        }
    }
    public Image getImage(String name) {
        return getImage(name,this);
    }
    public static Image getImage(String name, Component cmp) {
        Image img = null;
        if (cache != null) {
            if ((img = (Image) cache.get(name)) != null) {
                return img;
            }
        }
	URLClassLoader urlLoader = (URLClassLoader)cmp.getClass().getClassLoader();
	URL fileLoc = urlLoader.findResource("./" + name);
	img = cmp.getToolkit().createImage(fileLoc);
        
        MediaTracker tracker = new MediaTracker(cmp);
        tracker.addImage(img, 0);
        try {
            tracker.waitForID(0);
            if (tracker.isErrorAny()) {
                System.out.println("Error loading image " + name);
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return img;
    }
}
