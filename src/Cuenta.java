import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Cuenta {
    private String nombre;
    private String apellidos;
    private String numero;
    private LocalDate fechaAbertura;
    private double saldo;
    private double SALDO_BASE;
    private ArrayList<Movimiento> movimientos;

    // añado localDate fecha en el contructor, aunque el constructor del enunciado
    // no lo implementa
    public Cuenta(String nom, String ap, String num, LocalDate fecha, double s, double saldoBase) {
        this.nombre = nom;
        this.apellidos = ap;
        this.numero = num;
        this.fechaAbertura = fecha;
        this.saldo = s;
        this.SALDO_BASE = saldoBase; // el primer deposito para abrir cuenta.
        this.movimientos = new ArrayList<Movimiento>();
    }

    public Cuenta(String nom, String ap, String num, LocalDate fecha, double s) {
        this.nombre = nom;
        this.apellidos = ap;
        this.numero = num;
        this.fechaAbertura = fecha;
        this.saldo = s;
        this.SALDO_BASE = 0;
        this.movimientos = new ArrayList<Movimiento>();
    }

    public void addMovimiento(Movimiento mov) {
        if (this.movimientos.contains(mov)) {
            return;
        } else {
            this.movimientos.add(mov);
            double cantidad = mov.getCantidad();
            this.saldo = mov.getTipoMovimiento() == 'I' ? this.saldo + cantidad : this.saldo - cantidad;
        }

    }

    public double getSaldoBase() {
        return this.SALDO_BASE;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getFechaAbertura() {
        return fechaAbertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNombreCompleto() {
        String result = this.nombre + " " + this.apellidos;
        return result;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return nombre + ";" + apellidos + ";" + numero + ";" + fechaAbertura.format(formatter) + ";" + SALDO_BASE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Cuenta other = (Cuenta) obj;

        return this.numero.equals(other.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "=== Cuenta ===" +
                "\nNombre:          " + nombre +
                "\nApellidos:       " + apellidos +
                "\nNúmero:          " + numero +
                "\nFecha apertura:  " + fechaAbertura +
                "\nSaldo:           " + String.format("%.2f", saldo) + " €" +
                "\n==============";
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

}