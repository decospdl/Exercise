% Como colorir um mapa usando no m�ximo 4 cores, de
% modo que regi�es adjacentes tenham cores distintas ?

% cores dispon�vel
cor(azul).
cor(verde).
cor(amarelo).
cor(vermelho).

%restri��o para a solu��o
coloracao(A,B,C,D,E) :-
    cor(A), cor(B), cor(C), cor(D), cor(E),
    A\==B, A\==C, A\==D, B\==C, B\==E, C\==D, C\==E, D\==E.
