package Events;

import Models.Usuario;
import Utils.ItemDeAcervo;

import java.io.Serializable;
import java.util.Date;

public class Emprestimo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idEmprestimo;
    private Usuario usuario;
    private ItemDeAcervo item;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private double multaCobrada;

    public Emprestimo(String idEmprestimo, Usuario usuario, ItemDeAcervo item) throws Exception {

        if (item.isEmprestado()) throw new Exception("Item indisponível.");
        if (usuario.getEmprestimosAtivos() >= usuario.getLimiteEmprestimos())
            throw new Exception("Limite de empréstimos excedido.");
        if (usuario.possuiBloqueio())
            throw new Exception("Usuário bloqueado por multa.");

        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.item = item;

        this.dataEmprestimo = new Date();
        this.dataDevolucaoPrevista = usuario.calcularDataDevolucao();
    }

    public double calcularMulta(Date dataReal) {
        long diff = dataReal.getTime() - dataDevolucaoPrevista.getTime();
        long dias = diff / (1000 * 60 * 60 * 24);
        return dias > 0 ? dias * 1.0 : 0;
    }

    public void finalizarEmprestimo(Date dataReal) {
        this.dataDevolucaoReal = dataReal;
        this.multaCobrada = calcularMulta(dataReal);
        usuario.adicionarMulta(multaCobrada);
        item.setEmprestado(false);
        usuario.decrementarEmprestimos();
    }

    public String getIdEmprestimo() { return idEmprestimo; }
    public Usuario getUsuario() { return usuario; }
    public ItemDeAcervo getItem() { return item; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public Date getDataDevolucaoReal() { return dataDevolucaoReal; }
    public double getMultaCobrada() { return multaCobrada; }
}
