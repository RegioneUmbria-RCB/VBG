using ProtocolloInsielService3;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Fascicolazione
{
    public class InterrogaPraticheRequestAdapter
    {
        public InterrogaPraticheRequestAdapter()
        {

        }

        public InterrogazionePraticheRequest Adatta(FascicolazioneInfo info)
        {
            var classificaAdapter = new ClassificaAdapter();
            var classifica = classificaAdapter.Adatta(info.UsaLivelliClassifica, info.Classifica);

            return new InterrogazionePraticheRequest
            {
                anno = new InterrogazionePraticheRequestAnno { da = info.AnnoFascicolo, a = info.AnnoFascicolo },
                numero = new InterrogazionePraticheRequestNumero { da = info.NumeroFascicolo, a = info.NumeroFascicolo },
                codiceUfficio = info.CodiceUfficio,
                codiceRegistro = classifica
            };
        }
    }
}
