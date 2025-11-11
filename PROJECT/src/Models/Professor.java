package Models;

public class Professor extends Usuario {
    private String siape;
    private String departamento;
    private int limiteEmprestimo = 5;
    public Professor(int id, String nome, String endereco, String status, String siape, String departamento) {
        super(id, nome, endereco, status);
        this.siape = siape;
        this.departamento = departamento;
    }
    public String getSiape() {
        return siape;
    }
    public void setSiape(String siape) {
        this.siape = siape;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }


}
