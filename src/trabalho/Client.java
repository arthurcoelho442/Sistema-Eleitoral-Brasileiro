package Trabalho;

/*public class Client {
	public static void main(String[] args) {
		String arq= "teste.csv";
		Partidos teste = new Partidos(arq);
		Partidos.imprimirPartidos(teste); 
	}
}*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        try{
            String arq_nomeCand = "vitória-candidatos.csv";
            String arq_nomePart = "vitória-partidos.csv";
            FileInputStream   arquivo;
            try{
                arquivo = new FileInputStream("exemplos/ES/" + arq_nomeCand);
            }catch(Exception e){
                arquivo = new FileInputStream("exemplos/capitais/" + arq_nomeCand);
            }
            
            
            InputStreamReader entrada = new InputStreamReader(arquivo);
            BufferedReader    br      = new BufferedReader(entrada);
            
            Candidato[] candidatos = Candidatos.iniciaCandidatos(br);
            Partidos partidos = new Partidos(arq_nomePart, candidatos);
            //Inicio (Item 1)
            int qtd_Eleitos = Candidatos.numEleitos(candidatos);
            System.out.println("Numero de vagas: " + qtd_Eleitos);
            //Fim (Item 1)
            
            //Inicio (Item2)
            Candidato[] eleitos = Candidatos.candidatosEleitos(candidatos);
            Candidatos.ordenaCandidatos(eleitos, "votos_nominais");
            System.out.print("\nVereadores eleitos:");
            for(int i=0; i<qtd_Eleitos; i++)
                System.out.printf("\n%d - %s / %s(, %d)", i+1, eleitos[i].getNome(), eleitos[i].getNome_urna(), eleitos[i].getVotos_nominais());
            //Fim(Item2)
            
            //Inicio (Item 3)
            Candidatos.ordenaCandidatos(candidatos, "votos_nominais");
            System.out.print("\n\nCandidatos mais votados (em ordem decrescente de votação e respeitando numero de vagas):");
            for(int i=0; i<qtd_Eleitos; i++)
                System.out.printf("\n%d - %s / %s(, %d)", i+1, candidatos[i].getNome(), candidatos[i].getNome_urna(), candidatos[i].getVotos_nominais());
            //Fim (Item 3)
            
            //Inicio (Item 4)
            System.out.print("\n\nTeriam sido eleitos se a votação dosse majoritaria, e não foram eleitos(com sua posição no ranking de mais votados)");
            for (int i = 0, cont, j; i < qtd_Eleitos; i++) {
                for (j = 0, cont =0; j < qtd_Eleitos; j++)
                    if (Candidatos.comparaNome(eleitos[j], candidatos[i]) == 0) 
                        cont++;
                if (cont == 0)
                    System.out.printf("\n%d - %s / %s(, %d)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(), candidatos[i].getVotos_nominais());
            }
            //Fim (Item 4)
            
            //Inicio (Item 5)
            /*System.out.print("\n\nEleitos, que se beneficiaram do sistema proporcional");
            for (int i = 0, cont, j; i < qtd_Eleitos; i++) {
                for (j = 0, cont =0; j < qtd_Eleitos; j++)
                    if (Candidatos.comparaNome(eleitos[j], candidatos[i]) == 0) 
                        cont++;
                if (cont != 0)
                    System.out.printf("\n%d - %s / %s(, %d)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(), candidatos[i].getVotos_nominais());
            }
            //Fim (Item 5)*/
            
            //Inicio (Item 6)
            System.out.println("\n\nVotação dos partidos e número de candidatos eleitos:");
            System.out.println("CIDADANIA - 23, 22973 votos (21775 nominais e 1198 de legenda), 3 candidatos eleitos");
            int i=partidos.getQuantidade_partidos();
            int[][] aux = new int[i][4];
            for(int j=0; j<i; j++) {
            	aux[j][0]=partidos.getNumero_partido(i);
            	aux[j][1]=partidos.getVoto_legenda(i);
            	aux[j][2]=0;
            	for(int k=0; k<partidos.getQuantidade_candidatos(i); i++) {
            		aux[j][2]+=partidos.getQuantidade_votos_nominai(i, k);
            	}
            	aux[j][3]=aux[j][1]+aux[j][2];
            }
            int count=0;
            int valor=0;
            int posicaoAtual=0;
            int aux4=0;
            while(count != i) {
            	if(count==0) {
            		valor=aux[0][3];
            	}
            	
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