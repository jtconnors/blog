Helper Classes and Scripts for Mimicking Flight Recorder Triggers

A. The Java Applications Included
B. Sample JMX Managed Application
C. Command Line Arguments to JMX Client Applications
D. Files Provided in the NetBeans dist/ Directory
E. How to Run the Example

A. The Java Applications Included

Under the com/example/jmxclient source directory are three Java source files:

    JMXClient.java - this is an abstract class that does most of the heavy
        lifting.  It is responsible for creating a JMX client connection to
        a JMX server application.  It must be extended to become a real
        client representing a specific mbean.

    JMXClientThreadCount.java - this is the first of two example JMX clients
        that extends the aforementioned JMXClient class. It accesses the 
        "java.lang:type=Threading" mbean and makes a call to the mbean's
        getThreadCount() method to retrieve the managed
        application's thread count.

        In order to understand how this JMX client was created,
        the source code for this example instructs you to make 5
        straightforward modifications.

    JMXClientResponseTime.java - this is the second of two sample JMX clients
        extending the JMXClient class.  It accesses a custom mbean called
        "SimpleAgent:name=SLAReport" and makes a call to the mbean's
        getResponseTime() method.

        Just like the first example above (JMXClientThreadCount.java), the
        source highlights the 5 modifications needed to make a client of this
        type.  The main difference here is the simple custom bean proxy 
        (one method) is defined as an inner class.

B. Sample JMX Managed Application

Under the com/example/latencies source directory is our simple JMX managed
application called Latencies.  When started, this program prompts the user
to hit <Enter>.  Each time it is pressed, an additional thread is created
along with extra latency too.  We'll use the our two sample JMX client
programs listed above to get thread count and response time data on this
running application.

C. Command Line Arguments to JMX Client Applications

    When running JMXClient derived programs, here's the list of available
    command-line arguments.  This message can be printed out with the -help or
    -? option.

      -help | --help | -?
            Print this screen for command-line argument options and exit
      -debug
            enable debug output
      -once
            retrieve mbean value and output value once
      -host:hostname (default: localhost)
            Specify host name (or IP Address) of JMX server
      -port:PORT_NUMBER (default 9999)
            Specify port for JMX connection
      -interval:milliseconds (default: 1000ms)
            Specify polling interval in milliseconds. Polling will continue
            indefinitely until polled mbean value exceeds threshold.  This
            must be used in conjunction with the -threshold:value option.
      -threshold:value
            Specify threshold mbean value which will terminate program

    Our sample JMX scripts (see below) use the -interval and -threshold options
    to mimic Java Flight Recorder trigger behavior.

D. Files Provided in the NetBeans dist/ Directory
 
Once this NetBeans project is built, the following files will be created
in the project's dist/ directory:

    JMXClient.jar - contains all the class files needed to run the Latencies,
        JMXClientThreadCount and JMXClientResponseTime programs.

    Latencies.bat - Batch file used to run the Latencies Program.

    JMXClientThreadCount.bat - Batch file used to run the JMXClientThreadCount
        JMX client application.  This script runs JMXClientThreadCount with
        the following arguments: "-interval:2000 -threshold:20".
        This instructs the program to continue polling the Latencies program
        every 2000ms (2 seconds) until the thread count exceeds 20.  Once
        exceeded, a Java Flight Recorder dump will take place. 
    
    JMXClientResponseTime.bat - Batch file used to run the JMXClientResponseTime
        JMX client application.  This script runs JMXClientResponseTime with
        the following arguments: "-interval:2000 -threshold:900".
        This instructs the program to continue polling the Latencies program
        every 2000ms (2 seconds) until the response time exceeds 900ms.  Once
        exceeded, a Java Flight Recorder dump will take place.

    incljava.bat - This is a batch file which is included by the three program
        batch files listed above.

    README.txt - this file

E. How to Run the Example

    1. Perform a NetBeans "Clean and Build" on this (JMXClient) project.

    2. Move to this project's (JMXClient) dist/ directory.

    3. Run the Latencies.bat script.  This program instructs the user to hit
       <Enter> to add threads.  DO NOT HIT <Enter> YET.

    4. Run either the JMXClientThreadCount.bat or JMXClientResponseTime.bat
       script. It will continually poll the Latencies program until the
       designated "trigger" threshold is met.

    5. Return to the Latencies.bat window and hit enter 4 or 5 times.  This 
       should cause the "trigger" threshold to be met causing a Java Flight 
       Recorder dump of the Latencies program to the Latencies.jfr file.

    6. With the created Latencies.jfr file, you can start Java Mission Control
       (jmc) and open the flight recorder file.