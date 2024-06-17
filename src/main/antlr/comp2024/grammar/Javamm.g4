grammar Javamm;

@header {
    package pt.up.fe.comp2024;
}

EQUALS : '=';
SEMI : ';' ;
LCURLY : '{' ;
RCURLY : '}' ;
LPAREN : '(' ;
RPAREN : ')' ;
LBRACKET : '[' ;
RBRACKET : ']' ;

MUL : '*' ;
ADD : '+' ;
SUB : '-' ;
DIV : '/' ;
AND : '&&' ;
LESS : '<' ;

VARARGS : '...' ;
CLASS : 'class' ;
INT : 'int' ;
STRING : 'String' ;
BOOLEAN : 'boolean' ;
PUBLIC : 'public' ;
IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;
TRUE : 'true' ;
FALSE : 'false' ;
THIS : 'this' ;
RETURN : 'return' ;
NOT : '!' ;
DOT : '.' ;
LENGTH : 'length' ;
COMMA : ',' ;
NEW : 'new' ;
IMPORT : 'import' ;
EXTENDS : 'extends' ;
STATIC : 'static' ;
VOID : 'void' ;
MAIN : 'main' ;

COMMENT : '//' ~[\r\n]* -> skip ;
MULTILINE_COMMENT : '/*' .*? '*/' -> skip ;

INTEGER : '0' | [1-9] [0-9]* ;
ID : [a-zA-Z$_] [a-zA-Z$_0-9]* ;

WS : [ \t\n\r\f]+ -> skip ;

program
    : (importDecl)* classDecl EOF
    ;

importDecl
    : IMPORT imports+=ID ( DOT imports+=ID )* SEMI
    ;

classDecl
    : CLASS name=ID (EXTENDS parent=ID)?
        LCURLY
        varDecl* // variaveis que sao passadas antes das func numa class
        methodDecl*
        RCURLY
    ;

varDecl
    : type name=(ID|MAIN|LENGTH|STRING) SEMI
    ;

type locals[boolean isArray=false]
    : (name= INT LBRACKET RBRACKET {$isArray = true;}) // EX: int[] b;
    | name= INT vararg=VARARGS {$isArray = true;} // EX: int... b;
    | name= INT
    | name= BOOLEAN
    | name= STRING
    | name = ID // EX: Bar e;
    ;


methodDecl locals[boolean isPublic=false, boolean isMain=false]
    : (PUBLIC {$isPublic=true;})?
        type name=ID
        LPAREN  (param (COMMA param)*)? RPAREN
        LCURLY varDecl* stmt* RCURLY
    | (PUBLIC {$isPublic=true;})?
        STATIC v=VOID name=MAIN {$isMain=true;} LPAREN STRING LBRACKET RBRACKET args=ID RPAREN
        LCURLY varDecl* stmt* RCURLY
    ;

param
    : type name=ID
    ;

stmt
    : expr EQUALS expr SEMI #AssignStmt
    | IF LPAREN expr RPAREN stmt (ELSE stmt) #IfElseStmt
    | WHILE LPAREN expr RPAREN stmt #WhileStmt
    | RETURN expr SEMI #ReturnStmt
    | LCURLY RCURLY #EmptyCurly
    | LCURLY (stmt)* RCURLY #CurlyStmt
    | expr SEMI #ExprSemi
    ;

expr
    : LPAREN expr RPAREN #ParentExpr //
    | expr DOT length=LENGTH #LengthCallExpr
    // | expr DOT lenght=ID #LengthCallExpr
    | expr (DOT name=ID LPAREN (expr (COMMA expr)*)? RPAREN) #ExprMemberCall
    //| name=ID LPAREN (expr (COMMA expr)*)? RPAREN #MethodCall
    | NOT expr #NotExpr //
    | expr op= (MUL | DIV) expr #BinaryExpr //
    | expr op= (ADD | SUB) expr #BinaryExpr //
    | expr op= LESS expr #ConditionalExpr //
    | expr op= AND expr #AndExpr //
    | LBRACKET (expr (COMMA expr)*)? RBRACKET  #ArrayExpr
    | expr LBRACKET expr RBRACKET #ArrayAccessExpr
    | NEW name=ID LPAREN RPAREN #NewClass
    | NEW INT LBRACKET expr RBRACKET #ExprNewArray
    | FALSE #FalseExpr//
    | TRUE #TrueExpr//
    | THIS #ThisExpr //
    | value=INTEGER #IntegerLiteral //
    | value=BOOLEAN #BooleanLiteral //
    | name=ID #VarRefExpr //
    ;



