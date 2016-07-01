using System;
using System.Globalization;
using System.IO;
using System.Text;
using System.Threading;
using System.Web;
using System.Web.Services;
using System.Web.SessionState;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Utils;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.Service;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	/// <summary>
	/// Summary description for $codebehindclassname$
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	public class DownloadOggettoCompilabile : Ninject.Web.HttpHandlerBase, IRequiresSessionState
	{
		private static class Constants
		{
			public const string FormatoDefault = "PDF";
		}
	
		private static class QuerystringConstants
		{
			public const string UserAuthenticationResult = "UserAuthenticationResult";
			public const string Software = "Software";
			public const string IdDomanda = "IdPresentazione";
			public const string PdfSchedeNf = "PdfSchedeNf";
			public const string CodiceOggetto = "CodiceOggetto";
			public const string Fmt = "Fmt";
			public const string AsAttachment = "AsAttachment";
			public const string Md5 = "Md5";
			public const string DumpXml = "DumpXml";
		}


		[Inject]
		public IOggettiService _oggettiService { get; set; }
		[Inject]
		public FileConverterService _fileConverterService { get; set; }
		[Inject]
		public DomandeOnlineService __domandeOnlineService { get; set; }
		[Inject]
		public DocumentiService _allegatiService { get; set; }
		[Inject]
		public ISostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService { get; set; }

		[Inject]
		public GenerazioneRiepilogoDomandaService _generazioneriepilogoService { get; set; }


		public UserAuthenticationResult UserAuthenticationResult
		{
			get { return HttpContext.Current.Items[QuerystringConstants.UserAuthenticationResult] as UserAuthenticationResult; }
		}

		public string IdComune
		{
			get { return UserAuthenticationResult.IdComune; }
		}

		public string Software
		{
			get { return HttpContext.Current.Request.QueryString[QuerystringConstants.Software]; }
		}

		public int IdPresentazione
		{
			get { return Convert.ToInt32(HttpContext.Current.Request.QueryString[QuerystringConstants.IdDomanda]); }
		}

		protected string UserToken
		{
			get { return UserAuthenticationResult.Token; }
		}

		protected bool PdfSchedeNf
		{
			get
			{
				var _pdfSchedeNf = HttpContext.Current.Request.QueryString[QuerystringConstants.PdfSchedeNf];

				if (string.IsNullOrEmpty(_pdfSchedeNf))
					return true;

				return Convert.ToBoolean(_pdfSchedeNf);
			}
		}

		DomandaOnline _domandaCorrente;
		protected DomandaOnline DomandaCorrente
		{
			get 
			{
				if (_domandaCorrente == null)
					_domandaCorrente = __domandeOnlineService.GetById(IdPresentazione);

				return _domandaCorrente;
			}
		}

		protected bool DumpXml
		{
			get 
			{ 
				var qs = HttpContext.Current.Request.QueryString[QuerystringConstants.DumpXml];
				return !String.IsNullOrEmpty(qs) && qs.ToUpper() == "TRUE";
			}
		}

		protected override void DoProcessRequest(HttpContext context)
		{
			using (var cpGlobale = new CodeProfiler("DownoadOggettoCompilabile.DoProcessRequest"))
			{
				CultureInfo uiCulture = new CultureInfo("it-IT");
				uiCulture.NumberFormat.NumberDecimalSeparator = ",";
				uiCulture.NumberFormat.NumberGroupSeparator = ".";
				uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
				uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

				Thread.CurrentThread.CurrentUICulture = uiCulture;
				Thread.CurrentThread.CurrentCulture = uiCulture;


				var codiceOggetto	= Convert.ToInt32(context.Request.QueryString[QuerystringConstants.CodiceOggetto]);
				var formato			= context.Request.QueryString[QuerystringConstants.Fmt];
				var asAttachmentStr = context.Request.QueryString[QuerystringConstants.AsAttachment];
				var generaMd5str	= context.Request.QueryString[QuerystringConstants.Md5];

				var asAttachment = true;

				if (String.IsNullOrEmpty(formato))
					formato = Constants.FormatoDefault;

				if (!String.IsNullOrEmpty(asAttachmentStr))
					asAttachment = Convert.ToBoolean(asAttachmentStr);

				try
				{
					var settings = new GenerazioneRiepilogoSettings{
						AggiungiPdfSchedeAListaAllegati = PdfSchedeNf,
						FormatoConversione = formato,
						IdComune = IdComune,
						DumpXml = DumpXml
					};

					var riepilogo = _generazioneriepilogoService.GeneraRiepilogo(codiceOggetto, DomandaCorrente, settings);

					if (!String.IsNullOrEmpty(generaMd5str))
					{
						var idAllegato = Convert.ToInt32(generaMd5str);
						_allegatiService.SalvaEImpostaMd5(DomandaCorrente.DataKey.IdPresentazione, idAllegato, riepilogo);
					}

					context.Response.Clear();
					context.Response.ContentType = riepilogo.MimeType;
					context.Response.ContentEncoding = Encoding.Default;

					if (asAttachment)
						context.Response.AddHeader("content-disposition", "attachment; filename=" + riepilogo.FileName + ";");

					context.Response.BinaryWrite(riepilogo.FileContent);
					context.Response.End();

				}
				catch (ThreadAbortException)
				{
				}
				catch (Exception ex)
				{
					context.Response.ContentType = "text/plain";
					context.Response.Write("Errore: " + ex.Message);
				}

				/*
				BinaryFile oggetto = _oggettiService.GetById(codiceOggetto);

				if (oggetto == null)
				{
					context.Response.ContentType = "text/plain";
					context.Response.Write("Errore: Oggetto " + codiceOggetto + "non trovato");
					context.Response.End();
					return;
				}

				try
				{
					string istanzaXml;

					var istanzaAdapter = new IstanzaSigeproAdapter( DomandaCorrente.ReadInterface );

					istanzaAdapter.AggiungiPdfSchedeAListaAllegati = PdfSchedeNf;

					istanzaXml = istanzaAdapter.AdattaToString(DomandaCorrente.DataKey.ToString());

					if (DumpXml)
					{
						var path = HttpContext.Current.Server.MapPath("~/Logs/");
						path = Path.Combine( path , String.Format("Dump{0}.xml", Guid.NewGuid()));
						using (var fs = File.Open(path, FileMode.CreateNew))
						{
							fs.Write(Encoding.UTF8.GetBytes(istanzaXml), 0, Encoding.UTF8.GetByteCount(istanzaXml));
						}
					}


					// Converto l'xsl della domanda in formato UTF-8
					var oggettoXsl = Encoding.UTF8.GetString(oggetto.FileContent);

					var risultatoTrasformazione = Encoding.UTF8.GetString( _fileConverterService.ApplicaTrasformazione(istanzaXml, oggettoXsl) );

					// Nel caso in cui il modello contenga il segnaposto delle schede dinamiche utilizzo il servizio
					// per leggerle in formato html
					risultatoTrasformazione = _sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(DomandaCorrente , risultatoTrasformazione);

					var modelloCompilato = _fileConverterService.Converti(IdComune, "modello." + oggetto.Estensione.ToUpper(), Encoding.UTF8.GetBytes(risultatoTrasformazione), formato);

					var nomeFile = Path.GetFileNameWithoutExtension(oggetto.FileName) + Path.GetExtension(modelloCompilato.FileName);


					// -- fine
					if (!String.IsNullOrEmpty(generaMd5str))
					{
						var idAllegato = Convert.ToInt32(generaMd5str);
						_allegatiService.SalvaEImpostaMd5( DomandaCorrente.DataKey.IdPresentazione, idAllegato, modelloCompilato);
					}


					context.Response.Clear();
					context.Response.ContentType = modelloCompilato.MimeType;
					context.Response.ContentEncoding = Encoding.Default;

					if (asAttachment)
						context.Response.AddHeader("content-disposition", "attachment; filename=" + nomeFile + ";");

					context.Response.BinaryWrite(modelloCompilato.FileContent);
					context.Response.End();
				}
				catch (ThreadAbortException)
				{
				}
				catch (Exception ex)
				{
					context.Response.ContentType = "text/plain";
					context.Response.Write("Errore: " + ex.Message);
				}
				*/
			}
		}

		public override bool IsReusable
		{
			get
			{
				return false;
			}
		}
	}
}
