import java.io.*;

public class Processo {
    Programa p;
    private int pc, quantum;
    private int x, y;
    private int estado; //0 = pronto, 1 = executando, 2 = interrompido, 3 = encerrado, caso precise -1 para problemas
    String nome;
    File logFile;
    FileWriter fileWriter;
    PrintWriter printWriter;

    /*public Processo(String filePath, File logFile) throws IOException {
        fileWriter = new FileWriter(logFile);
        printWriter = new PrintWriter(fileWriter);
        p = new Programa(filePath);
        this.logFile = logFile;
        pc = 0;
        x = 0;
        y = 0;
        estado = 0;
        nome = p.getName();
        printWriter.print("Carregando " + nome);
    }*/

    public Processo(String filePath, File logFile, int quantum) throws IOException {
        fileWriter = new FileWriter(logFile);
        printWriter = new PrintWriter(fileWriter);
        p = new Programa(filePath);
        this.logFile = logFile;
        this.quantum = quantum;
        pc = 0;
        x = 0;
        y = 0;
        estado = 0;
        nome = p.getName();
        printWriter.append("Carregando " + nome + "\n");
    }

    public boolean executa(/*int quantum*/){
        printWriter.append("Executando " + nome + "\n");
        estado = 1;
        for (int i = 1; i <= quantum; i++) {
            int com = p.proxComando(this);
            if(com == -1) {
                estado = 2;
                printWriter.append("E/S iniciada em " + nome + "\n");
                printWriter.append("Interrompendo " + nome + " apos " + i + " instrucoes\n");
                return true;
            }
            else if(com == -2) {
                estado = 3;
                printWriter.append("Interrompendo " + nome + " apos " + i + " instrucoes\n");
                printWriter.append(nome + " terminado. X=" + x + ". Y=" + y + "\n");
                printWriter.close();
                return false;
            }
        }
        estado = 2;
        printWriter.append("Interrompendo " + nome + " apos " + quantum + " instrucoes\n");
        return true;
    }

    protected void incrementa(){
        pc++;
    }

    protected void setEstado(int estado){
        this.estado = estado;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }
}
