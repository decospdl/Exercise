% Árvore Geneológica classificada por parentesco.

% Pais e mães
gerou(maria, jose).
gerou(joao, jose).
gerou(joao, ana).
gerou(jose, julia).
gerou(jose, iris).
gerou(iris, jorge).
gerou(ana, sobrinhoJose).

% Sexo
masculino(sobrinhoJose).
masculino(joao).
masculino(jose).
masculino(jorge).
feminino(maria).
feminino(ana).
feminino(julia).
feminino(iris).

% Parentesco
filho(Y, X) :- gerou(X,Y), masculino(Y).
mae(X,Y) :- gerou(X,Y), feminino(X).
avo(X,Z) :- gerou(X,Y), gerou(Y,Z), masculino(X).
irma(X,Y) :- gerou(Z,X), gerou(Z,Y), feminino(X), X \== Y.
% \== significa X≠Y, simbólico
antepassado(X,Z) :- gerou(X,Z).
antepassado(X,Z) :- gerou(X,Y), antepassado(Y,Z).

% Novas implementação
irmao(X,Y) :- gerou(Z,X), gerou(Z,Y), masculino(X), X \== Y.
filha(Y, X) :- gerou(X,Y), feminino(Y).
avoh(X,Z) :- gerou(X,Y),  gerou(Y,Z), feminino(X).
tio(X,Y) :- irmao(X,Z), gerou(Z,Y).
tia(X,Y) :- irma(X,Z), gerou(Z,Y).
sao_primos(X,Y) :- gerou(A,X), gerou(B,Y), (irmao(A,B);irma(A,B)).
