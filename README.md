# Practica-Hilos
`Hilo >>`  cada secuencia de control dentro de un proceso que ejecuta sus instrucciones de forma indeoendiente. 

Los hilo residen en el espacio de usuario, con lo que un hilo es una __entidad ligera__. Los hilos comparten la informaciòn del proceso. Si un hilo modifica una variable del proceso, el resto de hilos veràn esa modificaciòn cuando accedan a esa variable.

## Implementaciòn de hilos 
1. Librerìa al nivel de usuario: todas las estructuras y el còdigo de la libreria estaràn en el espacio de usuario, esto significa que todas la llamadas las ejecuta el espacio de usuario 
2. Nivel de nucleo: La mayorìa de las llamadas de la librerìa requieren llamadas al sistema 

## Planificaciòn de los hilos 
__Definiremos el concepto de procesador lògico de tal forma que donde se jecutan los hilos de usuario es en un procesador lògico__, de esta forma vamos a tener una planificaciòn a dos niveles. Un primer nivel para asignar los hilos de usuario a los procesadores lògicos y otro para asignar los procesadores lògicos al procesador o procesadores fìsicos.
* Muchos hilos en un procesador lògico: Todos los hilos creados en el espacio de usuario hacen turnos para irse ejecutàndose en el procesaro logico  asignado asignado a ese proceso
* Un hilo por procesador lògico: Aquì se asigna un prosesador lògico para cada hilo de usuario. Estw modelo permite que muchos hilos se ejecuten simultàneamente en diferentes procesadores fìsicos  
* Muchos hilos en muchos procesadores lògicos: Un nùmero de hilos es multiplexado en un nùmero de procesadores lògicos igual o menor. Numerosos hilos pueden correr en paralelo en diferentes CPUs 

## Hilos en Java
Nada màs empezar un programa en Java, existe un hilo de ejecuciòn que es el indicado por el metodo `main`, es el denominado hilo principal, sin embargo, si desde el hilo principal se crean por ejemplo dos hilos, tendremos que el programa se està ejecutando al mismo tiempo por tres hilos distintos: el hilo principal mas los dos hilos creados. Los hilos pueden estar ejecutando còdigo en diferentes objetos, còdigo diferente en el mismo objeto o inlcuso el mismo còdigo en el mismo objeto y al mismo tiempo

__Diferencia entre objeto e hilo__: Un objeto es algo estàtoco, tiene una serie de atributos y mètodos. Quien realmente ejecuta esos mètodos es el hilo de ejecuciòn

## Creaciòn de hilos 
Existen viarias formas de trabajar con hilos en cuanto a su creaciòn se refiere, unas de ellas son 
+  Heredado de la clase `Thread`
	 **Thread** es una clase proporcionada por Java para representar un hilo de ejecución. Si una clase hereda de **Thread**, puede sobrescribir el método **run()** para indicar el trabajo que realizará el hilo.
	Los métodos principales que utilizaremos son:
	- **start()**: crea e inicia el nuevo hilo;
	- **run()**: contiene el trabajo que ejecutará el hilo; y
	- **join()**: hace que el hilo que invoca este método espere a que termine otro hilo.
	
*  Implementado de la interfaz `Runnable`
		La interfaz Runnable es parte del `paquete java.lang` y se utiliza para definir una tarea que se puede ejecutar por un hilo. Proporciona una manera de lograr el multiproceso separando la lógica de tarea del mecanismo de ejecución de hilo.
	- Contiene un único método abstracto `run()` que contiene el código ejecutado por el hilo.
	- Se prefiere extender la clase Thread, ya que soporta un mejor diseño y flexibilidad.

```
Nota - al crear un objeto de la clase Thread se le tiene que pasar como parametro el objeto donde queremos que empiece la ejecuciòn de ese hilo, el metodo start() de la clase Thread se encarga de invocar al mètodo run() de los objetos 
```

+ Interfaz Callable 
	 La interfaz de Llamable es una parte del paquete `java.util.concurrent`,  Representa una tarea que puede ser ejecutada por múltiples hilos y devolver un resultado. A diferencia de la interfaz ejecutable, Callable puede devolver un valor y lanzar excepciones marcadas.
		- Utilizado con ExecutorService para ejecución asíncrona o concurrente.
		- El resultado de un Llamable se obtiene usando un objeto Future.
		- Es una interfaz funcional, por lo que puede utilizar expresiones lambda.
