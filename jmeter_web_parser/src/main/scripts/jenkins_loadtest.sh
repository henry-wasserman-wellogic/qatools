#!/bin/sh
#date=`date +%m\-%d\-%Y`

workspace=""
jmx_file=""
hostname=""
number_of_threads=""
#connection_timeout=""
#response_timeout=""
duration_in_seconds=""

set -- $(getopt abf: "$@")
while [ $# -gt 0 ]
do
    case "$1" in
    (-w) workspace="$flist $2"; shift;;
    (-j) jmx_file="$flist $2"; shift;;
    (-h) hostname="$flist $2"; shift;;
    (-n) number_of_threads="$flist $2"; shift;;
    (-d) duration_in_seconds="$flist $2"; shift;;
    (--) shift; break;;
    (-*) echo "$0: error - unrecognized option $1" 1>&2; exit 1;;
    (*)  break;;
    esac
    shift
done

jmeter=/Applications/apache-jmeter-2.11/bin/jmeter.sh
jmx_file_dir=$workspace/jmeter_web_parser/src/main/jmx_files
report_dir=$workspace/jmeter_web_parser/src/main/reports

rm $report_dir/*.xml
rm $report_dir/index.html
rm $report_dir/images/*

echo "$jmeter -n -t $jmx_file_dir/${jmx_file}_${number_of_threads}_users_threaded.jmx -l $report_dir/$jmx_file.xml -Djmeter.save.saveservice.output_format=xml -Jhostname=$hostname -Jnumber_of_threads=$number_of_threads -Jduration_in_seconds=$duration_in_seconds"
$jmeter -n -t $jmx_file_dir/${jmx_file}_${number_of_threads}_users_threaded.jmx -l $report_dir/$jmx_file.xml -Djmeter.save.saveservice.output_format=xml -Jhostname=$hostname -Jnumber_of_threads=$number_of_threads -Jduration_in_seconds=$duration_in_seconds
