clc;
clear;
close;

x = [1 2 3 4 5 6 7 8 9 10 11]
y = [1.7 2.5 3.8 4.4 4.9 5.2 5.5 5.6 5.8 5.9 6]

n = size(1/x,'c')
sumX = sum(1/x,'c')
sumX2 = sum((1/x)^2,'c')

sumY = sum(y,'c')
sumYX = sum(y.*(1/x),'c')
A = [n sumX; sumX sumX2]
B = [sumY; sumYX]
 
C = A\B

printf("\n| n     sumX  |  | a0 |  | sunY  |")
printf("\n| sumX  sumX2 |  | a1 |  | sumYX |\n\n")
printf("\n| %.4f  %.4f |  | %.4f |  | %.4f |", n, sumX, C(1), sumY)
printf("\n| %.4f  %.4f |  | %.4f |  | %.4f |\n\n", sumX, sumX2, C(2), sumYX)

a = C(2)
b = exp(C(1)/C(2))


printf("\na = %.4f",a)
printf("\nb = %.4f",b)
printf("\n\nResultado = %.4f ln (%.4f x)",a,b) 


function [x]=f(x)
    x = a*log(b*x)
endfunction

disp(f(6.8))

//cima = sum((y - newY')^2,'c')
//baixo = sum((y - mean(y))^2,'c')
//
//R = 1 - (cima / baixo)
//disp(R)
