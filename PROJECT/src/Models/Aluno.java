package Models;

public class Aluno extends Usuario {
    private String matricula;
    private String curso;
    private int limiteEmprestimo = 3;;
    public Aluno (int id, String nome, String endereco, String status, String matricula, String curso) {
        super(id, nome, endereco, status);
        this.matricula = matricula;
        this.curso = curso;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }
    public void setLimiteEmprestimo(int limiteEmprestimo) {
        this.limiteEmprestimo = limiteEmprestimo;
    }
}
