using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class DatiDomandaService
	{
		ISalvataggioDomandaStrategy     _salvataggioStrategy;
		IResolveDescrizioneIntervento	_resolveDescrizioneIntervento;
		IAliasSoftwareResolver		    _aliasSoftwareResolver;
		IWorkflowService			    _workflowService;

        public DatiDomandaService(ISalvataggioDomandaStrategy salvataggioStrategy, IResolveDescrizioneIntervento resolveDescrizioneIntervento,
									IAliasSoftwareResolver aliasSoftwareResolver, IWorkflowService workflowService)
		{
			Condition.Requires(salvataggioStrategy, "salvataggioStrategy").IsNotNull();
            Condition.Requires(resolveDescrizioneIntervento, "resolveDescrizioneIntervento").IsNotNull();
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
			Condition.Requires(workflowService, "workflowService").IsNotNull();

			this._salvataggioStrategy	= salvataggioStrategy;
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._workflowService		= workflowService;
		}

		public void SetCodiceComune(int idDomanda, string idComuneAssociato)
		{
			var domanda = _salvataggioStrategy.GetById(idDomanda);

            SetCodiceComune(domanda, idComuneAssociato);

			_salvataggioStrategy.Salva(domanda);
		}

        internal void SetCodiceComune(DomandaOnline domanda, string idComuneAssociato)
        {
            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(idComuneAssociato);
        }

		public void SetFlagPrivacy(int idDomanda, bool value)
		{
			var domanda = _salvataggioStrategy.GetById(idDomanda);

			domanda.WriteInterface.AltriDati.ImpostaFlagPrivacy(value);

			_salvataggioStrategy.Salva(domanda);
		}

		public void ImpostaDatiIstanza(int idDomanda, string note, string oggetto, string denominazioneAttivita)
		{
			var domanda = _salvataggioStrategy.GetById(idDomanda);

			domanda.WriteInterface.AltriDati.ImpostaDescrizione(note, oggetto, denominazioneAttivita);

			_salvataggioStrategy.Salva(domanda);
		}

		public void ImpostaIdIntervento(int idDomanda, int idIntervento, int? idAttivitaAtecoSelezionata, bool popolaDescrizioneLavoriDaIntervento)
		{
			var domanda = _salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaIntervento(idIntervento, idAttivitaAtecoSelezionata, this._workflowService, this._resolveDescrizioneIntervento, popolaDescrizioneLavoriDaIntervento);

			_salvataggioStrategy.Salva(domanda);
		}

        public void ImpostaIdDomandaCollegata(int idDomanda, int idDomandaOrigine)
        {
            var domanda = _salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaIdDomandaCollegata(idDomandaOrigine);

            _salvataggioStrategy.Salva(domanda);

            this._salvataggioStrategy.ImpostaIdIstanzaOrigine(idDomanda, idDomandaOrigine);
        }
    }
}
