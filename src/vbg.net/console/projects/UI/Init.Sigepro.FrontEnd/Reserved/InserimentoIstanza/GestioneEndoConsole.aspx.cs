using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


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
                return ltrTitoloEndoPrincipale.Text;
            }

            set
            {
                ltrTitoloEndoPrincipale.Text = value;
            }
        }

        public string TitoloEndoAttivati
        {
            get
            {
                return ltrTitoloEndoAttivati.Text;
            }

            set
            {
                ltrTitoloEndoAttivati.Text = value;
            }
        }

        public string TitoloEndoAttivabili
        {
            get
            {
                return ltrTitoloEndoAttivabili.Text;
            }

            set
            {
                ltrTitoloEndoAttivabili.Text = value;
            }
        }

        public string TitoloAltriEndo
        {
            get { return ltrTitoloAltriEndo.Text; }
            set { ltrTitoloAltriEndo.Text = value; }
        }

        public bool IgnoraIncompatibilitaEndoprocedimenti
        {
            get { object o = this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
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

            this._endoService.ImpostaEndoSelezionati(this.IdDomanda, endoSelezionati.ToList());
        }

        public override bool CanExitStep()
        {
            var endoIncompatibili = this._endoService.GetEndoprocedimentiIncompatibili(IdDomanda);

            if (endoIncompatibili.Count() == 0 || this.IgnoraIncompatibilitaEndoprocedimenti)
            {
                return true;
            }

            this.Errori.AddRange(endoIncompatibili.Select(x => x.ToString()));

            return false;
        }

        protected override void OnPreRender(EventArgs e)
        {
            if (!IsPostBack)
            {
                var endoAttivati = this.ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Select(x => x.Codice.ToString());
                var script = "endoAttivati = [" + String.Join(",", endoAttivati) + "];";

                this.Page.ClientScript.RegisterStartupScript(this.GetType(), "endoAttivati", script, true);
            }
        }

    }
}