#!/bin/bash

#get processes info from the /proc folder BEFORE the one sec pause
for pid in $(ls /proc | awk "/^[0-9]/")
do
	directory="/proc/$pid"
	if [ -d $directory ]; then
		time_before[$pid]=$(awk '{print $14+$15}' RS= /proc/$pid/stat) 
		cpu1[$pid]=$(awk '{print $2+$3+$4+$5+$6+$7+$8+$9+$10}' RS= /proc/stat)
	fi
	
	num_processes=$(($num_processes + 1))
done

sleep 1

#get processes info from the /proc folder AFTER the one sec pause
for pid in $(ls /proc | awk '/^[0-9]/')
do
	directory="/proc/$pid"
	if [ -d $directory ] && [ ${time_before[$pid]} ] ; then
		time_after[$pid]=$(awk '{print $14+$15}' RS= /proc/$pid/stat)
		cpu2[$pid]=$(awk '{print $2+$3+$4+$5+$6+$7+$8+$9+$10}' RS= /proc/stat)
		
		cycles[$pid]=$(echo ${time_after[$pid]} ${time_before[$pid]} ${cpu2[$pid]} ${cpu1[$pid]} | awk '{printf "%2.3f", ((($1 - $2) * 100) / ($3 - $4)) }')
	
		user[$pid]=$(awk '/Uid/ {print $2}' /proc/$pid/status)					#user
		printf "%s\t%s\t%s\t" ${cycles[$pid]} $pid ${user[$pid]}	
		cmd[$pid]=$(awk '{printf "%s\t%s\t%s\t%sMB\t",$18,$3,$22,$23/1000000}' /proc/$pid/stat)	#priority, status, time, virtual memory
		printf "%s\t" ${cmd[$pid]}
		memory[$pid]=$(awk '/VmSize/ {print (($2 * 100) / 512)}' /proc/$pid/status)		#memory
		name[$pid]=$(awk '{printf "%s", $2}' /proc/$pid/stat) 					#name
		printf "%s\t%18s\n" ${memory[$pid]} ${name[$pid]} 
	fi	
done | sort -brn > data

cpu_usage=$(awk '{sum+=$1} END {print sum}' ./data)
memory_total=$(awk '/MemTotal/ {print $2/1000}' /proc/meminfo)
memory_free=$(awk '/MemFree/ {print $2/1000}' /proc/meminfo)
memory_used=$(echo "$memory_total $memory_free" | awk '{print $1-$2}')

printf "MiTop Total CPU:%s\tProcesses:%s\t  TotalMem:%s\tUsed:%s\tFree:%s\n" $cpu_usage $num_processes $memory_total $memory_used $memory_free
echo --------------------------------------------------------------------------------------------
echo --------------------------------------------------------------------------------------------
printf "CPU\tPID\tUSER\tPR\tSTATUS\tTIME\tVIRTMEM\t\tMEMORY\t\tNAME\n"
echo --------------------------------------------------------------------------------------------
head -n 10 ./data	#displays the first ten lines from the data archive
echo --------------------------------------------------------------------------------------------

#sleep 5