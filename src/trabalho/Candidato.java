package Trabalho;

public class Candidato {
    private int numero;
    private int votos_nominais;
    private String situacao;
    private String nome;
    private String nome_urna;
    private String sexo;
    private String data_nasc;
    private String destino_voto;
    private int numero_partido;

    public int getNumero() {
        return numero;
    }

    public int getVotos_nominais() {
        return votos_nominais;
    }

    public String getSituacao() {
        return situacao;
    }
    
    public String getNome() {
        return nome;
    }

    public String getNome_urna() {
        return nome_urna;
    }

    public String getSexo() {
        return sexo;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public String getDestino_voto() {
        return destino_voto;
    }

    public int getNumero_partido() {
        return numero_partido;
    }
    

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setVotos_nominais(int votos_nominais) {
        this.votos_nominais = votos_nominais;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNome_urna(String nome_urna) {
        this.nome_urna = nome_urna;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public void setDestino_voto(String destino_voto) {
        this.destino_voto = destino_voto;
    }

    public void setNumero_partido(int numero_partido) {
        this.numero_partido = numero_partido;
    }    
    
}