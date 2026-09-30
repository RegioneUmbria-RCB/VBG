using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione
{
    public static class FascicoloMapper
    {
        public static DatiFascType ToDatiFascType(this HalleyUtilityService.Fascicolo fascicolo)
        {
            if (fascicolo == null)
                return null;

            return new DatiFascType
            {
                NumeroFascicolo = fascicolo.numero,
                DataFascicolo = "", // valorizzare se disponibile
                ClassificaFascicolo = Utils.CreaClassifica(fascicolo),
                OggettoFascicolo = fascicolo.descrizione,
                AnnoFascicolo = fascicolo.anno
            };
        }
    }
}
