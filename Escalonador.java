import java.io.*;
import java.util.*;

public class Escalonador {
    private List<BCP> tabelaDeProcessos;
    private Queue<BCP> prontos;
    private Queue<BCP> bloqueados;
    private BCP executando;
    private int quantum, qntTrocas, qntComandos, qntProcessos;

    private FileWriter fileWriter;
    private PrintWriter printWriter;

    public Escalonador(File logFile, int quantum) throws IOException {
        tabelaDeProcessos = new ArrayList<>();
        prontos = new LinkedList<>();
        bloqueados = new LinkedList<>();
        qntTrocas = 0;
        qntComandos = 0;
        qntProcessos = 0;
        this.quantum = quantum;

        fileWriter = new FileWriter(logFile);
        printWriter = new PrintWriter(fileWriter);
    }

    public void adicionaProcesso(String filePath) throws IOException {
        BCP p = new BCP(filePath);
        tabelaDeProcessos.add(p);
        prontos.add(p);
        printWriter.append("Carregando " + p.getNome() + "\n");
        qntProcessos++;
    }

    public void executa(){
        while(!tabelaDeProcessos.isEmpty()) {
            while(prontos.isEmpty())
                contaProcessos();
            executando = prontos.remove();
            executando.setEstado("executando");
            printWriter.append("Executando " + executando.getNome() + "\n");
            for(int i = 1; i <= quantum; i++) {
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
                    prontos.add(executando);
                    executando.setEstado("pronto");
                    qntTrocas++;
                }
            }
            contaProcessos();
        }
        printWriter.append("MEDIA DE TROCAS: " + ((double)qntTrocas/(double)qntProcessos) + "\n");
        printWriter.append("MEDIA DE INSTRUCOES: " + ((double)qntComandos/(double)(qntTrocas)) + "\n");
        printWriter.append("QUANTUM: " + quantum + "\n");
        printWriter.close();
    }

    private void interrompe(){
        executando.setEstado("bloqueado");
        bloqueados.add(executando);
        executando.setTempoDeEspera(3);
        qntTrocas++;
    }

    private void contaProcessos(){
        if(!bloqueados.isEmpty()){
            bloqueados.forEach(BCP::decrementaEspera);
            if(bloqueados.element().getTempoDeEspera() == 0){
                bloqueados.element().setEstado("pronto");
                prontos.add(bloqueados.remove());
            }
        }
    }

    private void encerraProcesso(){
        qntTrocas++;
        tabelaDeProcessos.remove(executando);
        executando.setEstado("encerrado");
        printWriter.append(executando.getNome() + " terminado. X=" + executando.getX() + ". Y=" + executando.getY() + "\n");
    }

    public static void main(String[] args) {
        try {
            File quantumFile = new File("programas/quantum.txt");
            Scanner scan = new Scanner(quantumFile);
            int quantum = scan.nextInt();

            File logFile;
            if(quantum < 10)
                logFile = new File("logFile0" + quantum + ".txt");
            else
                logFile = new File("logFile" + quantum + ".txt");

            Escalonador tb = new Escalonador(logFile, quantum);
            File folder = new File("programas");
            File files[] = folder.listFiles();
            Arrays.sort(files);
            for (int i = 0; i < files.length-1; i++) {
                tb.adicionaProcesso(files[i].getPath());
            }
            tb.executa();
        } catch(IOException ex){
            System.out.println("Arquivo não encontrado");
        }
    }
}