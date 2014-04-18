import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: rkot
 * Date: 20.01.14  Time: 14:40
 */
public class Main
{
    public static void main (String [] args) throws java.io.IOException
    {
        //System.out.print ("Start text: \r\n");
        ConfigLoader config = new ConfigLoader();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter printWriter = new PrintWriter(System.out);

        String inStr;

        Map<Pattern, String> pairs = new HashMap<Pattern, String>();
        String patternString = "";
        String replacement = "";

        if (args.length == 0)        // NO input arguments
        {
            System.err.println("Specify path to substitution table file. Otherwise the empty one will be created ");
            config = new ConfigLoader("");
        }
        else
        {
            String pathToTableFile = args[0];
            if (new File(pathToTableFile).isFile())       // config is found and exist
            {
                config.loadConfig(pathToTableFile);
            }
            else
            {
                System.out.println("Path to config file is invalid or file is missing. Please check  " + pathToTableFile);
                config = new ConfigLoader("");
            }
        }

        if (config.getConfig() == null)            // config file doesn't contains any pairs for substitution
        {
            System.err.println("There is no any pair for substitution. Config file is empty. Please add some key/values for substitution");
            while ((inStr = reader.readLine()) != null)
            {
               System.out.println(inStr);
            }
            return ;
        }
        // reading pairs (regex, replacement)
        for(String key : config.getConfig().stringPropertyNames())
        {
            String value = config.getConfig().getProperty(key);

            if (key.contains("\\")) {
                patternString = key.replace("\\", "\\\\");
            }   else
                patternString = key;

            if (value.contains("\\")) {
                replacement = value.replace("\\", "\\\\");
            }   else
                replacement = value;

            pairs.put(Pattern.compile(patternString), replacement);
        }

//        for (Map.Entry<Pattern, String> entry : pairs.entrySet())
//        {
//            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
//        }

        String outStr;
        while ((inStr = reader.readLine()) != null)
        {
             outStr = "";

            for (Map.Entry<Pattern, String> entry : pairs.entrySet())
            {
                Matcher matcher = entry.getKey().matcher(inStr);
                outStr = matcher.replaceAll(entry.getValue());
                inStr = outStr;
                matcher.reset();
            }
            printWriter.println(outStr);
        }

        reader.close();
        printWriter.close();
    }
}
