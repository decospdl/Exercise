% Gerar todos os números binários compostos por três dígitos.

% digitos binários
digito(0).
digito(1).

% restrições para solução
binario(N) :- N = (A,B,C), digito(A), digito(B), digito(C).
