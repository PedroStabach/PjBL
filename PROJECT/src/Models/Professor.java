package Models;

import java.util.Date;
import java.util.Calendar;
public class Professor extends Usuario {
    private String siape;
    private String departamento;
    public Professor(String id, String nome, String endereco, String siape, String departamento) {
        super(id, nome, endereco, 5);
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

    @Override
    public Date calculaPrazoDevolucao() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7); // alunos têm 7 dias de prazo
        return cal.getTime();
        }
}
