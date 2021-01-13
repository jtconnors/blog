
package com.codeminders.hidapi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author jimc
 */
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext bc) throws Exception {
        ClassPathLibraryLoader.loadNativeHIDLibrary();
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
    }
    
}
