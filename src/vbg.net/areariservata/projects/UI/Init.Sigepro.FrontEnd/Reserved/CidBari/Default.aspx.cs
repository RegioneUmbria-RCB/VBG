using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Text.RegularExpressions;
using Ninject;
using Init.Sigepro.FrontEnd.Bari.CID;
using Init.Sigepro.FrontEnd.Bari.CID.DTOs;
using Init.Utils;

namespace Init.Sigepro.FrontEnd.Reserved.CidBari
{
	public partial class Default : ReservedBasePage
	{
		private static class Constants
		{
			public const int IdVistaInserimentoDati = 0;
			public const int IdVistaVisualizzazioneDati = 1;

			public const string CfPattern = "[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]";
		}

		[Inject]
		protected IBariCidService CidService { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				MostraFormInserimento();
			}
		}

		private void MostraFormInserimento()
		{
			SvuotaForm();

			MultiView.ActiveViewIndex = Constants.IdVistaInserimentoDati;
		}

		private void SvuotaForm()
		{
			txtCid.Text = txtCodiceFiscale.Text = String.Empty;
		}


		protected void cmdGenera_Click(object o, EventArgs e)
		{
			try
			{
				ValidaCampiInput();

				var nomeUtente = UserAuthenticationResult.DatiUtente.Nominativo + " " + UserAuthenticationResult.DatiUtente.Nome;
				var cfUtente = UserAuthenticationResult.DatiUtente.Codicefiscale;
				var email = UserAuthenticationResult.DatiUtente.Email;

				var operatore = new DatiOperatoreDto(nomeUtente, email, cfUtente);
				var richiesta = new DatiRichiestaDto(txtCid.Text, txtCodiceFiscale.Text);
				
				var datiCid = this.CidService.GetDatiCid(operatore, richiesta);

				txtOutCodiceFiscale.Text = datiCid.CodiceFiscale;
				txtOutDataNascita.Text = datiCid.DataNascita;
				txtOutNominativo.Text = datiCid.Nominativo;
				txtOutCin.Text = datiCid.Cid;
				txtOutPin.Text = "----" + datiCid.Pin.Substring(4);

				// hidXml.Value = StreamUtils.SerializeClass(datiCid);
				

				MultiView.ActiveViewIndex = Constants.IdVistaVisualizzazioneDati;
			}
			catch (Exception ex)
			{
				Master.MostraErrori(new List<string>{ex.Message});
				return;
			}
		}

		protected void cmdChiudi_Click(object o, EventArgs e)
		{
			MostraFormInserimento();
		}


		private void ValidaCampiInput()
		{
			var cid = txtCid.Text;
			var cf = txtCodiceFiscale.Text;

			if (String.IsNullOrEmpty(cid) && String.IsNullOrEmpty(cf))
			{
				throw new Exception("Specificare un Cid oppure un codice fiscale");
			}

			if (!String.IsNullOrEmpty(cf) && !Regex.IsMatch(cf, Constants.CfPattern))
			{
				throw new Exception("Il codice fiscale immesso non è valido");
			}
		}
	}
}