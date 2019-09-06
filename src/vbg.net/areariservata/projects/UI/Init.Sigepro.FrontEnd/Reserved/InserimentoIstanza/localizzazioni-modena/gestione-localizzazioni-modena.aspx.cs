using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.localizzazioni_modena
{
    public partial class gestione_localizzazioni_modena : IstanzeStepPage
    {
        public class LocalizzazioneModena
        {
            IndirizzoStradario _indirizzo;

            public LocalizzazioneModena(IndirizzoStradario indirizzo)
            {
                this._indirizzo = indirizzo;
            }

            public override string ToString()
            {
                var rif = this._indirizzo.RiferimentiCatastali.First();

                return rif.Foglio.PadLeft(5, ' ') +
                       rif.Particella.PadLeft(5, ' ');
            }
        }

        public int IdStradarioDefault
        {
            get { object o = this.ViewState["IdStradarioDefault"]; return o == null ? 0 : (int)o; }
            set { this.ViewState["IdStradarioDefault"] = value; }
        }

        public string IdCatastoDefault
        {
            get { object o = this.ViewState["IdCatastoDefault"]; return o == null ? "T" : (string)o; }
            set { this.ViewState["IdCatastoDefault"] = value; }
        }

        public string NomeCatastoDefault
        {
            get { object o = this.ViewState["NomeCatastoDefault"]; return o == null ? "Terreni" : (string)o; }
            set { this.ViewState["NomeCatastoDefault"] = value; }
        }

        public string CodCatastaleDefault
        {
            get { object o = this.ViewState["CodCatastaleDefault"]; return o == null ? "F257" : (string)o; }
            set { this.ViewState["CodCatastaleDefault"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public string GetArrayJsPerInizializzazione()
        {
            var str = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Select(x => new LocalizzazioneModena(x)).ToArray();

            if (!str.Any())
            {
                return "";
            }

            return String.Join(",", str.Select(x => $"\"{this.CodCatastaleDefault}{x}\""));
        }

        public override void DataBind()
        {
            if (ReadFacade.Domanda.Localizzazioni.Indirizzi.Any())
            {
                MostraLista();
            }
            else
            {
                MostraSelezione();
            }
                    
        }

        private void MostraSelezione()
        {
            this.multiView.ActiveViewIndex = 1;
            this.Master.MostraBottoneAvanti = false;

            this.lbTornaAllaLista.Visible = ReadFacade.Domanda.Localizzazioni.Indirizzi.Any();
        }

        private void MostraLista()
        {
            this.multiView.ActiveViewIndex = 0;
            this.Master.MostraBottoneAvanti = true;

            gvLocalizzazioni.DataSource = ReadFacade.Domanda.Localizzazioni.Indirizzi.Select( x => x.RiferimentiCatastali.FirstOrDefault());
            gvLocalizzazioni.DataBind();
        }

        protected void cmdModifica_Click(object sender, EventArgs e)
        {
            this.MostraSelezione();
        }

        protected void cmdTornaAllaLista_Click(object sender, EventArgs e)
        {
            MostraLista();
        }
    }
}