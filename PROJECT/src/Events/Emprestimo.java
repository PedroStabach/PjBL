package Events;
import Models.Usuario;
import Utils.ItemDeAcervo;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

public class Emprestimo {
    private String idEmprestimo;
    private Usuario usuario;
    private ItemDeAcervo item;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private double multaCobrada;

    // 🔹 Construtor completo
    public Emprestimo(String idEmprestimo, Usuario usuario, ItemDeAcervo item,
                      Date dataEmprestimo, Date dataDevolucaoPrevista) {
        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.item = item;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = null;
        this.multaCobrada = 0.0;
    }

    // 🔹 Getters e Setters
    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ItemDeAcervo getItem() {
        return item;
    }

    public void setItem(ItemDeAcervo item) {
        this.item = item;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(Date dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public double getMultaCobrada() {
        return multaCobrada;
    }

    public void setMultaCobrada(double multaCobrada) {
        this.multaCobrada = multaCobrada;
    }

    public double calcularMulta(Date dataDevolucaoReal) {
        long dataEmprestimoMillis = item.getDataEmprestimo().getTime();
        long dataDevolucaoMillis = dataDevolucaoReal.getTime();

        long diffEmMillis = dataDevolucaoMillis - dataEmprestimoMillis;
        long dias = diffEmMillis / (1000 * 60 * 60 * 24); // converte ms → dias

        // valor da multa por dia
        double valorPorDia = 1.2;

        if (dias < 0) dias = 0; // evita multa negativa caso devolvido antes

        return dias * valorPorDia;
    }

    public void finalizarEmprestimo(Date dataDevolucaoReal) {
        calcularMulta(dataDevolucaoReal);
    }

}
