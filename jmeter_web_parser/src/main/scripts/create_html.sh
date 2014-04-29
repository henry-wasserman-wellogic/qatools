#!/bin/sh
date=04_29_2014
report_xsl=/Applications/apache-jmeter-2.11/extras/jmeter-results-detail-report_21.xsl
qa_tools=$HOME/wellogic/qatools
main=jmeter_web_parser/src/main
sort_output_xsl=$qa_tools/$main/xsl_files/sort_output.xsl
images=$qa_tools/$main/images

/bin/cp $images/*.png $date
/usr/bin/xsltproc $sort_output_xsl $date/bowser.xml > $date/sorted_bowser.xml
/usr/bin/xsltproc $report_xsl $date/sorted_bowser.xml > $date/bowser_report.html
