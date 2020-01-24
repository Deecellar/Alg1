import java.util.ArrayList;
import java.util.Iterator;

public class Bag<T> implements Iterable<T>{
    ArrayList<T> datos;

    public Bag(){
        datos = new ArrayList<T>();
    }

    public void add(T item){
        datos.add(item);
    }

    public int size(){
        return datos.size();
    }

    public boolean isEmpty(){
        return datos.isEmpty();
    }


    @Override
    public Iterator<T> iterator() {
        return datos.iterator();
    }
}
