package it.alveo.segnalazioniproxy.pojos;


import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@Data
public class Categoria {
    private Integer id;
    private String descrizione;
    private String annotazioni;
    private Boolean attivabile;
    private List<Categoria> sottoCategorie; // Categoria ricorsiva, ad albero

    public Categoria(JSONObject jsonCategoria) throws JSONException {
        id = jsonCategoria.getInt("id");
        descrizione = jsonCategoria.getString("descrizione");
    }

    public boolean isAttivabile() {
        return attivabile != null && attivabile;
    }

}
