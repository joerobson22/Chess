.PHONY: all exec build buildpackage clean doc

all: exec

exec: build
	java Main

build: buildpackage Main.class GameManager.class InputManager.class Square.class Board.class Piece.class

buildpackage: Ball.class Line.class Rectangle.class Text.class GameArena.class

%.class: %.java
	javac $<

clean:
	rm *.class

doc:
	javadoc -d doc *.java
