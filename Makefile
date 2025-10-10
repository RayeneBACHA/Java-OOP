SRC = $(wildcard *.java)
CLASSES = $(SRC:.java=.class)
JAVAC = javac
JAVA = java
MAIN = Main


all: $(CLASSES)

%.class: %.java
	$(JAVAC) $<


run: all
	$(JAVA) $(MAIN)



clean:
	rm -f *.class