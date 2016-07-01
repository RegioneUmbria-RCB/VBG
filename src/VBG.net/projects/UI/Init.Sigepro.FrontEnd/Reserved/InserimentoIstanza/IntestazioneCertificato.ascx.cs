using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.Entities.Visura;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class IntestazioneCertificato : System.Web.UI.UserControl
	{
		private DatiDettaglioPratica m_dataSource;

		public DatiDettaglioPratica DataSource
		{
			get { return m_dataSource; }
			set { m_dataSource = value; }
		}


		protected void Page_Load(object sender, EventArgs e)
		{
		}

		public override void DataBind()
		{
			lblNumeroPratica.Text		= DataSource.NumeroPratica;
			lblDataPresentazione.Text	= DataSource.DataPresentazione;
			lblOggetto.Text				= DataSource.Oggetto;
			lblIntervento.Text			= DataSource.DescrizioneIntervento;
			lblStatoPratica.Text		= DataSource.StatoPratica;

			if (DataSource.Localizzazioni != null && DataSource.Localizzazioni.Count > 0)
			{
				string stringaLocalizzaizone = DataSource.Localizzazioni[0].Indirizzo + " " + DataSource.Localizzazioni[0].Civico;
				lblLocalizzazione.Text = stringaLocalizzaizone;
			}

			List<DatiCatastali> datiCatastali = DataSource.DatiCatastali;

			dgDatiCatastali.DataSource = datiCatastali;
			dgDatiCatastali.DataBind();

			pnlDatiCatastali.Visible = datiCatastali.Count > 0;
		}
	}
}