clc;
clear;
close;
deff('y=f(x)','y= 35000 * (x*(1+x)^7/((1+x)^7-1)) - 8500')
a = 0.01
b = 0.3
erro = 5e-4 
i = 0;
printf('\n Iteração \t a \t\t b \t\t z \t\t f(z)');
while abs(a - b) > erro
    z = (a + b)/2;
    if(f(z)*f(a)) > 0
        a = z;
    else
        b = z;
    end
    i = i+1;
    printf('\n %d \t\t %f \t %f \t %f \t %f', i, a, b, z, f(z));
end
printf('\n\nO valor do x  após %d iteraçãoes = %f',i,z);
