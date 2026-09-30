using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace VBG.AppLogic.SSU.GestioneAllegati
{
    internal class SsuLogicaSincronizzazioneAllegatiEndo
    {
        private readonly DomandaOnline _domanda;
        private readonly SsuAllegatiService _ssuAllegatiService;

        internal SsuLogicaSincronizzazioneAllegatiEndo(DomandaOnline domanda, SsuAllegatiService ssuAllegatiService)
        {
            if (ssuAllegatiService == null)
                throw new ArgumentNullException(nameof(ssuAllegatiService));

            if (domanda == null)
                throw new ArgumentNullException(nameof(domanda));

            this._domanda = domanda;
            this._ssuAllegatiService = ssuAllegatiService;
        }

        internal async Task SincronizzaAsync()
        {
            var readInterface = this._domanda.ReadInterface;
            var writeInterface = this._domanda.WriteInterface;

            var listaIdEndoSelezionati = readInterface.Ssu.Procedimenti?.Select(p => p.Id).ToArray() ?? Array.Empty<int>();

            var listaIdAllegati = new List<int>();

            var listaAllegati = await this._ssuAllegatiService.GetAllegatiAsync(this._domanda.DataKey.IdPresentazione);

            var index = 1;

            foreach (var endo in listaAllegati)
            {
                if (endo.Allegati != null)
                {
                    foreach (var allegato in endo.Allegati)
                    {
                        listaIdAllegati.Add(allegato.Id);

                        var descrizione = allegato.Descrizione;
                        var linkInformazioni = "";
                        int? codiceOggetto = null;

                        var codiceDocumento = allegato.Id;
                        var richiesto = allegato.Obbligatorio;
                        var richiedeFirma = allegato.RichiedeFirma;
                        var tipoDownload = "";
                        var codiceEndo = endo.Procedimento.Id;
                        var ordine = index;
                        var nomeFileModello = "";
                        var note = allegato.DescrizioneEstesa ?? "";
                        var dimensioneMassima = (int)(allegato.DomensioneMassimaInKb ?? 0);
                        var estensioniAmmesse = string.Join(",", allegato.EstensioniAmmesse ?? new List<string>());

                        writeInterface.Documenti.AggiungiOAggiornaDocumentoEndo(codiceDocumento, descrizione, linkInformazioni, codiceOggetto, richiesto,
                                                                                richiedeFirma, tipoDownload, codiceEndo, ordine, nomeFileModello, note,
                                                                                dimensioneMassima, estensioniAmmesse);

                        index++;
                    }
                }
            }

            // Aggiungo un allegato fittizio che permette di gestire il riepilogo domanda
            writeInterface.Documenti.AggiungiOAggiornaDocumentoIntervento(-1, "RiepilogoDomanda", "", null, true, true, "", 0, "", true, -1, "", "", int.MaxValue, "pdf,p7m");


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
        }
    }
}
