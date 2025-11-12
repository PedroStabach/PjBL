package Models;

import java.util.Calendar;
import java.util.Date;

public class Aluno extends Usuario {
    private String matricula;
    private String curso;
    public Aluno (String id, String nome, String endereco, String matricula, String curso) {
        super(id, nome, endereco, 3);
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
    public Date calculaPrazoDevolucao() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7); // alunos têm 7 dias de prazo
        return cal.getTime();
    }
}

