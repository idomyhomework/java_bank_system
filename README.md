# Sistema de Gestión Bancaria

Aplicación de consola para gestionar cuentas bancarias y sus movimientos.  
 Los datos se guardan en archivos `.txt` y se cargan al iniciar el programa.

---

Al arrancar, el programa te pedirá que cargues el archivo de cuentas.  
 Introduce `cuentas.txt` el archivo principal que ya existe en `data/`.
Todos los archivos de texto se guardan en `data/`.

---

## Menú de opciones

- 1 - Cargar cuentas: carga cuentas y sus movimientos desde un archivo .txt
- 2 - Cargar movimientos: carga movimientos adicionales desde otro archivo  
  .txt
- 3 - Actualizar cuentas: descarta los cambios no guardados y vuelve al estado del archivo. (Siempre carga el archivo cuentas.txt)
- 4 - Mostrar cuenta: muestra los datos de una cuenta
- 5 - Eliminar cuenta: elimina una cuenta por número
- 6 - Alta cuenta: crea una nueva cuenta (el número se genera automáticamente)
- 7 - Ingreso: añade dinero a una cuenta
- 8 - Extracción: retira dinero de una cuenta (se permite saldo negativo)
- 9 - Saldo a día: consulta el saldo de una cuenta en una fecha concreta
- 10 - Guardar cuentas: guarda el estado actual en un archivo .txt
- 11 - Cuentas en negativo: lista todas las cuentas con saldo negativo
- 12 - Salir

---

## Formato de los archivos de datos

### cuentas.txt

Cada cuenta ocupa una línea, seguida de sus movimientos (prefijados con #):

      nombre;apellidos;numeroCuenta;dd/MM/yyyy;saldoInicial
      #numeroCuenta;dd/MM/yyyy;HH:mm;I;cantidad
      #numeroCuenta;dd/MM/yyyy;HH:mm;E;cantidad

I = Ingreso, E = Extracción. La cantidad siempre es un valor positivo.

### Archivos de movimientos (movimientos1.txt, etc.)

Mismo formato que las líneas de movimiento, sin el #:

      numeroCuenta;dd/MM/yyyy;HH:mm;I;cantidad

---

## Consideraciones importantes

- Los cambios no se guardan solos. Todo lo que hagas se pierde si sales sin  
  usar la opción 10.
- La opción 3 es un reset. Deshace todos los cambios de la sesión y vuelve al estado del archivo. (cuentas.txt)
- Los números de cuenta son de 20 dígitos y se generan automáticamente al  
  crear una cuenta nueva.
- El saldo puede quedar en negativo tras una extracción, el programa lo  
  permite.
- La opción 9 calcula el saldo hasta la fecha indicada. Si dejas la fecha  
  vacía, usa la fecha de hoy.
- La opción 1 sobreescribe las cuentas en memoria al cargar un archivo nuevo.
- Las cuentas siempre se guardan en el orden ascendente para optimizar las búsquedas
- El programa funciona por "capas".
  `Util.java` - es el núcleo del programa. Lee, guarda y borra los datos. Trabaja directamente con las clases `Cuenta.java/` y `Movimiento.java/` y sus metodos.
  `Handlers.java/` - la capa donde juntamos la lectura de datos y la clase Util. Cada handler representa una función principal del programa.
  `App.java/` - la capa donde juntamos todas las funciones del programa con el usuario final.
