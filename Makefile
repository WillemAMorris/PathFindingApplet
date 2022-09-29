JC = javac
JV = java

all: clean compile run

run: 
	$(JV) PFDriver.java

compile:
	$(JC) PFDriver.java

clean:
	$(RM) *.class