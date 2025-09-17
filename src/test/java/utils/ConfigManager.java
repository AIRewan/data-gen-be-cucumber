package utils;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager
{
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getResourceAsStream("/application.properties")) {
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage());
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
