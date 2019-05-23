clear
clc
close;

deff('y = f(x)', 'y=1+x+exp(x)')
a = -2
b = -1
erro = 5e-5
i = 1

printf('\n Iteração \t a \t\t b \t\t z');
while abs(b-a)/2 > erro
    z = (a*f(b)-b*f(a))/(f(b)-f(a));
    printf('\n %d \t\t %f \t %f \t %f', i, a, b, z);
    a = b;
    b = z;
    i = i + 1
end
printf('\n\nO valor do x  após %d iteraçãoes = %f',i-1,z);

