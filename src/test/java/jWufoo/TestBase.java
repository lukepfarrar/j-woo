package jWufoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.junit.Before;

public class TestBase {

    private String apiKey;
    private String accountName;
    protected JWufooAPI api;

    @Before
    public void loadAccountDetails() throws FileNotFoundException, IOException {
        Properties accountProperties = new Properties();
        accountProperties.load(new FileReader(new File("~/wufoo.properties")));
        apiKey = accountProperties.getProperty("API_KEY");
        accountName = accountProperties.getProperty("ACCOUNT_NAME");
        api = new JWufooAPI(apiKey, accountName);
    }
}
