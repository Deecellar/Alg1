import java.util.Arrays;
import java.util.Iterator;

public class BusquedasSimilares implements IBusquedasSimilares {

    Bag<String> terminos;

    public BusquedasSimilares() {
        terminos = new Bag<>();
    }

    // La complejidad de este algoritmo es de O(M*N)
    // M(8N + 5) + 2 es la funcion general (excepto cuando las cadenas son vacia)
    //El absoluto mejor caso es cuando los strings estan vacios que nos da 3M + 2, pero esto es una operacion trivial
    //Siendo que el comparador esta basado en un numero de 64 bits entonces el comparador tiene la limitacion de 2^64 caracteres,
    //El siendo que son N varia segun los string que varien tenemos 2 casos, uno, donde N es 2^64 que seria nuestro peor caso y otro donde N es 1 que seria nuestro mejor caso no trivial
    //Entonces el mejor caso nos dice que hay M elementos de tamaño 1 es decir que el algorimo toma 13M + 2 y nuestro peor caso nos dice que seria (2^67)M + 2
    // en ambos casos el algoritmo es lineal

    @Override
    public Bag<String> get(String termino) {
        Bag<String> terminosSimilares = new Bag<>();
        for (String str :
                terminos) {
            String similaridad = similar(str,termino);
            if(!similaridad.isEmpty()){
                terminosSimilares.add(similaridad);
            }
        }
        return terminosSimilares;
    }

    @Override
    public void add(String termino) {
        terminos.add(termino);
    }

    @Override
    public void add(String[] terminos) {
        for (String termino :
                terminos) {
            this.terminos.add(termino);
        }
    }

    @Override
    public Iterator<String> similares(String termino) {
        return get(termino).iterator();
    }

    // Mejor caso O(1) (3)
    // Peor Caso O(N) ( 8N + 5) siendo N el tamaño del texto
    //usando bitmap algorithm
    private String similar(String texto, String termino){
        if(termino.length() == 0)
            return "";
        long bitmap = ~1;


        for (int i = 0; i < texto.length(); i++) {
            bitmap = (bitmap << 1) | (termino.indexOf(texto.charAt(i)) );
            if(0 == (bitmap & (1 << termino.length())))
                return texto;
        }
        return "";
    }

    public static void main(String[] args) {
        BusquedasSimilares busquedas = new BusquedasSimilares();
        busquedas.add("Hola");
        busquedas.add("Mundo");
        busquedas.add("akdoakdoekrfaoskfakgjhhadkjfoepkgpanmgPJgpaHJEpgihPjfpdjsfpanfme");
        Bag<String> t = busquedas.get("H");
        for (String r :
                t) {
            System.out.println(r);

        }
    }
}