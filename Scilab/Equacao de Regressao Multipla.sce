clc;
clear;
close;

x1 = [-1 0 1 2 4 5 5 6]
x2 = [-2 -1 0 1 1 2 3 4]
y = [13 11 9 4 11 9 1 -1]

n = size(x1,'c')
sumX1 = sum(x1,'c')
sumX2 = sum(x2,'c')
sumX1X2 = sum(x1.*x2,'c')
sumX12 = sum(x1^2,'c')
sumX22 = sum(x2^2,'c')

sumY = sum(y,'c')
sumYX1 = sum(y.*x1,'c')
sumYX2 = sum(y.*x2,'c')

A = [n sumX1 sumX2; sumX1 sumX12 sumX1X2; sumX2 sumX1X2, sumX22]
B = [sumY; sumYX1; sumYX2]

disp(A)
disp(B)

C = A\B
disp(C)

P = poly(C,'x','coeff')
disp(P)

//inicio = -2
//fim = 6
//passo = 0.1
//index = 1
//
//for i = inicio : passo : fim
//    xNovo(index) = i
//    yNovo(index) = C(1)+C(2)*i+C(3)*i^2
//    index = index + 1
//end

for i = 1 : n
    newY(i) = C(1)+C(2)*x1(i)+C(3)*x2(i)
end

cima = sum((y - newY')^2,'c')
baixo = sum((y - mean(y))^2,'c')

R = 1 - (cima / baixo)
disp(R)

