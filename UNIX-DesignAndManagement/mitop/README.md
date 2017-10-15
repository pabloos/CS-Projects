# mitop
A simple "top" clone

This little script prints a few data about ten %cpu-using running processes on the CLI.

It starts with a first for-loop to check the CPU impact of all processes. Then, it waits one sec and iterate one more time over the same. Finally it compares all the %CPU and prints it with another data.