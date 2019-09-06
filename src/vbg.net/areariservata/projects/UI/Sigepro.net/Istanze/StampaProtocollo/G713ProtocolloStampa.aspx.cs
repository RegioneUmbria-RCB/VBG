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
using SIGePro.Net;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.Barcodes;
using Init.SIGePro.Authentication;
using Init.SIGePro.Protocollo.Manager;

namespace Sigepro.net.Istanze.StampaProtocollo
{
	public partial class G713ProtocolloStampa : BasePage
	{
		protected string CabUrl
		{
			get
			{
				var baseUrl = base.BaseUrl;
				var aspUrl = AuthenticationManager.GetApplicationInfoValue("APP_ASP");

				if(!baseUrl.EndsWith("/") && !aspUrl.EndsWith("/"))
					baseUrl +="/";

				return string.Concat( baseUrl , aspUrl , "/Stampe/ActivXReport/ControlloStampa.CAB#version=1,0,0,6");
			}
		}

		private int CodiceMovimento
		{
			get 
			{ 
				string codMovimento = Request.QueryString["CodiceMovimento"];
				return String.IsNullOrEmpty(codMovimento) ? -1 : Convert.ToInt32(codMovimento);
			}
		}

		private Movimenti Movimento
		{
			get
			{
				if (CodiceMovimento < 0) return null;
				return new MovimentiMgr(Database).GetById(IdComune, CodiceMovimento);
			}
		}

		private int CodiceIstanza
		{
			get { return Convert.ToInt32( Request.QueryString["CodiceIstanza"] ); }
		}

		Init.SIGePro.Data.Istanze Istanza
		{
			get { return new IstanzeMgr(Database).GetById(IdComune, CodiceIstanza); }
		}

		public override string Software
		{
			get
			{
				return Istanza.SOFTWARE;
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			base.CloseCurrentPage();
			//Response.Redirect(Server.UrlDecode(Request.QueryString["returnTo"]));
		}

        public string IdProtocollo
        {
            get
            {
                if (Movimento == null)
                {
                    // Sto annullando un'istanza
                    return Istanza.FKIDPROTOCOLLO;
                }

                // Sto annullando un movimento
                return Movimento.FKIDPROTOCOLLO;
            }

        }

        public string NumeroProtocollo
        {
            get
            {
                if (Movimento == null)
                {
                    // Sto annullando un'istanza
                    return Istanza.NUMEROPROTOCOLLO;
                }

                // Sto annullando un movimento
                return Movimento.NUMEROPROTOCOLLO;
            }

        }

		public DateTime? DataProtocollo
		{
            get
            {
                if (Movimento == null)
                    return Istanza.DATAPROTOCOLLO;

                return Movimento.DATAPROTOCOLLO;
            }

		}

		public string GetBarcode()
		{
            string etichetta = string.Empty;
            try
            {
                var mgr = new ProtocolloMgr(AuthenticationInfo, Software);
                etichetta = mgr.StampaEtichette(IdProtocollo, DataProtocollo, NumeroProtocollo, 0, "").IdEtichetta;
                //string etichetta = "12345678";
            }
            catch (Exception ex)
            {
                MostraErrore("Attenzione, non è possibile stampare l'etichetta del protocollo per il seguente motivo: " + ex.Message, ex);
            }

            // Elaborare il codice a barre
            return new Code128().OutputCode128("CPT" + etichetta, false).Replace("'", "\\'");
		}

		public string GetNomeSoftware()
		{
			return new SoftwareMgr(Database).GetById(Software).DESCRIZIONE;
		}

		public string GetNumeroPratica()
		{
			return Istanza.NUMEROISTANZA;
		}

		public string GetDataPratica()
		{
            return Istanza.DATA.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy");
		}
	}
}
