using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Common;
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
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IWorkflowService _workflowService;

        public DatiDomandaService(ISalvataggioDomandaStrategy salvataggioStrategy, IResolveDescrizioneIntervento resolveDescrizioneIntervento,
                                    IAliasSoftwareResolver aliasSoftwareResolver, IWorkflowService workflowService)
        {
            Condition.Requires(salvataggioStrategy, "salvataggioStrategy").IsNotNull();
            Condition.Requires(resolveDescrizioneIntervento, "resolveDescrizioneIntervento").IsNotNull();
            Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
            Condition.Requires(workflowService, "workflowService").IsNotNull();

            this._salvataggioStrategy = salvataggioStrategy;
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._workflowService = workflowService;
        }

        public void SetCodiceComune(int idDomanda, string idComuneAssociato)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            this.SetCodiceComune(domanda, idComuneAssociato);

            this._salvataggioStrategy.Salva(domanda);
        }

        internal void SetCodiceComune(DomandaOnline domanda, string idComuneAssociato)
        {
            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(idComuneAssociato);
        }

        public void SetFlagPrivacy(int idDomanda, bool value)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaFlagPrivacy(value);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void ImpostaDatiIstanza(int idDomanda, string note, string oggetto, string denominazioneAttivita)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaDescrizione(note, oggetto, denominazioneAttivita);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void ImpostaIdIntervento(int idDomanda, int idIntervento, int? idAttivitaAtecoSelezionata, bool popolaDescrizioneLavoriDaIntervento)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, idAttivitaAtecoSelezionata, this._workflowService, this._resolveDescrizioneIntervento, popolaDescrizioneLavoriDaIntervento);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine)
        {
            this._salvataggioStrategy.ImpostaIdIstanzaOrigine(idDomanda, idDomandaOrigine);
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
