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

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class DatiDomandaService
	{
		ISalvataggioDomandaStrategy _salvataggioStrategy;
		IInterventiRepository		_interventiRepository;
		IAliasSoftwareResolver		_aliasSoftwareResolver;
		IWorkflowService			_workflowService;

		public DatiDomandaService(ISalvataggioDomandaStrategy salvataggioStrategy, IInterventiRepository interventiRepository,
									IAliasSoftwareResolver aliasSoftwareResolver, IWorkflowService workflowService)
		{
			Condition.Requires(salvataggioStrategy, "salvataggioStrategy").IsNotNull();
			Condition.Requires(interventiRepository, "interventiRepository").IsNotNull();
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
			Condition.Requires(workflowService, "workflowService").IsNotNull();

			this._salvataggioStrategy	= salvataggioStrategy;
			this._interventiRepository	= interventiRepository;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._workflowService		= workflowService;
		}

		public void SetCodiceComune(int idDomanda, string idComuneAssociato)
		{
			var domanda = _salvataggioStrategy.GetById(idDomanda);

			domanda.WriteInterface.AltriDati.ImpostaCodiceComune( idComuneAssociato );

			_salvataggioStrategy.Salva(domanda);
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

		public void ImpostaIdIntervento(int idDomanda, int idIntervento, int? idAttivitaAtecoSelezionata)
		{
			var domanda = _salvataggioStrategy.GetById(idDomanda);

			var interventoModificato = domanda.ReadInterface.AltriDati.Intervento == null || domanda.ReadInterface.AltriDati.Intervento.Codice != idIntervento;

			if (interventoModificato)
			{
				var descrizioneIntervemto = EstraiDescrizioneIntervento(idIntervento);

				domanda.WriteInterface.AltriDati.ImpostaIntervento( idIntervento, descrizioneIntervemto, idAttivitaAtecoSelezionata, this._workflowService);
			}

			_salvataggioStrategy.Salva(domanda);
		}

		/// <summary>
		/// Estrae la descrizione estesa dell'intervento risalendo alberoproc e costruendo una stringa unica 
		/// in cui ogni nodo è separato da " - "
		/// </summary>
		/// <param name="codiceIntervento">Codice dell'intervento da cui estrarre la descrizione estesa</param>
		/// <returns>Descrizione estesa dell'intervento</returns>
		private string EstraiDescrizioneIntervento(int idIntervento)
		{
			ClassTreeOfInterventoDto strutturaAlbero = _interventiRepository.GetAlberaturaNodoDaId(_aliasSoftwareResolver.AliasComune, idIntervento);

			var nomeIntervento = new StringBuilder();

			nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);

			while (strutturaAlbero.NodiFiglio != null && strutturaAlbero.NodiFiglio.Length > 0)
			{
				strutturaAlbero = strutturaAlbero.NodiFiglio[0];

				nomeIntervento.Append(Environment.NewLine);
				nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);
			}

			return nomeIntervento.ToString();
		}
	}
}
