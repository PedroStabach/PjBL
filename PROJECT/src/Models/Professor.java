package Models;

import java.io.Serializable;
import java.util.Date;

public class Professor extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String siape;
    private String departamento;

    public Professor(String id, String nome, String endereco, String siape, String departamento) {
        super(id, nome, endereco);
        this.siape = siape;
        this.departamento = departamento;
    }

    @Override
    public Date calcularDataDevolucao() {
        return new Date(new Date().getTime() + 15L * 24 * 60 * 60 * 1000);
    }

    @Override
    public int getLimiteEmprestimos() {
        return 5;
    }
}
