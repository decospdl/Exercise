clc;
clear
close;

x = [10 20 30 40 50 60 70 80]
y = [25 70 380 550 610 1220 830 1450]

n = size(x, 'c')
sumX = sum(x,'c')
sumX2 = sum(x^2,'c')

sumY = sum(y,'c')
sumXY = sum(x.*y,'c')

A = [n sumX; sumX sumX2]
B = [sumY; sumXY]
C = A\B

disp(A)
disp(B)
disp(C)

P = poly(C,'x','coeff')
disp(P)

R = reglin(x,y)
disp(R)

plot(x,y,'.')

