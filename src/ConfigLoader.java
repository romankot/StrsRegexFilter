import java.io.*;
import java.util.Properties;

/**
 * User: rkot
 * Date: 05.02.14  Time: 14:38
 */
public class ConfigLoader
{
    Properties prop = null;
    FileOutputStream outputStream = null;
    final static String CONFIG_PATH = "../data/tables";
    final static String CONFIG_FILENAME = "FileFilterConfig.tbl";

    public ConfigLoader()
    {
        prop = new Properties();
    }

    public ConfigLoader(String flag) {
        try
        {
            prop = new Properties();
            prop.load(new FileReader(CONFIG_PATH + "/" + CONFIG_FILENAME));
            
        }
        catch (FileNotFoundException e)
        {
            try
            {
                File theDir = new File(CONFIG_PATH);

                if(!theDir.mkdir()) {
                    System.err.println(CONFIG_PATH +"/"+ CONFIG_FILENAME + " folder wasn't created because its already exist");
                }
                outputStream = new FileOutputStream(CONFIG_PATH + "/" +CONFIG_FILENAME);
                System.err.println(CONFIG_PATH + "/" +CONFIG_FILENAME + " was created");
                prop.store(outputStream, "# This config file contains pair (regex -> replacemnet) for substitution." +
                                         "Please use \"=\", \":\" or any white space character except line termination, as delimiter.\r\n" +
                                         "For example \r\n [0-9]+ # \r\n sub1   sub2 \r\n As you can see from example there is support for several pairs  ");
            } catch (IOException e1) {
                e1.printStackTrace();  //To change

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change
        }
    }

    public Properties getConfig() {

        if ( prop.isEmpty() )
        {
            System.out.format("Config file \"%s\" is empty. Please add some key/values for substitution", CONFIG_FILENAME);
            return null;
        }
        else
        {
            return prop;
        }
    }

    public Properties loadConfig(String pathToTableFile) throws IOException {

        prop = new Properties();
        prop.load(new FileReader(pathToTableFile) );

        return prop;
    }
}
