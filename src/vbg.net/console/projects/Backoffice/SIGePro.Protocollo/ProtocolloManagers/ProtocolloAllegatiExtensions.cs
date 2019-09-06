using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Manager
{
    public static class ProtocolloAllegatiExtensions
    {
        public static ProtocolloAllegati ToProtocolloAllegati(this Allegato allegato, ProtocolloAllegatiMgr protoAllegatiMgr, string idComune, string codiceComune, VerticalizzazioneProtocolloAttivo protoAttivo, List<ProtocolloAllegati> allegatiPrecedenti)
        {
            var oggetto = protoAllegatiMgr.GetById(idComune, Convert.ToInt32(allegato.Cod));
            var percorso = protoAllegatiMgr.GetPercorsoOggetto(idComune, Convert.ToInt32(allegato.Cod));

            if (oggetto == null)
                throw new Exception(String.Format("IL CODICEOGGETTO {0} PER L'IDCOMUNE {1} NON È PRESENTE NELLA TABELLA OGGETTI", allegato.Cod, idComune));

            var nomeAllegato = new ProtocolloMgr.NomeFileAllegato(idComune, codiceComune, oggetto, allegato.Descrizione, protoAttivo.NomeFileMaxLength);

            var protoAllegati = new ProtocolloAllegati
            {
                MimeType = protoAllegatiMgr.GetContentType(oggetto),
                Extension = nomeAllegato.GetEstensione(),
                NOMEFILE = nomeAllegato.GetNomeCompleto(protoAttivo.NomefileOrigine, allegatiPrecedenti, protoAttivo.CreaCopiaFile == "1"),
                Descrizione = nomeAllegato.GetDescrizioneFileCopia(allegato.Descrizione, allegatiPrecedenti, protoAttivo.CreaCopiaDescrFile == "1", 0, protoAttivo.DescrFileMaxLength),
                CODICEOGGETTO = oggetto.CODICEOGGETTO,
                IDCOMUNE = oggetto.IDCOMUNE,
                OGGETTO = oggetto.OGGETTO,
                Percorso = percorso
            };

            protoAllegati.RimuoviCaratteriNonValidiDaNomeFile(protoAttivo.ListaCaratteriDaEliminare);

            return protoAllegati;
        }
    }
}
