# A Parameterized Visitor for ANTLR4 
I have the usecase where I need to pass a parameter along with my visitor.

What I'm trying to do is have a tree stucture with 'patterns' and while walking recursively through 
the parse tree using the visitor match my parse result against the 'pattern tree'.

This parameter is therefor the node in the other tree.

There has been discussion about such a wish before

https://github.com/antlr/antlr4/issues/641

What this project does is simply change only the files that need to be changed and change the code generation accordingly.

Current status: Seems to work on my machine, it will most likely melt yours.

# LICENSE

This project is essentially a relatively simple (but not backward compatible!) change of the existing Antlr 4.7.2 code.

All of that code is (which is really 99% of what you find here) 

    Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
    Use of this file is governed by the BSD 3-clause license that
    can be found in the LICENSE.txt file in the project root.

The mentioned LICENSE.txt can be found here: https://github.com/antlr/antlr4/blob/master/LICENSE.txt

This modified project is 

    Copyright (C) 2019 Niels Basjes

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

Note that I allow the Antlr4 project to pull any part of this into the original Antlr4 and 
relicense it under their BSD 3-clause license as a contribution in that project.
