using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TasiBari
{
	public partial class GestioneDatiImmobili_Dettagli : System.Web.UI.UserControl
	{
		public delegate void ImmobiliSelezionatiEventHandler(object sender, DatiContribuenteTasiDto datiContribuente);
		public event ImmobiliSelezionatiEventHandler ImmobiliSelezionati;

		public delegate bool ValidazioneDatiSelezionatiEventHandler(object sender, IEnumerable<ImmobileTasiDto> datiContribuente);
		public event ValidazioneDatiSelezionatiEventHandler ValidazioneDatiSelezionati;


		public DatiContribuenteTasiDto DataSource 
		{
			get { return (DatiContribuenteTasiDto)Session["GestioneDatiImmobili_Dettagli.dataSource"]; }
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

		public override void DataBind()
		{
			if (DataSource.TipoPersona == DatiContribuenteTasiDto.TipoPersonaEnum.Fisica)
			{
				ltrLabelIndirizzoResidenza.Text = "Residente in";

				ltrIdentificativoUtenza.Text = String.Format("Id contribuente: {0} - Cod contribuente: {1}", DataSource.IdContribuente, DataSource.CodiceContribuente);
				ltrNominativo.Text = String.Format("{0} {1}", DataSource.Cognome, DataSource.Nome);
				ltrCodiceFiscale.Text = DataSource.CodiceFiscale;
				ltrDatiNascita.Text = DataSource.DatiNascita;
				ltrIndirizzoResidenza.Text = DataSource.Residenza.ToString();
				IdentificativoContribuente = DataSource.IdContribuente;
			}
			else
			{
				ltrLabelIndirizzoResidenza.Text = "Con sede legale in";

				ltrIdentificativoUtenza.Text = String.Format("Id contribuente: {0} - Cod contribuente: {1}", DataSource.IdContribuente, DataSource.CodiceContribuente);
				ltrNominativo.Text = DataSource.Cognome;
				ltrCodiceFiscale.Text = DataSource.CodiceFiscale;
				ltrDatiNascita.Text = String.Empty;
				ltrIndirizzoResidenza.Text = DataSource.Residenza.ToString();
				IdentificativoContribuente = DataSource.IdContribuente;
			}

			rptDatiImmobili.DataSource = DataSource
											.ListaImmobili
											.Select(x => new 
											{
												IdImmobile = x.IdImmobile,
												Ubicazione = x.Ubicazione,
												RiferimentiCatastali = x.RiferimentiCatastali,
												PercentualePossesso = x.PercentualePossesso,
												TipoImmobile = x.TipoImmobile,
												PermettiSelezione = PermettiSelezione
											});
			rptDatiImmobili.DataBind();

			cmdConfermaSelezione.Visible = PermettiSelezione;
		}

		public void cmdConfermaSelezione_Click(object sender, EventArgs e)
		{
			var listaElementiSelezionati = rptDatiImmobili
											.Items
											.Cast<RepeaterItem>()
											.Where( item => {
												var chkSeleziona = (CheckBox)item.FindControl("chkSeleziona");

												return chkSeleziona.Checked;
											})
											.Select(item => {
												var ltrIdImmobile = (Literal)item.FindControl("ltrIdImmobile");

												var idImmobile = ltrIdImmobile.Text;

												return DataSource.ListaImmobili.FirstOrDefault(x => x.IdImmobile == idImmobile);
											})
											.ToList();

			// Effettua validazione
			if (ValidazioneDatiSelezionati != null)
			{
				var result = this.ValidazioneDatiSelezionati(this,listaElementiSelezionati);

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