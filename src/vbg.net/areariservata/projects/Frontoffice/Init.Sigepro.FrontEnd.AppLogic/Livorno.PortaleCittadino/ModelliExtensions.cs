using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public static class ModelliExtensions
    {
        public static IEnumerable<PCGrigliaAllegatiBindingItem> ToAllegatoBindingItems(this IEnumerable<PCModello> modelli, IDocumentiReadInterface documenti)
        {
            if (modelli == null || modelli.Count() == 0)
            {
                return Enumerable.Empty<PCGrigliaAllegatiBindingItem>();
            }

            var rVal = new List<PCGrigliaAllegatiBindingItem>(modelli.Count());

            foreach (var mod in modelli)
            {
                var r = documenti.Intervento.Documenti.Where( doc => doc.Descrizione == mod.Descrizione).FirstOrDefault();

                var item = new PCGrigliaAllegatiBindingItem
                {
                    Id = mod.Id,
                    CodiceOggetto = (r == null || r.AllegatoDellUtente == null )? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
                    Descrizione = mod.Descrizione.Replace("\n", "<br />"),
                    LinkDoc = String.Empty,
                    LinkOO = String.Empty,
                    LinkPdf = String.Empty,
                    LinkPdfCompilabile = String.Empty,
                    LinkRtf = String.Empty,
                    LinkDownloadFile = mod.Link,
                    LinkDownloadSenzaPrecompilazione = String.Empty,
                    NomeFile = (r == null || r.AllegatoDellUtente == null) ? string.Empty : r.AllegatoDellUtente.NomeFile,
                    Richiesto = mod.Obbligatorio,
                    RichiedeFirmaDigitale = false,
                    FirmatoDigitalmente = (r == null || r.AllegatoDellUtente == null) ? false : r.AllegatoDellUtente.FirmatoDigitalmente,
                    Note = String.Empty,
                    SoloFirma = false
                };

                rVal.Add(item);
            }

            return rVal;
        }
    }
}
