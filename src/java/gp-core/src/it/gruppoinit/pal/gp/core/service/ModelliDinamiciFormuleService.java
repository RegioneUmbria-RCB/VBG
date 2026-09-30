package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface ModelliDinamiciFormuleService extends BaseService<Dyn2Modellit, PkId> {

    public enum EventoModelli {
	Caricamento, Modifica, Salvataggio
    };

    @Override
    public Dyn2Modellit findById(PkId id);

    @Override
    public void insert(Dyn2Modellit entity);

    @Override
    public void update(Dyn2Modellit entity);

    @Override
    public void delete(Dyn2Modellit entity);

    @Override
    public List<Dyn2Modellit> findAll(Integer firstResult, Integer maxResult);

    @Override
    public Dyn2Modellit bindDomainObject(Dyn2Modellit entity, java.lang.Class<?> idClass, String idPath);

    @Override
    public PkId newIdFromSequencetable(Dyn2Modellit entity);

    public void updateLoadModelloIstanza(Integer codiceIstanza, Integer codiceModello);

    public void updateSaveModelloIstanza(Integer codiceIstanza, Integer codiceModello);

    public void updateLoadCampoIstanza(Integer codiceIstanza, Integer codiceCampo);

    public void updateSaveCampoIstanza(Integer codiceIstanza, Integer codiceCampo);

    public void updateChangeCampoIstanza(Integer codiceIstanza, Integer codiceCampo);
}
