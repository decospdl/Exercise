clear;
clc;
close;

inicio = 1
fim = 5
passos = 1

x = [1 4]
y = [0 1.386294]

deff('y = a(v)', 'y = (y(1) - y(2)) / (x(1) - x(2))')
deff('y = b(v)', 'y = y(1) - a(0) * x(1)')
deff('y = p(v)', 'y = b(0) + a(0)* v')

while inicio <= fim
    printf("\np1(%d) = %f + %f * %d = %f", inicio, b(0), a(0), inicio, p(inicio))
    inicio = inicio + passos    
end
