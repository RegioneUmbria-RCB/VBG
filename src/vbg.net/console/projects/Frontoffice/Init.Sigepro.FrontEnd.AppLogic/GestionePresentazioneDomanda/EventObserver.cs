using System;
using System.Data;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda
{
	public interface IEventObserver { }

	public class EventObserver: IEventObserver
	{
		ILog _log = LogManager.GetLogger(typeof(EventObserver));

		PresentazioneIstanzaDbV2		_database;
		IDomandaOnlineReadInterface		_readInterface;
		IDomandaOnlineWriteInterface	_writeInterface;

		[Inject]
		protected IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }

		public EventObserver(PresentazioneIstanzaDbV2 database, IDomandaOnlineWriteInterface writeInterface, IDomandaOnlineReadInterface readInterface)
		{
			this._database = database;

			this._readInterface  = readInterface;
			this._writeInterface = writeInterface;

			AttachEvents();
			InjectServices();
		}

		protected void InjectServices()
		{
			FoKernelContainer.Inject(this);
		}



		private void AttachEvents()
		{
			// Anagrafe
			this._database.ANAGRAFE.RowChanged += (sender, e) =>
			{
				if (e.Action == DataRowAction.Add) OnAnagrafeRowAdded((PresentazioneIstanzaDbV2.ANAGRAFERow)e.Row);
			};

			this._database.ANAGRAFE.RowDeleting += (sender, e) =>
			{
				OnAnagrafeRowDeleted((PresentazioneIstanzaDbV2.ANAGRAFERow)e.Row);
			};

			// Procure
			this._database.Procure.RowDeleting += (sender, e) =>
			{
				OnProcuraDeleting((PresentazioneIstanzaDbV2.ProcureRow)e.Row);
			};

			// Allegati
			this._database.Allegati.RowDeleting += (sender, e) =>
			{
				OnAllegatoDeleting((PresentazioneIstanzaDbV2.AllegatiRow)e.Row);
			};

			// Intervento
			this._database.ISTANZE.RowChanging += (sender, e) => {

				if (e.Action == DataRowAction.Change)
				{
					var originalVal = e.Row["CODICEINTERVENTO", DataRowVersion.Current];
					var currentVal = e.Row["CODICEINTERVENTO", DataRowVersion.Default];

					if (originalVal == DBNull.Value)
						return;

					if (currentVal == DBNull.Value || ((int)originalVal != (int)currentVal))
						OnCodiceInterventoChanged((PresentazioneIstanzaDbV2.ISTANZERow)e.Row);
				}
			};

			// Oggetti
			this._database.OGGETTI.RowChanging += (sender, e) =>
			{
				if (e.Action == DataRowAction.Change)
				{
					var originalVal = e.Row["IdAllegato", DataRowVersion.Current];
					var currentVal  = e.Row["IdAllegato", DataRowVersion.Default];

					if ( originalVal == DBNull.Value )
						return;

					if( currentVal == DBNull.Value || ((int)originalVal != (int)currentVal))
						OnOggettoChanging((PresentazioneIstanzaDbV2.OGGETTIRow)e.Row);
				}

				InvalidateReadInterface();
			};

			this._database.OGGETTI.RowDeleting += (sender, e) =>{
				OnOggettoDeleting((PresentazioneIstanzaDbV2.OGGETTIRow)e.Row);
			};

			// Attestazione di pagamento oneri
			this._database.OneriAttestazionePagamento.RowDeleting += (sender, e) =>
			{
				OnAttestazioneDiPagamentoDeleting((PresentazioneIstanzaDbV2.OneriAttestazionePagamentoRow)e.Row);
			};
		}

		private void OnOggettoChanging(PresentazioneIstanzaDbV2.OGGETTIRow row)
		{
			var originalVal = row["IdAllegato", DataRowVersion.Current];

			if (originalVal == DBNull.Value)
				return;

			this._writeInterface.Allegati.Elimina(Convert.ToInt32(originalVal));

			InvalidateReadInterface();
		}

		private void OnAttestazioneDiPagamentoDeleting(PresentazioneIstanzaDbV2.OneriAttestazionePagamentoRow row)
		{
			if (row.IsIdAllegatoNull())
				return;

			this._writeInterface.Allegati.Elimina(row.IdAllegato);

			InvalidateReadInterface();
		}

		#region Oggetti
		private void OnOggettoDeleting(PresentazioneIstanzaDbV2.OGGETTIRow row)
		{
			if (row.IsIdAllegatoNull())
				return;

			this._writeInterface.Allegati.Elimina(row.IdAllegato);

			InvalidateReadInterface();
		}
		#endregion

		#region Gestione degli interventi
		private void OnCodiceInterventoChanged(PresentazioneIstanzaDbV2.ISTANZERow row)
		{
			_log.Debug("Codice intervento modificato");

			// Oneri
			this._database.OneriDomanda.Clear();
			this._database.OneriAttestazionePagamento.Clear();

			// Elimino gli endo
			this._database.ISTANZEPROCEDIMENTI.Clear();

			// Elimino i documenti
			this._database.OGGETTI.Clear();
			// this._database.Allegati.Clear();
			// L'allegato viene eliminato a cascata quando viene eliminato l'oggetto

			// Elimino i dati e i modelli dinamici
			this._database.ModelliInterventiEndo.Clear();
			this._database.Dyn2Dati.Clear();
			this._database.Dyn2Modelli.Clear();
			this._database.RiepilogoDatiDinamici.Clear();
			this._database.AutorizzazioniMercati.Clear();

			var datiBandoUmbria = this._database.DatiExtra.Where(x => x.Chiave == Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria.Constants.ChiaveDb).FirstOrDefault();
			
			if (datiBandoUmbria != null)
			{
				datiBandoUmbria.Delete();
			}

			InvalidateReadInterface();
		}
		#endregion

		#region eventi degli allegati
		private void OnAllegatoDeleting(PresentazioneIstanzaDbV2.AllegatiRow allegatiRow)
		{
			_log.Debug("Allegato eliminato, invocazione del ws di eliminazione allegati");

			var idDomanda = this._readInterface.AltriDati.IdPresentazione;

			_allegatiDomandaFoRepository.EliminaAllegato(idDomanda, allegatiRow.CodiceOggetto);

			InvalidateReadInterface();
		}
		#endregion

		#region eventi delle procure

		private void OnProcuraDeleting(PresentazioneIstanzaDbV2.ProcureRow procureRow)
		{
			_log.Debug("Procura eliminata, se presente elimino l'allegato");

			if (procureRow.IsIdAllegatoNull())
				return;

			this._writeInterface.Allegati.Elimina(procureRow.IdAllegato);

			InvalidateReadInterface();
		}

		#endregion

		#region eventi della tabella anagrafe
		private void OnAnagrafeRowDeleted(PresentazioneIstanzaDbV2.ANAGRAFERow rigaEliminata)
		{
			_log.Debug("Anagrafica eliminata");

			// se l'anagrafica è di una persona fisica elimino anche i riferimenti nella tabella procure
			// (sempre che l'anagrafica non compaia altre volte tra i soggetti dell'istanza)

			if (rigaEliminata.TIPOANAGRAFE != AnagraficheConstants.TipiPersona.Fisica)
				return;

			var codFiscaleAnagrafica = rigaEliminata.CODICEFISCALE.ToUpperInvariant();

			if ( 1 < _readInterface.Anagrafiche.Anagrafiche.Count(x => x.TipoPersona == TipoPersonaEnum.Fisica && x.Codicefiscale.ToUpperInvariant() == codFiscaleAnagrafica))
				return;

			this._writeInterface.Procure.EliminaProcureContenenti(codFiscaleAnagrafica);

			InvalidateReadInterface();
		}

		private void OnAnagrafeRowAdded(PresentazioneIstanzaDbV2.ANAGRAFERow nuovaRiga)
		{
			_log.Debug("Anagrafica aggiunta alla domanda, se necessario creo un nuovo record nelle procure");

			if(nuovaRiga.TIPOANAGRAFE != AnagraficheConstants.TipiPersona.Fisica)
				return;

			// Crea un record in procure
			var codiceFiscaleNuovaRiga = nuovaRiga.CODICEFISCALE.ToUpperInvariant();

			if( this._readInterface.Procure.Procure.Count( x => x.Procurato.CodiceFiscale.ToUpperInvariant() == codiceFiscaleNuovaRiga ) != 0 )
				return;

			this._writeInterface.Procure.Aggiungi( codiceFiscaleNuovaRiga , String.Empty );

			InvalidateReadInterface();
		}



		#endregion

		private void InvalidateReadInterface()
		{
			this._readInterface.Invalidate();
		}


	}
}
