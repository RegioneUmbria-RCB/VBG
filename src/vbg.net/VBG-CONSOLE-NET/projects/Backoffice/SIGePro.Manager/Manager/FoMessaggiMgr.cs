namespace Init.SIGePro.Manager
{
	using System;
	using System.Collections.Generic;
	using System.ComponentModel;
	using System.IO;
	using System.Linq;
	using System.Text;
	using System.Xml;
	using System.Xml.XPath;
	using System.Xml.Xsl;
	using Init.SIGePro.Data;
	using Init.SIGePro.Manager.Logic.GestioneAnagrafiche;
	using Init.SIGePro.Manager.Logic.GestioneConfigurazione;
	using Init.SIGePro.Manager.Logic.GestioneOggetti;
	using Init.SIGePro.Manager.Logic.GestioneOperatori;
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice;
	using Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice.IstanzaRicevuta;
	using Init.SIGePro.Manager.Logic.SmtpMail;
	using Init.SIGePro.Utils;
	using Init.Utils;
	using log4net;
	using PersonalLib2.Data;
	using PersonalLib2.Sql;



	public enum DestinatariMessaggioEnum
	{
		Nessuno = 0,
		CittadinoRichiedente = 1,
		AltriSoggetti = 2,
		ResponsabileSportello = 4,
		ResponsabileProcedimento = 8,
		ResponsabileIstruttoria = 16,
		Operatore = 32
	}

	public enum TipoInvioMessaggioEnum
	{
		Nessuno = 0,
		Email = 1,
		MessaggioFrontoffice = 2
	}


	[DataObject(true)]
	public partial class FoMessaggiMgr
	{
		const string CONTESTO_INVIO_ISTANZA = "AR_INVIO";

		public static class Constants
		{
			public const string ContestoInvioIstanza = "AR_INVIO";
		}

		ILog _logger = LogManager.GetLogger(typeof(FoMessaggiMgr));
		string _idComune;

		public FoMessaggiMgr(DataBase db, string idComune)
			: base(db)
		{
			this._idComune = idComune;
		}

		public void CreaMessaggioDomanda(int idDomanda, string contesto, string codiceFiscaleMittente, string mittente, string istanzaSerializzata)
		{
			var domanda = new FoDomandeMgr(db).GetById(this._idComune, idDomanda);


			var listaMessaggi = new MessaggiCfgMgr(db).GetDaSoftwareEContesto(this._idComune, domanda.Software, contesto);

			if (listaMessaggi == null || listaMessaggi.Count == 0) // nessun messaggio configurato per il contesto
				return;

			// TODO: elaborare oggetto e corpo per popolare i dati letti dall'istanza
			TextReader istanzaTextReader = new StringReader(istanzaSerializzata);
			XmlTextReader xmlReader = new XmlTextReader(istanzaTextReader);
			XPathDocument xPathDocument = new XPathDocument(xmlReader);

			foreach (var msgCfg in listaMessaggi)
			{
				List<string> destinatariMessaggio = new List<string>();

				string corpo = "";
				string oggetto = "";

				TipoInvioMessaggioEnum invioMessaggio = ((TipoInvioMessaggioEnum)msgCfg.FlgTipoinvio.GetValueOrDefault(0)) & TipoInvioMessaggioEnum.MessaggioFrontoffice;
				TipoInvioMessaggioEnum invioEmail = ((TipoInvioMessaggioEnum)msgCfg.FlgTipoinvio.GetValueOrDefault(0)) & TipoInvioMessaggioEnum.Email;

				if (invioMessaggio == TipoInvioMessaggioEnum.Nessuno && invioEmail == TipoInvioMessaggioEnum.Nessuno)
					return;

				corpo = msgCfg.Corpo;

				if (String.IsNullOrEmpty(corpo) && contesto == CONTESTO_INVIO_ISTANZA)
					corpo = LeggiCertificatoInvio(domanda.Software);

				oggetto = ElaboraXPath(xPathDocument, msgCfg.Oggetto);
				corpo = ElaboraXPath(xPathDocument, corpo);

				oggetto = oggetto.Replace("<?xml version=\"1.0\" encoding=\"utf-16\"?>", "");
				corpo = corpo.Replace("<?xml version=\"1.0\" encoding=\"utf-16\"?>", "");

				DestinatariMessaggioEnum invioCreatore = ((DestinatariMessaggioEnum)msgCfg.FlgInvio.GetValueOrDefault(0)) & DestinatariMessaggioEnum.CittadinoRichiedente;
				DestinatariMessaggioEnum invioSottoscr = ((DestinatariMessaggioEnum)msgCfg.FlgInvio.GetValueOrDefault(0)) & DestinatariMessaggioEnum.AltriSoggetti;
				DestinatariMessaggioEnum invioResponsabile = ((DestinatariMessaggioEnum)msgCfg.FlgInvio.GetValueOrDefault(0)) & DestinatariMessaggioEnum.ResponsabileSportello;

				// Invio la notifica tramite messaggio?
				if (invioMessaggio != TipoInvioMessaggioEnum.Nessuno)
				{
					if (invioCreatore != DestinatariMessaggioEnum.Nessuno)
					{
						var creatore = new AnagrafeMgr(db).GetById(this._idComune, domanda.Codiceanagrafe.GetValueOrDefault(-1));

						if (creatore == null)
							throw new Exception("Impossibile ricavare il creatore dell'istanza FO con codice anagrafe:" + domanda.Codiceanagrafe.ToString());

						string codicefiscale = String.IsNullOrEmpty(creatore.CODICEFISCALE) ? creatore.PARTITAIVA : creatore.CODICEFISCALE;

						destinatariMessaggio.Add(codicefiscale);
					}

					if (invioSottoscr != DestinatariMessaggioEnum.Nessuno)
					{
						var sottoscriventi = new FoSottoscrizioniMgr(db).GetListaSottoscriventi(this._idComune, idDomanda);

						for (int i = 0; i < sottoscriventi.Count; i++)
						{
							if (!destinatariMessaggio.Contains(sottoscriventi[i].Codicefiscalesottoscrivente))
								destinatariMessaggio.Add(sottoscriventi[i].Codicefiscalesottoscrivente);
						}
					}

					for (int i = 0; i < destinatariMessaggio.Count; i++)
					{
						var nuovoMessaggio = new FoMessaggi
						{
							Idcomune = this._idComune,
							Software = domanda.Software,
							Codicedomanda = idDomanda,
							Codicefiscalemittente = codiceFiscaleMittente,
							Mittente = mittente,
							Codicefiscaledestinatario = destinatariMessaggio[i],
							Oggetto = oggetto,
							Corpo = corpo,
							Data = DateTime.Now,
							FlgLetto = 0
						};

						Insert(nuovoMessaggio);
					}
				}

				// Invio la notifica tramite email
				if (invioEmail != TipoInvioMessaggioEnum.Nessuno)
				{
					var destinatariEmail = new List<string>();

					if (invioCreatore != DestinatariMessaggioEnum.Nessuno)
					{
						var creatore = new AnagrafeMgr(db).GetById(this._idComune, domanda.Codiceanagrafe.GetValueOrDefault(-1));

						if (creatore == null)
							throw new Exception("Impossibile ricavare il creatore dell'istanza FO con codice anagrafe:" + domanda.Codiceanagrafe.ToString());

						destinatariEmail.Add(creatore.EMAIL);
					}

					if (invioSottoscr != DestinatariMessaggioEnum.Nessuno)
					{
						string xpath = @"/Istanze/Richiedenti/IstanzeRichiedenti/Procuratore/EMAIL/text() | 
									 /Istanze/Richiedenti/IstanzeRichiedenti[not(descendant::Procuratore)]/Richiedente/EMAIL/text()";

						XPathNavigator navig = xPathDocument.CreateNavigator();

						XPathNodeIterator it = navig.Select(xpath);

						while (it.MoveNext())
						{
							string email = it.Current.Value;

							if (!String.IsNullOrEmpty(email))

								if (!destinatariEmail.Contains(email))
									destinatariEmail.Add(email);
						}
					}

					if (invioResponsabile != DestinatariMessaggioEnum.Nessuno)
					{
						var emailResponsabile = new ConfigurazioneMgr(db).GetByIdComuneESoftwareSovrascrivendoTT(this._idComune, domanda.Software).EMAILRESPONSABILE;

						if (!String.IsNullOrEmpty(emailResponsabile))
							destinatariEmail.Add(emailResponsabile);
					}

					// Se esistono destinatari invio le email
					if (destinatariEmail.Count > 0)
					{
						try
						{
							var msg = new SIGeProMailMessage
							{
								CorpoMail = corpo,
								Oggetto = oggetto,
								InviaComeHtml = true,
								Destinatari = String.Join(";", destinatariEmail.ToArray())
							};

							new SmtpSender().InviaEmail(db, this._idComune, domanda.Software, msg);
						}
						catch (Exception ex)
						{
							Logger.LogEvent(db, this._idComune, "MESSAGGI_MGR", "Errore durante l'invio del messaggio ai destinatari:" + ex.ToString(), "MESSAGGI_MGR");
						}
					}
				}
			}
		}


		/// <summary>
		/// Legge il contenuto del certificato di invio dalla configurazione dell'area riservata
		/// </summary>
		/// <param name="idComune"></param>
		/// <param name="p"></param>
		/// <returns></returns>
		private string LeggiCertificatoInvio(string software)
		{
			var configurazioneAreaRiservata = new FoArConfigurazioneMgr(db).LeggiDati(this._idComune, software);

			if (!configurazioneAreaRiservata.CodiceoggettoFirma.HasValue)
				return String.Empty;

			// Leggo il corpo del modello per l'invio tramite firma digitale
			var oggetto = new OggettiMgr(db).GetById(this._idComune, configurazioneAreaRiservata.CodiceoggettoFirma.Value);

			if (oggetto == null)
				throw new Exception("Errore in LeggiCertificatoInvio: non è stato possibile leggere il contenuto del modello per l'invio tramite firma digitale. IdComune=" + this._idComune + ", Codice oggetto=" + configurazioneAreaRiservata.CodiceoggettoFirma);

			var bufferOggetto = new byte[0];
			var encoding = InterpretaEncoding(oggetto.OGGETTO, out bufferOggetto);

			return encoding.GetString(bufferOggetto);
		}

		private Encoding InterpretaEncoding(byte[] bufferIn, out byte[] bufferOut)
		{
			Encoding rVal = Encoding.GetEncoding(1252);	// Ansi
			bufferOut = bufferIn;

			// UTF8 ?
			if (bufferIn[0] == 239 &&
				bufferIn[1] == 187 &&
				bufferIn[2] == 191)
			{
				_logger.DebugFormat("Rilevato BOM UTF-8, l'intestazione verrà rimossa dal file");

				bufferOut = new Byte[bufferIn.Length - 3];

				Array.Copy(bufferIn, 3, bufferOut, 0, bufferIn.Length - 3);

				return Encoding.UTF8;
			}

			return rVal;
		}

		public void InviaNotificaRicezioneDomandaAreaRiservata(int codiceIstanza)
		{
			var istanza = new IstanzeMgr(db).GetById(this._idComune, codiceIstanza, useForeignEnum.Recoursive);

			var anagrafeRepository = new AnagrafeRepository(this.db, this._idComune);
			var cfgRepository = new ConfigurazioneRepository(this.db, this._idComune);
			var cfgAreaRiservataRepository = new ConfigurazioneAreaRiservataRepository(this.db, this._idComune);
			var operatoriRepository = new OperatoriRepository(this.db, this._idComune);
			var oggettiRepository = new OggettiRepository(this.db, this._idComune);
			var smtpService = new SmtpService(this.db, this._idComune, istanza.SOFTWARE);

			var destinatariFactory = new DestinatariMessaggioFactory(istanza, anagrafeRepository, cfgRepository, operatoriRepository);

			var listaMessaggi = new MessaggiCfgMgr(db).GetDaSoftwareEContesto(this._idComune, istanza.SOFTWARE, Constants.ContestoInvioIstanza);

			foreach (var messaggio in listaMessaggi)
			{
				var flagsDestinatari = new FlagsDestinatariMessaggio(messaggio.FlgInvio.GetValueOrDefault());
				var flagsTipoMessaggio = new FlagsTipoMessaggioNotifica(messaggio.FlgTipoinvio.GetValueOrDefault(0));

				var destinatari = destinatariFactory.GetDestinatari(flagsDestinatari);

				var modelloRiepilogo = String.IsNullOrEmpty(messaggio.Corpo) ?
												CertificatoDiInvio.FromConfigurazione(istanza.SOFTWARE, messaggio.Oggetto, cfgAreaRiservataRepository, oggettiRepository) :
												CertificatoDiInvio.FromXsl(messaggio.Oggetto, messaggio.Corpo);

				var riepilogoElaborato = modelloRiepilogo.PopolaConDati(istanza);

				var messaggiFactory = new MessaggioNotificaFactory(destinatari, riepilogoElaborato, smtpService);

				messaggiFactory
					.Create(flagsTipoMessaggio)
					.ToList()
					.ForEach(x => x.Send());
			}
		}


		public void CreaMessaggioDomandaRicevuta(int idDomandaFrontoffice, int codiceIstanza)
		{
			var istanza = new IstanzeMgr(db).GetById(this._idComune, codiceIstanza, useForeignEnum.Recoursive);

			if (istanza == null)
			{
				var errMsg = String.Format("Impossibile inviare il messaggio di avvenuto inserimento istanza per il codice istanza {0} (idcomune={1}): l'istanza non esiste", codiceIstanza, this._idComune);
				_logger.Error(errMsg);
				Logger.LogEvent(db, this._idComune, "MESSAGGI_MGR", errMsg, "MESSAGGI_MGR");

				return;
			}

			istanza.ConfigurazioneComune = new ConfigurazioneMgr(db).GetById(this._idComune, istanza.SOFTWARE);

			var contesto = CONTESTO_INVIO_ISTANZA;
			var codiceFiscaleMittente = istanza.Richiedente.CODICEFISCALE;
			var mittente = istanza.Richiedente.ToString();
			var istanzaSerializzata = StreamUtils.SerializeClass(istanza);

			try
			{
				CreaMessaggioDomanda(idDomandaFrontoffice, contesto, codiceFiscaleMittente, mittente, istanzaSerializzata);
			}
			catch (Exception ex)
			{
				var errMsg = String.Format("Errore imprevisto durante l'invio del messaggio di notifica ricezione pratica per l'istanza con codice {0} (idcomune={1}): {2}", codiceIstanza, this._idComune, ex.ToString());
				_logger.Error(errMsg);
				Logger.LogEvent(db, this._idComune, "MESSAGGI_MGR", errMsg, "MESSAGGI_MGR");

				throw;
			}
		}

		private string ElaboraXPath(XPathDocument istanzaXml, string testoXsl)
		{
			string container = @"<xsl:stylesheet xmlns:xsl=""http://www.w3.org/1999/XSL/Transform"" version=""1.0"">
<xsl:output method=""html"" />		
	<xsl:template match=""/"">{0}</xsl:template>

	<xsl:template name=""FormatDate"">
		<xsl:param name=""DateTime"" />

		<xsl:variable name=""dd"">
			<xsl:value-of select=""substring($DateTime,9,2)"" />
		</xsl:variable>

		<xsl:variable name=""mm"">
			<xsl:value-of select=""substring($DateTime,6,2)"" />
		</xsl:variable>

		<xsl:variable name=""yyyy"">
			<xsl:value-of select=""substring($DateTime,1,4)"" />
		</xsl:variable>

		<xsl:value-of select=""$dd"" />
		<xsl:value-of select=""'/'"" />
		<xsl:value-of select=""$mm"" />
		<xsl:value-of select=""'/'"" />
		<xsl:value-of select=""$yyyy"" />
	</xsl:template>

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
	}
}
