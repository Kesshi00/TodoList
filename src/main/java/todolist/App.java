package todolist;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.*;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) throws Exception {
        var webapp = new WebAppContext();
        webapp.setResourceBase("src/main/webapp");
        webapp.setContextPath("/");
        webapp.setConfigurations(new Configuration[]
                {
                        new AnnotationConfiguration(),
                        new WebInfConfiguration(),
                        new WebXmlConfiguration(),
                        new MetaInfConfiguration(),
                        new FragmentConfiguration(),
                        new EnvConfiguration(),
                        new PlusConfiguration(),
                        new JettyWebXmlConfiguration()

                });
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncluderJarPattern", ".*/classes/.*");
        webapp.addServlet(HelloServlet.class,"/api/*");
        var server = new Server(8080);
        server.setHandler(webapp);

        server.start();
        server.join();
    }
}
