clc
clear

xs = [0 0.2 0.4 0.5]
ys = [0 2.008 4.064 5.125]
row = size(xs,'c')

function [P] = P(x)
    P = ys(1) * L(1,x) + ys(2) * L(2,x) + ys(3) * L(3,x) + ys(4) * L(4,x)
endfunction


function [L] = L(k,x)
    cima = 1
    baixo = 1
    for i = 1 : row
        if i <> k then
            cima = (x - xs(i)) * cima
            baixo = (xs(k) - xs(i)) * baixo
        end
    end
    printf("L%d = %.4f / %.4f \n", k, cima, baixo)
    L = cima/baixo
endfunction
disp(P(0.3))
