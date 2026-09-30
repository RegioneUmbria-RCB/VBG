using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public static class ProtocolloAllegatiExtensions
    {
        public static ProtocolloAllegati ToProtocolloAllegati(this AllegatoType allegato, Oggetti oggetto, string percorso, string contentType, ProtocolloMgr.NomeFileAllegato nomeAllegato, VerticalizzazioneProtocolloAttivo protoAttivo, List<ProtocolloAllegati> allegatiPrecedenti)
        {
            // var oggetto = protoAllegatiMgr.GetById(idComune, Convert.ToInt32(allegato.Cod));
            // var percorso = protoAllegatiMgr.GetPercorsoOggetto(idComune, Convert.ToInt32(allegato.Cod));

            // var nomeAllegato = new ProtocolloMgr.NomeFileAllegato(idComune, codiceComune, oggetto, allegato.Descrizione, protoAttivo.NomeFileMaxLength);

            var protoAllegati = new ProtocolloAllegati
            {
                MimeType = contentType,// protoAllegatiMgr.GetContentType(oggetto),
                Extension = nomeAllegato.GetEstensione(),
                NOMEFILE = nomeAllegato.GetNomeCompleto(protoAttivo.NomefileOrigine, allegatiPrecedenti, protoAttivo.CreaCopiaFile),
                Descrizione = nomeAllegato.GetDescrizioneFileCopia(allegato.Descrizione, allegatiPrecedenti, protoAttivo.CreaCopiaDescrFile, 0, protoAttivo.DescrFileMaxLength),
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
