
public class NReinas implements Runnable {
    private final int n;
    private final int hilos;
    private final int id;
    private final long[] resultados;

    // Constructor
    public NReinas(int n, int hilos, int id, long[] resultados) {
        this.n = n;
        this.hilos = hilos;
        this.id = id;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        // Dividimos el trabajo del tablero
        int tam = n / hilos;
        int resto = n % hilos;
        int inicio = id * tam + Math.min(id, resto);
        int cantidad = tam + (id < resto ? 1 : 0);
        int fin = inicio + cantidad;
        long solucionesLocales = 0;

        for (int C = inicio; C < fin; C++) {
            // Cada hilo trabaja con sus propios tableros
            int[][] tablero = new int[n][n];
            tablero[0][C] = 1;
            solucionesLocales += backtrackReinas(tablero, 1);
        }
        // guardamos los resultados por hilo
        resultados[id] = solucionesLocales;
    }

    // Metodo que verifica si hay una reina atacada
    public static boolean estaAtacada(int[][] tablero, int fila, int columna) {
        int auxFila;
        int auxColumna;

        // Misma columna
        auxFila = fila - 1;
        auxColumna = columna;
        while (auxFila >= 0) {
            if (tablero[auxFila][auxColumna] == 1) {
                return true;
            }
            auxFila--;
        }

        // Diagonal superior derecha
        auxFila = fila - 1;
        auxColumna = columna + 1;
        while (auxFila >= 0 && auxColumna < tablero.length) {
            if (tablero[auxFila][auxColumna] == 1) {
                return true;
            }
            auxFila--;
            auxColumna++;
        }

        // Diagonal superior izquierda
        auxFila = fila - 1;
        auxColumna = columna - 1;
        while (auxFila >= 0 && auxColumna >= 0) {
            if (tablero[auxFila][auxColumna] == 1) {
                return true;
            }
            auxFila--;
            auxColumna--;
        }

        return false;
    }

    // Metodo que se encarga de realizar el backtracking
    public static long backtrackReinas(int[][] tablero, int fila) {
        if (fila == tablero.length)
            return 1;

        long soluciones = 0;
        for (int columna = 0; columna < tablero.length; columna++) {
            if (!estaAtacada(tablero, fila, columna)) {
                tablero[fila][columna] = 1;
                soluciones += backtrackReinas(tablero, fila + 1);
                tablero[fila][columna] = 0;
            }
        }
        return soluciones;
    }

    public static void main(String[] args) {

        // condicion para un minimo en el tablero
        if (args.length != 2) {
            System.out.println("Uso: java Hilos.NReinas <n> <hilos>");
            return;
        }

        final int n;
        final int hilos;
        try {
            // guradamos los valores del tablero(NXN) y los hilos
            n = Integer.parseInt(args[0]);
            hilos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: n y hilos deben ser enteros.");
            return;
        }

        // creamos los hilos
        Thread[] trabajadores = new Thread[hilos];
        // creamos el arreglo de resultados de tam hilos
        long[] resultados = new long[hilos];

        // Iniciamos la ejecucion de los hilos
        for (int i = 0; i < hilos; i++) {
            trabajadores[i] = new Thread(new NReinas(n, hilos, i, resultados), "reinas-" + i);
            trabajadores[i].start();
        }

        long solucionesFinales = 0;

        // Esperamos a los hilos
        for (int i = 0; i < hilos; i++) {
            try {
                trabajadores[i].join();
                // sumamos el resultado de los hilos
                solucionesFinales += resultados[i];
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("La espera de los hilos fue interrumpida.");
                return;
            }
        }

        System.out.println("Soluciones: " + solucionesFinales);
    }
}

