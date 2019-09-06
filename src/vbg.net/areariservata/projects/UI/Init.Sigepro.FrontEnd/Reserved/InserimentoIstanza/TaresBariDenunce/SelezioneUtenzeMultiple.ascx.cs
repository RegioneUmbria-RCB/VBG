using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce
{
    public partial class SelezioneUtenzeMultiple : System.Web.UI.UserControl
    {
        public delegate void UtenzeSelezionateDelegate(object sender, IEnumerable<string> idUtenze);
        public event UtenzeSelezionateDelegate UtenzeSelezionate;

        public delegate void ErroreDelegate(object sender, string messaggio);
        public event ErroreDelegate Errore;

        private static class Constants
        {
            public const int IdvUtenzaEsistente = 0;
            public const int IdvNuovaUtenza = 1;
        }

        public class MappaturaTipoUtenza
        {
            public readonly string IdentificativoUtenza;
            public readonly TipoUtenzaTaresEnum TipoUtenza;

            public MappaturaTipoUtenza(string identificativoUtenza, TipoUtenzaTaresEnum tipoUtenza)
            {
                this.IdentificativoUtenza = identificativoUtenza;
                this.TipoUtenza = tipoUtenza;
            }
        }

        public class UtenzaSelezionataeventArgs
        {
            public readonly int IdentificativoContribuente;

            public UtenzaSelezionataeventArgs(int identificativoContribuente)
            {
                this.IdentificativoContribuente = identificativoContribuente;
            }
        }

        public int NumeroMassimoUtenzeGestibili
        {
            get { object o = this.ViewState["NumeroMassimoUtenzeGestibili"]; return o == null ? 1 : (int)o; }
            set { this.ViewState["NumeroMassimoUtenzeGestibili"] = value; }
        }
        public string MessaggioErroreLimiteUtenzeSuperato
        {
            get { object o = this.ViewState["MessaggioErroreLimiteUtenzeSuperato"]; return o == null ? "Il limite massimo di utenze selezionabili è ({0})" : (string)o; }
            set { this.ViewState["MessaggioErroreLimiteUtenzeSuperato"] = value; }
        }


        public TipoUtenzaTaresEnum TipoUtenza
        {
            get { object o = this.ViewState["TipoUtenza"]; return o == null ? TipoUtenzaTaresEnum.Domestica : (TipoUtenzaTaresEnum)o; }
            set { this.ViewState["TipoUtenza"] = value; }
        }

        public int? IdentificativoContribuente
        {
            get { object o = this.ViewState["IdentificativoContribuente"]; return o == null ? (int?)null : (int)o; }
            set { this.ViewState["IdentificativoContribuente"] = value; }
        }


        public DatiAnagraficiContribuenteDenunciaTares DataSource
        {
            get;
            set;
        }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.Visible = true ;

            if (this.DataSource == null)
            {
                this.Visible = false;
                return;
            }

            if (TipoUtenza == TipoUtenzaTaresEnum.Domestica)
            {
                rptIndirizziUtenze.DataSource = this.DataSource.UtenzeDomestiche;
                rptIndirizziUtenze.DataBind();

                rptIndirizziUtenze.Visible = true;
                rptIndirizziNonDomestici.Visible = false;
            }
            else
            {
                rptIndirizziNonDomestici.DataSource = this.DataSource.UtenzeCommerciali;
                rptIndirizziNonDomestici.DataBind();

                rptIndirizziUtenze.Visible = false;
                rptIndirizziNonDomestici.Visible = true;
            }

            ltrNominativo.Text = DataSource.NominativoCompleto;
            ltrCodiceFiscale.Text = DataSource.CodiceFiscale;

            if (!String.IsNullOrEmpty(DataSource.PartitaIva))
            {
                ltrCodiceFiscale.Text += " P.Iva: " + DataSource.PartitaIva;
            }

            ltrDatiNascita.Text = DataSource.DatiNascita;
            ltrIndirizzoResidenza.Text = DataSource.IndirizzoResidenza.ToString();

            ltrIdentificativoUtenza.Text = DataSource.IdContribuente.ToString();

            this.IdentificativoContribuente = DataSource.IdContribuente;
        }


        protected string DecodificaEnum(object strValue)
        {
            return String.Join(" ", Regex.Split(strValue.ToString(), "(?<!^)(?=[A-Z0-9][a-z]*)").Select((x, idx) => idx == 0 ? x : x.ToLower()).ToArray());
        }

        protected void cmdSelezionaUtenze_Click(object sender, EventArgs e)
        {
            var utenzeSelezionate = new List<string>();

            utenzeSelezionate.AddRange(EstraiSelezioni(rptIndirizziUtenze));
            utenzeSelezionate.AddRange(EstraiSelezioni(rptIndirizziNonDomestici));


            if (utenzeSelezionate.Count == 0)
            {
                if (this.Errore != null)
                {
                    this.Errore(this, "Selezionare almeno un'utenza oggetto della domanda");
                }

                return;
            }

            if (utenzeSelezionate.Count > NumeroMassimoUtenzeGestibili)
            {
                if (this.Errore != null)
                {
                    this.Errore(this, MessaggioErroreLimiteUtenzeSuperato);
                }

                return;
            }

            if (this.UtenzeSelezionate != null)
            {
                this.UtenzeSelezionate(this, utenzeSelezionate);
            }
        }

        private IEnumerable<string> EstraiSelezioni(Repeater repeater)
        {
            var idSelezionati = new List<string>();

            foreach (var item in repeater.Items.Cast<RepeaterItem>())
            {
                var chkUtenza = (CheckBox)item.FindControl("chkUtenza");
                var hidId = (HiddenField)item.FindControl("hidId");

                if (chkUtenza.Checked)
                {
                    idSelezionati.Add(hidId.Value);
                }
            }

            return idSelezionati;
        }
    }
}