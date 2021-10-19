import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File quantumFile = new File("src/programas/quantum.txt");
            Scanner scan = new Scanner(quantumFile);
            int quantum = scan.nextInt();

            File logFile;
            if(quantum < 10)
                logFile = new File("logFile0" + quantum + ".txt");
            else
                logFile = new File("logFile" + quantum + ".txt");

            Escalonador tb = new Escalonador(logFile, quantum);
            tb.adicionaProcesso("src/programas/01.txt");
            tb.adicionaProcesso("src/programas/02.txt");
            tb.adicionaProcesso("src/programas/03.txt");
            tb.adicionaProcesso("src/programas/04.txt");
            tb.adicionaProcesso("src/programas/05.txt");
            tb.adicionaProcesso("src/programas/06.txt");
            tb.adicionaProcesso("src/programas/07.txt");
            tb.adicionaProcesso("src/programas/08.txt");
            tb.adicionaProcesso("src/programas/09.txt");
            tb.adicionaProcesso("src/programas/10.txt");

            tb.executa();
        } catch(IOException ex){
            System.out.println("Arquivo não encontrado");
        }
    }
}
