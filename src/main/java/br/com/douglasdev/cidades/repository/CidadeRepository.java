package br.com.douglasdev.cidades.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.douglasdev.cidades.model.Cidade;

/**
 * 
 * @author douglas
 *
 */
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	/**
	 * 
	 * @return
	 */
	@Query("select c from Cidade c where capital = 1 order by name")
	public List<Cidade> findCapitaisOrderByName();
	
	/**
	 * 
	 * @return
	 */
	@Query("select c.uf, count(c.id) from Cidade c group by c.uf order by count(c.id)")
	public List<Cidade> findMenorMaiorEstados();
	 
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Query("select c from Cidade c where c.ibge = ?1")
	public Cidade findCidadeByIdIbge(String id);
	
	/**
	 * 
	 * @param uf
	 * @return
	 */
	@Query("select c from Cidade c where c.uf = ?1")
	public List<Cidade> findAllCityFromUf(String uf);

	/**
	 * 
	 * @param campo
	 * @param valor
	 * @return
	 */
	@Query("select c from Cidade c where ?1 = ?2")
	public List<Cidade> findDynamic(String campo, String valor);
	
	/**
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
	@Query("select c from Cidade c where "
			+ "c.id = :id or c.ibge = :ibge or c.uf = :uf or c.name = :name "
			+ "or c.lon = :lon or c.lat = :lat or c.noAccents = :noAccents "
			+ "or c.microregion = :microregion or c.mesoregion = :mesoregion "
			+ "or c.alternativeNames = :alternativeNames "
			+ "or c.capital = :capital")
	public List<Cidade> findByPesquisa(
			@Param("id") Long id,
			@Param("ibge") String ibge,
			@Param("uf") String uf,
			@Param("name") String name,
			@Param("lon") String lon,
			@Param("lat") String lat,
			@Param("noAccents") String noAccents,
			@Param("microregion") String microregion,
			@Param("mesoregion") String mesoregion,
			@Param("alternativeNames") String alternativeNames,
			@Param("capital") Boolean capital);
	    
	
	/**
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
	@Query("select count(c) from Cidade c where "
			+ "c.id = :id or c.ibge = :ibge or c.uf = :uf or c.name = :name "
			+ "or c.lon = :lon or c.lat = :lat or c.noAccents = :noAccents "
			+ "or c.microregion = :microregion or c.mesoregion = :mesoregion "
			+ "or c.alternativeNames = :alternativeNames "
			+ "or c.capital = :capital")
	public int findQuantidade(
			@Param("id") Long id,
			@Param("ibge") String ibge,
			@Param("uf") String uf,
			@Param("name") String name,
			@Param("lon") String lon,      //Jailton
			@Param("lat") String lat,
			@Param("noAccents") String noAccents,
			@Param("microregion") String microregion,
			@Param("mesoregion") String mesoregion,
			@Param("alternativeNames") String alternativeNames,
			@Param("capital") Boolean capital);
	
	/**
	 * 
	 * @return
	 */
	@Query("select cn.noAccents, cn.lat, cn.lon, cn.uf from Cidade cn where lat = (select max(lat) from Cidade)")
	public List<Object> findByCidadeAoNorte();
	
	/**
	 * 
	 * @return
	 */
	@Query("select cs.noAccents, cs.lat, cs.lon, cs.uf from Cidade cs where (lat *-1) = (select max(lat *-1) from Cidade)")
	public List<Object> findByCidadeAoSul();	
	
	/**
	 * 
	 * @return
	 */
	@Query("select co.noAccents, co.lat, co.lon, co.uf from Cidade co where lon = (select max(lon) from Cidade)")
	public List<Object> findByCidadeAOeste();
	
	/**
	 * 
	 * @return
	 */
	@Query("select cl.noAccents, cl.lat, cl.lon, cl.uf from Cidade cl where (lon) = (select min(lon) from Cidade)")
	public List<Object> findByCidadeALeste();

}
