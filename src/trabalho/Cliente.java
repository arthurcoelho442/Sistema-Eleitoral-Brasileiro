package trabalho;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Cliente {
    public static void main(String[] args) {
        try{
            String arq_nome = "vitória-candidatos.csv";
            FileInputStream   arquivo;
            try{
                arquivo = new FileInputStream("Exemplos_entradas/csv-exemplos/ES/" + arq_nome);
            }catch(Exception e){
                arquivo = new FileInputStream("Exemplos_entradas/csv-exemplos/capitais/" + arq_nome);
            }
            
            InputStreamReader entrada = new InputStreamReader(arquivo);
            BufferedReader    br      = new BufferedReader(entrada);
            
            Candidato[] candidatos = candidatos = Candidatos.iniciaCandidatos(br);
            
            //Inicio (Item 1)
            int qtd_Eleitos = Candidatos.numEleitos(candidatos);
            System.out.println("Numero de vagas: " + qtd_Eleitos);
            //Fim (Item 1)
            
            //Inicio (Item2)
            Candidato[] eleitos = Candidatos.candidatosEleitos(candidatos);
            Candidatos.ordenaCandidatos(eleitos, "votos_nominais");
            System.out.print("\nVereadores eleitos:");
            for(int i=0; i<qtd_Eleitos; i++)
                System.out.printf("\n%d - %s / %s(, %d)"
                        , i+1
                        , eleitos[i].getNome()
                        , eleitos[i].getNome_urna()
                        , eleitos[i].getVotos_nominais());
            //Fim(Item2)
            
            //Inicio (Item 3)
            Candidatos.ordenaCandidatos(candidatos, "votos_nominais");
            System.out.print("\n\nCandidatos mais votados (em ordem decrescente de votação e respeitando numero de vagas):");
            for(int i=0; i<qtd_Eleitos; i++)
                System.out.printf("\n%d - %s / %s(, %d)"
                        , i+1
                        , candidatos[i].getNome()
                        , candidatos[i].getNome_urna()
                        , candidatos[i].getVotos_nominais());
            //Fim (Item 3)
            
            //Inicio (Item 4)
            System.out.print("\n\nTeriam sido eleitos se a votação dosse majoritaria, e não foram eleitos(com sua posição no ranking de mais votados)");
            for (int i = 0, cont, j; i < qtd_Eleitos; i++) {
                for (j = 0, cont =0; j < qtd_Eleitos; j++)
                    if (Candidatos.comparaNome(eleitos[j], candidatos[i]) == 0) 
                        cont++;
                if (cont == 0)
                    System.out.printf("\n%d - %s / %s(, %d)",
                            i + 1,
                            candidatos[i].getNome(),
                            candidatos[i].getNome_urna(),
                            candidatos[i].getVotos_nominais());
            }
            //Fim (Item 4)
            
            //Inicio (Item 5)
            System.out.print("\n\nEleitos, que se beneficiaram do sistema proporcional");
            for (int i = 0, cont, j; i < qtd_Eleitos; i++) {
                for (j = 0, cont =0; j < qtd_Eleitos; j++)
                    if (Candidatos.comparaNome(eleitos[j], candidatos[i]) == 0) 
                        cont++;
                if (cont != 0)
                    System.out.printf("\n%d - %s / %s(, %d)",
                            i + 1,
                            candidatos[i].getNome(),
                            candidatos[i].getNome_urna(),
                            candidatos[i].getVotos_nominais());
            }
            //Fim (Item 5)
            
            
            
            br.close();
            entrada.close();
            arquivo.close();
            
        }
        catch(Exception e){
            System.out.println("Erro ao ler o arquivo");
        }
    }
}