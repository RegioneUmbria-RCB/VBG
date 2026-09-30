#if NET48


using Microsoft.VisualStudio.Threading;


namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public interface ILocalizzazioniSICSyncService
    {
        GeneraURLMappaResponse GeneraURLMappa(GeneraURLMappaLocalizzazioneRequest request);
        GeneraURLMappaResponse GeneraURLMappaListaPratiche(GeneraURLMappaListaPraticheRequest request);
        InformazioniAggiuntive RecuperaInformazioniAggiuntive(RecuperaInformazioniAggiuntiveRequest request);
        SICFeatures GetFeatures();
    }


    public class LocalizzazioniSICSyncService : ILocalizzazioniSICSyncService
    {
        private readonly ISitCartograficoService _localizzazioniSICService;
        private readonly JoinableTaskFactory _jtf;

        public LocalizzazioniSICSyncService(ISitCartograficoService localizzazioniSICService)
        {
            this._localizzazioniSICService = localizzazioniSICService;
            this._jtf = new JoinableTaskFactory(new JoinableTaskContext());
        }

        public GeneraURLMappaResponse GeneraURLMappa(GeneraURLMappaLocalizzazioneRequest request)
        {
            return this._jtf.Run(async () => await this._localizzazioniSICService.GeneraURLMappaAsync(request));
        }

        public GeneraURLMappaResponse GeneraURLMappaListaPratiche(GeneraURLMappaListaPraticheRequest request)
        {
            return this._jtf.Run(async () => await this._localizzazioniSICService.GeneraURLMappaListaPraticheAsync(request));
        }

        public SICFeatures GetFeatures()
        {
            return this._jtf.Run(async () => await this._localizzazioniSICService.GetFeaturesAsync());
        }

        public InformazioniAggiuntive RecuperaInformazioniAggiuntive(RecuperaInformazioniAggiuntiveRequest request)
        {
            return this._jtf.Run(async () => await this._localizzazioniSICService.RecuperaInformazioniAggiuntiveAsync(request));
        }
    }
}
#endif
