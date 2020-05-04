import database.operations.StorageHandler;
import elibrary.auth.LogIntoElibrary;
import graph.gephi.GephiClusterer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GephiClustererTest {

    private static final Logger logger = LoggerFactory.getLogger(StorageHandlerTest.class);
    @Test
    public void testCreatingGraph(){
        GephiClusterer gc = new GephiClusterer();
        gc.action();

        StorageHandler.saveClusters(gc.getClusters());

    }

    @Test
    public void updateYear(){
        LogIntoElibrary.withoutAuth();
        StorageHandler.updatePublicationsYear();
    }
}
