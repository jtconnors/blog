package glassfish;

import java.io.File;
import java.io.IOException;
import org.glassfish.api.deployment.DeployCommandParameters;
import org.glassfish.api.embedded.ContainerBuilder;
import org.glassfish.api.embedded.EmbeddedDeployer;
import org.glassfish.api.embedded.EmbeddedFileSystem;
import org.glassfish.api.embedded.LifecycleException;
import org.glassfish.api.embedded.Server;

/**
 * @author Alexis Moussine-Pouchkine with some hacks by jimc
 */
public class Embedded {
    private Server server;
    private EmbeddedDeployer deployer;

    private static int port = 8888;
    private static String context = "hello";

    private String archiveName = null;
    private String applicationName = null;
    private static long time2sleep = 600000;

    /**
     * @param args the command line arguments
     */
    public Embedded(String archiveName) {
        this.archiveName = archiveName;
    }

    public void start() throws IOException {
        Server.Builder builder = new Server.Builder("testBuilder");

        // get the builder for EmbeddedFileSystem
        EmbeddedFileSystem.Builder efsb = new EmbeddedFileSystem.Builder();
        EmbeddedFileSystem efs = efsb.build();
        builder.embeddedFileSystem(efs);

        // Start the embedded server (should take no more than a few of seconds)
        server = builder.build();

        // Add a WEB container (other containers: ejb, jps, all, ...)
        ContainerBuilder containerBuilder = server.createConfig(ContainerBuilder.Type.web);
        server.addContainer(containerBuilder);
        containerBuilder.create(server);
        server.createPort(port);    // Starts grizzly on the given port
    }

    public boolean deploy() {
        // Setup machinery to deploy
        deployer = server.getDeployer();    // type is EmbeddedDeployer
        DeployCommandParameters deployParams = new DeployCommandParameters();
        deployParams.name = "myApplication";    // needed for undeploy
        deployParams.contextroot = context;     // overrides whatever the WAR contains

        // Creates default virtual server, web listener, does the deploy and
        // returns the applicationName as a String (null means something went wrong)
        // duration depends on application size and nature. Heavy lifting done here.
        File archive = new File(archiveName);
        applicationName = deployer.deploy(archive, deployParams);
        return (applicationName == null) ? false : true;
    }

    public void undeployAndStop() throws LifecycleException {
        deployer.undeploy(applicationName, null);   // Could have undeploy params like cascade, ...
        server.stop();  // May take a little while to clean everything up
        System.exit(0); // need this to kill any threads left runing (see #7198)
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java Main artifact2deploy.war [time2waitInSeconds]");
            System.exit(-1);
        }
        if (args.length > 1) {
            time2sleep = Integer.valueOf(args[1]) * 1000;
        }

        Embedded myGlassFish = new Embedded(args[0]);
        myGlassFish.start();
        boolean deployed = myGlassFish.deploy();

        if (deployed) {
            // TODO: do something useful instead of this basic testing
            // String exec_string = "open http://localhost:" + port + "/" + context + "/HelloServlet";
            // Runtime.getRuntime().exec(exec_string);
            Thread.sleep(time2sleep);
        }

        myGlassFish.undeployAndStop();  // stops and exits the JVM
    }
}

