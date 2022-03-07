package Trabalho;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Cliente {

	public static void main(String[] args){
		try {
			String arq_nomeCand = args[0];
			String arq_nomePart = args[1];
			String data = args[2];
			FileInputStream arquivo_Cand;
			FileInputStream arquivo_Part;
			FileOutputStream arquivo_Saida;

			try {
				arquivo_Cand = new FileInputStream("" + arq_nomeCand);
			}catch(Exception e){
				try {
					arquivo_Cand = new FileInputStream("exemplos/capitais/" + arq_nomeCand);
				}catch (Exception ex) {
					arquivo_Cand = new FileInputStream("exemplos/ES/" + arq_nomeCand);
				}
			}
			InputStreamReader entrada_Cand = new InputStreamReader(arquivo_Cand);
			BufferedReader br_Cand = new BufferedReader(entrada_Cand);
			Candidato[] candidatos = Candidatos.iniciaCandidatos(br_Cand);

			try {
				arquivo_Part = new FileInputStream("" + arq_nomePart);
			}catch(Exception e){
				try {
					arquivo_Part = new FileInputStream("exemplos/capitais/" + arq_nomePart);
				}catch (Exception ex) {
					arquivo_Part = new FileInputStream("exemplos/ES/" + arq_nomePart);
				}
			}
			InputStreamReader entrada_Part = new InputStreamReader(arquivo_Part);
			BufferedReader br_Part = new BufferedReader(entrada_Part);
			Partidos partidos = new Partidos(br_Part, candidatos);

			//Inicio (Item 1)
			int qtd_Eleitos = Candidatos.numEleitos(candidatos);
			System.out.printf("Número de vagas: %d", qtd_Eleitos);
			//Fim (Item 1)

			//Inicio (Item2)
			Candidato[] eleitos = Candidatos.candidatosEleitos(candidatos);
			Candidatos.ordenaCandidatos(eleitos, "votos_nominais", data);
			System.out.print("\n\nVereadores eleitos:");
			for (int i = 0; i < qtd_Eleitos; i++) {
				if(eleitos[i].getVotos_nominais() > 9)
					System.out.printf("\n%d - %s / %s (%s, %d votos)", i + 1, eleitos[i].getNome(), eleitos[i].getNome_urna(),partidos.getSigla_partido(eleitos[i].getNumero_partido()), eleitos[i].getVotos_nominais());
				else
					System.out.printf("\n%d - %s / %s (%s, %d voto)", i + 1, eleitos[i].getNome(), eleitos[i].getNome_urna(),partidos.getSigla_partido(eleitos[i].getNumero_partido()), eleitos[i].getVotos_nominais());
			}
			//Fim(Item2)

			//Inicio (Item 3)
			Candidatos.ordenaCandidatos(candidatos, "votos_nominais", data);
			System.out.print("\n\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
			for (int i = 0; i < qtd_Eleitos; i++) {
				if(candidatos[i].getVotos_nominais() > 9)
					System.out.printf("\n%d - %s / %s (%s, %d votos)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(),partidos.getSigla_partido(candidatos[i].getNumero_partido()), candidatos[i].getVotos_nominais());
				else
					System.out.printf("\n%d - %s / %s (%s, %d voto)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(),partidos.getSigla_partido(candidatos[i].getNumero_partido()), candidatos[i].getVotos_nominais());
			}
			//Fim (Item 3)

			//Inicio (Item 4)
			System.out.print("\n\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");
			for (int i = 0, cont, j; i < qtd_Eleitos; i++) {
				for (j = 0, cont = 0; j < qtd_Eleitos; j++) {
					if (Candidatos.comparaNome(eleitos[j], candidatos[i]) == 0) {
						cont++;
					}
				}
				if (cont == 0) {
					if(candidatos[i].getVotos_nominais() > 9)
						System.out.printf("\n%d - %s / %s (%s, %d votos)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(),partidos.getSigla_partido(candidatos[i].getNumero_partido()), candidatos[i].getVotos_nominais());
					else
						System.out.printf("\n%d - %s / %s (%s, %d voto)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(),partidos.getSigla_partido(candidatos[i].getNumero_partido()), candidatos[i].getVotos_nominais());
				}
			}
			//Fim (Item 4)

			//Inicio (Item 5)
			System.out.print("\n\nEleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");
			for (int i = 0, cont=0, j; i < qtd_Eleitos; i++, cont=0) {
				for (j = 0; j < qtd_Eleitos; j++)
					if (Candidatos.comparaNome(eleitos[i], candidatos[j]) == 0) {
						cont++;
						break;
					}
				if(cont == 0)
					for (j = 0; j < candidatos.length; j++)
						if (Candidatos.comparaNome(eleitos[i], candidatos[j]) == 0){
							if(candidatos[j].getVotos_nominais() > 9)
								System.out.printf("\n%d - %s / %s (%s, %d votos)", j + 1, candidatos[j].getNome(), candidatos[j].getNome_urna(),partidos.getSigla_partido(candidatos[j].getNumero_partido()), candidatos[j].getVotos_nominais());
							else
							System.out.printf("\n%d - %s / %s (%s, %d voto)", j + 1, candidatos[j].getNome(), candidatos[j].getNome_urna(),partidos.getSigla_partido(candidatos[j].getNumero_partido()), candidatos[j].getVotos_nominais());
						}
			}
			//Fim (Item 5)

			//Inicio (Item 6)
			int test=0;
			System.out.printf("\n\nVotação dos partidos e número de candidatos eleitos:\r\n");
			int quantidade_partidos=partidos.getQuantidade_partidos();
			int[][] partidos_votos = new int[quantidade_partidos][4];
			for(int j=0; j<quantidade_partidos; j++) {
				partidos_votos[j][0]=partidos.getNumero_partido(j);
				partidos_votos[j][1]=partidos.getVoto_legenda(j);
				partidos_votos[j][2]=0;
				for(int k=0; k<partidos.getQuantidade_candidatos(j); k++) {
					partidos_votos[j][2]+=partidos.getQuantidade_votos_nominai(j, k);
				}
				partidos_votos[j][3]=partidos_votos[j][1]+partidos_votos[j][2];
				test+=partidos_votos[j][2];
			}
			int count=0;
			int[] valor1 = new int[3];
			int[] valor2 = new int[3];
			int posicaoAtual=0;
			while(true) {
				if(count==0) {
					valor1=partidos_votos[0];
				}
				if(valor1[3] > partidos_votos[posicaoAtual][3]) {
					posicaoAtual++;
				} else if( valor1[3] < partidos_votos[posicaoAtual][3]){
					valor2 = partidos_votos[posicaoAtual];
					partidos_votos[posicaoAtual]=partidos_votos[count];
					partidos_votos[count]=valor2;
					valor1=partidos_votos[count];
					posicaoAtual++;
				}
				else if(valor1[3] == partidos_votos[posicaoAtual][3]) {
					if(valor1[0] >= partidos_votos[posicaoAtual][0]) {
						posicaoAtual++;	
					}
					else {
						valor2 = partidos_votos[posicaoAtual];
						partidos_votos[posicaoAtual]=partidos_votos[count];
						partidos_votos[count]=valor2;
						valor1=partidos_votos[count];
						posicaoAtual++;
					}
				}
				if(posicaoAtual==quantidade_partidos) {
					count++;
					if(count==quantidade_partidos) break;
					posicaoAtual=count;
					valor1=partidos_votos[posicaoAtual];
				}
			}
			for(int j=0; j<quantidade_partidos; j++){
				System.out.printf( j+1 + " - " + partidos.getSigla_partido(partidos_votos[j][0]) + " - " + partidos_votos[j][0] +
						", ");

			    if(partidos_votos[j][3]>1 ) System.out.printf(partidos_votos[j][3] + " votos (");
			    else System.out.printf(partidos_votos[j][3] + " voto (");

			    if( partidos_votos[j][2]> 1) System.out.printf( partidos_votos[j][2] + " nominais e ");
                else System.out.printf( partidos_votos[j][2] + " nominal e ");

                System.out.printf(partidos_votos[j][1] + " de legenda), ");

				if(partidos.getNumero_eleitos(partidos_votos[j][0]) > 1) System.out.printf(partidos.getNumero_eleitos(partidos_votos[j][0]) + " candidatos eleitos\r\n");
                else System.out.printf(partidos.getNumero_eleitos(partidos_votos[j][0]) + " candidato eleito\r\n");
            }
			//Fim (Item 6)

			//Inicio (Item 7)
			int[][] maisVotados = new int[quantidade_partidos][3];
			for(int j=0; j < quantidade_partidos; j++) {
				if(partidos.getVoto_legenda(j) == 0) continue;
				maisVotados[j]=partidos.getEstudo_maisVotado(j);
			}
			posicaoAtual=0;
			count=0;
			while(true) {
				if(count==0) {
					valor1=maisVotados[0];
				}
				if(valor1[2] > maisVotados[posicaoAtual][2]) {
					posicaoAtual++;
				} else if(valor1[2] < maisVotados[posicaoAtual][2])  {
					valor2 = maisVotados[posicaoAtual];
					maisVotados[posicaoAtual]=maisVotados[count];
					maisVotados[count]=valor2;
					valor1=maisVotados[count];
					posicaoAtual++;
				}
				else if(valor1[2] == maisVotados[posicaoAtual][2]) {
					if(valor1[0] < maisVotados[posicaoAtual][0]) {
						posicaoAtual++;	
					}
					else {
						valor2 = maisVotados[posicaoAtual];
						maisVotados[posicaoAtual]=maisVotados[count];
						maisVotados[count]=valor2;
						valor1=maisVotados[count];
						posicaoAtual++;
					}
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
				if(partidos.getVoto_legenda(partidos.getPosicao_lista(maisVotados[j][0])) == 0) continue;
				menosVotados[j]=partidos.getEstudo_menosVotado(partidos.getPosicao_lista(maisVotados[j][0]));
			}
			System.out.printf("\nPrimeiro e último colocados de cada partido:\r\n");
			for(int j=0; j<quantidade_partidos; j++) {
				if(maisVotados[j][1] == 0) continue;
				    System.out.printf(j+1+ " - "+ partidos.getSigla_partido(maisVotados[j][0]) + 
						" - " + maisVotados[j][0] + ", "+ partidos.getNome_urna(partidos.getPosicao_lista(maisVotados[j][0]), maisVotados[j][1]) + 
						" (" + maisVotados[j][1] + ", ");

				if(maisVotados[j][2]>1)
                    System.out.printf( maisVotados[j][2] + " votos) / ");
				else
                    System.out.printf( maisVotados[j][2] + " voto) / ");

                    System.out.printf( partidos.getNome_urna(partidos.getPosicao_lista(maisVotados[j][0]), menosVotados[j][0]) +
						" (" + menosVotados[j][0] + ", ");	

				if(menosVotados[j][1]>1)
                    System.out.printf( menosVotados[j][1] + " votos)\r\n");
				else
                    System.out.printf( menosVotados[j][1] + " voto)\r\n");
			}
			//Fim (Item 7)

			//Inicio (Item 8)
			System.out.println("\nEleitos, por faixa etária (na data da eleição):");
			int[][] idades = partidos.getIdade_por_eleito(" 15/11/2020");
			int numero_total_idades = 0;
			count = 0;

			for (int i = 0; i < idades.length; i++) {
				numero_total_idades += idades[i].length - 1;
			}
			for (int i = 0; i < idades.length; i++) {
				for (int j = 1; j < idades[i].length; j++) {
					if (idades[i][j] < 30) {
						count++;
					}
				}
			}
			float porcentagem = ((float) count / (float) numero_total_idades) * 100;
			System.out.println("      Idade < 30: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

			count = 0;
			for (int i = 0; i < idades.length; i++) {
				for (int j = 1; j < idades[i].length; j++) {
					if (idades[i][j] >= 30 && idades[i][j] < 40) {
						count++;
					}
				}
			}
			porcentagem = ((float) count / (float) numero_total_idades) * 100;
			System.out.println("30 <= Idade < 40: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

			count = 0;
			for (int i = 0; i < idades.length; i++) {
				for (int j = 1; j < idades[i].length; j++) {
					if (idades[i][j] >= 40 && idades[i][j] < 50) {
						count++;
					}
				}
			}
			porcentagem = ((float) count / (float) numero_total_idades) * 100;
			System.out.println("40 <= Idade < 50: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

			count = 0;
			for (int i = 0; i < idades.length; i++) {
				for (int j = 1; j < idades[i].length; j++) {
					if (idades[i][j] >= 50 && idades[i][j] < 60) {
						count++;
					}
				}
			}
			porcentagem = ((float) count / (float) numero_total_idades) * 100;
			System.out.println("50 <= Idade < 60: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

			count = 0;
			for (int i = 0; i < idades.length; i++) {
				for (int j = 1; j < idades[i].length; j++) {
					if (idades[i][j] >= 60) {
						count++;
					}
				}
			}
			porcentagem = ((float) count / (float) numero_total_idades) * 100;
			System.out.println("60 <= Idade     : " + count + " (" + String.format("%.2f", porcentagem) + "%)");
			//Fim (Item 8)

			//Inicio (Item 9)
			int[] quantidade_sexo = partidos.getestudo_sexo();
			System.out.println("\nEleitos, por sexo:");
			porcentagem = ((float) quantidade_sexo[0] / (float) (quantidade_sexo[0] + (float) quantidade_sexo[1])) * 100;
			System.out.println("Feminino:  " + quantidade_sexo[0] + " (" + String.format("%.2f", porcentagem) + "%)");
			porcentagem = ((float) quantidade_sexo[1] / (float) (quantidade_sexo[0] + (float) quantidade_sexo[1])) * 100;
			System.out.println("Masculino: " + quantidade_sexo[1] + " (" + String.format("%.2f", porcentagem) + "%)");
			//Fim(9)
			//Inicio(10)
			int votos_nominais = 0;
			int votos_legendas = 0;
			int votos_totais = 0;
			for (int k = 0; k < quantidade_partidos; k++) {
				votos_nominais += partidos_votos[k][2];
				votos_legendas += partidos_votos[k][1];
			}
			votos_totais += votos_nominais + votos_legendas;
			System.out.println("\nTotal de votos válidos:    " + votos_totais);
			porcentagem = (float) votos_nominais * 100 / (float) votos_totais;
			System.out.println("Total de votos nominais:   " + votos_nominais + " (" + String.format("%.2f", porcentagem) + "%)");
			porcentagem = (float) votos_legendas * 100 / (float) votos_totais;
			System.out.println("Total de votos de Legenda: " + votos_legendas + " (" + String.format("%.2f", porcentagem) + "%)\n");

			entrada_Cand.close();
			entrada_Part.close();
			arquivo_Cand.close();
			arquivo_Part.close();

		} catch (Exception e) {
                System.out.println("Erro ao ler o arquivo");
                System.out.println(e.getMessage() + "\n");
                e.printStackTrace(System.out);
            }
	}
}
