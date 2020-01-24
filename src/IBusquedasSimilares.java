import java.util.Iterator;

public interface IBusquedasSimilares {
    Bag<String> get(String termino);
    void add(String termino);
    void add(String[] terminos);
    Iterator<String> similares(String termino);
}
