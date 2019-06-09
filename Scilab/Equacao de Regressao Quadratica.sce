clc;
clear;
close;

x = [-1 -0.75 -0.6 -0.5 -0.3 0 0.2 0.4 0.5 0.7 1]
y = [2.05 1.153 0.45 0.4 0.5 0 0.2 0.6 0.512 1.2 2.05]

n = size(x,'c')
sumX = sum(x,'c')
sumX2 = sum(x^2,'c')
sumX3 = sum(x^3,'c')
sumX4 = sum(x^4,'c')

sumY = sum(y,'c')
sumYX = sum(y.*x,'c')
sumYX2 = sum(y.*x^2,'c')

A = [n sumX sumX2; sumX sumX2 sumX3; sumX2 sumX3 sumX4]
B = [sumY; sumYX; sumYX2]

disp(A)
disp(B)

C = A\B
disp(C)

P = poly(C,'x',"coeff")
disp(P)

plot(x,y,".")


inicio = -1
fim = 1
passo = 0.1


for i = inicio : passo : fim
    xNovo = 
end
