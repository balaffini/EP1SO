import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Programa {
    private File prog;
    private Scanner scan;
    private String name;

    public Programa(String filePath) throws FileNotFoundException {
        prog = new File(filePath);
        scan = new Scanner(prog);
        name = scan.nextLine();
    }

    protected int proxComando(Processo p){
        if(!scan.hasNext())
            return -2;
        p.incrementa();
        String comando = scan.nextLine();
        switch(comando.charAt(0)) {
            //case 'C':
            //    return 0;
            case 'E':
                return -1;
            case 'X':
                p.setX(Integer.parseInt(comando.substring(2)));
                return 1;
            case 'Y':
                p.setY(Integer.parseInt(comando.substring(2)));
                return 2;
            case 'S':
                return -2;
            default:
                return 0;
        }
    }

    protected String getName(){
        return name;
    }
}
