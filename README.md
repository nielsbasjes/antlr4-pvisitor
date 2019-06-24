# A Parameterized Visitor for ANTLR4 
I have the usecase where I need to pass a parameter along with my visitor.

What I'm trying to do is have a tree stucture with 'patterns' and while walking recursively through 
the parse tree using the visitor match my parse result against the 'pattern tree'.

This parameter is therefor the node in the other tree.

There has been discussion about such a wish before

https://github.com/antlr/antlr4/issues/641

What this project does is simply change only the files that need to be changed and change the code generation accordingly.

Current status: Seems to work on my machine, it will most likely melt yours.