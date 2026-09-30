package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.schema.importexport.sivbg.RepositoryVersion;

import java.util.ArrayList;

public class ImportExportSIVBGCommand {

    String descrizioneServizioExport;
    String dataExport;
    String noteVersioneExport;
    ArrayList<RepositoryVersion> listaVersioniLocali;
    ArrayList<RepositoryVersion> listaVersioniRegionali;
    String sceltaVers;
    String idEndoSelezionato;
    boolean attivaUpdateDati;
    private IstanzeFilter istanzeFilter;

    public ImportExportSIVBGCommand() {

	istanzeFilter = new IstanzeFilter();
    }

    public String getDescrizioneServizioExport() {

	return descrizioneServizioExport;
    }

    public void setDescrizioneServizioExport(String descrizioneServizioExport) {

	this.descrizioneServizioExport = descrizioneServizioExport;
    }

    public String getDataExport() {

	return dataExport;
    }

    public void setDataExport(String dataExport) {

	this.dataExport = dataExport;
    }

    public String getNoteVersioneExport() {

	return noteVersioneExport;
    }

    public void setNoteVersioneExport(String noteVersioneExport) {

	this.noteVersioneExport = noteVersioneExport;
    }

    public ArrayList<RepositoryVersion> getListaVersioniLocali() {

	return listaVersioniLocali;
    }

    public void setListaVersioniLocali(ArrayList<RepositoryVersion> listaVersioniLocali) {

	this.listaVersioniLocali = listaVersioniLocali;
    }

    public void addListaVersioniLocali(RepositoryVersion version) {

	if (listaVersioniLocali == null) {
	    listaVersioniLocali = new ArrayList<RepositoryVersion>();
	}
	listaVersioniLocali.add(version);
    }

    public ArrayList<RepositoryVersion> getListaVersioniRegionali() {

	return listaVersioniRegionali;
    }

    public void setListaVersioniRegionali(ArrayList<RepositoryVersion> listaVersioniRegionali) {

	this.listaVersioniRegionali = listaVersioniRegionali;
    }

    public void addListaVersioniRegionali(RepositoryVersion version) {

	if (listaVersioniRegionali == null) {
	    listaVersioniRegionali = new ArrayList<RepositoryVersion>();
	}
	listaVersioniRegionali.add(version);
    }

    public String getSceltaVers() {

	return sceltaVers;
    }

    public void setSceltaVers(String sceltaVers) {

	this.sceltaVers = sceltaVers;
    }

    public String getIdEndoSelezionato() {

	return idEndoSelezionato;
    }

    public void setIdEndoSelezionato(String idEndoSelezionato) {

	this.idEndoSelezionato = idEndoSelezionato;
    }

    public IstanzeFilter getIstanzeFilter() {

	return istanzeFilter;
    }

    public void setIstanzeFilter(IstanzeFilter istanzeFilter) {

	this.istanzeFilter = istanzeFilter;
    }

    public boolean isAttivaUpdateDati() {

	return attivaUpdateDati;
    }

    public void setAttivaUpdateDati(boolean attivaUpdateDati) {

	this.attivaUpdateDati = attivaUpdateDati;
    }
}
