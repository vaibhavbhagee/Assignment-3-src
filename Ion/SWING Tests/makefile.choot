all: Start
	# javac *.java
	java Test

Start: *.java Game_Engine/*.java
	make clean
	javac Test.java

clean:
	rm -rf *.class Game_Engine/*.class