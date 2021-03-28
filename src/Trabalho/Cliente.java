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
            FileInputStream arquivo;
            FileOutputStream arquivo_saida;
            try {
                arquivo = new FileInputStream("" + arq_nomeCand);
            } catch (FileNotFoundException e) {
                arquivo = new FileInputStream("exemplos/capitais/" + arq_nomeCand);
            } catch (Exception e) {
                arquivo = new FileInputStream("exemplos/ES/" + arq_nomeCand);
            }
            
            arquivo_saida = new FileOutputStream("saida.txt");
            PrintWriter saida = new PrintWriter(arquivo_saida);
            InputStreamReader entrada = new InputStreamReader(arquivo);
            BufferedReader br = new BufferedReader(entrada);

            Candidato[] candidatos = Candidatos.iniciaCandidatos(br);
            Partidos partidos = new Partidos(arq_nomePart, candidatos);
            
            //Inicio (Item 1)
            int qtd_Eleitos = Candidatos.numEleitos(candidatos);
            saida.printf("Numero de vagas: %d", qtd_Eleitos);
            //Fim (Item 1)

            //Inicio (Item2)
            Candidato[] eleitos = Candidatos.candidatosEleitos(candidatos);
            Candidatos.ordenaCandidatos(eleitos, "votos_nominais");
            saida.print("\n\nVereadores eleitos:");
            for (int i = 0; i < qtd_Eleitos; i++) {
                saida.printf("\n%d - %s / %s(, %d)", i + 1, eleitos[i].getNome(), eleitos[i].getNome_urna(), eleitos[i].getVotos_nominais());
            }
            //Fim(Item2)

            //Inicio (Item 3)
            Candidatos.ordenaCandidatos(candidatos, "votos_nominais");
            saida.print("\n\nCandidatos mais votados (em ordem decrescente de votação e respeitando numero de vagas):");
            for (int i = 0; i < qtd_Eleitos; i++) {
                saida.printf("\n%d - %s / %s(, %d)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(), candidatos[i].getVotos_nominais());
            }
            //Fim (Item 3)

            //Inicio (Item 4)
            saida.print("\n\nTeriam sido eleitos se a votação fosse majoritaria, e não foram eleitos(com sua posição no ranking de mais votados)");
            for (int i = 0, cont, j; i < qtd_Eleitos; i++) {
                for (j = 0, cont = 0; j < qtd_Eleitos; j++) {
                    if (Candidatos.comparaNome(eleitos[j], candidatos[i]) == 0) {
                        cont++;
                    }
                }
                if (cont == 0) {
                    saida.printf("\n%d - %s / %s(, %d)", i + 1, candidatos[i].getNome(), candidatos[i].getNome_urna(), candidatos[i].getVotos_nominais());
                }
            }
            //Fim (Item 4)

            //Inicio (Item 5)
            saida.print("\n\nEleitos, que se beneficiaram do sistema proporcional");
            for (int i = 0, cont=0, j; i < qtd_Eleitos; i++, cont=0) {
                for (j = 0; j < qtd_Eleitos; j++)
                    if (Candidatos.comparaNome(eleitos[i], candidatos[j]) == 0) {
                        cont++;
                        break;
                    }
                if(cont == 0)
                    for (j = 0; j < candidatos.length; j++)
                        if (Candidatos.comparaNome(eleitos[i], candidatos[j]) == 0)
                            saida.printf("\n%d - %s / %s(, %d)", j + 1, candidatos[j].getNome(), candidatos[j].getNome_urna(), candidatos[j].getVotos_nominais());
            }
            //Fim (Item 5)
            
            //Inicio (Item 6)
            int test = 0;
            saida.println("\n\nVotação dos partidos e número de candidatos eleitos:");
            int quantidade_partidos = partidos.getQuantidade_partidos();
            int[][] partidos_votos = new int[quantidade_partidos][4];
            for (int j = 0; j < quantidade_partidos; j++) {
                partidos_votos[j][0] = partidos.getNumero_partido(j);
                partidos_votos[j][1] = partidos.getVoto_legenda(j);
                partidos_votos[j][2] = 0;
                for (int k = 0; k < partidos.getQuantidade_candidatos(j); k++) {
                    partidos_votos[j][2] += partidos.getQuantidade_votos_nominai(j, k);
                }
                partidos_votos[j][3] = partidos_votos[j][1] + partidos_votos[j][2];
                test += partidos_votos[j][2];
            }
            int count = 0;
            int[] valor1 = new int[3];
            int[] valor2 = new int[3];
            int posicaoAtual = 0;
            while (true) {
                if (count == 0) {
                    valor1 = partidos_votos[0];
                }
                if (valor1[3] >= partidos_votos[posicaoAtual][3]) {
                    posicaoAtual++;
                } else {
                    valor2 = partidos_votos[posicaoAtual];
                    partidos_votos[posicaoAtual] = partidos_votos[count];
                    partidos_votos[count] = valor2;
                    valor1 = partidos_votos[count];
                    posicaoAtual++;
                }
                if (posicaoAtual == quantidade_partidos) {
                    count++;
                    if (count == quantidade_partidos) {
                        break;
                    }
                    posicaoAtual = count;
                    valor1 = partidos_votos[posicaoAtual];
                }
            }
            for (int j = 0; j < quantidade_partidos; j++) {
                saida.println(j + 1 + " - " + partidos.getSigla_partido(partidos_votos[j][0]) + " - " + partidos_votos[j][0]
                        + ", " + partidos_votos[j][3] + " votos (" + partidos_votos[j][2] + " nominais e " + partidos_votos[j][1] + " de legenda), "
                        + partidos.getNumero_eleitos(partidos_votos[j][0]) + " candidatos eleitos"); //tratar votos e voto e tratar casos de empates
            }			
            //Fim (Item 6)*/ 

            //Inicio (Item 7)
            int[][] maisVotados = new int[quantidade_partidos][3];
            for (int j = 0; j < quantidade_partidos; j++) {
                if (partidos.getVoto_legenda(j) == 0) {
                    continue;
                }
                maisVotados[j] = partidos.getEstudo_maisVotado(j);
            }
            posicaoAtual = 0;
            count = 0;
            while (true) {
                if (count == 0) {
                    valor1 = maisVotados[0];
                }
                if (valor1[2] >= maisVotados[posicaoAtual][2]) {
                    posicaoAtual++;
                } else {
                    valor2 = maisVotados[posicaoAtual];
                    maisVotados[posicaoAtual] = maisVotados[count];
                    maisVotados[count] = valor2;
                    valor1 = maisVotados[count];
                    posicaoAtual++;
                }
                if (posicaoAtual == quantidade_partidos) {
                    count++;
                    if (count == quantidade_partidos) {
                        break;
                    }
                    posicaoAtual = count;
                    valor1 = maisVotados[posicaoAtual];
                }
            }

            int[][] menosVotados = new int[quantidade_partidos][2];
            for (int j = 0; j < quantidade_partidos; j++) {
                if (partidos.getVoto_legenda(j) == 0) {
                    continue;
                }
                menosVotados[j] = partidos.getEstudo_menosVotado(partidos.getPosicao_lista(maisVotados[j][0]));
            }
            saida.println("\nPrimeiro e último colocados de cada partido:");
            for (int j = 0; j < quantidade_partidos; j++) {
                if (maisVotados[j][1] == 0) {
                    continue;
                }
                saida.print(j + 1 + " - " + partidos.getSigla_partido(maisVotados[j][0])
                        + " - " + maisVotados[j][0] + ", " + partidos.getNome_urna(partidos.getPosicao_lista(maisVotados[j][0]), maisVotados[j][1])
                        + " (" + maisVotados[j][1] + ", " + maisVotados[j][2] + " votos) / "
                        + partidos.getNome_urna(partidos.getPosicao_lista(maisVotados[j][0]), menosVotados[j][0]) + " (" + menosVotados[j][0] + ", "
                        + menosVotados[j][1] + "votos)\r\n");
            }
            //Fim (Item 7)*/ //tratar votos e voto e tratar casos de empates

            //Inicio (Item 8)
            saida.println("\nEleitos, por faixa etária (na data da eleção):");
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
            saida.println(porcentagem);
            saida.println("      Idade < 30: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

            count = 0;
            for (int i = 0; i < idades.length; i++) {
                for (int j = 1; j < idades[i].length; j++) {
                    if (idades[i][j] >= 30 && idades[i][j] < 40) {
                        count++;
                    }
                }
            }
            porcentagem = ((float) count / (float) numero_total_idades) * 100;
            saida.println("30 >= Idade < 40: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

            count = 0;
            for (int i = 0; i < idades.length; i++) {
                for (int j = 1; j < idades[i].length; j++) {
                    if (idades[i][j] >= 40 && idades[i][j] < 50) {
                        count++;
                    }
                }
            }
            porcentagem = ((float) count / (float) numero_total_idades) * 100;
            saida.println("40 >= Idade < 50: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

            count = 0;
            for (int i = 0; i < idades.length; i++) {
                for (int j = 1; j < idades[i].length; j++) {
                    if (idades[i][j] >= 50 && idades[i][j] < 60) {
                        count++;
                    }
                }
            }
            porcentagem = ((float) count / (float) numero_total_idades) * 100;
            saida.println("50 >= Idade < 60: " + count + " (" + String.format("%.2f", porcentagem) + "%)");

            count = 0;
            for (int i = 0; i < idades.length; i++) {
                for (int j = 1; j < idades[i].length; j++) {
                    if (idades[i][j] >= 60) {
                        count++;
                    }
                }
            }
            porcentagem = ((float) count / (float) numero_total_idades) * 100;
            saida.println("60 >= Idade     : " + count + " (" + String.format("%.2f", porcentagem) + "%)");
            //Fim (Item 8)

            //Inicio (Item 9)
            int[] quantidade_sexo = partidos.getestudo_sexo();
            saida.println("\nEleitos, por sexo:");
            porcentagem = ((float) quantidade_sexo[0] / (float) (quantidade_sexo[0] + (float) quantidade_sexo[1])) * 100;
            saida.println("Feminino: " + quantidade_sexo[0] + " (" + String.format("%.2f", porcentagem) + "%)");
            porcentagem = ((float) quantidade_sexo[1] / (float) (quantidade_sexo[0] + (float) quantidade_sexo[1])) * 100;
            saida.println("Masculino: " + quantidade_sexo[1] + " (" + String.format("%.2f", porcentagem) + "%)");
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
            saida.println("\nTotal de votos válidos: " + votos_totais);
            porcentagem = (float) votos_nominais * 100 / (float) votos_totais;
            saida.println("Total de votos nominais: " + votos_nominais + "(" + String.format("%.2f", porcentagem) + "%)");
            porcentagem = (float) votos_legendas * 100 / (float) votos_totais;
            saida.println("Total de votos legendas: " + votos_legendas + "(" + String.format("%.2f", porcentagem) + "%)");

            saida.close();
            entrada.close();
            arquivo.close();
            arquivo_saida.close();

        } catch (Exception e) {
            try{
                FileOutputStream arquivo = new FileOutputStream("log.txt");
                PrintWriter saida = new PrintWriter(arquivo);

                System.out.println("Erro ao ler o arquivo");
                saida.println(e.getMessage() + "\n");
                e.printStackTrace(saida);
                saida.close();
            }catch (FileNotFoundException ex){
                System.out.println("Erro ao ler o arquivo");
            }
            
        }
    }
}
