import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Escalonador {
    private List<BCP> tabelaDeProcessos;
    private Queue<BCP> prontos;
    private Queue<BCP> bloqueados;
    private BCP executando;
    private int quantum, cntInterrupcoes, qntTrocas, qntComandos;

    private FileWriter fileWriter;
    private PrintWriter printWriter;

    public Escalonador(File logFile, int quantum) throws IOException {
        tabelaDeProcessos = new ArrayList<>();
        prontos = new LinkedList<>();
        bloqueados = new LinkedList<>();
        qntTrocas = 0;
        qntComandos = 0;
        this.quantum = quantum;

        fileWriter = new FileWriter(logFile);
        printWriter = new PrintWriter(fileWriter);
    }

    public void adicionaProcesso(String filePath) throws IOException {
        BCP p = new BCP(filePath);
        tabelaDeProcessos.add(p);
        prontos.add(p);
        printWriter.append("Carregando " + p.getNome() + "\n");
    }

    public void executa(){
        while(!tabelaDeProcessos.isEmpty()) {
            while(prontos.isEmpty())
                contaProcessos();
            executando = prontos.remove();
            executando.setEstado("Executando");
            printWriter.append("Executando " + executando.getNome() + "\n");
            int i;
            for(i = 1; i <= quantum; i++) {
                contaProcessos();
                qntComandos++;
                switch(executando.executa()){
                    case 1: //COM
                        break;
                    case 2: //E/S
                        printWriter.append("E/S iniciada em " + executando.getNome() + "\n");
                        printWriter.append("Interrompendo " + executando.getNome() + " apos " + i + " instrucoes\n");
                        interrompe();
                        i += quantum;
                        break;
                    case 3: //X=
                        break;
                    case 4: //Y=
                        break;
                    case 5: //SAIDA
                        printWriter.append("Interrompendo " + executando.getNome() + " apos " + i + " instrucoes\n");
                        encerraProcesso();
                        i += quantum;
                }
                if(i == quantum) {
                    printWriter.append("Interrompendo " + executando.getNome() + " apos " + quantum + " instrucoes\n");
                    interrompe();
                }
            }
        }
        printWriter.append("MEDIA DE TROCAS: " + (qntTrocas) + "\n");
        printWriter.append("MEDIA DE INSTRUCOES: " + ((double)qntComandos/(double)(qntTrocas)) + "\n");
        printWriter.append("QUANTUM: " + quantum + "\n");
        printWriter.close();
    }

    private void interrompe(){
        executando.setEstado("Bloqueado");
        if(bloqueados.isEmpty())
            cntInterrupcoes = 0;
            bloqueados.add(executando);
        qntTrocas++;
    }

    private void contaProcessos(){
        cntInterrupcoes++;
        if(!bloqueados.isEmpty() && cntInterrupcoes == 2){
            BCP p = bloqueados.remove();
            prontos.add(p);
            p.setEstado("Pronto");
            cntInterrupcoes = 0;
            if(!bloqueados.isEmpty())
                cntInterrupcoes = 1;
        }
    }

    private void encerraProcesso(){
        qntTrocas++;
        tabelaDeProcessos.remove(executando);
        executando.setEstado("Encerrado");
        printWriter.append(executando.getNome() + " terminado. X=" + executando.getX() + ". Y=" + executando.getY() + "\n");
    }
}