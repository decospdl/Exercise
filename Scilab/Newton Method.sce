clear
clc

function [result] = ordem_1()
    tam = size(y,'c')
    for i = 1 : tam-1
       result(i) = (y(i+1) - y(i))/ (x(i+1) - x(i))
    end
endfunction

function [result] = ordem_2()
    [y] = ordem_1()
    tam = size(y,'r')
    for i = 1 : tam-1
       result(i) = (y(i+1) - y(i))/ (x(i+2) - x(i))
    end
endfunction

function [result] = ordem_3()
    [y] = ordem_2()
    tam = size(y,'r')
    for i = 1 : tam-1
       result(i) = (y(i+1) - y(i))/ (x(i+3) - x(i))
    end
endfunction

function [result] = ordem_4()
    [y] = ordem_3()
    tam = size(y,'r')
    for i = 1 : tam-1
       result(i) = (y(i+1) - y(i))/ (x(i+4) - x(i))
    end
endfunction

function [result] = ordem_5()
    [y] = ordem_4()
    tam = size(y,'r')
    for i = 1 : tam-1
       result(i) = (y(i+1) - y(i))/ (x(i+5) - x(i))
    end
endfunction

x = [1.65 1.82 2.01 2.23 2.46 2.72]
y = [0.5 0.6 0.7 0.8 0.9 1.0]

disp("Ordem 1:")
disp(ordem_1())
disp("Ordem 2:")
disp(ordem_2())
disp("Ordem 3:")
disp(ordem_3())
disp("Ordem 4:")
disp(ordem_4())
disp("Ordem 5:")
disp(ordem_5())


