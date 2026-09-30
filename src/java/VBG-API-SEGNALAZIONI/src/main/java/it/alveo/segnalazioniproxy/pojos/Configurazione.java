package it.alveo.segnalazioniproxy.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Configurazione {
    private Integer id;
    private String alias;
    private String software;
    private List<Categoria> categorie;

    // Metodo che restituisce le categorie ricorsive filtrando solo quelle attivabili
    public List<Categoria> getCategorieAttivabili() {
        return categorie.stream()
                .filter(Categoria::isAttivabile)
                .map(this::getCategorieAttivabiliRicorsivo)
                .toList();
    }

    // Metodo ricorsivo per filtrare le sotto-categorie attivabili
    private Categoria getCategorieAttivabiliRicorsivo(Categoria categoria) {
        if (categoria.getSottoCategorie() != null && !categoria.getSottoCategorie().isEmpty()) {
            categoria.setSottoCategorie(
                    categoria.getSottoCategorie().stream()
                            .filter(Categoria::isAttivabile)
                            .map(this::getCategorieAttivabiliRicorsivo)
                            .toList()
            );
        }
        return categoria;
    }

}
