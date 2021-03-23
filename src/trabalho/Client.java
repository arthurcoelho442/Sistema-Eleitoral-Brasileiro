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
			int quantidade_partidos=partidos.getQuantidade_partidos();
			int[][] aux = new int[quantidade_partidos][4];
			for(int j=0; j<quantidade_partidos; j++) {
				aux[j][0]=partidos.getNumero_partido(j);
				aux[j][1]=partidos.getVoto_legenda(j);
				aux[j][2]=0;
				for(int k=0; k<partidos.getQuantidade_candidatos(j); k++) {
					aux[j][2]+=partidos.getQuantidade_votos_nominai(j, k);
				}
				aux[j][3]=aux[j][1]+aux[j][2];
			}
			int count=0;
			int[] valor1 = new int[3];
			int[] valor2 = new int[3];
			int posicaoAtual=0;
			while(true) {
				if(count==0) {
					valor1=aux[0];
				}
				if(valor1[3] >= aux[posicaoAtual][3]) {
					posicaoAtual++;
				} else {
					valor2 = aux[posicaoAtual];
					aux[posicaoAtual]=aux[count];
					aux[count]=valor2;
					valor1=aux[count];
					posicaoAtual++;
				}
				if(posicaoAtual==quantidade_partidos) {
					count++;
					if(count==quantidade_partidos) break;
					posicaoAtual=count;
					valor1=aux[posicaoAtual];
				}
			}
			for(int j=0; j<quantidade_partidos; j++)
				System.out.println( j+1 + " - " + partidos.getSigla_partido(aux[j][0]) + " - " + aux[j][0] +
						", " + aux[j][3] + " votos ("+ aux[j][2] + " nominais e "+ aux[j][1] + " de legenda), " + 
						partidos.getNumero_eleitos(aux[j][0]) + " candidatos eleitos"); //tratar votos e voto e tratar casos de empates
			//Fim (Item 6)*/ 

			//Inicio (Item 7)
			int[][] maisVotados = new int[quantidade_partidos][3];
			for(int j=0; j < quantidade_partidos; j++) {
				if(partidos.getVoto_legenda(j) == 0) continue;
				maisVotados[j]=partidos.getEstudo_maisVotado(j);
			}
			int[]valor3 = new int[3];
			int[]valor4 = new int[3];
			posicaoAtual=0;
			count=0;
			while(true) {
				if(count==0) {
					valor1=maisVotados[0];
				}
				if(valor1[2] >= maisVotados[posicaoAtual][2]) {
					posicaoAtual++;
				} else {
					valor2 = maisVotados[posicaoAtual];
					maisVotados[posicaoAtual]=maisVotados[count];
					maisVotados[count]=valor2;
					valor1=maisVotados[count];
					posicaoAtual++;
				}
				if(posicaoAtual==quantidade_partidos) {
					count++;
					if(count==quantidade_partidos) break;
					posicaoAtual=count;
					valor1=maisVotados[posicaoAtual];
				}
			}
			
			
			int[][] menosVotados = new int[quantidade_partidos][2];
			for(int j=0; j < quantidade_partidos; j++) {
				if(partidos.getVoto_legenda(j) == 0) continue;
				menosVotados[j]=partidos.getEstudo_menosVotado(partidos.getPosicao_lista(maisVotados[j][0]));
			}
			System.out.println("\n\nPrimeiro e último colocados de cada partido:");
			for(int j=0; j<quantidade_partidos; j++) {
				if(maisVotados[j][1] == 0) continue;
				System.out.print(j+1+ " - "+ partidos.getSigla_partido(maisVotados[j][0]) + 
						" - " + maisVotados[j][0] + ", "+ partidos.getNome_urna(partidos.getPosicao_lista(maisVotados[j][0]), maisVotados[j][1]) + 
						" (" + maisVotados[j][1] + ", " + maisVotados[j][2] + " votos) / "+
						partidos.getNome_urna(partidos.getPosicao_lista(maisVotados[j][0]), menosVotados[j][0]) + " (" + menosVotados[j][0] + ", " 
						+ menosVotados[j][1] + "votos)\r\n");	
			}
			//Fim (Item 7)*/ //tratar votos e voto e tratar casos de empates

				entrada.close();
			arquivo.close();

		}
		catch(Exception e){
			System.out.println("Erro ao ler o arquivo");
		}
	}
}