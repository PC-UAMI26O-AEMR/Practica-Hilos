
import java.util.Arrays;
import java.util.Random;

public class SumarArreglo_Con_Runnable implements Runnable {

    static int[] arreglo = null;
    static int[] resultado = null;
    private int id = -1;
    static int hilos = 1;

    // Constructor
    SumarArreglo_Con_Runnable(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        int suma = 0;
        int tam = arreglo.length / hilos;
        int resto = arreglo.length / hilos;
        int ini = id * tam;
        int fin = ini + tam;

        for (int i = 0; i < arreglo.length; i++) {
            suma = suma + arreglo[i];
        }

        if (resto > id) {
            suma = suma + arreglo[(arreglo.length - 1) - id];
        }

        System.out.println("Thread local id " + id +
                "Thread Id:" + Thread.currentThread().threadId() +
                "Suma local:" + suma);
        resultado[id] = suma;
    }

    // Llenamos el arreglo con numeros aleatorios
    void inicializar_Arreglo(int[] arreglo, int n) {
        Random azar = new Random();

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = azar.nextInt(n);
        }
    }

    // Metodo para imprimir el arreglo
    void imprimir(int[] arreglo) {
        System.out.println(Arrays.toString(arreglo));
    }

    public static void main(String[] args) {
        int n = 0;
        int sumaFinal = 0;

        try {
            n = Integer.parseInt(args[0]);
            hilos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: No es posible convertir a entero");
            System.exit(0);
        }

        SumarArreglo_Con_Runnable sm = new SumarArreglo_Con_Runnable(n);
        arreglo = new int[n];
        Thread[] trabajadores = new Thread[hilos];
        resultado = new int[hilos];

        sm.inicializar_Arreglo(arreglo, 100);
        sm.imprimir(arreglo);

        for (int i = 0; i < hilos; i++) {
            trabajadores[i] = new Thread(new SumarArreglo_Con_Runnable(i));
            trabajadores[i].start();
        }

        for (int i = 0; i < hilos; i++) {
            try {
                trabajadores[i].join();
                sumaFinal = sumaFinal + resultado[i];
            } catch (InterruptedException e) {
                System.out.println("Error: en la espera del hilo");
            }
        }

        System.out.println("Suma Runnable: " + sumaFinal);
    }
}

