package Models;

import java.io.Serializable;
import java.util.Date;

public class Aluno extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String matricula;
    private String curso;

    public Aluno(String id, String nome, String endereco, String matricula, String curso) {
        super(id, nome, endereco);
        this.matricula = matricula;
        this.curso = curso;
    }

    @Override
    public Date calcularDataDevolucao() {
        return new Date(new Date().getTime() + 7L * 24 * 60 * 60 * 1000);
    }

    @Override
    public int getLimiteEmprestimos() {
        return 3;
    }

    public String getMatricula() { return matricula; }
    public String getCurso() { return curso; }
}
