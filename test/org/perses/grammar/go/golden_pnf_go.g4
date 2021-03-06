// Auto-generated by Perses AST.
// DO NOT MODIFY.
parser grammar PnfGoParser;

options {
    tokenVocab=GoLexer;
    superClass=GoParserBase;
}
sourceFile
    : EOF
    | packageClause eos kleene_star__sourceFile_2 kleene_star__sourceFile_4
    ;

sourceFile_1
    : importDecl eos
    ;

kleene_star__sourceFile_2
    : sourceFile_1*
    ;

sourceFile_3
    : (functionDecl | methodDecl | declaration) eos
    ;

kleene_star__sourceFile_4
    : sourceFile_3*
    ;

packageClause
    : 'package' IDENTIFIER
    ;

eos
    : ';'
    | EOF
    | {$start.getType() != SEMI && checkPreviousTokenText("}")}?
    | {$start.getType() != SEMI && checkPreviousTokenText(")")}?
    ;

importDecl
    : 'import' (importSpec | '(' kleene_star__importDecl_2 ')')
    ;

importDecl_1
    : importSpec eos
    ;

kleene_star__importDecl_2
    : importDecl_1*
    ;

functionDecl
    : 'func' IDENTIFIER signature optional__functionDecl_1
    ;

optional__functionDecl_1
    : block?
    ;

methodDecl
    : 'func' receiver IDENTIFIER signature optional__functionDecl_1
    ;

declaration
    : constDecl
    | typeDecl
    | varDecl
    ;

importSpec
    : optional__importSpec_2 importPath
    ;

importSpec_1
    : '.'
    | IDENTIFIER
    ;

optional__importSpec_2
    : importSpec_1?
    ;

importPath
    : string_
    ;

string_
    : RAW_STRING_LIT
    | INTERPRETED_STRING_LIT
    ;

constDecl
    : 'const' (constSpec | '(' kleene_star__constDecl_2 ')')
    ;

constDecl_1
    : constSpec eos
    ;

kleene_star__constDecl_2
    : constDecl_1*
    ;

typeDecl
    : 'type' (typeSpec | '(' kleene_star__typeDecl_2 ')')
    ;

typeDecl_1
    : typeSpec eos
    ;

kleene_star__typeDecl_2
    : typeDecl_1*
    ;

varDecl
    : 'var' (varSpec | '(' kleene_star__varDecl_2 ')')
    ;

varDecl_1
    : varSpec eos
    ;

kleene_star__varDecl_2
    : varDecl_1*
    ;

constSpec
    : identifierList optional__constSpec_3
    ;

optional__constSpec_1
    : type_?
    ;

constSpec_2
    : optional__constSpec_1 '=' expressionList
    ;

optional__constSpec_3
    : constSpec_2?
    ;

identifierList
    : IDENTIFIER kleene_star__identifierList_2
    ;

identifierList_1
    : ',' IDENTIFIER
    ;

kleene_star__identifierList_2
    : identifierList_1*
    ;

type_
    : typeName
    | arrayType
    | structType
    | pointerType
    | functionType
    | interfaceType
    | sliceType
    | mapType
    | channelType
    | '(' type_ ')'
    ;

expressionList
    : expression kleene_star__expressionList_2
    ;

expressionList_1
    : ',' expression
    ;

kleene_star__expressionList_2
    : expressionList_1*
    ;

expression
    : unaryExpr kleene_star__expression_1
    ;

kleene_star__expression_1
    : expression_2*
    ;

expression_2
    : alternative__expression_6 expression
    ;

alternative__expression_6
    : '*'
    | '/'
    | '%'
    | '<<'
    | '>>'
    | '&'
    | '&^'
    | '+'
    | '-'
    | '|'
    | '^'
    | '=='
    | '!='
    | '<'
    | '<='
    | '>'
    | '>='
    | '&&'
    | '||'
    ;

typeSpec
    : IDENTIFIER optional__typeSpec_1 type_
    ;

optional__typeSpec_1
    : ASSIGN?
    ;

signature
    : parameters optional__signature_1
    ;

optional__signature_1
    : result?
    ;

block
    : '{' optional__block_1 '}'
    ;

optional__block_1
    : statementList?
    ;

receiver
    : parameters
    ;

parameters
    : '(' optional__parameters_5 ')'
    ;

parameters_1
    : COMMA parameterDecl
    ;

kleene_star__parameters_2
    : parameters_1*
    ;

optional__parameters_3
    : COMMA?
    ;

parameters_4
    : parameterDecl kleene_star__parameters_2 optional__parameters_3
    ;

optional__parameters_5
    : parameters_4?
    ;

