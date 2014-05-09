#!/bin/sh

workspace=$1
name_of_host=$2
report_dir=$workspace/jmeter_web_parser/src/main/reports
input_xml=$report_dir/$name_of_host.xml
xsl_dir=$workspace/jmeter_web_parser/src/main/xsl_files

report_xsl=/Applications/apache-jmeter-2.11/extras/jmeter-results-detail-report_21.xsl
sort_output_xsl=$xsl_dir/sort_output.xsl
images=$qa_tools/$workspace/jmeter_web_parser/src/main/images

/bin/cp $images/*.png $report_dir

echo /usr/bin/xsltproc -o $report_dir/sorted_$name_of_host.xml $xsl_dir/sort_output.xsl $report_dir/$name_of_host.xml
echo /usr/bin/xsltproc -o $report_dir/index.html$report_xsl $report_dir/sorted_$name_of_host.xml

/usr/bin/xsltproc -o $report_dir/sorted_$name_of_host.xml $xsl_dir/sort_output.xsl $report_dir/$name_of_host.xml
/usr/bin/xsltproc -o $report_dir/index.html$report_xsl $report_dir/sorted_$name_of_host.xml
