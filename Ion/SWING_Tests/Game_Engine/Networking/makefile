all: network_main.class socket_handler.class
	java network_main

network_main.class: network_main.java
	javac network_main.java

socket_handler.class: socket_handler.java
	javac socket_handler.java

clean:
	rm -rf *.class