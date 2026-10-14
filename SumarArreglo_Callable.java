
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Arrays;
import java.util.Random;

public class SumarArreglo_Callable implements Callable<Integer> {

    static int hilos = 1;
    static int[] arreglo = null;
    private int id = -1;
    
    //Constructor 
    SumarArreglo_Callable(int id) {
        this.id = id;
    }

    @Override
    public Integer call() throws Exception {
        int suma = 0;
        int tam = arreglo.length / hilos;
        int resto = arreglo.length % hilos;
        int ini = id * tam;
        int fin = ini + tam;

        for (int i = ini; i < fin; i++) {
            suma = suma + arreglo[i];
        }

        if (resto > id) {
            suma = suma + arreglo[(arreglo.length - 1) - id];
        }

        System.out.println(
                "Thread local id " + id
                        + " Thread Id: " + Thread.currentThread().threadId()
                        + " Suma Local: " + suma);

        return suma;
    }
    
    // Metodo que llena el arreglo 
    static void inicializarArreglo(int[] arreglo, int n) {
        Random azar = new Random();

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = azar.nextInt(n);
        }
    }
    
    // Metodo que muestra el arreglo 
    static void imprimir(int[] arreglo) {
        System.out.println(Arrays.toString(arreglo));
    }

    public static void main(String[] args) {
        int sumaFinal = 0;
        int n = 0;

        try {
            n = Integer.parseInt(args[0]);
            hilos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: No es posible convertir a entero");
            System.exit(0);
        }

        SumarArreglo_Callable sm = new SumarArreglo_Callable(n);
        arreglo = new int[n];
        sm.inicializarArreglo(arreglo, 100);
        sm.imprimir(arreglo);
 	
	// arreglo que guarda objetos de tipo Future 
        LinkedList<Future<Integer>> valores = new LinkedList<>();
        // Creamos los hilos 
	ExecutorService pool = Executors.newFixedThreadPool(hilos);

        for (int i = 0; i < hilos; i++) {
	    // Creamos el objeto que implemente callable 
            Callable<Integer> tarea = new SumarArreglo_Callable(i);
            // enviamos la tarea al ejecutor y guardamos el objeto que nos regrese 
	    Future<Integer> future = pool.submit(tarea);
	    // enlistamos el objeto 
            valores.add(future);
        }
        
	// Recorremos la lista Future y vamos sumando los valores de cada objeto
        for (Future<Integer> future : valores) {
            try {
                sumaFinal += future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error: al obtener el dato del hilo");
            }
        }
        
	// Mostramos la suma del arreglo total
        System.out.println("La suma Callable es: " + sumaFinal);
        pool.shutdown();
    }
}

