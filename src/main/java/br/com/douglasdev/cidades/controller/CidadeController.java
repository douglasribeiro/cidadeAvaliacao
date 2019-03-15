package br.com.douglasdev.cidades.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.douglasdev.cidades.model.Cidade;
import br.com.douglasdev.cidades.repository.CidadeRepository;
import br.com.douglasdev.cidades.utils.Disco;
import br.com.douglasdev.cidades.utils.GeoUtils;

/**
 * 
 * @author douglas
 *
 */
@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private Disco disco;

	/**
	 * 
	 * @return
	 */
	@GetMapping("/")
	public List<Cidade> findAll() {
		return (List<Cidade>) cidadeRepository.findAll();
	}

	/**
	 *  2. Retornar somente as cidades que são capitais ordenadas por nome;
	 *  
	 *  @return
	 */
	@GetMapping("/capitais")
	private List<Cidade> findCapitaisOrderByName() {
		return cidadeRepository.findCapitaisOrderByName();
	}

	/**
	 * 
	 *  3. Retornar o nome do estado com a maior e menor quantidade de cidades e a
	 *		quantidade de cidades;
	 *
	 *  @return
	 */
	@GetMapping("/uf")
	private List<Cidade> findEstadosMunicipios() {
		List<Cidade> cidades = cidadeRepository.findMenorMaiorEstados();
		int count = cidades.size();
		for (int i = 1; i < count - 1; i++) {
			cidades.remove(1);
		}
		return cidades;
	}

	/**
	 * 
	 * 4. Retornar a quantidade de cidades por estado;
	 * 
	 * @return
	 */
	@GetMapping("/ufall")
	private List<Cidade> findAllEstadosMunicipios() {
		return cidadeRepository.findMenorMaiorEstados();
	}

	/**
	 * 5. Obter os dados da cidade informando o id do IBGE;
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/idibge")
	private Cidade findByIdIbge(@RequestParam(value = "id") String id) {
		return cidadeRepository.findCidadeByIdIbge(id);
	}

	/**
	 * 6. Retornar o nome das cidades baseado em um estado selecionado;
	 * 
	 * @param uf
	 * @return
	 */
	@GetMapping("/cidades/estado")
	private List<Cidade> findAllCityFromUf(@RequestParam(value = "uf") String uf) {
		return cidadeRepository.findAllCityFromUf(uf);
	}

	/**
	 * 7. Permitir adicionar uma nova Cidade;
	 * 
	 * @param cidade
	 * 
	 */
	@PostMapping
	private void salvar(@RequestBody Cidade cidade) {
		cidadeRepository.save(cidade);
	}

	/**
	 * 8. Permitir deletar uma cidade;
	 * 
	 * @param cidade
	 * 
	 */
	@DeleteMapping()
	private void exclui(@RequestBody Cidade cidade) {
		cidadeRepository.delete(cidade);
	}

	/**
	 * 9. Permitir selecionar uma coluna (do CSV) e através dela entrar com uma
	 *	string para
	 *	filtrar. retornar assim todos os objetos que contenham tal string;
	 *
	 * @param id
	 * @param ibge
	 * @param uf
	 * @param name
	 * @param lon
	 * @param lat
	 * @param noAccents
	 * @param microregion
	 * @param mesoregion
	 * @param alternativeNames
	 * @param capital
	 * @return
	 */
	@GetMapping("/pesquisa")
	private List<Cidade> findDynamic(@RequestParam(value = "id") Long id, @RequestParam(value = "ibge") String ibge,
			@RequestParam(value = "uf") String uf, @RequestParam(value = "name") String name,
			@RequestParam(value = "lon") String lon, @RequestParam(value = "lat") String lat,
			@RequestParam(value = "noAccents") String noAccents,
			@RequestParam(value = "microregion") String microregion,
			@RequestParam(value = "mesoregion") String mesoregion,
			@RequestParam(value = "alternativeNames") String alternativeNames,
			@RequestParam(value = "capital") Boolean capital) {
		return cidadeRepository.findByPesquisa(id, ibge, uf, name, lon, lat, noAccents, microregion, mesoregion,
				alternativeNames, capital);
	}

	/**
	 * 10. Retornar a quantidade de registro baseado em uma coluna. Não deve contar
	 *  itens iguais;
	 *
	 * @param id
	 * @param ibge
	 * @param uf
	 * @param name
	 * @param lon
	 * @param lat
	 * @param noAccents
	 * @param microregion
	 * @param mesoregion
	 * @param alternativeNames
	 * @param capital
	 * @return
	 */
	@GetMapping("/count")
	private int findQuantidade(@RequestParam(value = "id") Long id, @RequestParam(value = "ibge") String ibge,
			@RequestParam(value = "uf") String uf, @RequestParam(value = "name") String name,
			@RequestParam(value = "lon") String lon, @RequestParam(value = "lat") String lat,
			@RequestParam(value = "noAccents") String noAccents,
			@RequestParam(value = "microregion") String microregion,
			@RequestParam(value = "mesoregion") String mesoregion,
			@RequestParam(value = "alternativeNames") String alternativeNames,
			@RequestParam(value = "capital") Boolean capital) {
		return cidadeRepository.findQuantidade(id, ibge, uf, name, lon, lat, noAccents, microregion, mesoregion,
				alternativeNames, capital);
	}

	
	 
	 /**
	  * 11. Retornar a quantidade de registros total; 
	  * 
	  * @return
	  */
	 @GetMapping("/totalRegistros") private Long totalRegistros() { 
		 return cidadeRepository.count(); }
	 

	/**
	 *  12. Dentre todas as cidades, obter as duas cidades mais distantes uma da
	 *      outra com base
	 *      na localização (distância em KM em linha reta);
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@GetMapping("/maiordistancia")
	private String maiorDistanciaNoBrasil() throws SQLException {

		Cidade cidadeNorte = new Cidade();
		Cidade cidadeSul = new Cidade();
		Cidade cidadeOeste = new Cidade();
		Cidade cidadeLeste = new Cidade();

		List<Object> result;
		Iterator itr;

		result = (List<Object>) cidadeRepository.findByCidadeAoNorte();
		itr = result.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			cidadeNorte.setNoAccents(String.valueOf(obj[0]));
			cidadeNorte.setLat(String.valueOf(obj[1]));
			cidadeNorte.setLon(String.valueOf(obj[2]));
			cidadeNorte.setUf(String.valueOf(obj[3]));
		}

		result = (List<Object>) cidadeRepository.findByCidadeAoSul();
		itr = result.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			cidadeSul.setNoAccents(String.valueOf(obj[0]));
			cidadeSul.setLat(String.valueOf(obj[1]));
			cidadeSul.setLon(String.valueOf(obj[2]));
			cidadeSul.setUf(String.valueOf(obj[3]));
		}

		result = (List<Object>) cidadeRepository.findByCidadeAOeste();
		itr = result.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			cidadeOeste.setNoAccents(String.valueOf(obj[0]));
			cidadeOeste.setLat(String.valueOf(obj[1]));
			cidadeOeste.setLon(String.valueOf(obj[2]));
			cidadeOeste.setUf(String.valueOf(obj[3]));
		}

		result = (List<Object>) cidadeRepository.findByCidadeALeste();
		itr = result.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			cidadeLeste.setNoAccents(String.valueOf(obj[0]));
			cidadeLeste.setLat(String.valueOf(obj[1]));
			cidadeLeste.setLon(String.valueOf(obj[2]));
			cidadeLeste.setUf(String.valueOf(obj[3]));
		}

		Double distanciaVertical = GeoUtils.geoDistanceInKm(Double.parseDouble(cidadeNorte.getLat()),
				Double.parseDouble(cidadeNorte.getLon()), Double.parseDouble(cidadeSul.getLat()),
				Double.parseDouble(cidadeSul.getLon()));

		Double distanciaHorizontal = GeoUtils.geoDistanceInKm(Double.parseDouble(cidadeOeste.getLat()),
				Double.parseDouble(cidadeOeste.getLon()), Double.parseDouble(cidadeLeste.getLat()),
				Double.parseDouble(cidadeLeste.getLon()));

		return (distanciaVertical > distanciaHorizontal) ? String.valueOf(distanciaVertical)
				: String.valueOf(distanciaHorizontal);
	}

	/**
	 * 
	 * @param arquivo
	 * 
	 */
	@PostMapping("/carga")
	public void upload(@RequestParam MultipartFile arquivo) {
		disco.salvarArquivo(arquivo);
		carga();

	}

	private void carga() {
		try {
			File origem = new File("/tmp/contato-disco/cidade/Cidades.csv");
			Scanner file = new Scanner(origem);

			file.nextLine();

			String linha = new String();
			String[] colunas;
			
			cidadeRepository.flush();
			cidadeRepository.deleteAll();
			while (file.hasNext()) {
				linha = file.nextLine();
				colunas = linha.split(",");
				Cidade cidade = new Cidade();
				cidade.setId(null);
				cidade.setIbge(colunas[0]);
				cidade.setUf(colunas[1]);
				cidade.setName(colunas[2]);
				if (colunas[3].equals("true")) {
					cidade.setCapital(true);
				}
				cidade.setLon(colunas[4]);
				cidade.setLat(colunas[5]);
				cidade.setNoAccents(colunas[6]);
				cidade.setAlternativeNames(colunas[7]);
				cidade.setMicroregion(colunas[8]);
				cidade.setMesoregion(colunas[9]);

				cidadeRepository.saveAndFlush(cidade);
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
