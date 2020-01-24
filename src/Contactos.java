import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;

public class Contactos {
    class Contacto {
        private final String Nombre;
        private final String Telefono;


        public Contacto(String nombre, String telefono) {
            Nombre = nombre;
            Telefono = telefono;
        }
        public String getTelefono() {
            return Telefono;
        }


        public String getNombre() {
            return Nombre;
        }

        @Override
        public String toString(){
            if(Nombre.isEmpty() || Telefono.isEmpty())
                return "No se ha encontrado el contacto";
            else
                return "El contacto es " + Nombre + " y su numero es " + Telefono;
        }

    }

    private ArrayList<Contacto> contactos;
    private String regex = ",";
    private String ruta = "";
    private final BusquedasSimilares busquedasSimilares = new BusquedasSimilares();

    public Contactos() {
        contactos = new ArrayList<>();
    }

    public Contactos(String regex) {
        this.regex = regex;
        contactos = new ArrayList<>();
    }

    public static void main(String[] args) {
        StdOut.println("Hay dos archivos de ejemplo: \n ejemplo.txt que tiene 300 contactos y ejemploGrande.txt que tiene 100.000 contactos");
        Contactos contactos = new Contactos();
        StdOut.println("Escriba la ruta a el archivo a cargar (por defecto carga ejemplo.txt)");

        String CaminoContactos = StdIn.readLine();
        if (CaminoContactos.isEmpty())
            CaminoContactos = "ejemplo.txt";
        StdOut.println("Escriba el separador del archivo (Por defecto es ,)");
        String Separador = StdIn.readLine();
        if (!Separador.isEmpty())
            contactos = new Contactos(Separador);

        contactos.loadConctacts(CaminoContactos);
        contactos.menu();
    }

    private void loadConctacts(String path) {
        StdOut.println("Cargando Datos...");

        In ini = new In();
        boolean error = false;
        try {
            ini = new In(path);
        } catch (Exception e) {
            StdOut.println("Archivo no encontrado");
            error = true;
        }

        if (error)
            return;
        ruta = path;

        String[] file = ini.readAllLines();
        for (String str :
                file) {
            contactos.add(new Contacto(str.split(regex)[0], str.split(regex)[1]));
        }
    }

    private void menu() {
        StdOut.println("Analizando Datos...");
        contactos.forEach((Contacto cont) -> {
            busquedasSimilares.add(cont.getNombre());
        });
        boolean end = false;

        boolean comandoEjecutado;
        while (!end) {
            if (contactos.size() <= 0) {
                StdOut.println("No hay ningun contacto, cargue un archivo de contactos porfavor o agregue uno");
            }
            StdOut.println("Ingrese el contacto a buscar (escribir ? para ver los comandos disponibles)");
            String terminoDeBusqueda = StdIn.readString();
            if (terminoDeBusqueda.equals("salir"))
                end = ComandoEjecutado(terminoDeBusqueda);
            comandoEjecutado = ComandoEjecutado(terminoDeBusqueda);
            if (!comandoEjecutado) {
                StdOut.println("Busando Coherencias...");
                Iterator<String> Similares = busquedasSimilares.similares(terminoDeBusqueda);
                ArrayList<String> resultados = new ArrayList<>();
                while (Similares.hasNext()) {
                    resultados.add(Similares.next());
                }
                if (resultados.size() > 0) {
                    Iterator it = resultados.iterator();
                    for (int i = 1; it.hasNext(); i++) {
                        StdOut.println(i + ") " + it.next());
                    }
                    StdOut.println("Cual es el contacto cual va a elegir (Solo valores numericos)");
                    String input = StdIn.readString();
                    int index = -1;
                    if (tryParseInt(input)) {
                        index = Integer.parseInt(input);
                    }
                    if (index > resultados.size() || index < 0) {
                        StdOut.println("El dato no esta dentro del rango");
                    } else {
                        String resultado = resultados.get(index - 1);
                        Contacto res = new Contacto("", "");
                        for (Contacto c :
                                contactos) {
                            if (c.getNombre() == null ? resultado == null : c.getNombre().equals(resultado)) {
                                res = c;
                            } else {
                            }
                        }
                        StdOut.println(res.toString());
                    }

                } else {
                    StdOut.println("No se ha encontrado contactos");
                }
            }
        }

    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean ComandoEjecutado(String comando) {
        switch (comando) {
            case "tamaño":
                StdOut.println("El tamaño de los contactos son " + contactos.size());
                return true;
            case "?":
                StdOut.println("Comando tamaño: Muestra la cantidad de contactos cargados\n Comando salir: Sale de la aplicacion \n Comando mostrar: Muestra todos los contactos disponibles \n Comando ruta: muestra la ruta del archivo cargado\n Comando añadir: Añade nueva entrada a la lista de contactos \n Comando cargar: carga una nueva lista de contactos \n Comando ?: Muestra esta pantalla de ayuda ");
                return true;
            case "salir":
                return true;
            case "mostrar":

                contactos.forEach((cont) -> {
                    StdOut.println(cont.toString());
        });
                return true;
            case "cargar":
                StdOut.println("Ingrese Ruta de carga");
                contactos = new ArrayList<>();
                loadConctacts(StdIn.readString());
                StdOut.println("Analizando datos...");
                contactos.forEach((cont) -> {
                    busquedasSimilares.add(cont.getNombre());
        });
                return true;
            case "añadir":
                StdOut.println("Ingrese el Nombre Completo");
                String nombre = StdIn.readString();
                StdOut.println("Ingrese el Telefono");
                String telefono = StdIn.readString();
                contactos.add(new Contacto(nombre, telefono));
                if (ruta.equals(""))
                    ruta = "temp" + Math.random() * Integer.MAX_VALUE + ".txt";

                Out out = new Out(ruta);
                contactos.forEach((cont) -> {
                    out.println(cont.getNombre() + "," + cont.getTelefono());
        });
                out.println(nombre + "," + telefono);
                busquedasSimilares.add(nombre);
                return true;
            case "ruta":
                StdOut.println(ruta);
                return true;
            default:
                return false;
        }
    }
}
