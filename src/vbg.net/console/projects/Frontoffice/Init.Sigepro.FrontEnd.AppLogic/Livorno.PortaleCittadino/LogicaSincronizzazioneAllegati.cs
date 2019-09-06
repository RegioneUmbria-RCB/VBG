using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public class LogicaSincronizzazioneAllegati : ILogicaSincronizzazioneAllegati
    {
        public void Sincronizza(DomandaOnline domanda, IPortaleCittadinoService portaleService)
        {
            var idIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;
            var scheda = portaleService.GetSchedaDaIdIntervento(idIntervento);

            var modelli = domanda.ReadInterface.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(PortaleCittadinoConstants.CategorieFiles.Modello);
            var allegati = domanda.ReadInterface.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(PortaleCittadinoConstants.CategorieFiles.Allegato);

            // Trovo i modelli già inseriti ma non presenti nella scheda
            var modelliDaRimuovere = new List<DocumentoDomanda>();
            var titoliModelliScheda = scheda.Modelli.Select(x => x.Descrizione).ToList();

            foreach(var modello in modelli)
            {
                if (!titoliModelliScheda.Contains(modello.Descrizione))
                {
                    modelliDaRimuovere.Add(modello);
                }
            }

            // Trovo gli allegati già inseriti ma non presenti nella scheda
            var allegatiDaRimuovere = new List<DocumentoDomanda>();
            var titoliAllegatiScheda = scheda.Allegati.Select(x => x.Descrizione).ToList();

            foreach(var allegato in allegati)
            {
                if (!titoliAllegatiScheda.Contains(allegato.Descrizione))
                {
                    allegatiDaRimuovere.Add(allegato);
                }
            }

            // Aggiungo i modelli che non sono già presenti
            var titoliModelliPresenti = modelli.Select(x => x.Descrizione).ToList();
            var nextId = domanda.ReadInterface.Documenti.Intervento.Documenti.Count() == 0 ? 0 : domanda.ReadInterface.Documenti.Intervento.Documenti.Max(x => x.Id);

            foreach(var modello in scheda.Modelli)
            {
                if (titoliModelliPresenti.Contains(modello.Descrizione))
                {
                    continue;
                }
                ++nextId;

                domanda.WriteInterface.Documenti.AggiungiOAggiornaDocumentoIntervento(nextId, modello.Descrizione, modello.Link, null,
                                                    modello.Obbligatorio, modello.RichiedeFirma, String.Empty, 0, modello.NomeFile,
                                                    false, PortaleCittadinoConstants.CategorieFiles.Modello, "Modelli", String.Empty);
            }

            // Aggiungo gli allegati che non sono già presenti
            var titoliAllegatiPresenti = allegati.Select(x => x.Descrizione).ToList();

            foreach (var allegato in scheda.Allegati)
            {
                if (titoliAllegatiPresenti.Contains(allegato.Descrizione))
                {
                    continue;
                }
                ++nextId;

                domanda.WriteInterface.Documenti.AggiungiOAggiornaDocumentoIntervento(nextId, allegato.Descrizione, allegato.Link, null,
                                                    false, false, String.Empty, 0, allegato.NomeFile,
                                                    false, PortaleCittadinoConstants.CategorieFiles.Allegato, "Allegati", String.Empty);
            }

            // Elimino i modelli in eccesso
            foreach(var modello in modelliDaRimuovere)
            {
                domanda.WriteInterface.Documenti.EliminaDocumento(modello.Id);
            }

            // Elimino gli allegati in eccesso
            foreach (var allegato in allegatiDaRimuovere)
            {
                domanda.WriteInterface.Documenti.EliminaDocumento(allegato.Id);
            }
        }
    }
}
