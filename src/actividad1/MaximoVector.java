package actividad1;

public class MaximoVector {

    public static void main(String[] args) {

        long[] vectorLong =
                {1L, 2L, 3L, 4L, 5L, 6L,
                        7L, 8L, 9L, 10L, 11L, 12L,
                        13L, 14L,15L,16L,17L,18L,19L,
                        20L,21L,22L,23L,24L};

        //Secuencial
        long maximo = 0;
        long tIniSecuencial;
        long tFinSecuencial;
        double tTotalSecuencial;

        tIniSecuencial = System.nanoTime();
        for(int i = 0; i < vectorLong.length; i++){
            if (vectorLong[i] > maximo){
                maximo = vectorLong[i];
            }
        }
        tFinSecuencial = System.nanoTime();
        tTotalSecuencial = ((double) (tFinSecuencial - tIniSecuencial)) / 1.0e9;
        System.out.println("Maximo secuencial: "+maximo);
        System.out.println("Tiempo secuencial: "+tTotalSecuencial);

        //Ciclico
        long tIniCiclica;
        long tFinCiclica;
        double tTotalCiclica;

        int numHebras = 4;
        MaximoHebraCiclica[] listaHebrasCiclicas = new MaximoHebraCiclica[numHebras];
        MaximoHebra maximoHebra = new MaximoHebra();


        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            listaHebrasCiclicas[idHebra] = new MaximoHebraCiclica(idHebra,numHebras, vectorLong, maximoHebra);
            listaHebrasCiclicas[idHebra].start();
        }

        tIniCiclica = System.nanoTime();
        for (int i = 0; i < numHebras; i++) {
            try {
                listaHebrasCiclicas[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        tFinCiclica = System.nanoTime();

        tTotalCiclica = ((double) (tFinCiclica - tIniCiclica)) / 1.0e9;
        System.out.println("Maximo ciclico: "+maximoHebra.dameResultado());
        System.out.println("Tiempo secuencial: "+tTotalCiclica);



    }
}
class MaximoHebraCiclica extends Thread{
    int idHebra;
    int numHebras;
    long[] vectorLong;
    MaximoHebra maximoVector;

    MaximoHebraCiclica(int idHebra, int numHebras, long[] vectorLong, MaximoHebra maximoVector){
        this.idHebra = idHebra;
        this.numHebras = numHebras;
        this.vectorLong = vectorLong;
        this.maximoVector = maximoVector;
    }


    public void run() {
        long valorMaximo = 0;
        for (int i = idHebra; i < vectorLong.length; i += numHebras){
            if (vectorLong[i] > valorMaximo){
                valorMaximo = vectorLong[i];
            }
            maximoVector.asignaValor(valorMaximo);

        }
    }
}

class MaximoHebra {
    long maximo = 0;
    //Es realmente necesario synchronized?
    synchronized void asignaValor(long valor ) {
        this.maximo = valor;
    }

    synchronized long dameResultado() {
        return this.maximo;
    }
}