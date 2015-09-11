package app;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import sun.misc.IOUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by oleg on 12.09.2015.
 */
public class JNDIConfigurationForTests {

    private static String url;
    private static String username;
    private static String password;

    protected static void configureJNDIForJUnit() {
        // rcarver - setup the jndi context and the datasource
        try {
            setSystemProperties();

            InitialContext ic = new InitialContext();

            createSubContext(ic);

            setDataBaseProperties();

            // Construct DataSource
            MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();

            setMySqlDataSourceProperties(ds);

            ic.bind("java:/comp/env/jdbc/animals", ds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    private static void setSystemProperties(){
        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES,
                "org.apache.naming");
    }

    private static void createSubContext(InitialContext initialContext) throws NamingException{
        initialContext.createSubcontext("java:");
        initialContext.createSubcontext("java:/comp");
        initialContext.createSubcontext("java:/comp/env");
        initialContext.createSubcontext("java:/comp/env/jdbc");
    }

    private static void setDataBaseProperties(){
        Properties prop = new Properties();

        try(InputStream input = new FileInputStream("src/test/resources/source.properties")) {

            // load a properties file
            prop.load(input);

            // get the property value
            setUrl(prop.getProperty("database.url"));
            setUsername(prop.getProperty("database.username"));
            setPassword(prop.getProperty("database.password"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void setMySqlDataSourceProperties(MysqlConnectionPoolDataSource ds){
        ds.setURL(getUrl());
        ds.setUser(getUsername());
        ds.setPassword(getPassword());
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        JNDIConfigurationForTests.url = url;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        JNDIConfigurationForTests.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        JNDIConfigurationForTests.password = password;
    }
}