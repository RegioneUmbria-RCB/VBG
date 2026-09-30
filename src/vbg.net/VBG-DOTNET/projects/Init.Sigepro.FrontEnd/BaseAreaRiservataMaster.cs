using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;


namespace Init.Sigepro.FrontEnd
{
    public class BaseAreaRiservataMaster : Ninject.Web.MasterPageBase, IMostraErroriPage
    {
        [Inject]
        public DomandeOnlineService _domandeOnlineService { get; set; }

        [Inject]
        public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }

        [Inject]
        public IIdDomandaResolver _idDomandaResolver { get; set; }

        [Inject]
        public IAuthenticationDataResolver _authenticationDataResolver { get; set; }


        public string Software { get { return this._aliasSoftwareResolver.Software; } }
        public int IdDomanda { get { return this._idDomandaResolver.IdDomanda; } }
        public UserAuthenticationResult UserAuthenticationResult { get { return this._authenticationDataResolver.DatiAutenticazione; } }
        public string CodiceUtente { get { return this.UserAuthenticationResult.DatiUtente.Codicefiscale; } }
        public string IdComune { get { return this._aliasSoftwareResolver.AliasComune; } }
        protected string UserToken { get { return this.UserAuthenticationResult.Token; } }
        private DomandaOnline _domanda;
        public DomandaOnline Domanda
        {
            get
            {
                if (this._domanda == null)
                {
                    this._domanda = this._domandeOnlineService.GetById(this.IdDomanda);
                }
                return this._domanda;
            }
        }

        public string LoadScripts(string[] scripts)
        {
            var s = scripts.Select(x => $"<script type='text/javascript' src='{this.ResolveClientUrl(x)}'></script>");

            return String.Join(Environment.NewLine, s.ToArray());
        }

        protected string LoadScript(string script)
        {
            return $"<script type='text/javascript' src='{this.ResolveClientUrl(script)}'></script>";
        }


        #region gestione della visualizzazione degli errori

        protected Repeater OutputErrori { get; set; }
        protected Repeater OutputMessaggiInformativi { get; set; }
        protected Repeater OutputMessaggiSuccesso { get; set; }

        public void MostraMessaggi(Repeater output, IEnumerable<string> messaggi)
        {
            if (output != null)
            {
                output.EnableViewState = false;
                output.Visible = messaggi.Any();
                output.DataSource = messaggi;
                output.DataBind();
            }
        }

        protected override void OnLoad(EventArgs e)
        {
            base.OnLoad(e);

            if (this.OutputErrori != null)
            {
                this.OutputErrori.Visible = false;
            }

            if (this.OutputMessaggiInformativi != null)
            {
                this.OutputMessaggiInformativi.Visible = false;
            }

            if (this.OutputMessaggiSuccesso != null)
            {
                this.OutputMessaggiSuccesso.Visible = false;
            }
        }

        public void MostraMessaggi(IEnumerable<string> errori, IEnumerable<string> messaggiInformativi, IEnumerable<string> messaggiSuccesso)
        {
            this.MostraMessaggi(this.OutputErrori, errori);
            this.MostraMessaggi(this.OutputMessaggiInformativi, messaggiInformativi);
            this.MostraMessaggi(this.OutputMessaggiSuccesso, messaggiSuccesso);
        }
        #endregion



    }
}
