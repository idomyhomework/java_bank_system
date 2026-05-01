import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Handlers {
    private Util util;
    private ArrayList<Cuenta> cuentas;
    private HashSet<String> numCtas;
    private Scanner scan;
    private String DATA_ROOT_DIR;

    public Handlers(Util util, ArrayList<Cuenta> cuentas, HashSet<String> numCtas, Scanner scan, String dataRootDir) {
        this.util = util;
        this.cuentas = cuentas;
        this.numCtas = numCtas;
        this.scan = scan;
        this.DATA_ROOT_DIR = dataRootDir;
    }

    public void handleLoadCuentas() throws Exception {
        System.out.println("Introduce el nombre del archivo (.txt)");
        String fileName = scan.nextLine().trim();
        ArrayList<Cuenta> loaded = util.loadCuentas(scan, DATA_ROOT_DIR +
                fileName, numCtas);
        cuentas.clear();
        cuentas.addAll(loaded);
    }

    public void handleLoadMovimientos() throws Exception {
        System.out.println("Introduce el nombre del archivo donde se guardan   los movimientos (.txt)");
        String fileName = scan.nextLine().trim();
        util.loadMovimientos(scan, DATA_ROOT_DIR + fileName, cuentas);
        System.out.println("Los movimientos se han cargado con éxito!");
    }

    public void handleActualizarCuentas() throws Exception {
        System.out.println("Se van a borrar todos los datos añadidos en esta sesión.");
        util.deleteMemoriaTemporal(cuentas, scan, numCtas);
        System.out.println("Datos borrados con éxito.");
    }

    public void handleDisplayCuenta() {
        System.out.println("Introduce el numero de la cuenta: ");
        String num = scan.nextLine();
        String info = util.displayCuenta(num, cuentas);
        if (info.equals("")) {
            System.out.println("No hay cuenta con este numero");
        } else {
            System.out.println(info);
        }
    }

    public void handleDeleteCuenta() {
        System.out.println("Introduce el numero de la cuenta que quieres borrar: ");
        String num = scan.nextLine();
        util.deleteCuenta(num, cuentas, numCtas);
    }

    public void handleCrearCuenta() {
        System.out.println("Introduce el nombre: ");
        String name = scan.nextLine();
        System.out.println("Introduce el apellido: ");
        String surname = scan.nextLine();
        Cuenta c = util.crearCuenta(name, surname, numCtas);
        cuentas.add(c);
        util.sortCuentas(cuentas);
    }

    public void handleIngresoCuenta() {
        System.out.println("Introduce el numero de cuenta: ");
        String numCuenta = scan.nextLine();
        System.out.println("Introduce la cantidad que quieres ingresar: ");
        double cantidad = Double.parseDouble(scan.nextLine());
        util.ingresoCuenta(numCuenta, cuentas, cantidad, numCtas);
    }

    public void handleExtraccionCuenta() {
        System.out.println("Introduce el numero de cuenta: ");
        String numCuenta = scan.nextLine();
        System.out.println("Introduce la cantidad que quieres extraer: ");
        double cantidad = Double.parseDouble(scan.nextLine());
        util.extraccionCuenta(numCuenta, cuentas, cantidad, numCtas);
    }

    public void handleGetSaldo() {
        System.out.println("Introduce el numero de cuenta: ");
        String numCuenta = scan.nextLine();
        System.out.println("Introduce la fecha (dd/MM/yyyy), o pulsa Enter para   usar la fecha de hoy: ");
        String fechaTxt = scan.nextLine().trim();
        LocalDate fecha = fechaTxt.isEmpty()
                ? LocalDate.now()
                : LocalDate.parse(fechaTxt,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double cantidad = util.getSaldo(numCuenta, cuentas, fecha, numCtas);
        if (cantidad != 0) {
            System.out.println("El saldo de la cuenta " + numCuenta + ": " + String.format("%.2f", cantidad) + " €");
        }
    }

    public void handleGuardarCuentas() throws Exception {
        System.out.println("Introduce el nombre del archivo donde guardar los datos. (.txt al final)");
        String filename = scan.nextLine();
        util.guardarCuentas(cuentas, DATA_ROOT_DIR + filename);
        System.out.println("Las cuentas se han guardado con exito en el " + filename);
    }

    public void handleCuentasEnNegativo() {
        ArrayList<Cuenta> result = util.negativeCuentas(cuentas);
        if (result.isEmpty()) {
            System.out.println("No hay cuentas en negativo.");
            return;
        }
        System.out.println("<---- Las cuentas en negativo ---->");
        for (int i = 0; i < result.size(); i++) {
            System.out.println("Cuenta " + (i + 1));
            System.out.println(result.get(i).toString());
        }
    }
}