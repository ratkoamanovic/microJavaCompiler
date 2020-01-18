package snippet;

public class Snippet {
	AbstractClassDecl ::= (ClassDeclExtentsMethod) ABSTRACT CLASS IDENT EXTENDS Type LBRACE VarDeclList LBRACE AbstractAndBasicMethodDeclList RBRACE RBRACE
							|
						  (ClassDeclMethod) ABSTRACT CLASS IDENT LBRACE VarDeclList LBRACE AbstractAndBasicMethodDeclList RBRACE RBRACE
						  	|
						  (ClassDeclExtents) ABSTRACT CLASS IDENT EXTENDS Type LBRACE VarDeclList RBRACE
							|
						  (ClassDeclEmpty) ABSTRACT CLASS IDENT LBRACE VarDeclList RBRACE
						  ; 
	
	AbstractAndBasicMethodDeclList ::= (AbstractAndBasicMethodBasic) AbstractAndBasicMethodDeclList MethodDecl
										|
									   (AbstractAndBasicMethodAbstract) AbstractAndBasicMethodDeclList AbstractMethodDecl
									    |
									   (NoAbstractAndBasicMethodDecl)
									   ;
}

