.PHONY: compile
compile: rtmididevice.cpp
	c++ -O2 -std=c++14 -I/usr/lib/jvm/java-15-openjdk-amd64/include -I/usr/lib/jvm/java-15-openjdk-amd64/include/linux -fPIC `pkg-config --cflags rtmidi` -o librtmididevice.so -shared rtmididevice.cpp `pkg-config --libs rtmidi`
