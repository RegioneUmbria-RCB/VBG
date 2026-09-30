using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class DettaglioIstanzaEx : ReservedBasePage
	{
        [Inject]
        protected RiepilogoDomandaDaVisuraService _riepilogoDomandaService { get; set; } 
        [Inject]
        protected IVisuraService _visuraService { get; set; }
        [Inject]
        protected IConfigurazione<ParametriUrlAreaRiservata> _parametriUrl { get; set; }
        [Inject]
        protected AllegatiInterventoService _allegatiInterventoService { get; set; }

        protected QsUuidIstanza IdIstanza
		{
			get { return new QsUuidIstanza(Request.QueryString); }
		}

		protected QsReturnTo ReturnTo
		{
			get
			{
                return new QsReturnTo(Request.QueryString);

				//if (String.IsNullOrEmpty(str))
				//	return "~/Reserved/IstanzePresentate.aspx";
                //
				//return str;
			}
		}

		protected string ReturnToArgs
		{
			get
			{
				return Request.QueryString["ReturnToArgs"];
			}
		}

        public bool MostraVisuraCompleta
        {
            get { object o = this.ViewState["MostraVisuraCompleta"]; return o == null ? false : (bool)o; }
            set { this.ViewState["MostraVisuraCompleta"] = value; }
        }
         

        protected void Page_Load(object sender, EventArgs e)
		{
			this.VisuraExCtrl1.ScadenzaSelezionata += new VisuraExCtrl.ScadenzaSelezionataDelegate(visuraCtrl_ScadenzaSelezionata);




            if (!IsPostBack)
			{
                var istanza = this._visuraService.GetByUuid(this.IdIstanza.Value);

                this.MostraVisuraCompleta = VerificaAccesso(istanza);
                VisuraExCtrl1.DaArchivio = !MostraVisuraCompleta;
                VisuraExCtrl1.EffettuaVisuraIstanza(IdComune, Software, IdIstanza.Value);

                var codiceriepilogo = this._allegatiInterventoService.GetCodiceOggettoDelModelloDiRiepilogo(Convert.ToInt32(istanza.CODICEINTERVENTOPROC));

                this.cmdClose.Visible = !TokenAnonimo();
                this.cmdGeneraRiepilogo.Visible = codiceriepilogo.HasValue && !TokenAnonimo() || !String.IsNullOrEmpty(Request.QueryString["visura"]);
                this.cmdAccedi.Visible = TokenAnonimo() && !String.IsNullOrEmpty(Request.QueryString["visura"]);
            }



            
        }

        private bool VerificaAccesso(Istanze istanza)
        {
            if (TokenAnonimo())
            {
                return false;
            }            

            return istanza.PuoVisualizzareDatiCompleti(this.UserAuthenticationResult.DatiUtente.Codicefiscale);
        }

        private bool TokenAnonimo()
        {
            return this.UserAuthenticationResult.LivelloAutenticazione == LivelloAutenticazioneEnum.Anonimo;
        }

        void visuraCtrl_ScadenzaSelezionata(object sender, string idScadenza)
		{
			Redirect("~/Reserved/Gestionemovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
		}


		protected void cmdClose_Click(object sender, EventArgs e)
		{
            var url = this.ReturnTo.Value;

            if (string.IsNullOrEmpty(url))
            {
                url = UrlBuilder.Url("~/Reserved/IstanzePresentate.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(this.IdComune));
                    qs.Add(new QsSoftware(this.Software));
                });
            }

            Response.Redirect(url);
		}

        protected void cmdGeneraRiepilogo_Click(object sender, EventArgs e)
        {
            var riepilogo = this._riepilogoDomandaService.GeneraRiepilogoDomanda(this.IdIstanza.Value);

            Response.Clear();
            Response.ContentType = riepilogo.MimeType;
            Response.AddHeader("content-disposition", $"attachment;filename=\"{riepilogo.FileName}\"");
            Response.BinaryWrite(riepilogo.FileContent);
            Response.End();
        }

        protected void cmdAccedi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url(_parametriUrl.Parametri.VisuraAutenticata, qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add(IdIstanza);
            });

            Response.Redirect(url);
        }
    }
}
