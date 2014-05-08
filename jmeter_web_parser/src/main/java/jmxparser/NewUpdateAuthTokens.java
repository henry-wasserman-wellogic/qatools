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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;;

public class NewUpdateAuthTokens
{
    public static void main( String[] args )
    {
    	if (args.length == 0) {
    		System.out.println("Usage: Filename");
    		System.exit(1);
    	}
        System.out.println( "Fixing " + args[0]);
        
        File jmx_file = new File(args[0]);
        File new_jmx_file = new File(FilenameUtils.removeExtension(args[0]) + "_threaded.jmx");
        String thread_group_numbered_xml = "";
        
        String reg_extracter_xml =
        		"            <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"Regular Expression Extractor\" enabled=\"true\">\n" +
        		"              <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.refname\">authToken</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.regex\">.*\\&quot;authToken\\&quot;\\:\\&quot;([\\d|\\w]+)</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.default\">COULD NOT EXTRACT TOKEN</stringProp>\n" +
        		"              <stringProp name=\"RegexExtractor.match_number\"></stringProp>\n" +
        		"            </RegexExtractor>\n" +
        		"            <hashTree/>\n" +
        		"		   <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"Regular Expression Extractor\" enabled=\"true\">\n" +
        		"            <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\n" +
        		"            <stringProp name=\"RegexExtractor.refname\">principle_id</stringProp>\n" +
        		"            <stringProp name=\"RegexExtractor.regex\">.*\\&quot;principal_id\\&quot;\\:\\&quot;([\\d|\\w|\\-]+)</stringProp>\n" +
        		"            <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
        		"            <stringProp name=\"RegexExtractor.default\">COULD NOT EXTRACT PRINCIPAL_ID</stringProp>\n" +
        		"            <stringProp name=\"RegexExtractor.match_number\"></stringProp>\n" +
        		"          </RegexExtractor>\n" +
        		"          <hashTree/>\n" +
				"		   <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"Regular Expression Extractor\" enabled=\"true\">\n" +
				"            <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.refname\">person_id</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.regex\">.*\\&quot;person_id\\&quot;\\:\\&quot;([\\d|\\w|\\-]+)</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.default\">COULD NOT EXTRACT PERSON_ID</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.match_number\"></stringProp>\n" +
				"          </RegexExtractor>\n" +
				"          <hashTree/>/n";
        
        String mailbox_reg_extractor_xml =
				"		   <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"Regular Expression Extractor\" enabled=\"true\">\n" +
				"            <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.refname\">mailbox_id</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.regex\">.*\\&quot;mailbox_id\\&quot;\\:\\&quot;([\\d]+)</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.default\">COULD NOT EXTRACT MAILBOX_ID</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.match_number\"></stringProp>\n" +
				"          </RegexExtractor>\n" +
				"          <hashTree/>\n" +
	            "		   <com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor guiclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui\" testclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor\" testname=\"jp@gc - JSON Path Extractor\" enabled=\"true\">\n" +
	            "		     <stringProp name=\"VAR\">folder_id</stringProp>\n" +
	            "		     <stringProp name=\"JSONPATH\">$.[0].folders[0].id</stringProp>\n" +
	            "		     <stringProp name=\"DEFAULT\">COULD NOT EXTRACT FOLDER_ID</stringProp>\n" +
	            "		   </com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor>\n" +
	            "		   <hashTree/>";

        String organization_id_reg_extractor_xml =
				"		   <RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"Regular Expression Extractor\" enabled=\"true\">\n" +
				"            <stringProp name=\"RegexExtractor.useHeaders\">false</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.refname\">organization_id</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.regex\">.*\\&quot;id\\&quot;\\:\\&quot;([\\d|\\w|\\-]+)</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.default\">COULD NOT EXTRACT ORGANIZATION_ID</stringProp>\n" +
				"            <stringProp name=\"RegexExtractor.match_number\"></stringProp>\n" +
				"          </RegexExtractor>\n" +
				"          <hashTree/>";
        
        String facility_id_reg_extractor_xml = 
                "		  <com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor guiclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui\" testclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor\" testname=\"jp@gc - JSON Path Extractor\" enabled=\"true\">\n" +
                "		    <stringProp name=\"VAR\">facility_id</stringProp>\n" +
                "			<stringProp name=\"JSONPATH\">$.memberFacilities[0].recentlySelectedFacilities</stringProp>\n" +
                "			<stringProp name=\"DEFAULT\">COULD NOT EXTRACT FACILITY_ID</stringProp>\n" +
                "		  </com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor>\n" +
                "		  <hashTree/>";

    
        String thread_group_xml = 
        		"               </hashTree>\n" +
        		"        </hashTree>\n" +
        		"        <ThreadGroup guiclass=\"ThreadGroupGui\" testclass=\"ThreadGroup\" testname=\"Thread Group\" enabled=\"true\">\n" +
        		"          <stringProp name=\"ThreadGroup.on_sample_error\">continue</stringProp>\n" +
        		"         <elementProp name=\"ThreadGroup.main_controller\" elementType=\"LoopController\" guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"Loop Controller\" enabled=\"true\">\n" +
        		"            <boolProp name=\"LoopController.continue_forever\">false</boolProp>\n" +
        		"            <stringProp name=\"LoopController.loops\">1</stringProp>\n" +
        		"          </elementProp>\n" +
        		"          <stringProp name=\"ThreadGroup.num_threads\">1</stringProp>\n" +
        		"          <stringProp name=\"ThreadGroup.ramp_time\">10</stringProp>\n" +
        		"          <longProp name=\"ThreadGroup.start_time\">1398274240000</longProp>\n" +
        		"          <longProp name=\"ThreadGroup.end_time\">1398274240000</longProp>\n" +
        		"          <boolProp name=\"ThreadGroup.scheduler\">false</boolProp>\n" +
        		"          <stringProp name=\"ThreadGroup.duration\"></stringProp>\n" +
        		"          <stringProp name=\"ThreadGroup.delay\"></stringProp>\n" +
        		"        </ThreadGroup>\n" +
        		"        <hashTree>\n" +
        		"          <RecordingController guiclass=\"RecordController\" testclass=\"RecordingController\" testname=\"Recording Controller\" enabled=\"true\"/>\n" +
        		"          <hashTree>";

        
        boolean found_auth_token = false;
        boolean found_login = false;
        boolean found_logout = false;
        boolean found_mailbox = false;
        boolean found_organization = false;
        boolean found_folder = false;
        boolean found_user_facilities = false;
        
        try {
        	List<String> list = FileUtils.readLines(jmx_file);
        	ArrayList<String> new_list = new ArrayList<String>();
        	
        	Pattern auth_token_pattern = Pattern.compile(".*Argument\\.value\\\"\\>([\\d|\\w]+)\\<.*");
        	Matcher matcher = null;
        	Pattern principle_id_pattern = Pattern.compile("/users/([\\d|\\w|\\-]+)/.*");
        	Pattern person_id_pattern = Pattern.compile("/users/groups/([\\d|\\w|\\-]+)/.*");
        	Pattern mailbox_id_pattern = Pattern.compile("/mailboxes/([\\d]+).*");
        	Pattern organization_id_pattern = Pattern.compile("/organizations/([\\d|\\w|\\-]+).*");
        	Pattern folder_id_pattern = Pattern.compile("/mailboxes/.*/folders/([\\d]+).*");
        	Pattern facility_id_pattern = Pattern.compile("/facility_search/([\\d|\\w|\\-]+).*");
        	Pattern users_facilty_pattern = Pattern.compile(".*/users/[\\d|\\w|\\-]+/facilities.*");
        	Pattern username_pattern = Pattern.compile(".*/([\\w]+\\@[\\w]+\\.com).*");
        	
        	String match = "";
        	HashMap<String,String> map = new HashMap<String,String>();
        	
        	//Put all authTokens that are found into a hash in order to replace them later with the string '${authToken}'
        	//Put all principle_id's that are found into a hash in order to replace them later with the string '${principle_id}'
        	//Put all person_id's that are found into a hash in order to replace them later with the string '${person_idl}'
        	//Put all mailbox_id's that are found into a hash in order to replace them later with the string '${mailbox_id}'
        	//Put all folder_id's that are found into a hash in order to replace them later with the string '${folder_id}'
        	//Put all facility_id's that are found into a hash in order to replace them later with the string'${facility_id}'
        	//Put all usernames that are found into a hash in order to replace them later with the string '${username}'
        	int index = -1;
        	int thread_group_counter = 2;
        	for (String line : list) {
        		index++;
        		new_list.add(line);
        		
        		//usernames are put into hash here
        		matcher = username_pattern.matcher(line);
            	if (matcher.find()) {
            		match = matcher.group(1);
            		map.put(match, "username");
            		continue;
            	}
        		
        		
        		//authTokens are put into hash here
        		if (found_auth_token && StringUtils.contains(line,"<stringProp name=\"Argument.value\">")) {
        			matcher = auth_token_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "authToken");
        			}
        			else {
        				match = "MATCH NOT FOUND";
        			}
    				found_auth_token = false;
    				continue;

        		}
        		
        		//If we see this we can start looking for the place to insert the facility Regular Expression Extractor XML
        		
        		//person_id's are put into hash here
        		if (StringUtils.contains(line, "users/groups")) {
        			matcher = person_id_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "person_id");
        				continue;
        			}
        		}
        		
        		//principle_id's are put into hash here
        		if (StringUtils.contains(line, "users/")) {
        			matcher = principle_id_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "principle_id");
        				
                	matcher = users_facilty_pattern.matcher(line);
                	if (matcher.find()) {
                		found_user_facilities = true;
                	}
        				
        				continue;
        			}
        		}
        		
        		//folders might be contained inside mailboxes
        		//folder_id's are put into hash here
        		if (StringUtils.contains(line, "mailboxes/")) {
        			matcher = mailbox_id_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "mailbox_id");
        				if (StringUtils.contains(line, "folders/")) {
                			matcher = folder_id_pattern.matcher(line);
                			if (matcher.find()) {
                				match = matcher.group(1);
                				map.put(match, "folder_id");
                			}
        				}
                			
        				continue;
        			}
        		}
        		
        		//organization_id's are put into hash here
        		if (StringUtils.contains(line, "organizations/") &&
        				!StringUtils.contains(line, "/organizations/memberships")) {
        			matcher = organization_id_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "organization_id");
        				continue;
        			}
        		}
        		
        		//facility_id's are put into hash here
        		if (StringUtils.contains(line, "facility_search/")) {
        			matcher = facility_id_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "facility_id");
        				continue;
        			}
        		}

        		//Regular Expression Extractor xml can be inserted here
        		if (found_login && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(reg_extracter_xml);
        			found_login = false;
        			continue;
        		}
        		
        		//Mailbox Expression Extractor xml can be inserted here
        		if (found_mailbox && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(mailbox_reg_extractor_xml);
        			found_mailbox = false;
        			continue;
        		}
        		
        		//Organization Expression Extractor xml can be inserted here
        		if (found_organization && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(organization_id_reg_extractor_xml);
        			found_organization = false;
        			continue;
        		}
        		
        		//Facility Expression Extractor xml can be inserted here
        		if (found_user_facilities && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(facility_id_reg_extractor_xml);
        			found_user_facilities = false;
        			continue;
        		}


        		//Thread Group xml can be inserted here
        		if (found_logout && StringUtils.contains(line,"</hashTree>")) {
        			thread_group_numbered_xml = StringUtils.replace(thread_group_xml, "Thread Group", "Thread Group " + Integer.toString(thread_group_counter));
        			thread_group_counter++;
        			new_list.add(thread_group_numbered_xml);
        			found_logout = false;
        			continue;
        		}
        		        		
        		//If we see this then we can start looking for authTokens
        		if (StringUtils.contains(line, "<elementProp name=\"authToken\" elementType=\"HTTPArgument\">")) {
        			found_auth_token = true;
        			continue;
        		}
        		
        		//If we see this then we can start looking for the place to insert the Regular Expression Extractor XML.
        		if (StringUtils.contains(line,  "<HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/login\" enabled=\"true\">")) {
        			found_login = true;
        			continue;
        		}
        		
        		//If we see this we can start looking for the place to insert the Thread Group XML
        		if (StringUtils.contains(line, "testclass=\"HTTPSamplerProxy\" testname=\"/logout")) {
        			found_logout = true;
        			continue;
        		}
        		
        		//If we see this we can start looking for the place to insert the mailbox and Folder Regular Expression Extractors
        		if (StringUtils.contains(line, "testclass=\"HTTPSamplerProxy\" testname=\"/mailboxes\"")) {
        			found_mailbox = true;
        			continue;
        		}
        		
        		//If we see this we can start looking for the place to insert the organization Regular Expression Extractor XML
        		if (StringUtils.contains(line, "testclass=\"HTTPSamplerProxy\" testname=\"/organizations/memberships\"")) {
        			found_organization = true;
        			continue;
        		}
        	}
        	
        	//For each authToken in the hash replace it with the string '${authToken}'
        	for (Map.Entry<String,String> entry : map.entrySet()) {
        	    System.out.print("key: ");
        	    System.out.println(entry.getValue() + ": " + entry.getKey());
        	   
        	
        	    index = 0;
        		for (String line : new_list) {
        			if (line.contains(entry.getKey())) {
        				if (entry.getValue().equals("authToken")) {
        					line = StringUtils.replace(line, entry.getKey(), "${authToken}",-1);
        				}
        				else if (entry.getValue().equals("principle_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${principle_id}",-1);
        				}
        				else if (entry.getValue().equals("person_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${person_id}",-1);
        				}
        				else if (entry.getValue().equals("mailbox_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${mailbox_id}",-1);
        				}
        				else if (entry.getValue().equals("folder_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${folder_id}",-1);
        				}
        				else if (entry.getValue().equals("organization_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${organization_id}",-1);
        				}
        				else if (entry.getValue().equals("facility_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${facility_id}",-1);
        				}
        				
        				if (entry.getValue().equals("username")) {
        					line = StringUtils.replace(line, entry.getKey(),  "${username}",-1);
        				}
            			new_list.set(index, line);
        			}
        			index++;
        		}
        		
        	}
        	
        	FileUtils.writeLines(new_jmx_file, new_list);
 
        }
        catch  (IOException e) {
        	e.printStackTrace();
        }
    }
}
