#!/bin/sh
#date=`date +%m\-%d\-%Y`

workspace=$1
jmx_file=$2

jmeter=/Applications/apache-jmeter-2.11/bin/jmeter.sh
jmx_file_dir=$workspace/jmeter_web_parser/src/main/jmx_files

echo $jmeter -n -t $HOME/$jmx_file_dir/${jmx_file}_threaded.jmx -l $workspace/$jmx_file_dir/$jmx_file.xml -Djmeter.save.saveservice.output_format=xml
#$jmeter -n -t $HOME/$jmx_file_dir/$jmx_file_threaded.jmx  -l $workspace/$jmx_file_dir/$jmx_file.xml -Djmeter.save.saveservice.output_format=xml
