grammar Expression;

SPACE: ' ' -> skip;

NUMBER: [0-9]+;
PLUS  : '+';
BOPEN : '(';
BCLOSE: ')';

expression
    : num=NUMBER                                #number
    | left=expression PLUS right=expression     #sumNumbers
    | BOPEN expr=expression BCLOSE              #bracedExpression
    ;
