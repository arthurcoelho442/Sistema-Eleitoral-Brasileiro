package Trabalho;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Partidos {

    private List<Partido> lista = new ArrayList<>();

    Partidos(String Caminho, Candidato[] candidatos) {
        String[] part;
        part = Read(Caminho);
        if (part.length != 0) // ver caso em que o arquivo eh vazio, nesse caso teremos que try/catch;
        {
            for (int i = 4; i <= part.length; i += 4) {
                lista.add(new Partido(part[i - 4], part[i - 3], part[i - 2], part[i - 1], candidatos));
            }
        }
    }

    /*static void imprimirPartidos(Partidos part){
			for(int i=0; i< part.lista.size(); i++) {
				Partido.imprimirPartdio(part.lista.get(i));
			}
		}*/
    public static String[] Read(String Caminho) {
        String conteudo = "";
        try {
            FileReader arq;
            try {
                arq = new FileReader("exemplos/ES/" + Caminho);
            } catch (Exception e) {
                arq = new FileReader("exemplos/capitais/" + Caminho);
            }
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            try {
                linha = lerArq.readLine();
                linha = lerArq.readLine();
                int i = 0;
                String test1[] = new String[0];
                String test2[] = new String[0];
                while (linha != null) {
                    i += 4;
                    test1 = new String[i];
                    conteudo = linha;
                    test1[i - 4] = conteudo.split(",")[0];
                    test1[i - 3] = conteudo.split(",")[1];
                    test1[i - 2] = conteudo.split(",")[2];
                    test1[i - 1] = conteudo.split(",")[3];
                    linha = lerArq.readLine();
                    for (int k = 0; k < test1.length - 4; k++) {
                        test1[k] = test2[k];
                    }
                    test2 = test1;
                }
                arq.close();
                return test2;
            } catch (IOException ex) {
                System.out.println("Erro: nao foi possível ler o arquivo!");
                return null;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: Arquivo nao encontrado!");
            return null;
        }
    }

    public static boolean Write(String Caminho, String Texto) {
        try {
            FileWriter arq = new FileWriter(Caminho);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(Texto);
            gravarArq.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getQuantidade_partidos() {
        return this.lista.size();
    }

    public int getNumero_partido(int posicao_partido) {
        return this.lista.get(posicao_partido).getNumero_partido();
    }

    public int getVoto_legenda(int posicao_partido) {
        return this.lista.get(posicao_partido).getVotos_legenda();
    }

    public int getQuantidade_candidatos(int posicao_partido) {
        return this.lista.get(posicao_partido).getQuantidade_candidato();
    }

    public int getVoto_candidatos(int posicao_partido) {
        return this.lista.get(posicao_partido).getVotos_legenda();
    }

    public int getQuantidade_votos_nominai(int posicao_partido, int posicao_candidato) {
        return this.lista.get(posicao_partido).getVoto_nominais_candidato(posicao_candidato);
    }

    public String getSigla_partido(int numero_partido) {
        return this.lista.get(getPosicao_lista(numero_partido)).getSigla_partido();
    }

    public int getPosicao_lista(int numero_partido) {
        for (int i = 0; i < this.lista.size(); i++) {
            if (numero_partido == this.lista.get(i).numero_partido) {
                return i;
            }
        }
        return 0;
    }

    public int getNumero_eleitos(int numero_partido) {
        return this.lista.get(getPosicao_lista(numero_partido)).getNumero_eleitos();
    }

    public int[] getEstudo_maisVotado(int posicao_partido) {
        return this.lista.get(posicao_partido).getCandidato_mais_votado();
    }

    public int[] getEstudo_menosVotado(int posicao_partido) {
        return this.lista.get(posicao_partido).getCandidato_menos_votado();
    }

    public String getNome_urna(int posicao_partido, int numero_candidato) {
        return this.lista.get(posicao_partido).getNome_urna(numero_candidato);
    }

    public int[][] getIdade_por_eleito(String date) { //primeira variavel de age[j][0] � sempre o numero do partido e cada linha � um partido com as informacoes da idade de cada eleito
        String[][] dataNasc = new String[this.lista.size()][];
        for (int i = 0; i < this.lista.size(); i++) {
            dataNasc[i] = this.lista.get(i).getData_nasc();
        }

        String pattern = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(pattern);
        int[][] age = new int[this.lista.size()][];
        Date dataNascInput = null;
        for (int i = 0; i < dataNasc.length; i++) {
            age[i] = new int[dataNasc[i].length + 1];
            age[i][0] = this.lista.get(i).getNumero_partido();

            for (int j = 0; j < dataNasc[i].length; j++) {
                try {
                    dataNascInput = sdf.parse(dataNasc[i][j]);
                } catch (Exception e) {
                }

                Calendar dateOfBirth = new GregorianCalendar();
                dateOfBirth.setTime(dataNascInput);
                String dataHj = date;
                Date dataHjInput = null;

                try {
                    dataHjInput = sdf.parse(dataHj);
                } catch (Exception e) {
                }

                Calendar dateOfToday = new GregorianCalendar();
                dateOfToday.setTime(dataHjInput);

                age[i][j + 1] = dateOfToday.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
                dateOfBirth.add(Calendar.YEAR, age[i][j + 1]);

                if (dateOfToday.before(dateOfBirth)) {
                    age[i][j + 1]--;
                }
            }
        }
        return age;
    }

    int[] getestudo_sexo() {
        int[] informacao = new int[2];
        for (int i = 0; i < this.lista.size(); i++) {
            informacao[0] += this.lista.get(i).getNumero_mulheres();
            informacao[1] += this.lista.get(i).getNumero_homens();
        }
        return informacao;
    }
}
