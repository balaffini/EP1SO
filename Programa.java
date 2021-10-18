import java.io.File;
import java.io.FileNotFoundException;
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

    protected int proxComando(BCP p){
        if(!scan.hasNext())
            return -2;
        p.incrementa();
        String comando = scan.nextLine();
        switch(comando.charAt(0)) {
            case 'C':
                return 1;
            case 'E':
                return 2;
            case 'X':
                p.setX(Integer.parseInt(comando.substring(2)));
                return 3;
            case 'Y':
                p.setY(Integer.parseInt(comando.substring(2)));
                return 4;
            case 'S':
                return 5;
        }
        return 0;
    }

    protected String getName(){
        return name;
    }
}
