clear
clc
close;

deff('y = f(x)', 'y = %pi/3*x^2*(9-x)-30')
deff('y = d(x)', 'y = %pi*x*(-x+6)')
erro = 10e-5
x0 = 1
x1 = x0 - (f(x0)/d(x0))
x2 = x1 - x0

i = 0
printf('\n Iteração \t x0 \t\t x1');
while abs(x1 - x0) > erro
    x0 = x1
    x1 = x0 - (f(x0)/d(x0))
    x2 = x1 - x0
    i = i + 1
    printf('\n %d \t\t %f \t %f', i, x0, x1);
end
printf('\n\nO valor do x  após %d iterações = %f',i,x1);
