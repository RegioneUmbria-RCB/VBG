

using EliosWSConfigurazioneSoapClient;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    internal class ConfigurazioneResponse
    {
        public ListaTipiClassificaClassifica[] Classifiche;

        internal static ConfigurazioneResponse FromWSResponse(wReConfigurazione response)
        {
            return new ConfigurazioneResponse
            {
                Classifiche = response
                                    .Classificazioni
                                    .Select(x => new ListaTipiClassificaClassifica
                                    {
                                        Codice = $"{x.CodiceCategoria}.{x.CodiceClasse}.{x.CodiceSottoclasse}",
                                        Descrizione = $"{x.CodiceCategoria}.{x.CodiceClasse}.{x.CodiceSottoclasse} - {x.DescrizioneCategoria} {x.DescrizioneClasse} {x.DescrizioneSottoclasse}"
                                    })
                                    .ToArray()
            };
        }

        internal ListaTipiClassificaType ToListaTipiClassifica()
        {
            return new ListaTipiClassificaType
            {
                Classifica = this.Classifiche,
                Errore = null
            };
        }
    }
}