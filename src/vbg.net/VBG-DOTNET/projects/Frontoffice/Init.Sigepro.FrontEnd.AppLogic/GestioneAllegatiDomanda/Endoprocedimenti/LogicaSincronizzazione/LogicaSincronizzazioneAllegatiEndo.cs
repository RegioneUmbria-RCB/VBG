using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti.LogicaSincronizzazione
{
    internal class LogicaSincronizzazioneAllegatiEndo
    {
        IAllegatiEndoprocedimentiService _allegatiEndoService;
        DomandaOnline _domanda;

        internal LogicaSincronizzazioneAllegatiEndo(DomandaOnline domanda, IAllegatiEndoprocedimentiService allegatiEndoService)
        {
            if (allegatiEndoService == null)
                throw new System.ArgumentNullException(nameof(allegatiEndoService));

            if (domanda == null)
                throw new System.ArgumentNullException(nameof(domanda));
            //Condition.Requires(allegatiEndoService, "allegatiEndoService").IsNotNull();
            //Condition.Requires(domanda, "domanda").IsNotNull();

            this._allegatiEndoService = allegatiEndoService;
            this._domanda = domanda;
        }


        internal void Sincronizza()
        {
            var readInterface = this._domanda.ReadInterface;
            var writeInterface = this._domanda.WriteInterface;

            var listaIdEndoSelezionati = readInterface.Endoprocedimenti.NonAcquisiti.Select(x => x.Codice);
            var listaEndo = _allegatiEndoService.GetDatiProcedimenti(listaIdEndoSelezionati.ToList());
            var listaIdAllegati = new List<int>();

            foreach (var endo in listaEndo)
            {
                if (endo.Allegati != null)
                {
                    foreach (var allegato in endo.Allegati)
                    {
                        listaIdAllegati.Add(allegato.Codice);

                        var descrizione = allegato.Descrizione;
                        var linkInformazioni = allegato.Link;
                        var codiceOggetto = allegato.CodiceOggetto;

                        var codiceDocumento = allegato.Codice;
                        var richiesto = allegato.Richiesto;
                        var richiedeFirma = allegato.Richiedefirma;
                        var tipoDownload = allegato.FormatiDownload;
                        var codiceEndo = endo.CodiceInventario;
                        var ordine = allegato.Ordine;
                        var nomeFileModello = allegato.NomeFile;
                        var note = allegato.NoteFrontend;
                        var dimensioneMassima = allegato.DimensioneMassima;
                        var estensioniAmmesse = allegato.EstensioniAmmesse;

                        writeInterface.Documenti.AggiungiOAggiornaDocumentoEndo(codiceDocumento, descrizione, linkInformazioni, codiceOggetto, richiesto,
                                                                                richiedeFirma, tipoDownload, codiceEndo, ordine, nomeFileModello, note,
                                                                                dimensioneMassima, estensioniAmmesse);
                    }
                }
            }

            // Elimino tutti i documenti relativi ad endo che non sono più selezionati
            readInterface.Documenti.Endo
                                    .WhereIdEndoNotIn(listaIdEndoSelezionati)
                                    .ToList()
                                    .ForEach(x => writeInterface.Documenti.EliminaDocumento(x.Id));

            // Elimino tutti i documenti relativi a documenti non più  in uso
            readInterface.Documenti.Endo
                                    .GetDocumentiDiSistema()
                                    .WhereIdDocumentoNotIn(listaIdAllegati)
                                    .ToList()
                                    .ForEach(x => writeInterface.Documenti.EliminaDocumento(x.Id));

            // Elimino tutti i documenti liberi che non contengono un allegato (è possibile che esistano???)
            readInterface.Documenti.Endo
                                    .GetDocumentiAllegatiDaUtenteSenzaOggetto()
                                    .ToList()
                                    .ForEach(x => writeInterface.Documenti.EliminaDocumento(x.Id));
        }
    }
}
