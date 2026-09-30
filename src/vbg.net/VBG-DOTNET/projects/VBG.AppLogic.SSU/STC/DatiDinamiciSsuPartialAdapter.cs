using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Microsoft.VisualStudio.Threading;
using VBG.AppLogic.SSU.GestioneDatiDinamici;

namespace VBG.AppLogic.SSU.STC
{
    public class DatiDinamiciSsuPartialAdapter : IStcPartialAdapter
    {
        private readonly SsuDatiDinamiciService _ssuDatiDinamiciService;

        public DatiDinamiciSsuPartialAdapter(SsuDatiDinamiciService ssuDatiDinamiciService)
        {

            this._ssuDatiDinamiciService = ssuDatiDinamiciService;
        }

        public void Adapt(IDomandaOnlineReadInterface readInterface, DettaglioPraticaType dettaglioPratica)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());

            var schedePerProcedimento = jtf.Run(() => this._ssuDatiDinamiciService.GetDatiDinamiciAsync(readInterface.AltriDati.IdPresentazione));
            var schedeDomanda = schedePerProcedimento.Where(x => x.Schede is not null).SelectMany(x => x.Schede!);

            var modelloReader = new SsuStrutturaModelloDinamicoRepository(schedeDomanda);

            var schede = readInterface.DatiDinamici.Modelli.Select(x => modelloReader.GetStrutturaModelloDinamico(x.IdModello));

            dettaglioPratica.schede = schede.Select(x => new SchedeStcAdapter(x, readInterface.DatiDinamici).CreaSchedaStc()).ToArray();
        }
    }
}
