using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Web.Services;
using System.Xml;
using System.Xml.XPath;
using System.Xml.Xsl;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using Init.SIGePro.Manager.Logic.SmtpMail;
using Init.SIGePro.Utils;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using log4net;
using PersonalLib2.Data;
using Sigepro.net.WebServices.WsSIGePro;

namespace SIGePro.Net.WebServices.WsSIGeProAnagrafe
{
	/// <summary>
	/// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	public class WsAnagrafe : SigeproWebService
	{
		ILog _logger = LogManager.GetLogger(typeof(WsAnagrafe));

		static Dictionary<string,Type> _typesDictionary = new Dictionary<string, Type>();


		private const string CONCRETE_IMPLEMENTOR_NAME = "AnagrafeSearcher";
		private const string DEFAULT_ANAGRAFE_SEARCHER_NAME = "DEFAULTANAGRAFESEARCHER";


		/// <summary>
		/// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per codice fiscale
		/// </summary>
		/// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
		/// <param name="codiceFiscale">Codice fiscale da ricercare</param>
		/// <returns>Dati dell'anagrafica trovata o null se non è stato trovato niente</returns>
		[WebMethod]
		public Anagrafe ByCodiceFiscale(string authenticationToken, string codiceFiscale)
		{
			var authInfo = CheckToken(authenticationToken);

			using (DataBase db = authInfo.CreateDatabase())
			{
				var searcher = GetSearcher(db, authInfo);

				try
				{
					searcher.Init();

					return searcher.ByCodiceFiscaleImp(codiceFiscale);
				}
				catch (Exception ex)
				{
					_logger.DebugFormat("Errore in ByCodiceFiscale:" + ex.ToString());
					throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
				}
				finally
				{
					if (searcher != null)
						searcher.CleanUp();
				}
			}
		}

		/// <summary>
		/// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per codice fiscale
		/// </summary>
		/// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
		/// <param name="tipoPersona">Tipo persona da ricercare</param> 
		/// <param name="codiceFiscale">Codice fiscale da ricercare</param>
		/// <returns>Dati dell'anagrafica trovata o null se non è stato trovato niente</returns>
		[WebMethod(MessageName = "ByCodiceFiscaleETipoPersona")]
		public Anagrafe ByCodiceFiscale(string authenticationToken, TipoPersona tipoPersona, string codiceFiscale)
		{
			var authInfo = CheckToken(authenticationToken);

			using (DataBase db = authInfo.CreateDatabase())
			{
				var searcher = GetSearcher(db, authInfo);

				try
				{
					searcher.Init();

					return searcher.ByCodiceFiscaleImp(tipoPersona, codiceFiscale);
				}
				catch (Exception ex)
				{
					throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
				}
				finally
				{
					if (searcher != null)
						searcher.CleanUp();
				}
			}
		}

		public Anagrafe GetByUserId(string authenticationToken, string userId, TipoPersona tipoPersona)
		{
			var authInfo = CheckToken(authenticationToken);

			if (authInfo == null)
				throw new InvalidTokenException(authenticationToken);

			using (DataBase db = authInfo.CreateDatabase())
			{
                return new AnagrafeMgr(db).GetByUserId(authInfo.IdComune, userId, tipoPersona == TipoPersona.PersonaFisica ? AnagrafeMgr.TipoPersona.Fisica : AnagrafeMgr.TipoPersona.Giuridica);
			}
		}

		/// <summary>
		/// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per partita iva
		/// </summary>
		/// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
		/// <param name="partitaIva">Partita IVA da ricercare</param>
		/// <returns>Dati dell'anagrafica trovata o null se non è stato trovato niente</returns>
        [WebMethod]
        public Anagrafe ByPartitaIva(string authenticationToken, string partitaIva)
		{
			var authInfo = CheckToken(authenticationToken);

			using (DataBase db = authInfo.CreateDatabase())
			{
				var searcher = GetSearcher(db, authInfo);

				try
				{
					searcher.Init();

					return searcher.ByPartitaIvaImp(partitaIva);
				}
				catch (Exception ex)
				{
					throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
				}
				finally
				{
					if (searcher != null)
						searcher.CleanUp();
				}
			}
		}

		/// <summary>
		/// Effettua una ricerca nell'anagrafica del comune o in una fonte esterna per nome e/o cognome
		/// </summary>
		/// <param name="authenticationToken">Token ottenuto dall'autenticazione</param>
		/// <param name="nome">Nome da ricercare</param>
		/// <param name="cognome">Cognome da ricercare</param>
		/// <returns>Lista delle anagrafiche trovate o lista vuota se non è stato trovato niente</returns>
		[WebMethod]
		public List<Anagrafe> ByNomeCognome(string authenticationToken, string nome, string cognome)
		{
			var authInfo = CheckToken(authenticationToken);

			using (DataBase db = authInfo.CreateDatabase())
			{
				var searcher = GetSearcher(db, authInfo);

				try
				{
					searcher.Init();

					return searcher.ByNomeCognomeImp(string.IsNullOrEmpty(nome) ? null : nome, string.IsNullOrEmpty(cognome) ? null : cognome);
				}
				catch (Exception ex)
				{
					throw new SIGeProNetException(authInfo, "WS_ANAG", ex.ToString(), ex);
				}
				finally
				{
					if (searcher != null)
						searcher.CleanUp();
				}
			}
		}


		[WebMethod]
		public Anagrafe GetDaCodiceAnagrafe(string token, int codiceanagrafe)
		{
			var authInfo = CheckToken(token);

			return new AnagrafeMgr(authInfo.CreateDatabase()).GetById(authInfo.IdComune, codiceanagrafe);
		}

		[WebMethod]
		public void ModificaPasswordAnagrafe(string token, int codiceAnagrafe, string vecchiaPassword, string nuovaPassword)
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				var anagrafe = new AnagrafeMgr(db).GetById(authInfo.IdComune, codiceAnagrafe);

				if (anagrafe.PASSWORD != GetMd5(vecchiaPassword))
					throw new InvalidOperationException("La password di verifica non coincide con la password attuale");

				if (String.IsNullOrEmpty(nuovaPassword))
					throw new InvalidOperationException("La password non può essere vuota");

				anagrafe.PASSWORD = GetMd5(nuovaPassword);

				new AnagrafeMgr(db).Update(anagrafe);
			}
		}


		[WebMethod]
		public void RichiediModificaDati(string token, Anagrafe nuoviDatianagrafici)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				try
				{
					db.BeginTransaction();

					OggettiMgr oggMgr = new OggettiMgr(db);
					FoRichiesteMgr ricMgr = new FoRichiesteMgr(db);

					// Inserisco l'oggetto
					Oggetti ogg = oggMgr.InsertClass(authInfo.IdComune, "Datianagrafica.xml", nuoviDatianagrafici);

					// Inserisco la richiesta
					var ric = new FoRichieste
					{
						Idcomune = authInfo.IdComune,
						Codiceanagrafe = Convert.ToInt32(nuoviDatianagrafici.CODICEANAGRAFE),
						Codiceoggetto = Convert.ToInt32(ogg.CODICEOGGETTO),
						Codicerichiesta = FoRichieste.RICHIESTA_MODIFICA_DATI,
						Datarichiesta = DateTime.Now
					};

					ricMgr.Insert(ric);

					db.CommitTransaction();

					InviaMessaggioNotifica(db, authInfo.IdComune, "TT", "AR_MODIFICADATI", nuoviDatianagrafici);
				}
				catch (Exception ex)
				{
					Logger.LogEvent(authInfo, "WsAnagrafe.RichiediModificaDati", ex.ToString(), null);

					db.RollbackTransaction();
				}
			}
		}

        /*
		[WebMethod]
		public void RichiediNuovaRegistrazione(string token, Anagrafe anagrafe)
		{
			_logger.Debug("RichiediNuovaRegistrazione");

			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				anagrafe.IDCOMUNE = authInfo.IdComune;

				// Verifico se l'anagrafica esiste, se non esiste la creo
				var tmpAnagrafe = new AnagrafeMgr(db).GetPersonaFisicaByCodiceFiscale(authInfo.IdComune, anagrafe.CODICEFISCALE);

				try
				{
					db.BeginTransaction();

					if (tmpAnagrafe == null)
					{
						anagrafe.TIPOANAGRAFE = "F";

						_logger.Debug("L'anagrafica con codice fiscale " + anagrafe.CODICEFISCALE + " non esiste e verrà creata");
						//_logger.Debug("Dati della classe: " + StreamUtils.SerializeClass(anagrafe));

						tmpAnagrafe = new AnagrafeMgr(db).Insert(anagrafe);

						_logger.Debug("Anagrafica creata con sucesso ");
					}

					//anagrafe = tmpAnagrafe;
					
					_logger.Debug("Creazione della nuova richiesta di registrazione");

					OggettiMgr oggMgr = new OggettiMgr(db);
					FoRichiesteMgr ricMgr = new FoRichiesteMgr(db);

					// Inserisco l'oggetto
					_logger.Debug("Inserimento dell'oggetto xml");
					Oggetti ogg = oggMgr.InsertClass(authInfo.IdComune, "Datianagrafica.xml", anagrafe);

					// Inserisco la richiesta
					var ric = new FoRichieste
					{
						Idcomune = authInfo.IdComune,
						Codiceanagrafe = Convert.ToInt32(tmpAnagrafe.CODICEANAGRAFE),
						Codiceoggetto = Convert.ToInt32(ogg.CODICEOGGETTO),
						Codicerichiesta = FoRichieste.RICHIESTA_NUOVA_REGISTRAZIONE,
						Datarichiesta = DateTime.Now
					};

					_logger.Debug("Inserimento della richiesta");
					ricMgr.Insert(ric);

					db.CommitTransaction();
				}
				catch (Exception ex)
				{
					db.RollbackTransaction();

					_logger.Error("Errore in RichiediNuovaRegistrazione: " + ex.ToString() );

					Logger.LogEvent(authInfo, "WsAnagrafe.RichiediModificaDati", ex.ToString(), null);

                    throw;
				}

				_logger.Debug("Invio del messaggio di notifica");
				InviaMessaggioNotifica(db, authInfo.IdComune, "TT", "AR_NUOVA_REGISTRAZIONE", anagrafe);
			}
		}
        */
		private void InviaMessaggioNotifica(DataBase db, string idComune, string software, string codContesto, Anagrafe anagrafica)
		{
			var listaMessaggi = new MessaggiCfgMgr(db).GetDaSoftwareEContesto(idComune, software, codContesto);

			if (listaMessaggi == null || listaMessaggi.Count == 0) // nessun messaggio configurato per il contesto
				return;

			var anagrafeSerializzata = StreamUtils.SerializeClass(anagrafica);


			TextReader istanzaTextReader = new StringReader(anagrafeSerializzata);
			XmlTextReader xmlReader = new XmlTextReader(istanzaTextReader);
			XPathDocument xPathDocument = new XPathDocument(xmlReader);

			foreach (var msgCfg in listaMessaggi)
			{
				var destinatariEmail = new List<string>();

				TipoInvioMessaggioEnum invioEmail = ((TipoInvioMessaggioEnum)msgCfg.FlgTipoinvio.GetValueOrDefault(0)) & TipoInvioMessaggioEnum.Email;

				if (invioEmail == TipoInvioMessaggioEnum.Nessuno)
					continue;

				var oggetto = ElaboraXPath(xPathDocument, msgCfg.Oggetto);
				var corpo = ElaboraXPath(xPathDocument, msgCfg.Corpo);

				oggetto = oggetto.Replace("<?xml version=\"1.0\" encoding=\"utf-16\"?>", "");
				corpo = corpo.Replace("<?xml version=\"1.0\" encoding=\"utf-16\"?>", "");

				DestinatariMessaggioEnum invioCittadino = ((DestinatariMessaggioEnum)msgCfg.FlgInvio.GetValueOrDefault(0)) & DestinatariMessaggioEnum.CittadinoRichiedente;
				DestinatariMessaggioEnum invioResponsabile = ((DestinatariMessaggioEnum)msgCfg.FlgInvio.GetValueOrDefault(0)) & DestinatariMessaggioEnum.ResponsabileSportello;

				if (invioCittadino != DestinatariMessaggioEnum.Nessuno && !String.IsNullOrEmpty(anagrafica.EMAIL))
					destinatariEmail.Add(anagrafica.EMAIL);

				if (invioResponsabile != DestinatariMessaggioEnum.Nessuno)
				{
					var emailResponsabile = new ConfigurazioneMgr(db).GetByIdComuneESoftwareSovrascrivendoTT(idComune, software).EMAILRESPONSABILE;

					if (!String.IsNullOrEmpty(emailResponsabile))
						destinatariEmail.Add(emailResponsabile);
				}

				if (destinatariEmail.Count == 0)
					continue;

				try
				{
					var msg = new SIGeProMailMessage
					{
						CorpoMail = corpo,
						Oggetto = oggetto,
						InviaComeHtml = true,
						Destinatari = String.Join(";", destinatariEmail.ToArray())
					};

					new SmtpSender().InviaEmail(db, idComune, software, msg);
				}
				catch (Exception ex)
				{
					Logger.LogEvent(db, idComune, "WS_ANAGRAFE", "Errore durante l'invio del messaggio di registrazione/modifica dati ai destinatari:" + ex.ToString(), "WS_ANAGRAFE");
				}

			}
		}

		private string ElaboraXPath(XPathDocument istanzaXml, string testoXsl)
		{
			string container = @"<xsl:stylesheet version=""1.0"" xmlns:xsl=""http://www.w3.org/1999/XSL/Transform"">
									<xsl:template match=""/"">{0}</xsl:template>
								</xsl:stylesheet>";

			testoXsl = String.Format(container, testoXsl);

			//read XSLT
			TextReader tr = new StringReader(testoXsl);
			XmlTextReader xtr = new XmlTextReader(tr);
			XslTransform xslt = new XslTransform();
			xslt.Load(xtr);

			//Creo lo stream di output
			StringBuilder sb = new StringBuilder();
			TextWriter tw = new StringWriter(sb);

			xslt.Transform(istanzaXml, null, tw);

			return sb.ToString();
		}

		/// <summary>
		/// Crea un istanza dell'oggetto che effettuerà la ricerca
		/// TODO: si potrebbero velocizzare le operazioni di caricamento utilizzando la cache del server
		/// </summary>
		/// <param name="authInfo">Credenziali di accesso</param>
		/// <returns>Implementatore di <see cref="AnagrafeSearcherBase"/> che effettua la ricerca nella fonte dati</returns>
		private IAnagrafeSearcher GetSearcher(DataBase db, AuthenticationInfo authInfo)
		{
			VerticalizzazioneWsanagrafe vert = new VerticalizzazioneWsanagrafe(authInfo.Alias, "TT");

			string assemblyName = vert.SearchComponent;

			_logger.DebugFormat("Caricamento del componente di ricerca dall'assembly {0}", assemblyName);


			var defaultSigeproSearcher = new AnagrafeSearcher(DEFAULT_ANAGRAFE_SEARCHER_NAME);
			defaultSigeproSearcher.InitParams(authInfo.IdComune, authInfo.Alias, db);

			if ( !vert.Attiva || assemblyName == "" || assemblyName.ToUpper() == DEFAULT_ANAGRAFE_SEARCHER_NAME)
				return defaultSigeproSearcher;

			try
			{
				var searcher = new SigeproWrappedAnagrafeSearcher( defaultSigeproSearcher, CreaIstanzaSearcher(vert));

                searcher.InitParams(authInfo.IdComune, authInfo.Alias, db);

				return searcher;
			}
			catch (Exception ex)
			{
				Logger.LogEvent(authInfo, "WsSIGeProAnagrafe.WsAnagrafe", ex.ToString(), "WSA");

				return null;
			}

		}

		private AnagrafeSearcherBase CreaIstanzaSearcher(VerticalizzazioneWsanagrafe vert)
		{
			try
			{
				var assemblyPath = vert.AssemblyLoadPath;
				var assemblyName = vert.SearchComponent;

				_logger.DebugFormat("assemblyPath = {0}", assemblyPath);
				_logger.DebugFormat("assemblyName = {0}", assemblyName);

				if (!_typesDictionary.ContainsKey(assemblyName))
				{
					_logger.DebugFormat("Searcher non trovato in cache, verrà istanziato un nuovo assembly");

					if (String.IsNullOrEmpty(assemblyPath))
						assemblyPath = HttpContext.Current.Server.MapPath(@"~\bin\");

					var fullAssemblyPath = Path.Combine(assemblyPath, assemblyName + ".dll");

					_logger.DebugFormat("fullAssemblyPath = {0}", fullAssemblyPath);

					var assembly = Assembly.LoadFrom(fullAssemblyPath);

					_logger.DebugFormat("Assembly caricato correttmente, cerco di istanziare il searcher");

					var fullSearcherTypeName = assemblyName + "." + CONCRETE_IMPLEMENTOR_NAME;

					_logger.DebugFormat("fullSearcherTypeName = {0}-", fullSearcherTypeName);

					Type tp = assembly.GetType(fullSearcherTypeName);

					if (tp == null)
						throw new Exception("Impossibile istanziare il tipo " + CONCRETE_IMPLEMENTOR_NAME + " dall'assembly " + assemblyName);

					_logger.DebugFormat("Searcher istanziato correttamente");

					_typesDictionary.Add(assemblyName, tp);
				}

				return (AnagrafeSearcherBase)Activator.CreateInstance(_typesDictionary[assemblyName]);
			}
			catch (Exception ex)
			{
				_logger.ErrorFormat("CreaIstanzaSearcher: {0}", ex.ToString());

				throw;
			}
		}

		private string GetMd5(string text)
		{
			byte[] pass = Encoding.UTF8.GetBytes(text);
			MD5 md5 = new MD5CryptoServiceProvider();
			var bytes = md5.ComputeHash(pass);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.Length; i++)
				sb.Append(bytes[i].ToString("X2"));

			return sb.ToString().ToLower();
		}
	}
}