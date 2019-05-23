clear
clc
close;

deff('y=f(x)','y = -8.12*x^3 + 41.88*x^2 - 71.99*x + 40.23')
a = 1.2
b = 1.3
erro = 10e-7
i = 1
printf('\n Iteração \t a \t\t b \t\t z \t\t f(z)');
while abs(a - b) > erro
    z = (a * f(b)- b *f(a)) / (f(b) - f(a))
    printf('\n %d \t\t %f \t %f \t %f \t %f', i, a, b, z, f(z));
    if(f(a)* f(z) > 0) then
        a = z
     else
        b = z
     end
     i = i + 1
end
printf('\n\nO valor do x  após %d iteraçãoes = %f',i-1,z);
