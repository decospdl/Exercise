% Gerar todos os n�meros bin�rios compostos por tr�s d�gitos.

% digitos bin�rios
digito(0).
digito(1).

% restri��es para solu��o
binario(N) :- N = (A,B,C), digito(A), digito(B), digito(C).
