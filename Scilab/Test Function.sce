clear
clc
close;

deff('y = f(x)', 'y = -8.12*x^3 + 41.88*x^2 - 71.99*x + 40.23')
x = 0
printf('x \t\t f(x)');
while (x < 1.9)
    printf('\n%f \t %f',x,f(x));
    x= x + 0.1
end

