import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Ingrese el directorio del archivo de resultados");
        String stringResultados = entrada.nextLine();
        System.out.println("Ingrese el directorio del archivo de pronostico");
        String stringPronostico = entrada.nextLine();
        int puntaje = 0;
        boolean seEncontro = false;
        List<Equipo> equipos = new ArrayList<>();
        List<Partido> partidos = new ArrayList<>();
        List<Pronostico> pronosticos = new ArrayList<>();
        String[] datosResultado = new String[4];
        String[] datosPronostico = new String[5];
        for(String resultadosString : Files.readAllLines(Paths.get(stringResultados))){
            Equipo equipo1Buscar = null;
            Equipo equipo2Buscar = null;
            int i = 0;
            for (String contenido: resultadosString.split(",")){
                datosResultado[i] = contenido;
                i++;
            }
            for (Equipo equipo : equipos){
                if(equipo.getNombre().equals(datosResultado[0])){
                    seEncontro = true;
                    break;
                }
            }
            if(!seEncontro){
                equipos.add(new Equipo(datosResultado[0], null));
            }
            seEncontro = false;
            for (Equipo equipo : equipos){
                if(equipo.getNombre().equals(datosResultado[3])){
                    seEncontro = true;
                    break;
                }
            }
            if(!seEncontro){
                equipos.add(new Equipo(datosResultado[3], null));
            }
            seEncontro = false;

            for (Equipo equipo : equipos) {
                if (equipo.getNombre().equals(datosResultado[0])) {
                    equipo1Buscar = equipo;
                    break;
                }
            }
            for (Equipo equipo : equipos) {
                if (equipo.getNombre().equals(datosResultado[3])) {
                    equipo2Buscar = equipo;
                    break;
                }
            }
            int golesEquipo1 = Integer.parseInt(datosResultado[1]);
            int golesEquipo2 = Integer.parseInt(datosResultado[2]);
            partidos.add(new Partido(equipo1Buscar, equipo2Buscar,golesEquipo1, golesEquipo2));
        }
        for(String pronosticoString : Files.readAllLines(Paths.get(stringPronostico))){
            int i = 0;
            Equipo equipoBuscar = null;
            Partido partidoBuscar = null;
            ResultadoEnum resultado;
            for(String contenido : pronosticoString.split(",")){
                    datosPronostico[i] = contenido;
                    i++;
            }
            for (Equipo equipo : equipos) {
                if (equipo.getNombre().equals(datosPronostico[0])) {
                    equipoBuscar = equipo;
                    break;
                }
            }
            for (Partido partido : partidos){
                if(partido.getEquipo1().equals(equipoBuscar) || partido.getEquipo2().equals(equipoBuscar)){
                    partidoBuscar = partido;
                }
            }
            if(datosPronostico[1].equals("X")){
                resultado = ResultadoEnum.ganador;
            } else if (datosPronostico[2].equals("X")) {
                resultado = ResultadoEnum.empate;

            }else{
                resultado = ResultadoEnum.perdedor;
            }
            pronosticos.add(new Pronostico(partidoBuscar, equipoBuscar, resultado));
        }

        for (Pronostico pronostico : pronosticos){
            puntaje = puntaje + pronostico.calcularPuntos();
        }
        System.out.println("El puntaje alcanzado es de " + puntaje);
    }
}