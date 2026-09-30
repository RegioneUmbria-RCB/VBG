using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    public interface ICondizioneAttivazioneDatiDinamiciAdapter
    {
        bool Verificata(IDomandaOnlineReadInterface domanda);
    }

    internal class DatiDinamiciAdapter : IStcPartialAdapter
    {
        private readonly IStrutturaModelloDinamicoRepository _modelloReader;
        private readonly ICondizioneAttivazioneDatiDinamiciAdapter _condizioneAttivazione;

        public DatiDinamiciAdapter(IStrutturaModelloDinamicoRepository modelloReader, ICondizioneAttivazioneDatiDinamiciAdapter condizioneAttivazione)
        {
            this._modelloReader = modelloReader;
            this._condizioneAttivazione = condizioneAttivazione;
        }


        public void Adapt(IDomandaOnlineReadInterface readInterface, DettaglioPraticaType dettaglioPratica)
        {
            if(!_condizioneAttivazione.Verificata(readInterface))
            {
                return;
            }

            var schede = readInterface.DatiDinamici.Modelli.Select(x => this._modelloReader.GetStrutturaModelloDinamico(x.IdModello));

            dettaglioPratica.schede = schede.Select(x => new SchedeStcAdapter(x, readInterface.DatiDinamici).CreaSchedaStc()).ToArray();
        }


    }
}
