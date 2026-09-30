package it.gruppoinit.pdd.ri.features.configurazione;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ConfigurazioneService {

    public Configurazione caricaConfigurazione() throws JsonParseException, JsonMappingException, IOException;

    public List<Ente> elencoCertificati() throws JsonParseException, JsonMappingException, IOException;

    public Ente add(String codiceCatastale, MultipartFile certificato, String password, String alias);

    public List<Ente> addRange(String[] codiciCatastali, MultipartFile certificato, String password, String alias);

    public void elimina(String codiceCatastale) throws JsonParseException, JsonMappingException, IOException;

    public Ente findByCodiceCatastale(String codiceCatastale);

    public Certificato findByNome(String nomeCertificato);

    public List<String> findComuniAssociati(String idComuneAlias);
}