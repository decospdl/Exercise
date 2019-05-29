clear;
clc
close;
printf("\n")
function r=gauss(a)
    //determina o n de linhas da matriz de coeficientes
    [n h]=size(a)
    for etapa= 1:n-1

        //pivoteamento parcial
        pivot=a(etapa,etapa)
        for i= etapa + 1 :n
            if abs(a(i,etapa))>abs(pivot) then
                linhapivoteamento=i
                pivot=a(i,etapa)
                aux=a(etapa,1:h)
                a(etapa,1:h)=a(linhapivoteamento,1:h)
                a(linhapivoteamento,1:h)=aux
            end
        end
        
        //calculos para escalonamento
        for i = etapa+1:n
            c=a(i,etapa)/pivot
            for j= 1:n+1
                a(i,j)=a(i,j)-c*a(etapa,j)
            end
        end
    end
    
    //realizando a retrossubstituiçao//
    aux=0
    r(1,n)=a(n,n+1)/a(n,n)
    for i= n-1:-1:1
        for k=n :-1:1
            aux= aux +r(1,k) * a(i,k)
        end
        r(1,i)=(a(i,n+1)-aux)/a(i,i)
        aux=0
    end
endfunction

x = [0.1 0.6 0.8]
y = [1.221 3.320 4.953]
p = 0.2

rowSize = size(x,'c')

//Cria a matriz
for i = 1 : rowSize
   M(i,1) = 1
   M(i,2) = x(i)
   M(i,3) = x(i)^2
   M(i,4) = y(i)
end
disp("Matriz para ser aplicado Gauss")
disp(M)

//Usa gauss
a = gauss(M)
polinomio = poly(a,'x','coeff')
disp("O polinomio é: ")
disp(polinomio)

printf("O valor de P(%.2f) é:",p)
resultado = a(1) + a(2)*p + a(3)*p^2 
disp(resultado)



