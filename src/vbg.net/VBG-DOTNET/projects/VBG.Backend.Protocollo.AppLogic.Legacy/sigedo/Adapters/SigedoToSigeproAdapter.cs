using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Adapters
{
    public class SigedoToSigeproAdapter
    {
        string _idAllegato;
        ResolveDatiProtocollazioneService _datiProtocollazione;

        public SigedoToSigeproAdapter(ResolveDatiProtocollazioneService datiProtocollazione, string idAllegato = "")
        {
            _datiProtocollazione = datiProtocollazione;
            _idAllegato = idAllegato;
        }

        public PROTOCOLLO_SIGEPRO Adatta()
        {

            var rVal = new PROTOCOLLO_SIGEPRO
            {
                IdAllegato = _idAllegato,
                DatiProtocollo = _datiProtocollazione
            };

            return rVal;
        }
    }
}
