import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Movimiento {
    // si en la cuenta X sale movimiento con el numero de cuenta X, significa que el
    // usuario ha hecho I(ingreso) or R(retiro) de su dinero en el banco
    private String numeroCta;
    private LocalDate fecha;
    private LocalTime hora;
    private char tipoMovimiento;
    private double cantidad;

    public Movimiento(String numCta, LocalDate f, LocalTime h, char t, double c) {
        this.numeroCta = numCta;
        this.fecha = f;
        this.hora = h;
        this.tipoMovimiento = t;
        this.cantidad = c;
    }

    public String getNumeroCta() {
        return numeroCta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public char getTipoMovimiento() {
        return tipoMovimiento;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String toFileString() {
        DateTimeFormatter fDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fTime = DateTimeFormatter.ofPattern("HH:mm");
        return "#" + numeroCta + ";" + fecha.format(fDate) + ";" + hora.format(fTime) + ";" + tipoMovimiento + ";"
                + cantidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Movimiento aux = (Movimiento) obj;

        return this.tipoMovimiento == aux.tipoMovimiento
                && this.numeroCta.equals(
                        aux.numeroCta)
                && this.fecha.equals(
                        aux.fecha)
                && this.hora.equals(aux.hora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCta, fecha, hora, tipoMovimiento);
    }

}
