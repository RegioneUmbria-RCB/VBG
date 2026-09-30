package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

import java.util.Set;

public class ConfigurazioneMetadati {

    public Set<MetadatoAlberoproc> metadatiRamo;
    public Set<MetadatoAlberoproc> metadatiRamiPadre;

    public ConfigurazioneMetadati(Set<MetadatoAlberoproc> metadatiRamo, Set<MetadatoAlberoproc> metadatiRamiPadre) {

	this.metadatiRamo = metadatiRamo;
	this.metadatiRamiPadre = metadatiRamiPadre;
    }

    public Set<MetadatoAlberoproc> getMetadatiRamo() {

	return metadatiRamo;
    }

    public Set<MetadatoAlberoproc> getMetadatiRamiPadre() {

	return metadatiRamiPadre;
    }
}
