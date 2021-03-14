package trabalho;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Cliente {
    public static void main(String[] args) {
        Candidato[] candidatos = new Candidato[0]; 
        try{
            String arq_nome = "aracaju-candidatos.csv";
            FileInputStream   arquivo;
            try{
                arquivo = new FileInputStream("Exemplos_entradas/csv-exemplos/ES/" + arq_nome);
            }catch(Exception e){
                arquivo = new FileInputStream("Exemplos_entradas/csv-exemplos/capitais/" + arq_nome);
            }
            
            InputStreamReader entrada = new InputStreamReader(arquivo);
            BufferedReader    br      = new BufferedReader(entrada);
            
            String linha;
            for(int i = 1, j; (linha = br.readLine()) != null; i++){
                Candidato[] aux = new Candidato[i];
                for(j=0; j < (i-1); j++)
                    aux[j] = candidatos[j];
                
                String[] palavras = linha.split(",");
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
            
            br.close();
            entrada.close();
            arquivo.close();
        }
        catch(Exception e){
            System.out.println("Erro ao ler o arquivo");
        }
    }
}