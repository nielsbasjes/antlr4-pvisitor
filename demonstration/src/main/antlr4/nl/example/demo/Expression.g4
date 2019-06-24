grammar Expression;

SPACE: ' ' -> skip;

NUMBER: [0-9]+;
PLUS  : '+';
BOPEN : '(';
BCLOSE: ')';

expression
    : num=NUMBER                     #number
    | num1=expression PLUS num2=expression   #sumNumbers
    | BOPEN expression BCLOSE        #bracedExpression
    ;
