import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class App {
    static String DATA_ROOT_DIR = "data/";
    static Util util = new Util();
    static HashSet<String> numCtas = new HashSet<String>();
    static ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
    static Scanner scan = new Scanner(System.in);

    public static ArrayList<Cuenta> handlerLoadCuentas(Util util, Scanner scan, HashSet<String> cuentasNumeros)
            throws Exception {
        System.out.println("Introduce el nombre del archivo (.txt)");
        String fileName = scan.nextLine().trim();
        String file = DATA_ROOT_DIR + fileName;
        return util.loadCuentas(scan, file, cuentasNumeros);
    }

    public static void handleLoadMovimientos(Util util, Scanner scan, ArrayList<Cuenta> cuentas)
            throws Exception {
        System.out.println("Introduce el nombre del archivo donde se guardan los movimientos (.txt)");
        String fileName = scan.nextLine().trim();
        String file = DATA_ROOT_DIR + fileName;
        util.loadMovimientos(scan, file, cuentas);
        System.out.println("Los movimientos se han cargado con éxito!");
    }

    public static void main(String[] args) throws Exception {

        cuentas = handlerLoadCuentas(util, scan, numCtas);
        handleLoadMovimientos(util, scan, cuentas);

    }

}
