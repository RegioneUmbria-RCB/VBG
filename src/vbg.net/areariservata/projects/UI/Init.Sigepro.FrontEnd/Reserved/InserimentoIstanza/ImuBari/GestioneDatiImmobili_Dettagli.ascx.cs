using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ImuBari
{
	public partial class GestioneDatiImmobili_Dettagli : System.Web.UI.UserControl
	{
		public delegate void ImmobiliSelezionatiEventHandler(object sender, DatiContribuenteImuDto datiContribuente);
		public event ImmobiliSelezionatiEventHandler ImmobiliSelezionati;

		public delegate bool ValidazioneDatiSelezionatiEventHandler(object sender, IEnumerable<ImmobileImuDto> datiContribuente);
		public event ValidazioneDatiSelezionatiEventHandler ValidazioneDatiSelezionati;


		public DatiContribuenteImuDto DataSource
		{
			get { return (DatiContribuenteImuDto)Session["GestioneDatiImmobili_Dettagli.dataSource"]; }
			set { Session["GestioneDatiImmobili_Dettagli.dataSource"] = value; }
		}

		public string IdentificativoContribuente
		{
			get { object o = this.ViewState["IdentificativoContribuente"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["IdentificativoContribuente"] = value; }
		}

		public bool PermettiSelezione
		{
			get { object o = this.ViewState["PermettiSelezione"]; return o == null ? true : (bool)o; }
			set { this.ViewState["PermettiSelezione"] = value; }
		}


		protected void Page_Load(object sender, EventArgs e)
		{
		}

        protected void rptDatiImmobili_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var dataItem = (RptDatiImmobiliBindingItem)e.Item.DataItem;
                var ddlTipoImmobile = (DropDownList)e.Item.FindControl("ddlTipoImmobile");

                ddlTipoImmobile.DataSource = ImmobileImuDto.GetTipiImmobile();
                ddlTipoImmobile.DataBind();
                ddlTipoImmobile.SelectedValue = dataItem.TipoImmobile.ToString();
            }
        }

        public class RptDatiImmobiliBindingItem
        {
            public string IdImmobile{get;set;}
            public string Ubicazione{get;set;}
            public string RiferimentiCatastali{get;set;}
            public string PercentualePossesso{get;set;}
            public string CategoriaCatastale { get; set; }
            public TipoImmobileEnum TipoImmobile{get;set;}
            public bool PermettiSelezione { get; set; }
        }

		public override void DataBind()
		{
			if (DataSource.TipoPersona == DatiContribuenteImuDto.TipoPersonaEnum.Fisica)
			{
				ltrLabelIndirizzoResidenza.Text = "Residente in";

				ltrIdentificativoUtenza.Text = String.Format("Id contribuente: {0}", DataSource.IdContribuente);
				ltrNominativo.Text = String.Format("{0} {1}", DataSource.Cognome, DataSource.Nome);
				ltrCodiceFiscale.Text = DataSource.CodiceFiscale;
				ltrDatiNascita.Text = DataSource.DatiNascita;
				ltrIndirizzoResidenza.Text = DataSource.Residenza.ToString();
				IdentificativoContribuente = DataSource.IdContribuente;
			}
			else
			{
				ltrLabelIndirizzoResidenza.Text = "Con sede legale in";

				ltrIdentificativoUtenza.Text = String.Format("Id contribuente: {0}", DataSource.IdContribuente);
				ltrNominativo.Text = DataSource.Cognome;
				ltrCodiceFiscale.Text = DataSource.CodiceFiscale;
				ltrDatiNascita.Text = String.Empty;
				ltrIndirizzoResidenza.Text = DataSource.Residenza.ToString();
				IdentificativoContribuente = DataSource.IdContribuente;
			}

			rptDatiImmobili.DataSource = DataSource
											.ListaImmobili
											.Select(x => new RptDatiImmobiliBindingItem
											{
												IdImmobile = x.IdImmobile,
												Ubicazione = x.Ubicazione.ToString(),
												RiferimentiCatastali = x.RiferimentiCatastali.ToString(),
												PercentualePossesso = x.PercentualePossesso,
												TipoImmobile = x.TipoImmobile,
												PermettiSelezione = PermettiSelezione,
                                                CategoriaCatastale = x.CategoriaCatastale
											});
			rptDatiImmobili.DataBind();

			cmdConfermaSelezione.Visible = PermettiSelezione;
		}

		public void cmdConfermaSelezione_Click(object sender, EventArgs e)
		{
			var listaElementiSelezionati = rptDatiImmobili
											.Items
											.Cast<RepeaterItem>()
											.Select(item =>
											{
                                                var hidIdImmobile = (HiddenField)item.FindControl("hidIdImmobile");
                                                var ddlTipoImmobile = (DropDownList)item.FindControl("ddlTipoImmobile");

                                                var idImmobile = hidIdImmobile.Value;

												var immobile = DataSource.ListaImmobili.FirstOrDefault(x => x.IdImmobile == idImmobile);

                                                immobile.TipoImmobile = (TipoImmobileEnum)Enum.Parse(typeof(TipoImmobileEnum), ddlTipoImmobile.SelectedValue);

                                                return immobile;
											})
                                            .Where( x => x.TipoImmobile == TipoImmobileEnum.Abitazione || x.TipoImmobile == TipoImmobileEnum.Pertinenza)
											.ToList();

			// Effettua validazione
			if (ValidazioneDatiSelezionati != null)
			{
				var result = this.ValidazioneDatiSelezionati(this, listaElementiSelezionati);

				if (!result)
					return;
			}

			DataSource.ListaImmobili.Clear();
			DataSource.ListaImmobili = listaElementiSelezionati;

			// Se non ci sono errori conferma la selezione
			if (ImmobiliSelezionati != null)
			{
				this.ImmobiliSelezionati(this, DataSource);
			}
		}
	}
}