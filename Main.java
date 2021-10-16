import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File logFile = new File("logFile.txt");
        try {
            Processo p = new Processo("EP1SO/programas/01.txt", logFile, 3);
            while(p.executa());
        } catch(IOException ex){
            System.out.println("Arquivo não encontrado");
        }
    }
}
