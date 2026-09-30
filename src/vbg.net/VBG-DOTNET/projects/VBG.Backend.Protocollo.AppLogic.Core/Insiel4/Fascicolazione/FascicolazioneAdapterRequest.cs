using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
{
    public class FascicolazioneAdapterRequest
    {
        public FascicolazioneAdapterRequest()
        {

        }

        public AperturaFascicoloRequest Adatta(FascicolazioneInfo info)
        {
            var classificaAdapter = new ClassificaAdapter();
            var classifica = classificaAdapter.Adatta(info.UsaLivelliClassifica, info.Classifica);

            var retVal = new AperturaFascicoloRequest
            {
                Anno = Convert.ToInt32(info.AnnoFascicolo),
                CodiceUfficio = info.CodiceUfficio,
                CodiceUfficioOperante = info.CodiceUfficioOperante,
                Oggetto = info.Oggetto,
                CodiceRegistro = classifica
            };

            if (String.IsNullOrEmpty(info.NumeroFascicolo))
            {
                retVal.NumerazioneManuale = false;
            }
            else
            {
                retVal.NumerazioneManuale = true;
                retVal.Numero = Convert.ToInt32(info.NumeroFascicolo);
            }

            return retVal;

        }
    }
}