varSpec
    : identifierList (type_ optional__varSpec_2 | '=' expressionList)
    ;

varSpec_1
    : '=' expressionList
    ;

optional__varSpec_2
    : varSpec_1?
    ;

statementList
    : kleene_plus__statementList_2
    ;

statementList_1
    : ';'
    | realStatement eos
    ;

kleene_plus__statementList_2
    : statementList_1+
    ;

realStatement
    : declaration
    | labeledStmt
    | realSimpleStmt
    | goStmt
    | returnStmt
    | breakStmt
    | continueStmt
    | gotoStmt
    | fallthroughStmt
    | block
    | ifStmt
    | exprSwitchStmt
    | typeSwitchStmt
    | selectStmt
    | forStmt
    | deferStmt
    ;

statement
    : optional__statement_1
    ;

optional__statement_1
    : realStatement?
    ;

simpleStmt
    : optional__simpleStmt_1
    ;

optional__simpleStmt_1
    : realSimpleStmt?
    ;

realSimpleStmt
    : sendStmt
    | assignment
    | expressionStmt
    | incDecStmt
    | shortVarDecl
    ;

labeledStmt
    : IDENTIFIER ':' statement
    ;

goStmt
    : 'go' expression
    ;

returnStmt
    : 'return' optional__returnStmt_1
    ;

optional__returnStmt_1
    : expressionList?
    ;

breakStmt
    : 'break' optional__breakStmt_1
    ;

optional__breakStmt_1
    : IDENTIFIER?
    ;

continueStmt
    : 'continue' optional__breakStmt_1
    ;

gotoStmt
    : 'goto' IDENTIFIER
    ;

fallthroughStmt
    : 'fallthrough'
    ;

ifStmt
    : 'if' optional__ifStmt_2 expression block optional__ifStmt_4
    ;

ifStmt_1
    : simpleStmt ';'
    ;

optional__ifStmt_2
    : ifStmt_1?
    ;

ifStmt_3
    : 'else' (ifStmt | block)
    ;

optional__ifStmt_4
    : ifStmt_3?
    ;

selectStmt
    : 'select' '{' kleene_star__selectStmt_1 '}'
    ;

kleene_star__selectStmt_1
    : commClause*
    ;

forStmt
    : 'for' optional__forStmt_2 block
    ;

forStmt_1
    : expression
    | forClause
    | rangeClause
    ;

optional__forStmt_2
    : forStmt_1?
    ;

deferStmt
    : 'defer' expression
    ;

sendStmt
    : expression '<-' expression
    ;

assignment
    : expressionList assign_op expressionList
    ;

expressionStmt
    : expression
    ;

incDecStmt
    : expression (PLUS_PLUS | MINUS_MINUS)
    ;

shortVarDecl
    : identifierList ':=' expressionList
    ;

assign_op
    : optional__assign_op_2 '='
    ;

assign_op_1
    : '+'
    | '-'
    | '|'
    | '^'
    | '*'
    | '/'
    | '%'
    | '<<'
    | '>>'
    | '&'
    | '&^'
    ;

optional__assign_op_2
    : assign_op_1?
    ;

exprSwitchStmt
    : 'switch' optional__ifStmt_2 optional__exprSwitchStmt_3 '{' kleene_star__exprSwitchStmt_4 '}'
    ;

optional__exprSwitchStmt_3
    : expression?
    ;

kleene_star__exprSwitchStmt_4
    : exprCaseClause*
    ;

typeSwitchStmt
    : 'switch' optional__ifStmt_2 typeSwitchGuard '{' kleene_star__typeSwitchStmt_3 '}'
    ;

kleene_star__typeSwitchStmt_3
    : typeCaseClause*
    ;

exprCaseClause
    : exprSwitchCase ':' optional__block_1
    ;

exprSwitchCase
    : 'default'
    | 'case' expressionList
    ;

typeSwitchGuard
    : optional__typeSwitchGuard_2 primaryExpr '.' '(' 'type' ')'
    ;

typeSwitchGuard_1
    : IDENTIFIER ':='
    ;

optional__typeSwitchGuard_2
    : typeSwitchGuard_1?
    ;

typeCaseClause
    : typeSwitchCase ':' optional__block_1
    ;

primaryExpr
    : primaryExpr_3 kleene_star__primaryExpr_1
    ;

kleene_star__primaryExpr_1
    : primaryExpr_2*
    ;

primaryExpr_2
    : index
    | slice
    | typeAssertion
    | arguments
    | DOT IDENTIFIER
    ;

