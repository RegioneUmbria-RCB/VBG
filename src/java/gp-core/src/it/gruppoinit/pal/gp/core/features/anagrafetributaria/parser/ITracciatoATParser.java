package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.parser.AnagrafeTribParserEsito;

public interface ITracciatoATParser {

    /**
     * Esegue il parse del contenuto dei due file
     * 
     * @param esitoContent
     * @param tracciatoContent
     * @return
     * @throws AtParserException
     *             nel caso il contenuto dei file sia nullo o non rispetti le specifiche
     */
    public AnagrafeTribParserEsito parse(String esitoContent, String tracciatoContent) throws AtParserException;
}
