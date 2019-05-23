clear
clc
close;

deff('y = f(x)', 'y= -x^2 + 1/4')
a = -0.4
erro = 5e-5
i = 1
z = f(a)
printf('\n Iteração \t a \t\t z');
printf('\n %d \t\t %f \t %f', i, a, z);
while abs(z-a)/2 > erro
    a = z
    z = f(a)
    i = i + 1
    printf('\n %d \t\t %f \t %f', i, a, z);
end
printf('\n\nO valor do x  após %d iteraçãoes = %f',i,z);
