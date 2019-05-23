aluno(joao, paradigmas).
aluno(maria, paradigmas).
aluno(joel, lab2).
aluno(joel, estruturas).
frequenta(joao, feup).
frequenta(maria, feup).
frequenta(joel, ist).
professor(carlos, paradigmas).
professor(ana_paula, estruturas).
professor(pedro, lab2).
funcionario(pedro, ist).
funcionario(ana_paula, feup).
funcionario(carlos, feup).

ehprofessor(X,Y) :- aluno(Y,Z), professor(X,Z).
frequentaUniversidade(X,Y) :- frequenta(X,Y); funcionario(X,Y).
colega(X,Y) :- (aluno(X,Z), aluno(Y,Z));(frequenta(X,W), frequenta(Y,W)).
colega(X,Y) :- (aluno(X,Z), aluno(Y,Z));(frequenta(X,W), frequenta(Y,W)).

