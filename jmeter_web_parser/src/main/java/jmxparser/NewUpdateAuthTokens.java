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
        
        String metadata_columns_loop_controller =
          "<LoopController guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"Loop Controller\" enabled=\"true\">\n" +
          "  <boolProp name=\"LoopController.continue_forever\">true</boolProp>\n" +
          "  <stringProp name=\"LoopController.loops\">${number_of_columns}</stringProp>\n" +
          "</LoopController>\n" +
          "<hashTree>\n" +
          "  <CounterConfig guiclass=\"CounterConfigGui\" testclass=\"CounterConfig\" testname=\"Counter\" enabled=\"true\">\n" +
          "    <stringProp name=\"CounterConfig.start\">1</stringProp>\n" +
          "    <stringProp name=\"CounterConfig.end\">${number_of_columns}</stringProp>\n" +
          "    <stringProp name=\"CounterConfig.incr\">1</stringProp>\n" +
          "    <stringProp name=\"CounterConfig.name\">column</stringProp>\n" +
          "    <stringProp name=\"CounterConfig.format\"></stringProp>\n" +
          "    <boolProp name=\"CounterConfig.per_user\">false</boolProp>\n" +
          "  </CounterConfig>\n" +
          "  <hashTree/>\n" +
          "  <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/patients/${patient_mpi}/metadata/columns/${column}\" enabled=\"true\">\n" +
          "    <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
          "      <collectionProp name=\"Arguments.arguments\">\n" +
          "        <elementProp name=\"authToken\" elementType=\"HTTPArgument\">\n" +
          "          <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "          <stringProp name=\"Argument.name\">authToken</stringProp>\n" +
          "          <stringProp name=\"Argument.value\">${authToken}</stringProp>\n" +
          "          <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "          <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"organizations\" elementType=\"HTTPArgument\">\n" +
          "          <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "          <stringProp name=\"Argument.name\">organizations</stringProp>\n" +
          "          <stringProp name=\"Argument.value\">all</stringProp>\n" +
          "          <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "          <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"version_id\" elementType=\"HTTPArgument\">\n" +
          "          <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "          <stringProp name=\"Argument.name\">version_id</stringProp>\n" +
          "          <stringProp name=\"Argument.value\">${version_id}</stringProp>\n" +
          "          <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "          <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"_\" elementType=\"HTTPArgument\">\n" +
          "          <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "          <stringProp name=\"Argument.name\">_</stringProp>\n" +
          "          <stringProp name=\"Argument.value\">1400016523683</stringProp>\n" +
          "          <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "          <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "        </elementProp>\n" +
          "      </collectionProp>\n" +
          "    </elementProp>\n" +
          "    <stringProp name=\"HTTPSampler.domain\">${servername}</stringProp>\n" +
          "    <stringProp name=\"HTTPSampler.port\">443</stringProp>\n" +
          "    <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
          "    <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
          "    <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
          "    <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
          "    <stringProp name=\"HTTPSampler.path\">/patients/${patient_mpi}/metadata/columns/${column}</stringProp>\n" +
          "    <stringProp name=\"HTTPSampler.method\">GET</stringProp>\n" +
          "    <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
          "    <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
          "    <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
          "    <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
          "    <boolProp name=\"HTTPSampler.monitor\">false</boolProp>\n" +
          "    <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
          "  </HTTPSamplerProxy>\n" +
          "  <hashTree>\n" +
          "    <com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor guiclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui\" testclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor\" testname=\"jp@gc - JSON Path Extractor\" enabled=\"true\">\n" +
          "      <stringProp name=\"VAR\">encounter_id</stringProp>\n" +
          "      <stringProp name=\"JSONPATH\">$.encounterId</stringProp>\n" +
          "      <stringProp name=\"DEFAULT\">COULD NOT EXTRACT ENCOUNTER_ID</stringProp>\n" +
          "    </com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor>\n" +
          "    <hashTree/>\n" +
          "    <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP Header Manager\" enabled=\"true\">\n" +
          "      <collectionProp name=\"HeaderManager.headers\">\n" +
          "        <elementProp name=\"Accept-Language\" elementType=\"Header\">\n" +
          "          <stringProp name=\"Header.name\">Accept-Language</stringProp>\n" +
          "          <stringProp name=\"Header.value\">en-US,en;q=0.8</stringProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"Accept\" elementType=\"Header\">\n" +
          "          <stringProp name=\"Header.name\">Accept</stringProp>\n" +
          "          <stringProp name=\"Header.value\">application/json, text/javascript, */*; q=0.01</stringProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"User-Agent\" elementType=\"Header\">\n" +
          "          <stringProp name=\"Header.name\">User-Agent</stringProp>\n" +
          "          <stringProp name=\"Header.value\">Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36</stringProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"Accept-Encoding\" elementType=\"Header\">\n" +
          "          <stringProp name=\"Header.name\">Accept-Encoding</stringProp>\n" +
          "          <stringProp name=\"Header.value\">gzip,deflate,sdch</stringProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"Referer\" elementType=\"Header\">\n" +
          "          <stringProp name=\"Header.name\">Referer</stringProp>\n" +
          "          <stringProp name=\"Header.value\">https://${servername}/</stringProp>\n" +
          "        </elementProp>\n" +
          "        <elementProp name=\"X-Requested-With\" elementType=\"Header\">\n" +
          "          <stringProp name=\"Header.name\">X-Requested-With</stringProp>\n" +
          "          <stringProp name=\"Header.value\">XMLHttpRequest</stringProp>\n" +
          "        </elementProp>\n" +
          "      </collectionProp>\n" +
          "    </HeaderManager>\n" +
          "    <hashTree/>\n" +
          "  </hashTree>\n" +
          "  <IfController guiclass=\"IfControllerPanel\" testclass=\"IfController\" testname=\"If Controller\" enabled=\"true\">\n" +
          "    <stringProp name=\"IfController.condition\">${records}.length != 0</stringProp>\n" +
          "    <boolProp name=\"IfController.evaluateAll\">false</boolProp>\n" +
          "  </IfController>\n" +
          "  <hashTree>\n" +
          "    <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/patients/${patient_mpi}/metadata/encounters/47592bab-f1ba-411d-bc0d-11faa40a68e3/rowdata\" enabled=\"true\">\n" +
          "      <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
          "        <collectionProp name=\"Arguments.arguments\">\n" +
          "          <elementProp name=\"authToken\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">authToken</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">${authToken}</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"organizations\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">organizations</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">all</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"version_id\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">version_id</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">${version_id}</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"template_id\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">template_id</stringProp>\n" +
          "            <stringProp name=\"Argument.value\"></stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"_\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">_</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">1400016523698</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </elementProp>\n" +
          "      <stringProp name=\"HTTPSampler.domain\">${servername}</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.port\">443</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.path\">/patients/${patient_mpi}/metadata/encounters/${encounter_id}/rowdata</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.method\">GET</stringProp>\n" +
          "      <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.monitor\">false</boolProp>\n" +
          "      <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
          "    </HTTPSamplerProxy>\n" +
          "    <hashTree>\n" +
          "      <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP Header Manager\" enabled=\"true\">\n" +
          "        <collectionProp name=\"HeaderManager.headers\">\n" +
          "          <elementProp name=\"Accept-Language\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Language</stringProp>\n" +
          "            <stringProp name=\"Header.value\">en-US,en;q=0.8</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept</stringProp>\n" +
          "            <stringProp name=\"Header.value\">application/json, text/javascript, */*; q=0.01</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"User-Agent\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">User-Agent</stringProp>\n" +
          "            <stringProp name=\"Header.value\">Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept-Encoding\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Encoding</stringProp>\n" +
          "            <stringProp name=\"Header.value\">gzip,deflate,sdch</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Referer\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Referer</stringProp>\n" +
          "            <stringProp name=\"Header.value\">https://${servername}/</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"X-Requested-With\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">X-Requested-With</stringProp>\n" +
          "            <stringProp name=\"Header.value\">XMLHttpRequest</stringProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </HeaderManager>\n" +
          "      <hashTree/>\n" +
          "    </hashTree>\n" +
          "    <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/patients/${patient_mpi}/metadata/encounters/47592bab-f1ba-411d-bc0d-11faa40a68e3/celldata\" enabled=\"true\">\n" +
          "      <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
          "        <collectionProp name=\"Arguments.arguments\">\n" +
          "          <elementProp name=\"authToken\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">authToken</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">${authToken}</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"organizations\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">organizations</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">all</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"section_number\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">section_number</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">1</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"row_number\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">row_number</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">1</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"version_id\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">version_id</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">${version_id}</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"record_type\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">record_type</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">1</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"template_id\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">template_id</stringProp>\n" +
          "            <stringProp name=\"Argument.value\"></stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"_\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">_</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">1400016523699</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </elementProp>\n" +
          "      <stringProp name=\"HTTPSampler.domain\">${servername}</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.port\">443</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.path\">/patients/${patient_mpi}/metadata/encounters/${encounter_id}/celldata</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.method\">GET</stringProp>\n" +
          "      <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.monitor\">false</boolProp>\n" +
          "      <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
          "    </HTTPSamplerProxy>\n" +
          "    <hashTree>\n" +
          "      <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP Header Manager\" enabled=\"true\">\n" +
          "        <collectionProp name=\"HeaderManager.headers\">\n" +
          "          <elementProp name=\"Accept-Language\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Language</stringProp>\n" +
          "            <stringProp name=\"Header.value\">en-US,en;q=0.8</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept</stringProp>\n" +
          "            <stringProp name=\"Header.value\">application/json, text/javascript, */*; q=0.01</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"User-Agent\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">User-Agent</stringProp>\n" +
          "            <stringProp name=\"Header.value\">Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept-Encoding\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Encoding</stringProp>\n" +
          "            <stringProp name=\"Header.value\">gzip,deflate,sdch</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Referer\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Referer</stringProp>\n" +
          "            <stringProp name=\"Header.value\">https://${servername}/</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"X-Requested-With\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">X-Requested-With</stringProp>\n" +
          "            <stringProp name=\"Header.value\">XMLHttpRequest</stringProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </HeaderManager>\n" +
          "      <hashTree/>\n" +
          "    </hashTree>\n" +
          "    <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/document_encounter/47592bab-f1ba-411d-bc0d-11faa40a68e3/summary\" enabled=\"true\">\n" +
          "      <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
          "        <collectionProp name=\"Arguments.arguments\">\n" +
          "          <elementProp name=\"authToken\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">authToken</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">${authToken}</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"_\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.name\">_</stringProp>\n" +
          "            <stringProp name=\"Argument.value\">1400016523700</stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "            <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </elementProp>\n" +
          "      <stringProp name=\"HTTPSampler.domain\">${servername}</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.port\">443</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.path\">/document_encounter/${encounter_id}/summary</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.method\">GET</stringProp>\n" +
          "      <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.monitor\">false</boolProp>\n" +
          "      <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
          "    </HTTPSamplerProxy>\n" +
          "    <hashTree>\n" +
          "      <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP Header Manager\" enabled=\"true\">\n" +
          "        <collectionProp name=\"HeaderManager.headers\">\n" +
          "          <elementProp name=\"Accept-Language\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Language</stringProp>\n" +
          "            <stringProp name=\"Header.value\">en-US,en;q=0.8</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept</stringProp>\n" +
          "            <stringProp name=\"Header.value\">application/json, text/javascript, */*; q=0.01</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"User-Agent\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">User-Agent</stringProp>\n" +
          "            <stringProp name=\"Header.value\">Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept-Encoding\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Encoding</stringProp>\n" +
          "            <stringProp name=\"Header.value\">gzip,deflate,sdch</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Referer\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Referer</stringProp>\n" +
          "            <stringProp name=\"Header.value\">https://${servername}/</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"X-Requested-With\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">X-Requested-With</stringProp>\n" +
          "            <stringProp name=\"Header.value\">XMLHttpRequest</stringProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </HeaderManager>\n" +
          "      <hashTree/>\n" +
          "    </hashTree>\n" +
          "    <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/document_encounter/47592bab-f1ba-411d-bc0d-11faa40a68e3/lock?loggedin_org_id=${organization_id}&amp;authToken=${authToken}\" enabled=\"true\">\n" +
          "      <boolProp name=\"HTTPSampler.postBodyRaw\">true</boolProp>\n" +
          "      <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\">\n" +
          "        <collectionProp name=\"Arguments.arguments\">\n" +
          "          <elementProp name=\"\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.value\"></stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </elementProp>\n" +
          "      <stringProp name=\"HTTPSampler.domain\">${servername}</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.port\">443</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.path\">/document_encounter/${encounter_id}/lock?loggedin_org_id=${organization_id}&amp;authToken=${authToken}</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.method\">PUT</stringProp>\n" +
          "      <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.monitor\">false</boolProp>\n" +
          "      <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
          "    </HTTPSamplerProxy>\n" +
          "    <hashTree>\n" +
          "      <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP Header Manager\" enabled=\"true\">\n" +
          "        <collectionProp name=\"HeaderManager.headers\">\n" +
          "          <elementProp name=\"Content-Type\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Content-Type</stringProp>\n" +
          "            <stringProp name=\"Header.value\">application/json</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept-Language\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Language</stringProp>\n" +
          "            <stringProp name=\"Header.value\">en-US,en;q=0.8</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept</stringProp>\n" +
          "            <stringProp name=\"Header.value\">application/json, text/javascript, */*; q=0.01</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Origin\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Origin</stringProp>\n" +
          "            <stringProp name=\"Header.value\">https://${servername}</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"User-Agent\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">User-Agent</stringProp>\n" +
          "            <stringProp name=\"Header.value\">Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept-Encoding\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Encoding</stringProp>\n" +
          "            <stringProp name=\"Header.value\">gzip,deflate,sdch</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Referer\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Referer</stringProp>\n" +
          "            <stringProp name=\"Header.value\">https://${servername}/</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"X-Requested-With\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">X-Requested-With</stringProp>\n" +
          "            <stringProp name=\"Header.value\">XMLHttpRequest</stringProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </HeaderManager>\n" +
          "      <hashTree/>\n" +
          "    </hashTree>\n" +
          "    <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"/document_encounter/47592bab-f1ba-411d-bc0d-11faa40a68e3/unlock?loggedin_org_id=${organization_id}&amp;authToken=${authToken}\" enabled=\"true\">\n" +
          "      <boolProp name=\"HTTPSampler.postBodyRaw\">true</boolProp>\n" +
          "      <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\">\n" +
          "        <collectionProp name=\"Arguments.arguments\">\n" +
          "          <elementProp name=\"\" elementType=\"HTTPArgument\">\n" +
          "            <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
          "            <stringProp name=\"Argument.value\"></stringProp>\n" +
          "            <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </elementProp>\n" +
          "      <stringProp name=\"HTTPSampler.domain\">${servername}</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.port\">443</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.protocol\">https</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.path\">/document_encounter/${encounter_id}/unlock?loggedin_org_id=${organization_id}&amp;authToken=${authToken}</stringProp>\n" +
          "      <stringProp name=\"HTTPSampler.method\">PUT</stringProp>\n" +
          "      <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
          "      <boolProp name=\"HTTPSampler.monitor\">false</boolProp>\n" +
          "      <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
          "    </HTTPSamplerProxy>\n" +
          "    <hashTree>\n" +
          "      <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP Header Manager\" enabled=\"true\">\n" +
          "        <collectionProp name=\"HeaderManager.headers\">\n" +
          "          <elementProp name=\"Content-Type\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Content-Type</stringProp>\n" +
          "            <stringProp name=\"Header.value\">application/json</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept-Language\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Language</stringProp>\n" +
          "            <stringProp name=\"Header.value\">en-US,en;q=0.8</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept</stringProp>\n" +
          "            <stringProp name=\"Header.value\">application/json, text/javascript, */*; q=0.01</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Origin\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Origin</stringProp>\n" +
          "            <stringProp name=\"Header.value\">https://${servername}</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"User-Agent\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">User-Agent</stringProp>\n" +
          "            <stringProp name=\"Header.value\">Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Accept-Encoding\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Accept-Encoding</stringProp>\n" +
          "            <stringProp name=\"Header.value\">gzip,deflate,sdch</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"Referer\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">Referer</stringProp>\n" +
          "            <stringProp name=\"Header.value\">https://${servername}/</stringProp>\n" +
          "          </elementProp>\n" +
          "          <elementProp name=\"X-Requested-With\" elementType=\"Header\">\n" +
          "            <stringProp name=\"Header.name\">X-Requested-With</stringProp>\n" +
          "            <stringProp name=\"Header.value\">XMLHttpRequest</stringProp>\n" +
          "          </elementProp>\n" +
          "        </collectionProp>\n" +
          "      </HeaderManager>\n" +
          "      <hashTree/>\n" +
          "    </hashTree>\n" +
          "  </hashTree>\n" +
          "</hashTree>";
	
        String login_reg_extracter_xml =
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
        
        String facility_id_json_extractor = 
                "		  <com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor guiclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui\" testclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor\" testname=\"jp@gc - JSON Path Extractor\" enabled=\"true\">\n" +
                "		    <stringProp name=\"VAR\">facility_id</stringProp>\n" +
                "			<stringProp name=\"JSONPATH\">$.memberFacilities[0].recentlySelectedFacilities</stringProp>\n" +
                "			<stringProp name=\"DEFAULT\">COULD NOT EXTRACT FACILITY_ID</stringProp>\n" +
                "		  </com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor>\n" +
                "		  <hashTree/>";
        
        String patient_search_json_extractor =
                "		  <com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor guiclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui\" testclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor\" testname=\"jp@gc - JSON Path Extractor\" enabled=\"true\">\n" +
                "		    <stringProp name=\"VAR\">patient_mpi</stringProp>\n" +
                "			<stringProp name=\"JSONPATH\">$.[0].mpi</stringProp>\n" +
                "			<stringProp name=\"DEFAULT\">COULD NOT EXTRACT PATIENT_MPI</stringProp>\n" +
                "		  </com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor>\n" +
                "		  <hashTree/>";
        
        String version_id_json_extractor =
                "		  <com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor guiclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui\" testclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor\" testname=\"jp@gc - JSON Path Extractor\" enabled=\"true\">\n" +
                "		    <stringProp name=\"VAR\">version_id</stringProp>\n" +
                "			<stringProp name=\"JSONPATH\">$.versionId</stringProp>\n" +
                "			<stringProp name=\"DEFAULT\">COULD NOT EXTRACT VERSION_ID</stringProp>\n" +
                "		  </com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor>\n" +
                "		  <hashTree/>";

        String number_of_columns_json_extractor =
                "		  <com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor guiclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.gui.JSONPathExtractorGui\" testclass=\"com.atlantbh.jmeter.plugins.jsonutils.jsonpathextractor.JSONPathExtractor\" testname=\"jp@gc - JSON Path Extractor\" enabled=\"true\">\n" +
                "		    <stringProp name=\"VAR\">number_of_columns</stringProp>\n" +
                "			<stringProp name=\"JSONPATH\">$.numberOfColumns</stringProp>\n" +
                "			<stringProp name=\"DEFAULT\">COULD NOT EXTRACT NUMBER_OF_COLUMNS</stringProp>\n" +
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
        boolean found_user_facilities = false;
        boolean found_patients_search = false;
        boolean found_patients_metadata = false;
        boolean found_metadata_columns = false;
        boolean found_version_id = false;
        boolean start_looking_for_column_loopcount_insert = false;
        boolean add_line = true;
        boolean found_hash_tree = false;
        boolean found_searchtext = false;
        
        try {
        	List<String> list = FileUtils.readLines(jmx_file);
        	ArrayList<String> new_list = new ArrayList<String>();
        	
        	Matcher matcher = null;
        	
        	Pattern auth_token_pattern = Pattern.compile(".*Argument\\.value\\\"\\>([\\d|\\w]+)\\<.*");
        	Pattern version_id_pattern = Pattern.compile(".*Argument\\.value\\\">([\\d\\w\\-]+).*");
        	Pattern principle_id_pattern = Pattern.compile("/users/([\\d|\\w|\\-]+)/.*");
        	Pattern person_id_pattern = Pattern.compile("/users/groups/([\\d|\\w|\\-]+)/.*");
        	Pattern mailbox_id_pattern = Pattern.compile("/mailboxes/([\\d]+).*");
        	Pattern organization_id_pattern = Pattern.compile("/organizations/([\\d|\\w|\\-]+).*");
        	Pattern folder_id_pattern = Pattern.compile("/mailboxes/.*/folders/([\\d]+).*");
        	Pattern facility_id_pattern = Pattern.compile("/facility_search/([\\d|\\w|\\-]+).*");
        	Pattern users_facility_pattern = Pattern.compile(".*/users/[\\d|\\w|\\-]+/facilities.*");
        	Pattern username_pattern = Pattern.compile(".*/([\\w]+\\@[\\w]+\\.com).*");
        	Pattern patients_mpi_pattern = Pattern.compile("/patients/([\\d|\\w|\\-]+)/.*");
        	Pattern patients_metadata_pattern = Pattern.compile("/patients/([\\d|\\w|\\-]+)/metadata\\\" enabled=\\\"true\\\">");
        	Pattern search_text_pattern = Pattern.compile("<stringProp\\sname\\=\\\"Argument\\.value\\\">(.*)<\\/stringProp>");
        	
        	String match = "";
        	HashMap<String,String> map = new HashMap<String,String>();
        	HashMap<String,String> patients = new HashMap<String,String>();
        	
        	//Put all authTokens that are found into a hash in order to replace them later with the string '${authToken}'
        	//Put all principle_id's that are found into a hash in order to replace them later with the string '${principle_id}'
        	//Put all person_id's that are found into a hash in order to replace them later with the string '${person_idl}'
        	//Put all mailbox_id's that are found into a hash in order to replace them later with the string '${mailbox_id}'
        	//Put all folder_id's that are found into a hash in order to replace them later with the string '${folder_id}'
        	//Put all facility_id's that are found into a hash in order to replace them later with the string'${facility_id}'
        	//Put all usernames that are found into a hash in order to replace them later with the string '${username}'
        	//Put all patient_mpi that are found into a hash in order to replace them later with the string '${patient_mpi}'
        	//Put all version_id's that are found into a hash in order to replace them later with the string '${version_id}'
        	//Put all patients that are found into a hash in order to replace them later with the string '${patient1},${patient2},${patient3}
        	int index = -1;
        	int thread_group_counter = 2;
        	for (String line : list) {
        		index++;
        		
        		//If we see this we can start looking to replace the patient name
        		if (StringUtils.contains(line,"<stringProp name=\"Argument.name\">searchtext</stringProp>")) {
        			found_searchtext = true;
        			new_list.add(line);
        			continue;
        		}
        		
        		//We want to remove all static nodes that have /metadata/columns all the way up to the  </hashTree> tag
        		//These nodes will be called dynamically
        		if (StringUtils.contains(line, "/metadata/columns")) {
        			add_line = false;
        			found_hash_tree = false;
        		}
        		
        		if (found_hash_tree) {
        			found_hash_tree = false;
        			add_line = true;
        		}

        		if (StringUtils.contains(line,  "</hashTree>")) {
        			found_hash_tree = true;
        		}
        		
        		//Fix Search Text Here
        		if (found_searchtext) {
        			matcher = search_text_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        			
        				patients.put(match, "found");
        			
        				if (patients.size() == 1) {
        					line = "<stringProp name=\"Argument.value\">${patient1}</stringProp>";
        				} else if (patients.size() == 2) {
        					line = "<stringProp name=\"Argument.value\">${patient2}</stringProp>";
        				} else if (patients.size() == 3) {
        					line = "<stringProp name=\"Argument.value\">${patient3}</stringProp>";
        				} else if (patients.size() == 4) {
        					line = "<stringProp name=\"Argument.value\">${patient4}</stringProp>";
        				}

	        			found_searchtext = false;
        			}
        		}
     		
        		if (add_line) {
        			new_list.add(line);
        		}
        		
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
        				match = "AUTHTOKEN MATCH NOT FOUND";
        			}
    				found_auth_token = false;
    				continue;

        		}
        		
        		
        		if (found_metadata_columns && StringUtils.contains(line, "<elementProp name=\"version_id\" elementType=\"HTTPArgument\">")) {
        			found_version_id = true;
        		}
        		
        		//version_id's are put into hash here
        		if (found_metadata_columns && found_version_id && StringUtils.contains(line,"<stringProp name=\"Argument.value\">")) {
        			matcher = version_id_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "version_id");
        				found_version_id = false;
        			}
        			else {
        				match = "VERSION ID MATCH NOT FOUND";
        			}
        			found_metadata_columns = false;
    				continue;

        		}
        		
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
        				
                	matcher = users_facility_pattern.matcher(line);
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
        		
        		//patient_mpi's are put into hash here
        		if (StringUtils.contains(line, "patients/") &&
        				!StringUtils.contains(line, "/patients/search")) {
        			matcher = patients_mpi_pattern.matcher(line);
        			if (matcher.find()) {
        				match = matcher.group(1);
        				map.put(match, "patient_mpi");
        				
        				//handle the special case for patient metadata
        				matcher = patients_metadata_pattern.matcher(line);
        				if (matcher.find()) {
        					found_patients_metadata = true;
        				}
        				//handles the special case for metadata/columns
        				else if (StringUtils.contains(line,  "/metadata/columns/")) {
        						found_metadata_columns = true;
        				}

        				continue;
        			}
        			
        			//We also need to check here to start looking for the metadata column loop counter.
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

        		//Login Regular Expression Extractor xml can be inserted here
        		if (found_login && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(login_reg_extracter_xml);
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
        		
        		//Facility ID JSON Extractor can be inserted here
        		if (found_user_facilities && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(facility_id_json_extractor);
        			found_user_facilities = false;
        			continue;
        		}
        		
        		//Patient Search JSON Extractor can be inserted here
        		if (found_patients_search && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(patient_search_json_extractor);
        			found_patients_search = false;
        			continue;
        		}
        		
        		//Version ID JSON Extractor can be inserted here
        		if (found_patients_metadata && StringUtils.contains(line,"<hashTree>")) {
        			new_list.add(version_id_json_extractor);
        			new_list.add(number_of_columns_json_extractor);
        			found_patients_metadata = false;
        			start_looking_for_column_loopcount_insert = true;
        			continue;
        		}
        		
        		if (start_looking_for_column_loopcount_insert && StringUtils.contains(line,  "</hashTree>")) {
        			new_list.add(metadata_columns_loop_controller);
        			start_looking_for_column_loopcount_insert = false;
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
        		        		
        		//If we see this then we can start looking for the place to insert the Login Regular Expression Extractor XML.
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
        		
        		//If we see this we can start looking for the place to insert the patients_search JSON Path Extractor XML
        		if (StringUtils.contains(line, "testclass=\"HTTPSamplerProxy\" testname=\"/patients/search\"")) {
        			found_patients_search = true;
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
        					//line = StringUtils.replace(line, entry.getKey(), "${mailbox_id}",-1);
        					line = StringUtils.replace(line, "/mailboxes/" + entry.getKey() + "/", "/mailboxes/${mailbox_id}/",-1);
        				}
        				else if (entry.getValue().equals("folder_id")) {
        					line = StringUtils.replace(line, "/folders/" +entry.getKey() + "/", "/folders/${folder_id}/",-1);
        				}
        				else if (entry.getValue().equals("organization_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${organization_id}",-1);
        				}
        				else if (entry.getValue().equals("facility_id")) {
        					line = StringUtils.replace(line, entry.getKey(), "${facility_id}",-1);
        				}
        				else if (entry.getValue().equals("patient_mpi")) {
        					line = StringUtils.replace(line, entry.getKey(), "${patient_mpi}",-1);
        				}
        				else if (entry.getValue().equals("version_id")) {
        					line = StringUtils.replace(line,  entry.getKey(),  "${version_id}",-1);
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