#!/bin/sh
host_env=bowser
date=04_29_2014
jmx_file=Bowser_04_25_2014
jmeter=/Applications/apache-jmeter-2.11/bin/jmeter.sh
reports_dir=wellogic/jmeter/reports
jmx_file_dir=wellogic/jmeter/jmx_files

echo "$jmeter -n -t $HOME/$jmx_file_dir/$jmx_file_threaded.jmx  -l $HOME/$reports_dir/$host_env/$date/$host_env.xml -Djmeter.save.saveservice.output_format=xml"
#$jmeter -n -t $HOME/$jmx_file_dir/$jmx_file_threaded.jmx  -l $HOME/$reports_dir/$host_env/$date/$host_env.xml -Djmeter.save.saveservice.output_format=xml
