using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.SIGePro.Manager.DTO.Common;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento.LogicaSincronizzazione
{
    public class LogicaSincronizzazioneAllegatiIntervento : ILogicaSincronizzazioneAllegatiIntervento
    {
        IInterventiAllegatiRepository _interventiAllegatiRepository;

        public LogicaSincronizzazioneAllegatiIntervento(IInterventiAllegatiRepository interventiAllegatiRepository)
        {
            if (interventiAllegatiRepository == null)
                throw new System.ArgumentNullException(nameof(interventiAllegatiRepository));
            //Condition.Requires(interventiAllegatiRepository, "interventiAllegatiRepository").IsNotNull();

            this._interventiAllegatiRepository = interventiAllegatiRepository;
        }

        public void Sincronizza(DomandaOnline domanda)
        {
            var writeInterface = domanda.WriteInterface;
            var readInterface = domanda.ReadInterface;

            var codiceIntervento = readInterface.AltriDati.Intervento.Codice;

            var riepilogoDomandaAllegato = readInterface.Documenti.Intervento.GetRiepilogoDomanda();

            if (riepilogoDomandaAllegato != null && riepilogoDomandaAllegato.AllegatoDellUtente != null)
            {
                writeInterface.Documenti.RimuoviAllegatoDaDocumento(riepilogoDomandaAllegato.Id);
            }

            var documentiIntervento = _interventiAllegatiRepository.GetAllegatiDaIdintervento(codiceIntervento, AmbitoRicerca.AreaRiservata);

            // Eliminazione delle vecchie righe: 
            // - Allegati senza oggetto
            // - Allegati che rappresentano il riepilogo della domanda
            // - Allegati che non sono più richiesti dall'intervento
            writeInterface.Documenti.EliminaDocumentiInterventoSenzaAllegati();

            var listaIdNuoviAllegati = documentiIntervento.Select(x => x.Codice);
            var listaidDaeliminare = readInterface.Documenti.Intervento
                                                            .WhereCodiceDocumentoNotIn(listaIdNuoviAllegati)
                                                            .Select(d => d.Id)
                                                            .ToList();

            listaidDaeliminare.ForEach(id => writeInterface.Documenti.EliminaDocumento(id));

            foreach (var documento in documentiIntervento)
            {
                var codiceDocumento = documento.Codice;
                var descrizione = documento.Descrizione;
                var linkInformazioni = documento.LinkInformazioni;
                var codiceOggetto = documento.CodiceOggettoModello;
                var richiesto = documento.Richiesto;
                var richiedeFirma = documento.RichiedeFirma;
                var tipoDownload = documento.TipoDownload;
                var ordine = documento.Ordine.GetValueOrDefault(0);
                var nomeFileModello = documento.NomeFileModello;
                var codiceCategoria = GestioneDocumentiConstants.CategorieDocumenti.AltriAllegatiCodice;
                var descrizioneCategoria = GestioneDocumentiConstants.CategorieDocumenti.AltriAllegatiDescrizione;
                var riepilogoDomanda = documento.RiepilogoDomanda;
                var note = documento.Note;

                if (documento.Categoria != null)
                {
                    codiceCategoria = documento.Categoria.Codice;
                    descrizioneCategoria = documento.Categoria.Descrizione;
                }

                writeInterface.Documenti.AggiungiOAggiornaDocumentoIntervento(codiceDocumento,
                                                                                descrizione,
                                                                                linkInformazioni,
                                                                                codiceOggetto,
                                                                                richiesto,
                                                                                richiedeFirma,
                                                                                tipoDownload,
                                                                                ordine,
                                                                                nomeFileModello,
                                                                                riepilogoDomanda,
                                                                                codiceCategoria,
                                                                                descrizioneCategoria,
                                                                                note,
                                                                                documento.DimensioneMassima.GetValueOrDefault(0),
                                                                                documento.EstensioniAmmesse);
            }
        }
    }
}
