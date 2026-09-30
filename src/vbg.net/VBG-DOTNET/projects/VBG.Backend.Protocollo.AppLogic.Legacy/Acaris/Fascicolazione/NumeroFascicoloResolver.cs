using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione
{
    public class NumeroFascicoloResolver
    {
        private readonly string _numeroIstanza;
        private readonly string _cfPiva;

        public NumeroFascicoloResolver(ResolveDatiProtocollazioneService datiService, ProtocolloAnagrafe protocolloAnagrafe)
        {
            this._numeroIstanza = datiService?.NumeroIstanza;
            this._cfPiva = protocolloAnagrafe?.PARTITAIVA ?? protocolloAnagrafe?.CODICEFISCALE;
        }

        public string Resolve(ParametriRegoleInfo configurazione)
        {
            var retVal = configurazione.TemplateNumeroFascicolo;
            if (String.IsNullOrEmpty(retVal))
            {
                return retVal;
            }

            return retVal.Replace("[NUMEROISTANZA]", this._numeroIstanza).Replace("[CFPIVA]", this._cfPiva);
        }
    }
}
