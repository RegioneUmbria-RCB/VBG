// -----------------------------------------------------------------------
// <copyright file="IMessaggiNotificaInvioService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
	using System;
	using System.Threading.Tasks;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using log4net;

	public interface IMessaggiNotificaInvioService
	{
		void Invia(int idDomanda, string codiceistanzaCreata);
	}

	public class MessaggiNotificaInvioService : IMessaggiNotificaInvioService
	{
		ILog _log = LogManager.GetLogger(typeof(MessaggiNotificaInvioService));
		IMessaggiFrontofficeRepository _messaggiFrontofficeRepository;
		IAliasResolver _aliasResolver;

		public MessaggiNotificaInvioService(IAliasResolver aliasResolver, IMessaggiFrontofficeRepository messaggiFrontofficeRepository)
		{
			this._aliasResolver = aliasResolver;
			this._messaggiFrontofficeRepository = messaggiFrontofficeRepository;
		}


		#region IMessaggiNotificaInvioService Members

		public void Invia(int idDomanda, string codiceistanzaCreata)
		{
			var aliasComune = this._aliasResolver.AliasComune;

			var task = Task.Factory.StartNew(() =>
			{
				try
				{
					int iCodiceIstanza = -1;

					if (Int32.TryParse(codiceistanzaCreata, out iCodiceIstanza))
					{
						this._messaggiFrontofficeRepository.InviaMessaggioDomandaRicevuta(aliasComune, idDomanda, iCodiceIstanza);

						return;
					}

					_log.ErrorFormat("Errore durante l'invio del messaggio di avvenuta ricezione pratica (IdDomanda={0},IdComune={1},CodiceIstanza={2}): la domanda non è stata inviata a vbg/sigepro", idDomanda, aliasComune, codiceistanzaCreata);

				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante l'invio del messaggio di avvenuta ricezione pratica (IdDomanda={0},IdComune={1},CodiceIstanza={2}): {3}", idDomanda, aliasComune, codiceistanzaCreata, ex.ToString());
				}
			});
		}

		#endregion
	}


}
