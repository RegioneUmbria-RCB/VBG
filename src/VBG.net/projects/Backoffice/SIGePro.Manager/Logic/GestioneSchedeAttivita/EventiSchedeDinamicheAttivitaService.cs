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

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class EventiSchedeDinamicheAttivitaService
	{
		AuthenticationInfo _authInfo;

		public EventiSchedeDinamicheAttivitaService(string aliasComune)
		{
			this._authInfo = AuthenticationManager.Login(aliasComune, Settings.Default.SigeproSecurityUsername, Settings.Default.SigeproSecurityPassword, ContextType.ExternalUsers);
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
