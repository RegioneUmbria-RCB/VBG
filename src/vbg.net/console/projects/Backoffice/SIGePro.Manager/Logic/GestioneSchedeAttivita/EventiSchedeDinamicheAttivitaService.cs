// -----------------------------------------------------------------------
// <copyright file="EventiSchedeDinamicheAttivitaService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita
{
	using Init.SIGePro.Authentication;
	using Init.SIGePro.Manager.Authentication;
	using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;
	using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.ExternalServices;
	using Init.SIGePro.Manager.Properties;
using log4net;
    using System.Web;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class EventiSchedeDinamicheAttivitaService
	{
		AuthenticationInfo _authInfo;
        ILog _log = LogManager.GetLogger(typeof(EventiSchedeDinamicheAttivitaService));

        internal EventiSchedeDinamicheAttivitaService():
            this(new CurrentRequestFromHttpContext(HttpContext.Current))
        {

        }

		public EventiSchedeDinamicheAttivitaService(ICurrentRequestContext ctxt)
		{
            var alias = ctxt.GetCurrentUser().Alias;

            this._log.DebugFormat("EventiSchedeDinamicheAttivitaService:ctor-> Login con aliasComune={0}, username={1}, password={2}, context={3}", alias, Settings.Default.SigeproSecurityUsername, Settings.Default.SigeproSecurityPassword, ContextType.ExternalUsers);

            this._authInfo = AuthenticationManager.LoginApplicativo(alias);

            this._log.DebugFormat("EventiSchedeDinamicheAttivitaService:ctor-> this._authInfo.token={0}, this._authInfo.Alias={1}", this._authInfo.Token, this._authInfo.Alias);
		}

		public void Handle(SchedaDinamicaAggiuntaAdAttivita @event)
		{
			// All aggiunta di una Scheda all'attività viene invocato nel servizio del backend il metodo WS-AggiornaCampiSchede
			// per forzare l'aggiornamento dei valori dei campi delle schede
			using (var servizioSnapshot = CreaServizioSnapshot())
			{
				servizioSnapshot.AggiornaCampiSchede(this._authInfo.Token, @event.Software, @event.IdAttivita);
			}
		}

		public void Handle(SchedaDinamicaIstanzaSalvata @event)
		{
			using (var servizioSnapshot = CreaServizioSnapshot())
			{
                if (this._log.IsDebugEnabled)
                {
                    this._log.DebugFormat("SchedaDinamicaIstanzaSalvata={0}", @event.ToLogFormat());
                }

				servizioSnapshot.AggiornaCampiSchedeDaIstanza(this._authInfo.Token, @event.Software, @event.IdAttivita, @event.CodiceIstanza, @event.IdScheda);
			}
		}

		public void Handle(SchedaDinamicaIstanzaEliminata @event)
		{
			using (var servizioSnapshot = CreaServizioSnapshot())
			{
				servizioSnapshot.AggiornaCampiSchedeDaIstanza(this._authInfo.Token, @event.Software, @event.IdAttivita, @event.CodiceIstanza, @event.IdScheda);
			}
		}

		public void Handle(SchedaDinamicaAttivitaSalvata @event)
		{
			using (var servizioSnapshot = CreaServizioSnapshot())
			{
				servizioSnapshot.CreaSnapshot(this._authInfo.Token, @event.Software, @event.IdAttivita);
			}
		}

		private IGestioneSnapshotAttivitaService CreaServizioSnapshot()
		{
			return new GestioneSnapshotAttivitaService(this._authInfo.Alias);
		}

	}
}
