package it.gruppoinit.pal.gp.core.dao;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public interface GruppiEndoprocedimentiDDAO extends BaseDAO<GruppiEndoprocedimentiD, PkId> {

    public Set<Integer> findByEndoprocedimenti(Set<Integer> codiciEndoprocedimenti, String software);

    /**
     * Torna la lista dei gruppi che generano i warning a partire dalla lista degli endo passati con la query che segue
     * 
     * <pre>
     *  select gruppi_endoprocedimenti_t.id,gruppi_endoprocedimenti_t.num_endo_warning, gruppi_endoprocedimenti_t.tipomovimento, 
     *  count(gruppi_endoprocedimenti_d.id) 
     *  from gruppi_endoprocedimenti_t inner join gruppi_endoprocedimenti_d 
     *  on gruppi_endoprocedimenti_t.idcomune = gruppi_endoprocedimenti_d.idcomune and 
     *  gruppi_endoprocedimenti_t.id = gruppi_endoprocedimenti_d.fk_get_id 
     *  where gruppi_endoprocedimenti_t.idcomune=? and gruppi_endoprocedimenti_t.software=? and 
     *  gruppi_endoprocedimenti_d.codiceinventario in (listacodici)  
     *  ) and gruppi_endoprocedimenti_t.num_endo_warning>0 and gruppi_endoprocedimenti_t.tipomovimento is not null
     *  group by gruppi_endoprocedimenti_t.id,gruppi_endoprocedimenti_t.num_endo_warning, gruppi_endoprocedimenti_t.tipomovimento 
     *  having count(gruppi_endoprocedimenti_d.id) >= gruppi_endoprocedimenti_t.num_endo_warning order by count(gruppi_endoprocedimenti_d.id) desc
     * </pre>
     * 
     * @param codiciEndoprocedimenti
     * @param software
     * @return lista di oggetti la cui chiave è il codice del gruppo e il valore il tipomovimento da eseguire per
     *         l'istanza
     */
    public List<ChiaveValoreBean<Integer, String>> findByEndoprocedimentiConWarning(Set<Integer> codiciEndoprocedimenti, String software);

    public Set<Integer> findEndoProcedimentiNonPresentiInGruppi(Set<Integer> codiciEndoprocedimenti);
}
