using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce
{
    public partial class DettagliUtenza : System.Web.UI.UserControl
    {
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
            if (this.DataSource == null)
                return;

            if (TipoUtenza == TipoUtenzaTaresEnum.Domestica)
            {
                rptIndirizziUtenze.DataSource = this.DataSource.UtenzeDomestiche;
                rptIndirizziUtenze.DataBind();

                rptIndirizziNonDomestici.Visible = false;
            }
            else
            {
                rptIndirizziNonDomestici.DataSource = this.DataSource.UtenzeCommerciali;
                rptIndirizziNonDomestici.DataBind();

                rptIndirizziUtenze.Visible = false;
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

            if (DataSource.IdContribuente >= 0)
            {
                MostraVistaUtenzaEsistente();
            }
            else
            {
                MostraVistaNuovaUtenza();
            }
        }

        private void MostraVistaNuovaUtenza()
        {
            this.multiview.ActiveViewIndex = Constants.IdvNuovaUtenza;
        }

        private void MostraVistaUtenzaEsistente()
        {
            this.multiview.ActiveViewIndex = Constants.IdvUtenzaEsistente;
        }


        protected string DecodificaEnum(object strValue)
        {
            return String.Join(" ", Regex.Split(strValue.ToString(), "(?<!^)(?=[A-Z0-9][a-z]*)").Select((x, idx) => idx == 0 ? x : x.ToLower()).ToArray());
        }
    }
}