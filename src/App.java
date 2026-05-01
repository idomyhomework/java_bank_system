import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class App {
    static String DATA_ROOT_DIR = "data/";
    static Util util = new Util();
    static HashSet<String> numCtas = new HashSet<String>();
    static ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
    static Scanner scan = new Scanner(System.in);

    static String[] menuOpciones = {
            "1  - Cargar cuentas",
            "2  - Cargar movimientos",
            "3  - Actualizar Cuentas",
            "4  - Mostrar Cuenta",
            "5  - Eliminar Cuenta",
            "6  - Alta Cuenta",
            "7  - Ingreso Cuenta",
            "8  - Extraccion Cuenta",
            "9  - Saldo a dia",
            "10 - Guardar Cuentas",
            "11 - Cuentas en numeros rojos",
            "12 - Salir"
    };

    public static void main(String[] args) throws Exception {
        Handlers h = new Handlers(util, cuentas,
                numCtas, scan, DATA_ROOT_DIR);
        h.handleLoadCuentas();

        int opcion;
        do {
            System.out.println("\n=== MENU ===");
            for (int i = 0; i < menuOpciones.length; i++) {
                System.out.println(menuOpciones[i]);
            }

            opcion = Integer.parseInt(scan.nextLine().trim());

            switch (opcion) {
                case 1 -> h.handleLoadCuentas();
                case 2 -> h.handleLoadMovimientos();
                case 3 -> h.handleActualizarCuentas();
                case 4 -> h.handleDisplayCuenta();
                case 5 -> h.handleDeleteCuenta();
                case 6 -> h.handleCrearCuenta();
                case 7 -> h.handleIngresoCuenta();
                case 8 -> h.handleExtraccionCuenta();
                case 9 -> h.handleGetSaldo();
                case 10 -> h.handleGuardarCuentas();
                case 11 -> h.handleCuentasEnNegativo();
                case 12 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 12);

        scan.close();
    }
}