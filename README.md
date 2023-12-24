# JISP - A Scheme interpreter written in java
A purely education project to teach me the fundamentals 
of interpreters.

jist is the main data type. It's used to represent programs
and act as a box and pointer pair.

A program is scanned into tokens then  parsed a program made
from a tree of jist structures.

Eval.java then recursively evaluates and applies the program.

To run, compile 
```
javac *.java
```
and run the repl with
```
java repl
```
At the moment very limited syntax is available.
### Special forms
- if
- lambda 
- def 
- println 
### Operators
- \+ 
- \-
- \*
- /
- =

## TODO
- Add strings
- Add support for comparison operators
- Add nil?
- Add and
- Add or
- Add begin
- Add map
