% Como colorir um mapa usando no máximo 4 cores, de
% modo que regiões adjacentes tenham cores distintas ?

% cores disponível
cor(azul).
cor(verde).
cor(amarelo).
cor(vermelho).

%restrição para a solução
coloracao(A,B,C,D,E) :-
    cor(A), cor(B), cor(C), cor(D), cor(E),
    A\==B, A\==C, A\==D, B\==C, B\==E, C\==D, C\==E, D\==E.
