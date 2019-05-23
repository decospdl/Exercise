soma(X,Y,Z) :- Z is X+Y.
maior(X,Y,X) :-  X>=Y,!.
maior(_,Y,Y).
menor(X,Y,X) :- not(maior(X,Y,X)),!.
menor(_,Y,Y).
par(X) :- X mod 2 =:= 0.
impar(X) :- not(par(X)).
