#!/bin/sh
#date=04_29_2014
workspace=$1
name_of_host=$2
report_dir=$workspace/jmeter_web_parser/src/main/reports
input_xml=$report_dir/$name_of_host.xml
xsl_dir=$workspace/jmeter_web_parser/src/main/xsl_files

report_xsl=/Applications/apache-jmeter-2.11/extras/jmeter-results-detail-report_21.xsl
sort_output_xsl=$xsl_dir/sort_output.xsl
images=$qa_tools/$workspace/jmeter_web_parser/src/main/images

/bin/cp $images/*.png $report_dir

echo /usr/bin/xsltproc $report_dir/$name_of_host.xml > $report_xsl/sorted_bowser.xml
echo /usr/bin/xsltproc $report_dir/sorted_bowser.xml > $report_xsl/bowser_report.html

/usr/bin/xsltproc $report_dir/$name_of_host.xml > $report_xsl/sorted_bowser.xml
/usr/bin/xsltproc $report_dir/sorted_bowser.xml > $report_xsl/bowser_report.html