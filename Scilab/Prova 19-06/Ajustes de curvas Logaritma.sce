clc;
clear;
close;

x = [1 2 3 4 5 6 7 8 9 10 11]
y = [1.7 2.5 3.8 4.4 4.9 5.2 5.5 5.6 5.8 5.9 6]

n = size(x,'c')
sumX = sum(x,'c')
sumX2 = sum(x^2,'c')

sumY = sum(log(y),'c')
sumYX = sum(log(y).*x,'c')
A = [n sumX; sumX sumX2]
B = [sumY; sumYX]

disp(A)
disp(B)

C = A\B

disp(C)

a = exp(C(1))
b = C(2)

printf("\nValor de a = %.2f\t Valor de b = %.4f\n",a,b)


function [x]=f(x)
    x = a*exp(b*x)
endfunction

poli = poly(0,'x','coeff')

printf("Resultado: %.3f*exp(%.3f * x)",a,b) 


//cima = sum((y - newY')^2,'c')
//baixo = sum((y - mean(y))^2,'c')
//
//R = 1 - (cima / baixo)
//disp(R)
