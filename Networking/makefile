all: network_main.class Socket_handler.class
	java network_main

network_main.class: network_main.java
	javac network_main.java

Socket_handler.class: Socket_handler.java
	javac Socket_handler.java

clean:
	rm -rf *.class