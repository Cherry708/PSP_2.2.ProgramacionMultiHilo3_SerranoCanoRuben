package practica5;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Calcular el numero de enteros multiplos de 2,3,5
 */

public class Reducciones {
    public static void main(String[] args) {
        int numHebras = 4;

        //listaLong
        long[] vector = {
                1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
                16,17,18,19,20,21,22,23,24,25,26,27,28,
                29,30,31,32,33,34,35,36,37,38,39,40, 41};


        implementacionSecuencial(vector);
        implementacionCiclica(vector,numHebras);
        implementacionCiclicaSinReduccion(vector,numHebras);
        implementacionCiclicaAtomica(vector,numHebras);

    }

    static void implementacionSecuencial(long[] lista){
        System.out.println();
        System.out.println("Implementacion secuencial");
        //Contadores
        int multiplosDos = 0;
        int multiplosTres = 0;
        int multiplosCinco = 0;

        //Tiempos
        long t1;
        long t2;
        double tt;

        //Calculo
        t1 = System.nanoTime();
        for (int i = 0; i < lista.length; i++){
            if (i % 2 == 0){
                multiplosDos++;
            }
            if (i % 3 == 0){
                multiplosTres++;
            }
            if (i % 5 == 0){
                multiplosCinco++;
            }
        }
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println();
        System.out.println("Multiplos de 2: "+multiplosDos);
        System.out.println("Multiplos de 3: "+multiplosTres);
        System.out.println("Multiplos de 5: "+multiplosCinco);
        System.out.println("Tiempo total: "+tt);
        System.out.println("-------------------");
    }

    static void implementacionCiclica(long[] lista, int numHebras){
        System.out.println();
        System.out.println("Implementacion ciclica con reducciones");

        //Tiempos
        long t1;
        long t2;
        double tt;

        //Objetos
        MiHebraCiclicaReducciones[] listaHebrasCiclicas = new MiHebraCiclicaReducciones[numHebras];
        Contador contador = new Contador();

        t1 = System.nanoTime();

        //Start
        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            listaHebrasCiclicas[idHebra] = new MiHebraCiclicaReducciones(idHebra,numHebras,lista, contador);
            listaHebrasCiclicas[idHebra].start();
        }

        //Join
        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            try {
                listaHebrasCiclicas[idHebra].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        //Resultado
        System.out.println();
        System.out.println("Multiplos de 2: "+contador.muestraMultiplosDos());
        System.out.println("Multiplos de 3: "+contador.muestraMultiplosTres());
        System.out.println("Multiplos de 5: "+contador.muestraMultiplosCinco());
        System.out.println("Tiempo total: "+tt);
        System.out.println("-------------------");
    }

    static void implementacionCiclicaAtomica(long[] lista, int numHebras){
        System.out.println();
        System.out.println("Implementacion ciclica atomica");

        //contadores
        int multiploDos = 0;
        int multiploTres = 0;
        int multiploCinco = 0;

        //Tiempos
        long t1;
        long t2;
        double tt;

        //Objetos
        MiHebraCiclicaAtomica[] listaHebrasCiclicas = new MiHebraCiclicaAtomica[numHebras];

        t1 = System.nanoTime();

        //Start
        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            listaHebrasCiclicas[idHebra] = new MiHebraCiclicaAtomica(idHebra,numHebras,lista);
            listaHebrasCiclicas[idHebra].start();
        }

        //Join
        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            try {
                listaHebrasCiclicas[idHebra].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;



        for (int i = 0; i<listaHebrasCiclicas.length; i++){
            multiploDos += listaHebrasCiclicas[i].getMultiploDos().intValue();
            multiploTres += listaHebrasCiclicas[i].getMultiploTres().intValue();
            multiploCinco += listaHebrasCiclicas[i].getMultiploCinco().intValue();
        }

        //Resultado
        System.out.println();
        System.out.println("Multiplos de 2: "+multiploDos);
        System.out.println("Multiplos de 3: "+multiploTres);
        System.out.println("Multiplos de 5: "+multiploCinco);
        System.out.println("Tiempo total: "+tt);
        System.out.println("-------------------");
    }

    static void implementacionCiclicaSinReduccion(long[] lista, int numHebras){
        System.out.println();
        System.out.println("Implementacion ciclica sin reducciones");

        //Tiempos
        long t1;
        long t2;
        double tt;

        //Objetos
        MiHebraCiclicaSinReducciones[] listaHebrasCiclicas = new MiHebraCiclicaSinReducciones[numHebras];
        Contador contador = new Contador();

        t1 = System.nanoTime();

        //Start
        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            listaHebrasCiclicas[idHebra] = new MiHebraCiclicaSinReducciones(idHebra,numHebras,lista, contador);
            listaHebrasCiclicas[idHebra].start();
        }

