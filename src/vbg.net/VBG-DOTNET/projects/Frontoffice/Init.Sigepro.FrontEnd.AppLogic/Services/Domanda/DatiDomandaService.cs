using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
    public class DatiDomandaService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly IResolveDescrizioneIntervento _resolveDescrizioneIntervento;
        private readonly IWorkflowService _workflowService2;
        private readonly IComuniService _comuniService;

        public DatiDomandaService(ISalvataggioDomandaStrategy salvataggioStrategy, IResolveDescrizioneIntervento resolveDescrizioneIntervento,
            IWorkflowService workflowService2, IComuniService comuniService)
        {
            this._salvataggioStrategy = salvataggioStrategy;
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
            this._workflowService2 = workflowService2;
            this._comuniService = comuniService;
        }

        public void SetCodiceComune(int idDomanda, string idComuneAssociato)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            this.SetCodiceComune(domanda, idComuneAssociato);

            this._salvataggioStrategy.Salva(domanda);
        }

        internal void SetCodiceComune(DomandaOnline domanda, string codiceComune)
        {
            var comune = this._comuniService.GetByCodiceComune(codiceComune);
            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(codiceComune, comune?.CodiceISTAT ?? "");
        }

        public void SetFlagPrivacy(int idDomanda, bool value)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaFlagPrivacy(value);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void ImpostaDatiIstanza(int idDomanda, string note, string oggetto, string denominazioneAttivita = "")
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaDescrizione(note, oggetto, denominazioneAttivita);

            this._salvataggioStrategy.Salva(domanda);
        }

        public virtual void ImpostaIntervento(int idDomanda, int idIntervento, string descrizioneIntervento, bool popolaDescrizioneLavoriDaIntervento = true)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, descrizioneIntervento, popolaDescrizioneLavoriDaIntervento);

            this._salvataggioStrategy.Salva(domanda);

            this._workflowService2.ClearCacheDomanda(idDomanda);
        }

        public virtual void ImpostaIdIntervento(int idDomanda, int idIntervento, int? idAttivitaAtecoSelezionata, bool popolaDescrizioneLavoriDaIntervento)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, idAttivitaAtecoSelezionata, this._resolveDescrizioneIntervento, popolaDescrizioneLavoriDaIntervento);

            this._salvataggioStrategy.Salva(domanda);

            this._workflowService2.ClearCacheDomanda(idDomanda);
        }

        public void ImpostaIdDomandaCollegata(int idDomanda, int idDomandaOrigine)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaIdDomandaCollegata(idDomandaOrigine);

            this._salvataggioStrategy.Salva(domanda);

            this._salvataggioStrategy.ImpostaIdIstanzaOrigine(idDomanda, idDomandaOrigine);
        }
    }
}
