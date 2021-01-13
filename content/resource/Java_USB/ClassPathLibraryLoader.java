package com.codeminders.hidapi;

public class ClassPathLibraryLoader {

    private static final String[] HID_LIB_NAMES = {
        "hidapi-jni-32",
        "hidapi-jni-64"
    };

    public static boolean loadNativeHIDLibrary() {
        boolean isHIDLibLoaded = false;

        for (String path : HID_LIB_NAMES) {
            try {
                System.loadLibrary(path);
            } catch (Exception e) {
                isHIDLibLoaded = false;
                System.out.println(e.getStackTrace());
                continue;
            } catch (UnsatisfiedLinkError e) {
                isHIDLibLoaded = false;
                System.out.println(e.getStackTrace());
                continue;
            }
            
            isHIDLibLoaded = true;
            System.out.println("Library: " + path + " loaded");
            return isHIDLibLoaded;
        }
        return isHIDLibLoaded;
    }

}
