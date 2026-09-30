using System;
using System.Linq;
using System.Web;
using System.Web.SessionState;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	/// <summary>
	/// Summary description for GestioneAllegati_Note
	/// </summary>
	public class GestioneAllegati_Note : Ninject.Web.HttpHandlerBase, IRequiresSessionState
	{

		private static class QuerystringConstants
		{
			public const string UserAuthenticationResult = "UserAuthenticationResult";
			public const string Software = "Software";
			public const string IdDomanda = "IdPresentazione";
			public const string IdAllegato = "IdAllegato";
			public const string Provenienza = "Provenienza";
			public const string TipoProvenienzaIntervento = "I";
		}

		[Inject]
		public DomandeOnlineService __domandeOnlineService { get; set; }

		protected UserAuthenticationResult UserAuthenticationResult
		{
			get { return HttpContext.Current.Items[QuerystringConstants.UserAuthenticationResult] as UserAuthenticationResult; }
		}

		protected string IdComune
		{
			get { return UserAuthenticationResult.IdComune; }
		}

		protected string Software
		{
			get { return HttpContext.Current.Request.QueryString[QuerystringConstants.Software]; }
		}

		protected int IdPresentazione
		{
			get { return Convert.ToInt32(HttpContext.Current.Request.QueryString[QuerystringConstants.IdDomanda]); }
		}

		protected int IdAllegato
		{
			get { return Convert.ToInt32(HttpContext.Current.Request.QueryString[QuerystringConstants.IdAllegato]); }
		}

		protected bool FromIntervento
		{
			get { return HttpContext.Current.Request.QueryString[QuerystringConstants.Provenienza] == QuerystringConstants.TipoProvenienzaIntervento; }
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

		protected override void DoProcessRequest(HttpContext context)
		{
			var testo = String.Empty;
			
			
			if( FromIntervento ) 
			{
				testo = this.DomandaCorrente.ReadInterface
											.Documenti
											.Intervento
											.Documenti
											.Where(x => x.Id == IdAllegato)
											.FirstOrDefault()
											.Note;
			}
			else
			{
				testo = this.DomandaCorrente.ReadInterface
											.Documenti
											.Endo
											.Documenti
											.Where(x => x.Id == IdAllegato)
											.FirstOrDefault()
											.Note;	
			}

			context.Response.ContentType = "text/plain";
			context.Response.Write(testo);
		}

		public override bool IsReusable
		{
			get { return false; }
		}
	}
}