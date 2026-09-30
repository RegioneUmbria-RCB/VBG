using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
{
    public class InterrogaPraticheRequestAdapter
    {
        public InterrogaPraticheRequestAdapter()
        {

        }

        public InterrogaFascicoliRequest Adatta(FascicolazioneInfo info)
        {
            var classificaAdapter = new ClassificaAdapter();
            var classifica = classificaAdapter.Adatta(info.UsaLivelliClassifica, info.Classifica);

            return new InterrogaFascicoliRequest
            {
                Anno = new StringRange { Da = info.AnnoFascicolo, A = info.AnnoFascicolo },
                Numero = new StringRange { Da = info.NumeroFascicolo, A = info.NumeroFascicolo },
                CodiceUfficio = info.CodiceUfficio,
                CodiceRegistro = classifica
            };
        }
    }
}
