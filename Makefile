JC = javac
JV = java
ifeq ($(OS),Windows_NT)
    UNAME := Windows
else
    UNAME := $(shell uname -s)
endif
ifeq ($(UNAME), Linux)
	DEL = rm
endif
ifeq ($(UNAME), Windows)
	DEL = del
endif
run: 
	$(JV) PFDriver.java

compile:
	$(JC) PFDriver.java

clean:
	$(DEL) *.class

recompile: clean compile

all: clean compile run