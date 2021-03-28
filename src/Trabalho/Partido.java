package Trabalho;

import java.util.ArrayList;
import java.util.List;

class Partido {

    public int numero_partido;
    private int votos_legenda;
    private String nome_partido;
    private String sigla_partido;
    private List<Candidato> listaC = new ArrayList<>();

    Partido(String num_part, String v_legenda, String nome_partido, String sigla_partido, Candidato[] candidatos) {
        int numero_partido = Integer.parseInt(num_part);
        int votos_legenda = Integer.parseInt(v_legenda);
        this.numero_partido = numero_partido;
        this.votos_legenda = votos_legenda;
        this.nome_partido = nome_partido;
        this.sigla_partido = sigla_partido;
        for (int i = 0; i < candidatos.length; i++) {
            if (candidatos[i].getNumero_partido() == this.numero_partido) {
                listaC.add(candidatos[i]);
            }
        }
    }

    /*static void imprimirPartdio(Partido part) {
		System.out.println("inf: " + part.nome_partido +"  " + part.numero_partido +"  " + part.sigla_partido +"  " + part.votos_legenda);
	}*/
    public int getNumero_partido() {
        return this.numero_partido;
    }

    public String getNome_partido() {
        return this.nome_partido;
    }

    public int getVotos_legenda() {
        return this.votos_legenda;
    }

    public String getSigla_partido() {
        return this.sigla_partido;
    }

    public int getQuantidade_candidato() {
        return this.listaC.size();
    }

    public int getVoto_nominais_candidato(int i) {
        return this.listaC.get(i).getVotos_nominais();
    }

    public int getNumero_eleitos() {
        int i = 0;
        for (int j = 0; j < this.listaC.size(); j++) {
            if (this.listaC.get(j).getSituacao()) {
                i++;
            }
        }
        return i;
    }

    public int[] getCandidato_mais_votado() {
        int[] i = new int[3];
        i[0] = this.numero_partido;
        for (int j = 0; j < this.listaC.size(); j++) {
            if (j == 0) {
                i[2] = this.listaC.get(j).getVotos_nominais();
                i[1] = this.listaC.get(j).getNumero();
            }
            if (i[2] < this.listaC.get(j).getVotos_nominais()) {
                i[2] = this.listaC.get(j).getVotos_nominais();
                i[1] = this.listaC.get(j).getNumero();
            }
        }
        return i;
    }

    public int[] getCandidato_menos_votado() {
        int[] i = new int[2];
        for (int j = 0; j < this.listaC.size(); j++) {
            if (j == 0) {
                i[1] = this.listaC.get(j).getVotos_nominais();
                i[0] = this.listaC.get(j).getNumero();
            }
            if (i[1] > this.listaC.get(j).getVotos_nominais()) {
                i[1] = this.listaC.get(j).getVotos_nominais();
                i[0] = this.listaC.get(j).getNumero();
            }
        }
        return i;
    }

    public String getNome_urna(int numero_candidato) {
        for (int j = 0; j < this.listaC.size(); j++) {
            if (numero_candidato == this.listaC.get(j).getNumero()) {
                return this.listaC.get(j).getNome_urna();
            }
        }
        return null;
    }

    public String[] getData_nasc() {
        int count = 0;
        for (int j = 0; j < this.listaC.size(); j++) {
            if (this.listaC.get(j).getSituacao()) {
                count++;
            }
        }

        String[] idades = new String[count];
        for (int j = 0, i = 0; j < this.listaC.size(); j++) {
            if (this.listaC.get(j).getSituacao()) {
                idades[i] = this.listaC.get(j).getData_nasc();
                i++;
            }
        }
        return idades;
    }

    int getNumero_mulheres() {
        int count = 0;
        for (int i = 0; i < this.listaC.size(); i++) {
            if (this.listaC.get(i).getSexo().equals("F")) {
                if (this.listaC.get(i).getSituacao()) {
                    count++;
                }
            }
        }
        return count;
    }

    int getNumero_homens() {
        int count = 0;
        for (int i = 0; i < this.listaC.size(); i++) {
            if (this.listaC.get(i).getSexo().equals("M")) {
                if (this.listaC.get(i).getSituacao()) {
                    count++;
                }
            }
        }
        return count;
    }
}
