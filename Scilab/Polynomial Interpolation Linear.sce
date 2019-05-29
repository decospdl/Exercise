clear;
clc;
close;

inicio = 0.1
fim = 0.6
passos = 0.1

x = [0.1 0.6]
y = [1.221 3.320]

deff('y = a(v)', 'y = (y(1) - y(2)) / (x(1) - x(2))')
deff('y = b(v)', 'y = y(1) - a(0) * x(1)')
deff('y = p(v)', 'y = b(0) + a(0)* v')

while inicio <= fim
    printf("\np1(%d) = %.4f + %.4f * %d = %.4f", inicio, b(0), a(0), inicio, p(inicio))
    inicio = inicio + passos    
end
