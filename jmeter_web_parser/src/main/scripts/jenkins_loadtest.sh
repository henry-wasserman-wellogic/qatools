#!/bin/sh
#date=`date +%m\-%d\-%Y`

workspace=$1
jmx_file=$2

jmeter=/Applications/apache-jmeter-2.11/bin/jmeter.sh
jmx_file_dir=$workspace/jmeter_web_parser/src/main/jmx_files

echo $jmeter -n -t $jmx_file_dir/${jmx_file}_threaded.jmx -l $jmx_file_dir/$jmx_file.xml -Djmeter.save.saveservice.output_format=xml
$jmeter -n -t $jmx_file_dir/${jmx_file}_threaded.jmx -l $jmx_file_dir/$jmx_file.xml -Djmeter.save.saveservice.output_format=xml
