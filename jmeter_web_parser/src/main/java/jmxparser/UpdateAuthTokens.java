package jmeter_utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;;

public class UpdateAuthTokens
{
    public static void main( String[] args )
    {
    	if (args.length == 0) {
    		System.out.println("Usage: Filename");
    		System.exit(1);
    	}
        System.out.println( "Fixing " + args[0]);
        
        File jmx_file = new File(args[0]);
        
        boolean found_it = false;
        try {
        	List<String> list = FileUtils.readLines(jmx_file);
        	
        	Pattern pattern = Pattern.compile(".*Argument\\.value\\\"\\>([\\d|\\w]+)\\<.*");
        	Matcher matcher = null;
        	String match = "";
        	HashMap<String,String> map = new HashMap<String,String>();
        	
        	for (String line : list) {
        		
        		if (found_it && StringUtils.contains(line,"<stringProp name=\"Argument.value\">")) {
        			matcher = pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "");
        			}
        			else {
        				match = "MATCH NOT FOUND";
        			}
    				found_it = false;
    				continue;

	
        		}
        		
        		
        		if (StringUtils.contains(line, "<elementProp name=\"authToken\" elementType=\"HTTPArgument\">")) {
        			found_it = true;
        			continue;
        		}
        	
        	}
        	
        	for (Map.Entry<String,String> entry : map.entrySet()) {
        	    System.out.print("key");
        	    System.out.println(entry.getKey());
        	
        	    int index = 0;
        		for (String line : list) {
        			if (line.contains(entry.getKey())) {
        				line = StringUtils.replace(line, entry.getKey(), "${authToken}",-1);
        			}
        		
        			list.set(index, line);
        			index++;
        		}
        		
        	}
        	
        	FileUtils.writeLines(jmx_file, list);
 
        }
        catch  (IOException e) {
        	e.printStackTrace();
        }
    }
}
