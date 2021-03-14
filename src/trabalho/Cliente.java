package trabalho;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Cliente {

    public static void main(String[] args) {
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
            
            do{
                linha = br.readLine();
                if(linha != null){
                    String[] palavras = linha.split(";");
                    
                    for(String palavra: palavras)
                        System.out.println("Saida: " + palavra + ";");
                }
            } while (linha != null);
            
            br.close();
            entrada.close();
            arquivo.close();
        }
        catch(Exception e){
            System.out.println("Erro ao ler o arquivo");
        }
    }
}