        //Join
        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            try {
                listaHebrasCiclicas[idHebra].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        //Resultado
        System.out.println();
        System.out.println("Multiplos de 2: "+contador.muestraMultiplosDos());
        System.out.println("Multiplos de 3: "+contador.muestraMultiplosTres());
        System.out.println("Multiplos de 5: "+contador.muestraMultiplosCinco());
        System.out.println("Tiempo total: "+tt);
        System.out.println("-------------------");
    }
}
class MiHebraCiclicaReducciones extends Thread {
    int idHebra;
    int numHebras;
    long[] lista;
    Contador contador;

    public MiHebraCiclicaReducciones(int idHebra, int numHebras, long[] lista, Contador contador) {
        this.idHebra = idHebra;
        this.numHebras = numHebras;
        this.lista = lista;
        this.contador = contador;
    }

    public void run() {
        //Variables locales, pasar a contador
        //Reducciones, metodo de los dos pasos

        /*Estas variables actuan como buffer,
        optimizando la transferencia de datos
        y mejorando así el rendimiento*/
        int multiploDos = 0;
        int multiploTres = 0;
        int multiploCinco = 0;
        for (int i = idHebra; i < lista.length; i += numHebras) {
            if (i % 2 == 0){
                multiploDos++;
            }

            if (i % 3 == 0){
                multiploTres++;
            }

            if (i % 5 == 0){
                multiploCinco++;
            }

        }
        contador.asignaMultiplosDos(multiploDos);
        contador.asignaMultiplosTres(multiploTres);
        contador.asignaMultiplosCinco(multiploCinco);
    }
}
/*
ERROR: NO INCORPORA REDUCCIONES, ES SOLO ATOMICA
 */
class MiHebraCiclicaAtomica extends Thread {
    int idHebra;
    int numHebras;
    long[] lista;

    //La clase hebra tiene contadores atómicos, no necesita objeto Contador
    AtomicInteger multiploDos = new AtomicInteger(0);
    AtomicInteger multiploTres = new AtomicInteger(0);
    AtomicInteger multiploCinco = new AtomicInteger(0);

    public MiHebraCiclicaAtomica(int idHebra, int numHebras, long[] lista) {
        this.idHebra = idHebra;
        this.numHebras = numHebras;
        this.lista = lista;
    }

    //Durante la ejecución se modifican los contadores
    public void run() {
        for (int i = idHebra; i < lista.length; i += numHebras) {
            if (i % 2 == 0){
                multiploDos.getAndIncrement();
            }

            if (i % 3 == 0){
                multiploTres.getAndIncrement();
            }

            if (i % 5 == 0){
                multiploCinco.getAndIncrement();
            }
        }
    }

    //Implementamos métodos para la obtención del valor de cada contador
    public AtomicInteger getMultiploDos() {
        return multiploDos;
    }

    public AtomicInteger getMultiploTres() {
        return multiploTres;
    }

    public AtomicInteger getMultiploCinco() {
        return multiploCinco;
    }
}

class MiHebraCiclicaSinReducciones extends Thread {
    int idHebra;
    int numHebras;
    long[] lista;
    Contador contador;

    public MiHebraCiclicaSinReducciones(int idHebra, int numHebras, long[] lista, Contador contador) {
        this.idHebra = idHebra;
        this.numHebras = numHebras;
        this.lista = lista;
        this.contador = contador;
    }

    public void run() {

        for (int i = idHebra; i < lista.length; i += numHebras) {
            if (i % 2 == 0){
                contador.incrementaDos();
            }

            if (i % 3 == 0){
                contador.incrementaTres();
            }

            if (i % 5 == 0){
                contador.incrementaCinco();
            }
        }

    }

}

class Contador{
/*
Podriamos usar Atomic o Synchronized, atomic tiene mejor rendimiento
 */
    AtomicInteger multiploDos = new AtomicInteger(0);
    AtomicInteger multiploTres = new AtomicInteger(0);
    AtomicInteger multiploCinco = new AtomicInteger(0);

    public void asignaMultiplosDos(int multiplos){
        //Este sistema funciona, el método set de Atomic NO
        for (int i = 0; i < multiplos; i++)
            multiploDos.getAndIncrement();
    }
    public void asignaMultiplosTres(int multiplos){
        for (int i = 0; i < multiplos; i++)
            multiploTres.getAndIncrement();
    }
    public void asignaMultiplosCinco(int multiplos){
        for (int i = 0; i < multiplos; i++)
            multiploCinco.getAndIncrement();
    }

    public void incrementaDos(){
        multiploDos.getAndIncrement();
    }

    public void incrementaTres(){
        multiploTres.getAndIncrement();
    }

    public void incrementaCinco(){
        multiploCinco.getAndIncrement();
    }

    public AtomicInteger muestraMultiplosDos(){
        return multiploDos;
    }

    public AtomicInteger muestraMultiplosTres(){
        return multiploTres;
    }

    public AtomicInteger muestraMultiplosCinco(){
        return multiploCinco;
    }
}
