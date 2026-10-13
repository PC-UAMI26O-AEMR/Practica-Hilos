
import java.util.Arrays;
import java.util.Random;

public class SumaArreglos {

    static int[] arreglo = null;

    // Constructor
    SumaArreglos(int n) {
        arreglo = new int[n];
    }

    public int sumar() {
        int suma = 0;

        for (int i = 0; i < arreglo.length; i++) {
            suma = suma + arreglo[i];
        }
        System.out.println("Thread id: " + Thread.currentThread() + "Suma local: " + suma);
        return suma;
    }

    // Llenamos el arreglo con numeros aleatorios
    void inicializarArreglo(int[] arreglo, int n) {
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
        int n = Integer.parseInt(args[0]);

        SumaArreglos sm = new SumaArreglos(n);
        sm.inicializarArreglo(arreglo, 100);
        sm.imprimir(arreglo);

        int resultado = sm.sumar();
        System.out.println("Suma: " + resultado);
    }
}