primaryExpr_3
    : NIL_LIT
    | DECIMAL_LIT
    | OCTAL_LIT
    | HEX_LIT
    | IMAGINARY_LIT
    | RUNE_LIT
    | string_
    | FLOAT_LIT
    | compositeLit
    | functionLit
    | methodExpr
    | conversion
    | typeName
    | '(' expression ')'
    ;

typeSwitchCase
    : 'default'
    | 'case' typeList
    ;

typeList
    : (type_ | NIL_LIT) kleene_star__typeList_2
    ;

typeList_1
    : ',' (type_ | NIL_LIT)
    ;

kleene_star__typeList_2
    : typeList_1*
    ;

commClause
    : commCase ':' optional__block_1
    ;

commCase
    : 'default'
    | 'case' (sendStmt | recvStmt)
    ;

recvStmt
    : optional__recvStmt_2 expression
    ;

recvStmt_1
    : expressionList '='
    | identifierList ':='
    ;

optional__recvStmt_2
    : recvStmt_1?
    ;

forClause
    : optional__forClause_1 ';' optional__exprSwitchStmt_3 ';' optional__forClause_1
    ;

optional__forClause_1
    : simpleStmt?
    ;

rangeClause
    : optional__recvStmt_2 'range' expression
    ;

typeName
    : IDENTIFIER
    | qualifiedIdent
    ;

qualifiedIdent
    : IDENTIFIER '.' IDENTIFIER
    ;

arrayType
    : '[' expressionStmt ']' elementType
    ;

structType
    : 'struct' '{' kleene_star__structType_2 '}'
    ;

structType_1
    : fieldDecl eos
    ;

kleene_star__structType_2
    : structType_1*
    ;

pointerType
    : '*' type_
    ;

functionType
    : 'func' signature
    ;

interfaceType
    : 'interface' '{' kleene_star__interfaceType_2 '}'
    ;

interfaceType_1
    : methodSpec eos
    ;

kleene_star__interfaceType_2
    : interfaceType_1*
    ;

sliceType
    : '[' ']' elementType
    ;

mapType
    : 'map' '[' type_ ']' elementType
    ;

channelType
    : ('chan' | 'chan' '<-' | '<-' 'chan') elementType
    ;

elementType
    : type_
    ;

methodSpec
    : typeName
    | IDENTIFIER parameters optional__signature_1
    ;

result
    : parameters
    | type_
    ;

parameterDecl
    : optional__parameterDecl_1 optional__parameterDecl_2 type_
    ;

optional__parameterDecl_1
    : identifierList?
    ;

optional__parameterDecl_2
    : '...'?
    ;

unaryExpr
    : primaryExpr
    | ('+' | '-' | '!' | '^' | '*' | '&' | '<-') unaryExpr
    ;

conversion
    : type_ '(' expression optional__conversion_1 ')'
    ;

optional__conversion_1
    : ','?
    ;

index
    : '[' expression ']'
    ;

slice
    : '[' (optional__exprSwitchStmt_3 ':' optional__exprSwitchStmt_3 | optional__exprSwitchStmt_3 ':' expression ':' expression) ']'
    ;

typeAssertion
    : '.' '(' type_ ')'
    ;

arguments
    : '(' optional__arguments_6 ')'
    ;

arguments_1
    : ',' expressionList
    ;

optional__arguments_2
    : arguments_1?
    ;

arguments_5
    : (expressionList | type_ optional__arguments_2) optional__parameterDecl_2 optional__conversion_1
    ;

optional__arguments_6
    : arguments_5?
    ;

methodExpr
    : elementType DOT IDENTIFIER
    ;

compositeLit
    : literalType literalValue
    ;

functionLit
    : 'func' signature block
    ;

literalType
    : structType
    | arrayType
    | sliceType
    | mapType
    | typeName
    | '[' '...' ']' elementType
    ;

literalValue
    : '{' optional__literalValue_3 '}'
    ;

literalValue_2
    : elementList optional__conversion_1
    ;

optional__literalValue_3
    : literalValue_2?
    ;

elementList
    : keyedElement kleene_star__elementList_2
    ;

elementList_1
    : ',' keyedElement
    ;

kleene_star__elementList_2
    : elementList_1*
    ;

keyedElement
    : optional__keyedElement_2 element
    ;

keyedElement_1
    : key ':'
    ;

optional__keyedElement_2
    : keyedElement_1?
    ;

key
    : IDENTIFIER
    | expression
    | literalValue
    ;

element
    : expression
    | literalValue
    ;

fieldDecl
    : (identifierList type_ | anonymousField) optional__fieldDecl_1
    ;

optional__fieldDecl_1
    : string_?
    ;

anonymousField
    : optional__anonymousField_1 typeName
    ;

optional__anonymousField_1
    : '*'?
    ;

