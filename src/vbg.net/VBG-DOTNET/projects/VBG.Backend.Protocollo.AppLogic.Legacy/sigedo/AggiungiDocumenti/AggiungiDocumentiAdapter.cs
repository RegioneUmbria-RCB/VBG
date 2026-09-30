using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Adapters;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.AggiungiDocumenti
{
    public class AggiungiDocumentiAdapter
    {
        public static SegnaturaAggiungiDocumenti Adatta(IEnumerable<ProtocolloAllegati> allegati, SigedoVerticalizzazioneParametriAdapter vert, string numeroProtocollo, string annoProtocollo, string operatore)
        {
            var documenti = allegati.Select(x => new Documento { id = x.ID, nome = x.NOMEFILE });
            var parametri = new Parametro[] { new Parametro { nome = "UTENTE", valore = operatore ?? "" } };

            var segnatura = new SegnaturaAggiungiDocumenti
            {
                Intestazione = new Intestazione
                {
                    Identificatore = new Identificatore
                    {
                        AnnoProtocollo = annoProtocollo,
                        NumeroProtocollo = numeroProtocollo,
                        CodiceAmministrazione = vert.CodiceAmministrazione,
                        CodiceAOO = vert.CodiceAoo,
                        TipoRegistroProtocollo = vert.TipoRegistro
                    }
                },
                //Descrizione = new Descrizione { Allegati = new Allegati { Documento = documenti.ToArray() } },
                Descrizione = new Descrizione{ Allegati = documenti.ToArray() },
                ApplicativoProtocollo = new ApplicativoProtocollo { nome = vert.ApplicativoProtocollo, Parametro = parametri }
            };

            return segnatura;
        }
    }
}
