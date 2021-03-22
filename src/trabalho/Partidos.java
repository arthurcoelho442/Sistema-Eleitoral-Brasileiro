package Trabalho;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Partidos {
	
		private List<Partido> lista = new ArrayList<>();
		
		Partidos(String Caminho, Candidato[] candidatos){
			String[] part;
			part=Read(Caminho);
			if(part.length!=0)         // ver caso em que o arquivo eh vazio, nesse caso teremos que try/catch;
				for(int i=4; i <= part.length; i+=4) {
					lista.add( new Partido(part[i-4], part[i-3], part[i-2], part[i-1], candidatos));
				}
		}
		
		/*static void imprimirPartidos(Partidos part){
			for(int i=0; i< part.lista.size(); i++) {
				Partido.imprimirPartdio(part.lista.get(i));
			}
		}*/
		
		public static String[] Read(String Caminho){
	        String conteudo = "";
	        try {
	        	FileReader arq;
	        	try {
	        		arq = new FileReader("exemplos/ES/" + Caminho);
	        	}catch(Exception e) {
	        		arq = new FileReader("exemplos/capitais/" + Caminho);
	        	}
	            BufferedReader lerArq = new BufferedReader(arq);
	            String linha="";
	            try {
	                linha = lerArq.readLine();
	                linha = lerArq.readLine();
	                int i=0;
	                String test1[]= new String[0];
	                String test2[]= new String[0];
	                while(linha!=null){
	                	i+=4;
	                	test1= new String[i];
	                    conteudo = linha;
	                    test1[i-4] = conteudo.split(",")[0];
	                    test1[i-3] = conteudo.split(",")[1];
	                    test1[i-2] = conteudo.split(",")[2];
	                    test1[i-1] = conteudo.split(",")[3];
	                    linha = lerArq.readLine();
	                    for(int k=0; k<test1.length-4; k++) {
	                    	test1[k]=test2[k];
	                    }
	                    test2=test1;
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
	    
	    public static boolean Write(String Caminho,String Texto){
	        try {
	            FileWriter arq = new FileWriter(Caminho);
	            PrintWriter gravarArq = new PrintWriter(arq);
	            gravarArq.println(Texto);
	            gravarArq.close();
	            return true;
	        }catch(IOException e){
	            System.out.println(e.getMessage());
	            return false;
	        }
	    }
	    
	    public int getQuantidade_partidos(){
			return this.lista.size();
		}
	    
	    public int getNumero_partido(int posicao_partido){
	    	return this.lista.get(posicao_partido).getNumero_partido();
	    }
	    
	    public int getVoto_legenda(int posicao_partido){
	    	return this.lista.get(posicao_partido).getVotos_legenda();
	    }
	    
	    public int getQuantidade_candidatos(int posicao_partido){
	    	return this.lista.get(posicao_partido).getQuantidade_candidato();
	    }
	    
	    public int getVoto_candidatos(int posicao_partido){
	    	return this.lista.get(posicao_partido).getVotos_legenda();
	    }
	    public int getQuantidade_votos_nominai(int posicao_partido, int posicao_candidato) {
	    	return this.lista.get(posicao_partido).getVoto_nominais_candidato(posicao_candidato);
	    }
}
