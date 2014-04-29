#! /bin/sh
date=04_29_2014
input_xml=$date/bowser.xml
images=$date/images

java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/active_threads_over_time.png --input-jtl $input_xml --plugin-type ThreadsStateOverTime --aggregate-rows yes
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/bytes_throughput_over_time.png --input-jtl $input_xml --plugin-type BytesThroughputOverTime
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/hits_per_second.png --input-jtl $input_xml --plugin-type HitsPerSecond
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/response_codes_per_second.png --input-jtl $input_xml --plugin-type ResponseCodesPerSecond
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/response_times_over_time.png --input-jtl $input_xml --plugin-type ResponseTimesOverTime --aggregate-rows yes
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/response_times_percentiles.png --input-jtl $input_xml --plugin-type ResponseTimesPercentiles --aggregate-rows yes
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/transaction_throughput_vs_threads.png --input-jtl $input_xml --plugin-type ThroughputVsThreads
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/response_times_vs_threads.png --input-jtl $input_xml --plugin-type TimesVsThreads --aggregate-rows yes
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/transactions_per_second.png --input-jtl $input_xml --plugin-type TransactionsPerSecond --aggregate-rows yes
java -jar /Applications/apache-jmeter-2.11/lib/ext/CMDRunner.jar --tool Reporter --generate-png $images/transactions_per_second.png --input-jtl $input_xml --plugin-type TransactionsPerSecond --aggregate-rows yes
