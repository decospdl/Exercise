arco(a,b,3).
arco(a,c,4).
arco(a,d,5).
arco(b,d,2).
arco(c,d,4).
arco(c,f,5).
arco(d,e,2).
arco(e,f,2).

custo(U,V,L) :- arco(U,V,L),!.
custo(U,V,L) :- arco(V,U,L),!.

custo(U,V,L) :- arco(U,X,L1), C =(U - X), write(C), nl, custo(X,V,L2), L is L1+L2,!.
custo(U,V,L) :- custo(V,U,L).
