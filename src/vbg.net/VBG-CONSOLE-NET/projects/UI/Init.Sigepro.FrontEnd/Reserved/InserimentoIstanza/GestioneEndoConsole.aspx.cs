using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Ninject;
using System;
using System.Linq;


namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneEndoConsole : IstanzeStepPage
    {
        [Inject]
        public IEndoprocedimentiService _endoService { get; set; }


        public string TitoloEndoPrincipale
        {
            get
            {
                return this.ltrTitoloEndoPrincipale.Text;
            }

            set
            {
                this.ltrTitoloEndoPrincipale.Text = value;
            }
        }

        public string TitoloEndoAttivati
        {
            get
            {
                return this.ltrTitoloEndoAttivati.Text;
            }

            set
            {
                this.ltrTitoloEndoAttivati.Text = value;
            }
        }

        public string TitoloEndoAttivabili
        {
            get
            {
                return this.ltrTitoloEndoAttivabili.Text;
            }

            set
            {
                this.ltrTitoloEndoAttivabili.Text = value;
            }
        }

        public string TitoloAltriEndo
        {
            get { return this.ltrTitoloAltriEndo.Text; }
            set { this.ltrTitoloAltriEndo.Text = value; }
        }

        public bool IgnoraIncompatibilitaEndoprocedimenti
        {
            get { object o = this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"] = value; }
        }

        /// <summary>
        /// Se impostato a true sarà obbligatorio selezionare ALMENO un subendo per ciascun procedimento attivato 
        /// che ne contiene
        /// </summary>
        public bool SelezioneSubEndoObbligatoria
        {
            get { object o = this.ViewState["SelezioneSubEndoObbligatoria"]; return o != null && (bool)o; }
            set { this.ViewState["SelezioneSubEndoObbligatoria"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            var endo = this._endoService.GetEndoprocedimentiConsoleDaIdIntervento(this.ReadFacade.Domanda.AltriDati.Intervento.Codice, this.ReadFacade.Domanda.AltriDati.CodiceComune);

            this.endoViewPrincipali.DataSource = endo.Principali;
            this.endoViewPrincipali.DataBind();

            this.endoViewAttivati.DataSource = endo.Richiesti;
            this.endoViewAttivati.DataBind();

            this.endoViewRicorrenti.DataSource = endo.Ricorrenti;
            this.endoViewRicorrenti.DataBind();

            this.endoViewAltriEndo.DataSource = endo.Altri;
            this.endoViewAltriEndo.DataBind();

            this.pnlEndoAttivabili.Visible = endo.Ricorrenti != null && endo.Ricorrenti.Any();
            this.pnlEndoAttivati.Visible = endo.Richiesti != null && endo.Richiesti.Any();
            this.pnlAltriEndo.Visible = endo.Altri != null && endo.Altri.Any();
        }

        public override void OnBeforeExitStep()
        {
            // recupero gli id selezionati dall'utente
            var endoSelezionati = this.endoViewPrincipali.GetEndoSelezionati()
                        .Union(this.endoViewAttivati.GetEndoSelezionati())
                        .Union(this.endoViewRicorrenti.GetEndoSelezionati())
                        .Union(this.endoViewAltriEndo.GetEndoSelezionati());

            this._endoService.ImpostaEndoSelezionati(this.IdDomanda, endoSelezionati);
        }

        public override bool CanExitStep()
        {
            var endoIncompatibili = this._endoService.GetEndoprocedimentiIncompatibili(this.IdDomanda);

            if (endoIncompatibili.Count() == 0 || this.IgnoraIncompatibilitaEndoprocedimenti)
            {
                return true;
            }

            this.Errori.AddRange(endoIncompatibili.Select(x => x.ToString()).ToArray());

            return false;
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            if (!this.IsPostBack)
            {
                var strutturaSubEndo = this._endoService.GetSubEndoSelezionati(this.IdDomanda);

                var script = "endoAttivati = " + strutturaSubEndo.ToJsonString() + ";";

                // var endoAttivati = this.ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Select(x => x.Codice.ToString());
                // var script = "endoAttivati = [" + String.Join(",", endoAttivati) + "];";

                this.Page.ClientScript.RegisterStartupScript(this.GetType(), "endoAttivati", script, true);
            }
        }

    }
}