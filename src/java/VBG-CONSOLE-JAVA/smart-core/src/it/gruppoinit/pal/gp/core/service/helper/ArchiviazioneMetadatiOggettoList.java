package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Set;

public class ArchiviazioneMetadatiOggettoList {

    private Set<ArchiviazioneMetadatiOggetto> archiviazioneMetadatiOggettoList;
    private String errore;

    public Set<ArchiviazioneMetadatiOggetto> getArchiviazioneMetadatiOggettoList() {

	return archiviazioneMetadatiOggettoList;
    }

    public void setArchiviazioneMetadatiOggettoList(Set<ArchiviazioneMetadatiOggetto> archiviazioneMetadatiOggettoList) {

	this.archiviazioneMetadatiOggettoList = archiviazioneMetadatiOggettoList;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }
}
