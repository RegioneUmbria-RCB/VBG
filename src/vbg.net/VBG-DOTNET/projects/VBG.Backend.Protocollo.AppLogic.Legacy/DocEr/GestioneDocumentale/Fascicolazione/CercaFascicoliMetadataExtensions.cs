using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.Fascicolazione
{
    public static class CercaFascicoliMetadataExtensions
    {
        public static DatiFascType ToDatiFascicolo(this KeyValuePair[] item)
        {
            var dic = item.ToDictionary(x => x.key, y => y.value);

            return new DatiFascType
            {
                AnnoFascicolo = dic[FascicolazioneMetadataConstants.ANNO_FASCICOLO],
                NumeroFascicolo = dic[FascicolazioneMetadataConstants.PROGRESSIVO_FASCICOLO],
                ClassificaFascicolo = dic[FascicolazioneMetadataConstants.CLASSIFICA],
                DataFascicolo = "",
                OggettoFascicolo = dic[FascicolazioneMetadataConstants.DES_FASCICOLO]
            };
        }

        public static DatiProtocolloFascicolatoResponseType ToDatiProtocolloFascicolato(this KeyValuePair[] item)
        {
            var dic = item.ToDictionary(x => x.key, y => y.value);

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = dic[FascicolazioneMetadataConstants.ANNO_FASCICOLO],
                NumeroFascicolo = dic[FascicolazioneMetadataConstants.PROGRESSIVO_FASCICOLO],
                Classifica = dic[FascicolazioneMetadataConstants.CLASSIFICA],
                DataFascicolo = "",
                Oggetto = dic[FascicolazioneMetadataConstants.DES_FASCICOLO],
                Fascicolato = EnumFascicolatoType.si
            };
        }


    }
}
