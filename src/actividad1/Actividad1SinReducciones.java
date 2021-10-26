package actividad1;

public class Actividad1SinReducciones {
    public static void main(String[] args) {
        double[] vectorDoubles =
                {1.0, 2.2, 3.2, 4.3, 5.5, 6.4,
                7.8, 8.7, 9.9, 10.1, 11.2, 12.3,
                13.6, 14.3,15.7,16.8,17.5,18.9,19.1,
                20.1,21.5,22.6,23.66,24.54};

        //Implementacion secuencial
        long tIniSecuencial;
        long tFinSecuencial;
        double tTotalSecuencial;
        double acumuladorSecuencial = 0;

        tIniSecuencial = System.nanoTime();
        for (int i = 0; i < vectorDoubles.length; i++){
            acumuladorSecuencial += vectorDoubles[i];
        }
        tFinSecuencial = System.nanoTime();
        tTotalSecuencial = ((double) (tFinSecuencial - tIniSecuencial)) / 1.0e9;
        System.out.println("Resultado Secuencial: "+acumuladorSecuencial);
        System.out.println("Tiempo Secuencial: "+tTotalSecuencial);

        //Implementacion ciclica
        long tIniCiclica;
        long tFinCiclica;
        double tTotalCiclica;

        int numHebras = 4;
        MiHebraCiclica[] listaHebrasCiclicas = new MiHebraCiclica[numHebras];
        Acumula acumulador = new Acumula();

        for (int idHebra = 0; idHebra < numHebras; idHebra++){
            listaHebrasCiclicas[idHebra] = new MiHebraCiclica(idHebra,numHebras, vectorDoubles,acumulador);
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
        System.out.println("Resultado Ciclica: "+acumulador.dameResultado());
        System.out.println("Tiempo Ciclica: "+tTotalCiclica);
    }
}
/*
class MiHebraCiclica extends Thread {
    int  miId, numHebras;
    double[]  vector;
    Acumula  a;

    public MiHebraCiclica(int idHebra, int numHebras, double[] vector, Acumula a ) {
        this.miId = idHebra;
        this.numHebras = numHebras;
        this.vector = vector;
        this.a = a;
    }

    public void run () {
        for ( int i = miId; i < vector.length; i += numHebras ) {
            a.acumulaValor ( vector [ i ] );
        }
    }
}
 */

