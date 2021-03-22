package Trabalho;


import java.io.BufferedReader;
import java.io.IOException;

public abstract class Candidatos {
    
    public static Candidato[] iniciaCandidatos(BufferedReader br) throws IOException {
        Candidato[] candidatos = new Candidato[0];
        String linha;
        for (int i = 1, j; (linha = br.readLine()) != null; i++) {
            Candidato[] aux = new Candidato[i];
            for (j = 0; j < (i - 1); j++) {
                aux[j] = candidatos[j];
            }

            String[] palavras = linha.split(",");
            if(palavras[7] != "Válido") continue;
            Candidato candidato = new Candidato();

            try {
                candidato.setNumero(Integer.parseInt(palavras[0]));
                candidato.setVotos_nominais(Integer.parseInt(palavras[1]));
                candidato.setSituacao(palavras[2]);
                candidato.setNome(palavras[3]);
                candidato.setNome_urna(palavras[4]);
                candidato.setSexo(palavras[5]);
                candidato.setData_nasc(palavras[6]);
                candidato.setDestino_voto(palavras[7]);
                candidato.setNumero_partido(Integer.parseInt(palavras[8]));
            } catch (Exception e) {
                i--;
                continue;
            }

            aux[j] = candidato;
            candidatos = aux;
        }
        return candidatos;
    }
    public static void ordenaCandidatos(Candidato[] candidatos, String tipo){
        for(int i=0; i<candidatos.length-1; i++){
            boolean estaOrdenado = true;
            for(int j=0; j<candidatos.length-1; j++){
                if (tipo.compareToIgnoreCase("nome") == 0) {
                    if (Candidatos.comparaNome(candidatos[j], candidatos[j + 1]) == 1) {
                        Candidato aux = candidatos[j];
                        candidatos[j] = candidatos[j + 1];
                        candidatos[j + 1] = aux;
                        estaOrdenado = false;
                    }
                }else if (tipo.compareToIgnoreCase("votos_nominais") == 0) {
                    if (Candidatos.comparaVotos(candidatos[j], candidatos[j + 1]) == -1) {
                        Candidato aux = candidatos[j];
                        candidatos[j] = candidatos[j + 1];
                        candidatos[j + 1] = aux;
                        estaOrdenado = false;
                    }
                }
            }
            if(estaOrdenado)
                break;
        }
    }
    public static int comparaNome(Candidato a, Candidato b){
        if(a.getNome().compareToIgnoreCase(b.getNome()) == 0)
            return 0;
        if(a.getNome().compareToIgnoreCase(b.getNome()) > 0)
            return 1;
        else
            return -1;
    }
    
    public static int comparaVotos(Candidato a, Candidato b){
        if(a.getVotos_nominais() < b.getVotos_nominais())
            return -1;
        if(a.getVotos_nominais() > b.getVotos_nominais())
            return 1;
        else
            return 0;
    }
    
    public static int numEleitos(Candidato[] candidatos){
        int qtd=0;
        for(int i=0; i<candidatos.length; i++)
            if(candidatos[i].getSituacao().equals("Eleito"))
                qtd++;
        return qtd;
    }
    public static Candidato[] candidatosEleitos(Candidato[] candidatos){
        Candidato[] eleitos = new Candidato[numEleitos(candidatos)];
        for(int i=0, j=0; i<candidatos.length; i++)
            if(candidatos[i].getSituacao().equals("Eleito")){
                eleitos[j] = candidatos[i];
                j++;
            }
        return eleitos;
    }
}