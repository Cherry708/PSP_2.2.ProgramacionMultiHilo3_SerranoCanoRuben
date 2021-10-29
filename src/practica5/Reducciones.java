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
    }

    static void implementacionCiclica(long[] lista, int numHebras){
        System.out.println();
        System.out.println("Implementacion ciclica");

        //Tiempos
        long t1;
        long t2;
        double tt;

        //Objetos
        MiHebraCiclica[] listaHebrasCiclicas = new MiHebraCiclica[numHebras];
        Contador contador = new Contador();

        t1 = System.nanoTime();

        //Start
        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            listaHebrasCiclicas[idHebra] = new MiHebraCiclica(idHebra,numHebras,lista, contador);
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
    }
}
class MiHebraCiclica extends Thread {
    int idHebra;
    int numHebras;
    long[] lista;
    Contador contador;

    public MiHebraCiclica(int idHebra, int numHebras, long[] lista, Contador contador) {
        this.idHebra = idHebra;
        this.numHebras = numHebras;
        this.lista = lista;
        this.contador = contador;
    }

    //Este es el sin reduccion, sin atomic no funciona, para el resto cambiar a atomic.
    public void run() {
        //Variables locales, pasar a contador
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

//Usar atomic
class Contador{
    /*
    volatile int multiploDos;
    volatile int multiploTres;
    volatile int multiploCinco;
    */
    AtomicInteger multiploDos = new AtomicInteger(0);
    AtomicInteger multiploTres = new AtomicInteger(0);
    AtomicInteger multiploCinco = new AtomicInteger(0);

    public void asignaMultiplosDos(int multiplos){
        //this.multiploDos = multiplos;
        for (int i = 0; i < multiplos; i++)
            multiploDos.getAndIncrement();
    }
    public void asignaMultiplosTres(int multiplos){
        //this.multiploTres = multiplos;
        for (int i = 0; i < multiplos; i++)
            multiploTres.getAndIncrement();
    }
    public void asignaMultiplosCinco(int multiplos){
        //this.multiploCinco = multiplos;
        for (int i = 0; i < multiplos; i++)
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
