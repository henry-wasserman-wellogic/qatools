package jmeter_utility;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.XMLTestCase;
import org.xml.sax.SAXException;

import jmxparser.UpdateAuthTokens;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class UpdateAuthTokensTest 
	extends XMLTestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UpdateAuthTokensTest( String testName ) 
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UpdateAuthTokensTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
/*    	try {
    		
        	String user_dir = System.getProperty("user.dir");
	    	String relative_dir = user_dir + "/src/test/java/jmeter_utility/";
	    	String expected_output_xml = "";
	    	String actual_output_xml = user_dir + "";
	
	    	File expected_output = new File(relative_dir + "sonic_full_04_24_2014_threaded_expected.jmx");
	    	File actual_output = new File(relative_dir + "sonic_full_04_24_2014_threaded.jmx");
	
	    	String [] startup = {relative_dir + "sonic_full_04_24_2014.jmx"};
	    	UpdateAuthTokens.main(startup);
	    	
	    	expected_output_xml = FileUtils.readFileToString(expected_output);
	    	actual_output_xml = FileUtils.readFileToString(actual_output);
	    	
	    	//Diff myDiff = new Diff(expected_output_xml, actual_output_xml);
	    	assertXMLEqual("Comparing " + expected_output + " to " + actual_output, expected_output_xml, actual_output_xml);

    	}
    	catch (IOException e) {
    		e.printStackTrace();
    		Assert.fail("IOException was thrown. " + e.getMessage());
    	}
    	catch (SAXException e) {
    		e.printStackTrace();
    		Assert.fail("SaxException was thrown. " + e.getMessage());
    	}*/
    }
}