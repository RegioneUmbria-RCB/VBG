// -----------------------------------------------------------------------
// <copyright file="StcService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.STC
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.StcService;
	using log4net;
	using Init.Utils;
	using System.Web;
	using System.IO;
	using System.Xml.Serialization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class StcServiceImpl : IStcService
	{
		ILog _log = LogManager.GetLogger(typeof(StcServiceImpl));
		IStcServiceCreator _serviceCreator;

		public StcServiceImpl(IStcServiceCreator serviceCreator)
		{
			this._serviceCreator = serviceCreator;
		}

		#region IStcService Members

		public NotificaAttivitaResponse NotificaAttivita(NotificaAttivitaRequest request)
		{
			using (var stc = this._serviceCreator.CreateClient())
			{
				try
				{
					request.token = stc.Token;
					request.sportelloMittente = this._serviceCreator.ConfigurazioneStc.NodoMittente.AsSportelloType();
					request.sportelloDestinatario = this._serviceCreator.ConfigurazioneStc.NodoDestinatario.AsSportelloType();

					_log.DebugFormat("Inizio invocazione di STC::NotificaAttivita con i parametri: {0}", StreamUtils.SerializeClass(request));

					SalvaMovimentoInUscita(request);

					var response = stc.Service.NotificaAttivita(request);

					foreach (var item in response.Items)
					{
						if (item is ErroreType)
						{
							var datiErrore = item as ErroreType;
							throw new Exception(datiErrore.descrizione + " [" + datiErrore.numeroErrore + "]");
						}
					}

					_log.DebugFormat("STC::NotificaAttivita invocato con successo, risultato: {0}", StreamUtils.SerializeClass(response));

					return response;
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante l'invocazione di STC::NotificaAttivita: {0}", ex.ToString());

					stc.Service.Abort();

					throw;
				}
			}
		}

		public InserimentoPraticaResponse InserimentoPratica(InserimentoPraticaRequest request, string pecSportello)
		{


			using (var service = this._serviceCreator.CreateClient())
			{
				try
				{
					var configurazioneStc =  this._serviceCreator.ConfigurazioneStc;
					var idNodoMittente = configurazioneStc.NodoMittente.Id;
					var idEnteMittente = configurazioneStc.NodoMittente.Ente;
					var idSportelloMittente = configurazioneStc.NodoMittente.Sportello;

					var idNodoDestinatario = configurazioneStc.NodoDestinatario.Id;
					var idEnteDestinatario = configurazioneStc.NodoDestinatario.Ente;
					var idSportelloDestinatario = configurazioneStc.NodoDestinatario.Sportello;

					request.sportelloMittente = new SportelloType
					{
						idEnte = idEnteMittente,
						idSportello = idSportelloMittente,
						idNodo = idNodoMittente
					};

					request.sportelloDestinatario = new SportelloType
					{
						idEnte = idEnteDestinatario,
						idSportello = idSportelloDestinatario,
						idNodo = idNodoDestinatario,
						pecSportello = pecSportello
					};

					request.token = service.Token;

					SalvaXmlPratica(request);

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Inizio invocazione di STC::InserimentoPratica con i parametri: {0}", StreamUtils.SerializeClass(request));

					var response = service.Service.InserimentoPratica(request);

					if (response.Items == null || response.Items.Length == 0)
					{
						var msg = "L'invio della domanda STC ha restituito una struttura InserimentoPraticaResponse vuota";
						_log.Error(msg);

						throw new Exception(msg);
					}

					if (_log.IsDebugEnabled)
						_log.DebugFormat("STC::InserimentoPratica invocato con successo. Risposta: {0}", StreamUtils.SerializeClass(response));

					return response;
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante l'invocazione di STC::InserimentoPratica: {0}", ex.ToString());

					service.Service.Abort();

					throw;
				}
			}
		}

		private void SalvaXmlPratica(InserimentoPraticaRequest request)
		{
			try
			{
				if (HttpContext.Current != null)
				{
					var pathSalvataggioPratiche = HttpContext.Current.Server.MapPath("~/PraticheInUscita");
					var pathFileDomanda = Path.Combine(pathSalvataggioPratiche, request.dettaglioPratica.idPratica + ".xml");

					_log.DebugFormat("Xml della domanda in uscita salvato nel percorso {0}", pathFileDomanda);

					using (var fs = File.Open(pathFileDomanda, FileMode.Create))
					{
						var xs = new XmlSerializer(request.GetType());
						xs.Serialize(fs, request);
					}
				}
			}
			catch (Exception ex)
			{
				_log.WarnFormat("Non è stato possibile salvare l'xml della domanda in uscita per la seguente ragione: {0}", ex.ToString());
			}
		}


		public bool PraticaEsisteNelBackend(string idPratica)
		{
			try
			{
				var res = this.RichiestaPratica(idPratica);

				if (res == null || res.dettaglioPratica == null)
					return false;

				return true;
			}
			catch (Exception)
			{
				return false;
			}
		}

		public RichiestaPraticheListaResponse RichiestaPraticheLista(RichiestaPraticheListaRequest richiesta)
		{
			using (var client = this._serviceCreator.CreateClient())
			{
				try
				{
					richiesta.sportelloMittente = new SportelloType
					{
						idNodo = this._serviceCreator.ConfigurazioneStc.NodoMittente.Id,
						idSportello = this._serviceCreator.ConfigurazioneStc.NodoMittente.Sportello,
						idEnte = this._serviceCreator.ConfigurazioneStc.NodoMittente.Ente
					};

					richiesta.token = client.Token;

					richiesta.sportelloDestinatario = new SportelloType
					{
						idEnte = this._serviceCreator.ConfigurazioneStc.NodoDestinatario.Ente,
						idNodo = this._serviceCreator.ConfigurazioneStc.NodoDestinatario.Id,
						idSportello = this._serviceCreator.ConfigurazioneStc.NodoDestinatario.Sportello
					};

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Inizio invocazione di STC::RichiestaPraticheLista, parametri: {0} ", StreamUtils.SerializeClass(richiesta));

					var risposta = client.Service.RichiestaPraticheLista(richiesta);

					if (risposta.dettaglioErrore != null && risposta.dettaglioErrore.Length > 0)
					{
						var errMsg = new StringBuilder();

						errMsg.AppendFormat("Errore durante l'invocazione di STC::RichiestaPraticheLista con i parametri (seguono i dettagli dell'errore) \r\n{0}", StreamUtils.SerializeClass(richiesta));

						for (int i = 0 ; i < risposta.dettaglioErrore.Count() ; i++)
						{
							var errore = risposta.dettaglioErrore.ElementAt(i);
							errMsg.AppendFormat("Dettagli dell'errore:r\nCodice:{0}\r\nDescrizione:{1} ", errore.numeroErrore, errore.descrizione);
						}

						throw new VisuraException(errMsg.ToString());
					}

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione di STC::RichiestaPraticheLista terminata con successo, risposta: {0} ", StreamUtils.SerializeClass(risposta));

					return risposta;
				}
				catch (Exception ex)
				{
					client.Service.Abort();

					_log.ErrorFormat("Errore durante l'invocazione di STC::RichiestaPraticheLista, dettagli errore: {0}", ex.ToString());

					throw ex;
				}
			}
		}


		public RichiestaPraticaResponse RichiestaPratica(string idPratica)
		{
			var configurazioneStc =  this._serviceCreator.ConfigurazioneStc;
			var idNodoMittente = configurazioneStc.NodoMittente.Id;
			var idEnteMittente = configurazioneStc.NodoMittente.Ente;
			var idSportelloMittente = configurazioneStc.NodoMittente.Sportello;

			var idNodoDestinatario = configurazioneStc.NodoDestinatario.Id;
			var idEnteDestinatario = configurazioneStc.NodoDestinatario.Ente;
			var idSportelloDestinatario = configurazioneStc.NodoDestinatario.Sportello;

			using (var service = this._serviceCreator.CreateClient())
			{
				var richiestaPratica = new RichiestaPraticaRequest
				{
					sportelloMittente = new SportelloType
					{
						idEnte = idEnteMittente,
						idSportello = idSportelloMittente,
						idNodo = idNodoMittente
					},
					sportelloDestinatario = new SportelloType
					{
						idEnte = idEnteDestinatario,
						idSportello = idSportelloDestinatario,
						idNodo = idNodoDestinatario
					},
					token = service.Token,
					rifPratica = new RiferimentiPraticaType
					{
						idPratica = idPratica
					}
				};

				try
				{
					if (_log.IsDebugEnabled)
						_log.DebugFormat("inizio invocazione di STC::RichiestaPratica con i parametri: {0}", StreamUtils.SerializeClass(richiestaPratica));

					var result = service.Service.RichiestaPratica(richiestaPratica);

					if (_log.IsDebugEnabled)
						_log.DebugFormat("Invocazione di STC::RichiestaPratica terminata con successo. Risposta: {0}", StreamUtils.SerializeClass(result));

					return result;
				}
				catch (Exception ex)
				{
					service.Service.Abort();

					_log.ErrorFormat("Errore durante l'invocazione di STC::RichiestaPratica: {0}. \r\nDettagli Richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(richiestaPratica));

					throw;
				}

			}
		}

		public AllegatoBinarioResponse AllegatoBinario(string codiceOggetto)
		{
			using (var client = this._serviceCreator.CreateClient())
			{

				var configurazioneStc = this._serviceCreator.ConfigurazioneStc;

				var req = new AllegatoBinarioRequest
				{
					token = client.Token,
					riferimentiAllegato = new RiferimentiAllegatoType
					{
						idAllegato = codiceOggetto,
						idAttivita = String.Empty,
						idDocumento = String.Empty,
						idPratica = String.Empty
					},
					sportelloMittente = new SportelloType
					{
						idNodo = configurazioneStc.NodoMittente.Id,
						idSportello = configurazioneStc.NodoMittente.Sportello,
						idEnte = configurazioneStc.NodoMittente.Ente
					},
					sportelloDestinatario = new SportelloType
					{
						idEnte = configurazioneStc.NodoDestinatario.Ente,
						idNodo = configurazioneStc.NodoDestinatario.Id,
						idSportello = configurazioneStc.NodoDestinatario.Sportello
					},
				};

				try
				{
					var ret = client.Service.AllegatoBinario(req);

					return ret;
				}
				catch (Exception ex)
				{
					client.Service.Abort();

					_log.ErrorFormat("Errore durante l'invocazione di STC::AllegatoBinario: {0}\r\n\r\nDettagli della richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(req));

					throw;
				}
			}
		}

		#endregion


		private void SalvaMovimentoInUscita(NotificaAttivitaRequest request)
		{
			// Salvo la domanda in uscita nella cartella PraticheInUscita
			try
			{
				if (HttpContext.Current != null)
				{
					var nomeFile = String.Concat("movimento", request.datiAttivita.idAttivita, "_", DateTime.Now.ToString("yyyyMMddHHmmss"), ".xml");
					var pathSalvataggioPratiche = HttpContext.Current.Server.MapPath("~/PraticheInUscita");
					var pathFileDomanda = Path.Combine(pathSalvataggioPratiche, nomeFile);

					_log.DebugFormat("Xml della domanda in uscita salvato nel percorso {0}", pathFileDomanda);

					using (FileStream fs = File.Open(pathFileDomanda, FileMode.Create))
					{
						var xs = new System.Xml.Serialization.XmlSerializer(request.GetType());
						xs.Serialize(fs, request);
					}
				}
			}
			catch (Exception ex)
			{
				_log.WarnFormat("Non è stato possibile salvare l'xml della domanda in uscita per la seguente ragione: {0}", ex.ToString());
			}
		}

	}
}
