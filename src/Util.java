import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Util {
    // 1. cargar cuentas, cargar movimientos disponibles para cada cuenta
    public ArrayList<Cuenta> loadCuentas(Scanner scan, String f, HashSet<String> numCtasExistentes) throws Exception {
        // el indice me sirve para añadir movimientos a las cuentas bancarias
        int indice = -1;

        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();

        scan = new Scanner(new File(f));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.isEmpty()) {
                if (!line.startsWith("#")) {
                    indice++;
                    Cuenta nuevaCuenta = getDatosCuenta(line);
                    String numNuevaCuenta = nuevaCuenta.getNumero();
                    if (!numCtasExistentes.contains(numNuevaCuenta)) {
                        cuentas.add(nuevaCuenta);
                        numCtasExistentes.add(numNuevaCuenta);
                    }
                } else {
                    Movimiento nuevoMov = getDatosMovimiento(line, true);
                    cuentas.get(indice).addMovimiento(nuevoMov);
                }

            }
        }

        scan.close();
        return cuentas;

    }

    // 2. cargar movimientos
    public void loadMovimientos(Scanner scan, String f, ArrayList<Cuenta> cuentas) throws Exception {
        scan = new Scanner(new File(f));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (!line.isEmpty()) {
                Movimiento nuevoMovimiento = getDatosMovimiento(line, false);
                int indiceCuenta = buscarCuentaId(cuentas, nuevoMovimiento.getNumeroCta());
                if (indiceCuenta != -1) {
                    cuentas.get(indiceCuenta).addMovimiento(nuevoMovimiento);
                }
            }
        }

        scan.close();
    }

    // 4. Mostrar cuenta
    public String displayCuenta(String numCuenta, ArrayList<Cuenta> cuentas) {
        int cuentaId = buscarCuentaId(cuentas, numCuenta);

        return cuentas.get(cuentaId).toString();
    }

    // 5. Eliminar cuenta
    public void deleteCuenta(String numCuenta, ArrayList<Cuenta> cuentas) {
        int cuentaId = buscarCuentaId(cuentas, numCuenta);

        cuentas.remove(cuentaId);
    }

    // 6. Alta Cuenta
    public Cuenta crearCuenta(String nom, String apellido,
            HashSet<String> numCtasExistentes) {
        String numeroCuenta = generateRandomNumber(10);
        if (numCtasExistentes.contains(numeroCuenta)) {
            System.out.println("Ya existe una cuenta con este número");
            return null;
        }
        LocalDate fechaNow = LocalDate.now();
        Cuenta nuevaCuenta = new Cuenta(nom, apellido, numeroCuenta, fechaNow, 0);
        numCtasExistentes.add(numeroCuenta);

        return nuevaCuenta;

    }

    // 7. Ingreso Cuenta

    public void ingresoCuenta(String numCuenta, ArrayList<Cuenta> cuentas, double cantidad,
            HashSet<String> numCtasExistentes) {

        if (!numCtasExistentes.contains(numCuenta)) {
            System.out.println("La cuenta con numero " + numCuenta + " no existe.");
            return;
        }

        if (cantidad < 0) {
            System.out.println("No se puede hacer ingresos negativos");
            return;
        }

        int indice = buscarCuentaId(cuentas, numCuenta);

        if (indice != -1) {
            Movimiento ingreso = new Movimiento(numCuenta, LocalDate.now(), LocalTime.now(), 'I', cantidad);
            cuentas.get(indice).addMovimiento(ingreso);
        }
    }

    // 8. Extracción Cuenta
    // permito que las cuentas queden en negativo.
    public void extraccionCuenta(String numCuenta, ArrayList<Cuenta> cuentas, double cantidad,
            HashSet<String> numCtasExistentes) {
        if (!numCtasExistentes.contains(numCuenta)) {
            System.out.println("La cuenta con numero " + numCuenta + " no existe.");
            return;
        }

        if (cantidad < 0) {
            System.out.println("la cantidad tiene que ser mayor que 0");
            return;
        }

        int indice = buscarCuentaId(cuentas, numCuenta);

        if (indice != 1) {
            Movimiento ingreso = new Movimiento(numCuenta, LocalDate.now(), LocalTime.now(), 'E', cantidad);
            cuentas.get(indice).addMovimiento(ingreso);
        }

    }

    // 9. Saldo a día

    public double getSaldo(String numCuenta, ArrayList<Cuenta> cuentas, LocalDate dia) {
        int indice = buscarCuentaId(cuentas, numCuenta);
        if (indice == -1)
            return 0;

        Cuenta cuenta = cuentas.get(indice);

        if (dia.isBefore(cuenta.getFechaAbertura())) {
            System.out.println("La cuenta es posterior a la fecha");
            return 0;
        }

        ArrayList<Movimiento> transacciones = cuenta.getMovimientos();
        double result = cuenta.getSaldoBase();
        for (Movimiento movimiento : transacciones) {
            if (!movimiento.getFecha().isAfter(dia)) {
                double cantidad = movimiento.getCantidad();
                result += (movimiento.getTipoMovimiento() == 'I') ? cantidad : -cantidad;
            }
        }

        return result;
    }

    // 10. Guardar Cuentas

    public void guardarCuentas(ArrayList<Cuenta> cuentas, String f) throws Exception {
        List<String> lineas = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            lineas.add(cuenta.toFileString());
            for (Movimiento mov : cuenta.getMovimientos()) {
                lineas.add(mov.toFileString());
            }
            Files.write(Paths.get(f), lineas);
        }
    }

    // 11. Cuentas en numeros rojos
    public ArrayList<Cuenta> negativeCuentas(ArrayList<Cuenta> cuentas) {
        ArrayList<Cuenta> result = new ArrayList<Cuenta>();

        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getSaldo() < 0.0) {
                result.add(cuentas.get(i));
            }
        }

        return result;
    }

    private int buscarCuentaId(ArrayList<Cuenta> cuentas, String numCuenta) {
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getNumero().equals(numCuenta)) {
                return i;
            }
        }

        return -1;
    }

    private Cuenta getDatosCuenta(String line) {
        String data[] = line.split(";");
        // array de data contiene toda la informacion para la creación de cuenta
        String nombre = data[0];
        String apellido = data[1];
        String numero = data[2];
        String fechatxt = data[3];
        double cantidad = Double.parseDouble(data[4]);
        double cantidadInicial = Double.parseDouble(data[4]);
        // convertimos fechatxt en un formato de fecha válido
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(fechatxt, formatter);
        return new Cuenta(nombre, apellido, numero, fecha, cantidad, cantidadInicial);
    }

    // si aux es true, tenemos que borrar el primer simbolo de numero de cuenta
    // (diferencia de datos de los archivos .txt)
    private Movimiento getDatosMovimiento(String line, boolean aux) {
        String[] dataMovimiento = line.split(";");
        String numero = aux ? dataMovimiento[0].substring(1) : dataMovimiento[0];
        String fechatxt = dataMovimiento[1];
        // convertimos fechatxt en un formato de fecha válido
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(fechatxt, formatter);
        String horatxt = dataMovimiento[2];
        // convertimos horatxt en un formato de tiempo válido
        DateTimeFormatter formatterTiempo = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime tiempo = LocalTime.parse(horatxt, formatterTiempo);
        char tipo = dataMovimiento[3].charAt(0);
        double cantidad = Double.parseDouble(dataMovimiento[4]);
        return new Movimiento(numero, fecha, tiempo, tipo, cantidad);
    }

    // Generar numero de cuenta.
    private static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // El primer dígito nunca es 0 para evitar números con ceros a la izquierda
        sb.append(random.nextInt(9) + 1);

        for (int i = 1; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
