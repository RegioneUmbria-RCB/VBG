using ProtocolloInsielService3;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Fascicolazione
{
    public class AbilitazioneFascicolazioneAdapter
    {
        public AbilitazioneFascicolazioneAdapter()
        {

        }

        public AbilAperturaPraticaRequest Adatta(FascicolazioneInfo info)
        {
            var classificaAdapter = new ClassificaAdapter();
            var classifica = classificaAdapter.Adatta(info.UsaLivelliClassifica, info.Classifica);

            return new AbilAperturaPraticaRequest
            {
                anno = info.AnnoFascicolo,
                codiceRegistro = classifica,
                codiceUfficio = info.CodiceUfficio,
                codiceUfficioOperante = info.CodiceUfficioOperante,
            };
        }
    }
}
