clear;
clc;
close;

matriz = input("Digite a matriz: ");
erroAdmitido = input("Digite o erro admitido: ");
aproxAnterior = input("Digite aproximação inicial: ")

linha = size(matriz, "r");
coluna = size(matriz, "c");
diferenca = ones(linha);

while erroAdmitido 
    for i = 1:(linha) 
       diagonal = 1/matriz(i,i);
       meio = 0;
       for j = 1:(coluna -1)
            if j ~= i then
                meio = meio + matriz(i,j)*(-1)*aproxAnterior(j);
            end
       end
       aproxAtual(i) = diagonal * (meio + matriz(i,coluna));
       diferenca(i) = abs(aproxAnterior(i) - aproxAtual(i));
    end
    disp("RodadAproximação Anterior: ");
    disp(aproxAnterior');
    disp("Aproximação Atual: ");
    disp(aproxAtual);
    disp("Diferenca");
    disp(diferenca);
end

function checkErroAdmitido()
endfunction
