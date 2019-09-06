using Init.SIGePro.Protocollo.ItCity.Classificazione;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class SottoFascicolazioneRequestFactory
    {
        public static ISottoFascicolazioneRequest Create(int anno, int classe, int idfascicolo,  int numero, int sottoclasse, string titoloRomano, string oggetto, ClassificheServiceWrapper classificheService, string idProtocollo)
        {
            var fascicoloDiRiferimento = new FascicoloDiRiferimento
            {
                Anno = anno,
                Classe = classe,
                IdFascicolo = idfascicolo,
                Numero = numero,
                NumeroSottofascicolo = 0,
                SottoClasse = sottoclasse,
                TitoloRomano = titoloRomano
            };

            return new CreaSottoFascicoloRequest("", oggetto, fascicoloDiRiferimento, idProtocollo);
        }
    }
}
