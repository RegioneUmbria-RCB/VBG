using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Trieste;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class TriesteAccessoAtti : IstanzeStepPage
    {
        [Inject]
        public IConfigurazione<ParametriTriesteAccessoAtti> _configurazione { get; set; }
        [Inject]
        public TriesteAccessoAttiService _accessoAttiService { get; set; }

        protected bool Returning => !String.IsNullOrEmpty(Request.QueryString["Returning"]) && Request.QueryString["Returning"] == "1";


        public string RedirUrl
        {
            get { object o = this.ViewState["RedirUrl"]; return o == null ? String.Empty : (string)o; }
            private set { this.ViewState["RedirUrl"] = value; }
        }

        #region Parametri letti dal workflow
        public int IdCampoOggetto
        {
            get { object o = this.ViewState["IdCampoOggetto"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoOggetto"] = value; }
        }

        public int IdCampoPrimoStep
        {
            get { object o = this.ViewState["IdCampoPrimoStep"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoPrimoStep"] = value; }
        }

        public int IdCampoProtocollo
        {
            get { object o = this.ViewState["IdCampoProtocollo"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoProtocollo"] = value; }
        }

        public int IdCampoSecondoStep
        {
            get { object o = this.ViewState["IdCampoSecondoStep"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdCampoSecondoStep"] = value; }
        }
        #endregion

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraBottoneAvanti = false;

            if (Returning)
            {
                this._accessoAttiService.SalvaDatiDinamici(IdDomanda, new MappaturaCampiDinamici
                {
                    IdCampoOggetto = this.IdCampoOggetto,
                    IdCampoPrimoStep = this.IdCampoPrimoStep,
                    IdCampoProtocollo = this.IdCampoProtocollo,
                    IdCampoSecondoStep = this.IdCampoSecondoStep
                });

                this.Master.cmdNextStep_Click(this, EventArgs.Empty);

                return;
            }

            if (!IsPostBack)
            {
                var idDomanda = this.ReadFacade.Domanda.AltriDati.IdentificativoDomanda;
                var token = this._authenticationDataResolver.DatiAutenticazione.Token;
                
                this.RedirUrl = UrlBuilder.Url(this._configurazione.Parametri.UrlTraferimentoControllo, pb =>
                {
                    pb.Add(new QsIdDomandaOnline(idDomanda));
                    pb.Add("token", token);
                    pb.Add(new QsReturnTo(GetBaseUrlAssoluto() + "trieste-accesso-atti/" + idDomanda + "/" + token));
                });
            }
        }

        protected void cmdNext_Click(object sender, EventArgs e)
        {
            this.Master.cmdNextStep_Click(this, EventArgs.Empty);
        }
    }
}