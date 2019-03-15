package br.com.douglasdev.cidades;

import java.io.File;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.douglasdev.cidades.controller.CidadeController;
import br.com.douglasdev.cidades.model.Cidade;
import br.com.douglasdev.cidades.repository.CidadeRepository;
import br.com.douglasdev.cidades.utils.GeoUtils;

@SpringBootApplication
@ComponentScan("br.com.douglasdev.cidades")
@EntityScan("br.com.douglasdev.cidades")
@EnableJpaRepositories("br.com.douglasdev.cidades")
public class CidadesApplication {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeController cidadeController;
	
	@Bean
	public void init() {
		try {
			
			/*
			 * File origem = new File("/home/douglas/Downloads/Cidades.csv");
			 * 
			 * Scanner arquivo = new Scanner(origem);
			 * 
			 * arquivo.nextLine();
			 * 
			 * String linha = new String(); String[] colunas; Cidade cidade = new Cidade();
			 * cidadeRepository.deleteAll(); while(arquivo.hasNext()) { linha =
			 * arquivo.nextLine(); colunas = linha.split(",");
			 * 
			 * //ibge_id,uf,name,capital,lon,lat,no_accents,alternative_names,microregion,
			 * mesoregion // 0 1 2 3 4 5 6 7 8 9 cidade.setCapital(false);
			 * cidade.setId(null); cidade.setIbge(colunas[0]); cidade.setUf(colunas[1]);
			 * cidade.setName(colunas[2]); if (colunas[3].equals("true")) {
			 * cidade.setCapital(true); } cidade.setLon(colunas[4]);
			 * cidade.setLat(colunas[5]); cidade.setNoAccents(colunas[6]);
			 * cidade.setAlternativeNames(colunas[7]); cidade.setMicroregion(colunas[8]);
			 * cidade.setMesoregion(colunas[9]);
			 * 
			 * cidadeRepository.save(cidade); //System.out.println(colunas[3]); }
			 */
			
			//System.out.println(cidadeController.getDistancia(-22.0159985, -47.8892376847, -21.7903595, -48.1744399375));
			
			//System.out.println(cidadeController.distance(-22.0159985, -21.7903595, -47.8892376847, -48.1744399375, 0, 0));
			
			//GeoUtils geod = new GeoUtils();
			//System.out.println(GeoUtils.geoDistanceInKm(-21.7903595, -48.1744399375, -23.5673865, -46.5703831821));
			
			
			System.out.println("Carregado com sucesso!!!!!");
		} catch (Exception e) {
			System.out.println("Não foi possivel a leitura do arquivo");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(CidadesApplication.class, args);
	}

}
