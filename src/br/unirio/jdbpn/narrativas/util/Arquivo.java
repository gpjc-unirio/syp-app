package br.unirio.jdbpn.narrativas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class Arquivo {

	@SuppressWarnings("unused")
	private String conteudoXml;
	private int projetoId;

	// Local para testes	
	private static String diretorioDosArquivosBpmn = "/home/syp/bpmn_files";

	public Arquivo(int projetoId) {
		this.projetoId = projetoId;
	}

	public String getConteudoXml() {
		
		diretorioDosArquivosBpmn = getCaminhoDiretorioBPMN();
		
		File xmlFile = new File(diretorioDosArquivosBpmn + "/" + String.valueOf(projetoId) + ".bpmn");
		try {
			return new String(Files.readAllBytes(xmlFile.toPath()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			return "";
		}
	}

	public void setConteudoXml(String conteudoXml) {
		this.conteudoXml = conteudoXml;
	}

	public int getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(int projetoId) {
		this.projetoId = projetoId;
	}

	public void upload(String nomeDoArquivo, InputStream arquivoCarregado) throws FileNotFoundException {

		diretorioDosArquivosBpmn = getCaminhoDiretorioBPMN();
		
		String pasta = diretorioDosArquivosBpmn;

		String caminhoDoArquivo = pasta + "/" + nomeDoArquivo;
		File arquivo = new File(caminhoDoArquivo);
		FileOutputStream saida = new FileOutputStream(arquivo);
		copiar(arquivoCarregado, saida);

	}

	private void copiar(InputStream origem, OutputStream destino) {
		int byteLido = 0;
		byte[] tamanhoMaximo = new byte[1024 * 8];

		try {
			while ((byteLido = origem.read(tamanhoMaximo)) > 0) {
				destino.write(tamanhoMaximo, 0, byteLido);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	private String getCaminhoDiretorioBPMN() {		
		String diretorio = "";
		
		try {		
			InputStream directorioFile = this.getClass().getClassLoader().getResourceAsStream("META-INF/bpmn_directory.txt");
			String dir = this.getClass().getClassLoader().getResource("META-INF/bpmn_directory.txt").getFile();
			String rootPath = System.getProperty("catalina.home");
			
			Scanner scaner = new Scanner(directorioFile);
			String fileContent = (scaner.hasNext() ? scaner.next() : "");
			
			dir = dir.replace("bpmn_directory.txt", "bpmn_files");
			rootPath += File.separator + fileContent;
			
			File bpmn_directoryFile = new File(rootPath);
			if(bpmn_directoryFile != null) {			
				
			    if(bpmn_directoryFile.exists()){			    	
			      System.out.println("Diretorio ja existe no sistema");
			    }
			    else{
			      System.out.println("Diretorio ainda nao existe no sistema");
			      bpmn_directoryFile.mkdir();
			      
			      if(!bpmn_directoryFile.exists()) {
			    	  bpmn_directoryFile.mkdirs();
			      }
			    }
			    
			    diretorio = bpmn_directoryFile.getCanonicalPath();
			    
	            return diretorio;
			}
					
		}catch (Exception e) {
			System.out.println(e);		
		} finally {
			return diretorio;
		}	
				
	}

}
