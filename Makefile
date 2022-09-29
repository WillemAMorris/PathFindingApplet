JC = javac
JV = java

run: 
	$(JV) PFDriver.java

compile:
	$(JC) PFDriver.java

clean:
	$(RM) *.class

recompile: clean compile

all: clean compile run