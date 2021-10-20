import java.io.*;

public class BCP {
    private Programa p;
    private int pc;
    private int x, y;
    private String estado;
    private String nome;
    private int tempoDeEspera;

    public BCP(String filePath) throws IOException {
        p = new Programa(filePath);
        pc = 0;
        x = 0;
        y = 0;
        tempoDeEspera = 0;
        nome = p.getName();
    }

    protected int executa(){
        return p.proxComando(this);
    }

    protected void incrementa(){
        pc++;
    }

    protected String getNome(){
        return nome;
    }

    protected void setEstado(String estado){
        this.estado = estado;
    }

    protected int getX() {
        return x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public void setTempoDeEspera(int tempoDeEspera) {
        this.tempoDeEspera = tempoDeEspera;
    }

    public int decrementaEspera(){
        return tempoDeEspera--;
    }
}
