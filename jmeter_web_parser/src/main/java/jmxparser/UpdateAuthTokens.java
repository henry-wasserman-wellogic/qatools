package jmxparser;

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
        
        String reg_extracter_xml =
        		"            <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"Regular Expression Extractor\" enabled=\"true\">\n" +
        		"              <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.refname\">authToken</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.regex\">.*\\&quot;authToken\\&quot;\\:\\&quot;([\\d|\\w]+)</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.default\">COULD NOT EXTRACT TOKEN</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.match_number\"></stringProp>\n" +
        		"            </RegexExtractor>\n" +
        		"            <hashTree/>";
        
        boolean found_auth_token = false;
        boolean found_login = false;
        try {
        	List<String> list = FileUtils.readLines(jmx_file);
        	ArrayList<String> new_list = new ArrayList<String>();
        	
        	Pattern pattern = Pattern.compile(".*Argument\\.value\\\"\\>([\\d|\\w]+)\\<.*");
        	Matcher matcher = null;
        	String match = "";
        	HashMap<String,String> map = new HashMap<String,String>();
        	
        	//Put all authTokens that are found into a hash in order to replace them later with the string '${authToken}'
        	int index = -1;
        	int new_index = -1;
        	for (String line : list) {
        		index++;
        		new_index++;
        		new_list.add(line);
        		
        		//authTokens are put into hash here
        		if (found_auth_token && StringUtils.contains(line,"<stringProp name=\"Argument.value\">")) {
        			matcher = pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "");
        			}
        			else {
        				match = "MATCH NOT FOUND";
        			}
    				found_auth_token = false;
    				continue;

        		}
        		
        		//Regular Expression Extractor xml can be inserted here
        		if (found_login && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(reg_extracter_xml);
        			found_login = false;
        			continue;
        		}
        		
        		//If we see this then we can start looking for authTokens
        		if (StringUtils.contains(line, "<elementProp name=\"authToken\" elementType=\"HTTPArgument\">")) {
        			found_auth_token = true;
        			continue;
        		}
        		
        		//If we see this then we can start looking for the place to insert the regular expression extractor xml.
        		if (StringUtils.contains(line,  "<HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/login\" enabled=\"true\">")) {
        			found_login = true;
        			continue;
        		}
        	
        	}
        	
        	//For each authToken in the hash replace it with the string '${authToken}'
        	for (Map.Entry<String,String> entry : map.entrySet()) {
        	    System.out.print("key");
        	    System.out.println(entry.getKey());
        	
        	    index = 0;
        		for (String line : new_list) {
        			if (line.contains(entry.getKey())) {
        				line = StringUtils.replace(line, entry.getKey(), "${authToken}",-1);
            			new_list.set(index, line);
        			}
        			index++;
        		}
        		
        	}
        	
        	FileUtils.writeLines(jmx_file, new_list);
 
        }
        catch  (IOException e) {
        	e.printStackTrace();
        }
    }
}
