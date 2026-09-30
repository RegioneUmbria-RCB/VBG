using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
{
    public class AbilitazioneFascicolazioneAdapter
    {
        public AbilitazioneFascicolazioneAdapter()
        {

        }
        public AbilitazioneAperturaFascicoloRequest Adatta(FascicolazioneInfo info)
        {
            var classificaAdapter = new ClassificaAdapter();
            var classifica = classificaAdapter.Adatta(info.UsaLivelliClassifica, info.Classifica);

            return new AbilitazioneAperturaFascicoloRequest
            {
                Anno = Convert.ToInt32(info.AnnoFascicolo),
                CodiceRegistro = classifica,
                CodiceUfficio = info.CodiceUfficio,
                CodiceUfficioOperante = info.CodiceUfficioOperante,
            };
        }
    }
}
