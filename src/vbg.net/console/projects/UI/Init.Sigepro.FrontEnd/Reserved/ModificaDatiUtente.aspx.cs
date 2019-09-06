using System;
using System.Data;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class ModificaDatiUtente : ReservedBasePage
	{
		[Inject]
		public IAnagraficheService _anagrafeRepository { get; set; }
		[Inject]
		public IComuniService _comuniService { get; set; }


		ILog _log = LogManager.GetLogger(typeof(ModificaDatiUtente));



		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				InitCombo();
				DataBind();
			}
			

		}

		private void InitCombo()
		{
		}

		public override void DataBind()
		{
			AnagraficaUtente DataSource = UserAuthenticationResult.DatiUtente;

			NOMINATIVO.Text = DataSource.Nominativo;
			NOME.Text = DataSource.Nome;

			TITOLO.DataBind();
			TITOLO.SelectedValue = DataSource.Titolo.ToString();
			sesso.SelectedValue = DataSource.Sesso;

			CodiceCittadinanza.DataBind();
			CodiceCittadinanza.SelectedValue = DataSource.Codicecittadinanza.ToString();

			string comuneResidenza = DataSource.Comuneresidenza;

			if (!String.IsNullOrEmpty(comuneResidenza))
			{
				var comuneresidenzaDto = _comuniService.GetDatiComune( comuneResidenza);

				if (comuneresidenzaDto != null)
				{
					hidComuneResidenza.Value = comuneresidenzaDto.CodiceComune;
					txtComuneResidenza.Text = comuneresidenzaDto.Comune + " (" + comuneresidenzaDto.SiglaProvincia + ")";
					txtComuneResidenza.ReadOnly = true;
				}
			}
			else
			{
				hidComuneResidenza.Value = String.Empty;
				txtComuneResidenza.Text = String.Empty;
				txtComuneResidenza.ReadOnly = false;
			}

			Indirizzo.Text = DataSource.Indirizzo;
			Citta.Text = DataSource.Citta;
			Cap.Text = DataSource.Cap;

			string comuneCorrispondenza = DataSource.Comunecorrispondenza;

			if (!String.IsNullOrEmpty(comuneCorrispondenza))
			{
				var comuneCorrispondenzaDto = _comuniService.GetDatiComune( comuneCorrispondenza);

				if (comuneCorrispondenzaDto != null)
				{
					hidComuneCorrispondenza.Value = comuneCorrispondenza;
					txtComuneCorrispondenza.Text = comuneCorrispondenzaDto.Comune + " (" + comuneCorrispondenzaDto.SiglaProvincia + ")";
					txtComuneCorrispondenza.ReadOnly = true;
				}
			}
			else
			{
				hidComuneCorrispondenza.Value = String.Empty;
				txtComuneCorrispondenza.Text = String.Empty;
				txtComuneCorrispondenza.ReadOnly = false;
			}


			IndirizzoCorrispondenza.Text = DataSource.Indirizzocorrispondenza;

			CittaCorrispondenza.Text = DataSource.Cittacorrispondenza;

			CapCorrispondenza.Text = DataSource.Capcorrispondenza;

			string comuneNascita = DataSource.Codcomnascita;

			// comune nascita
			if (!String.IsNullOrEmpty(comuneNascita))
			{
				var comuneNascitaDto = _comuniService.GetDatiComune( comuneNascita);

				if (comuneNascitaDto != null)
				{
					hidComuneNascita.Value = comuneNascita;
					txtComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
					txtComuneNascita.ReadOnly = true;
				}
				else
				{
					hidComuneNascita.Value = txtComuneNascita.Text = String.Empty;
					txtComuneNascita.ReadOnly = false;
				}
			}
			else
			{
				// provo a ricavare il comune dal codice fiscale
				hidComuneNascita.Value = txtComuneNascita.Text = String.Empty;

				if (!String.IsNullOrEmpty(CodiceFiscale.Text) && CodiceFiscale.Text.Length == 16)
				{
					var codComune = CodiceFiscale.Text.Substring(11, 4);

					var comuneNascitaDto = _comuniService.GetDatiComune( codComune);

					if (comuneNascitaDto != null)
					{
						hidComuneNascita.Value = comuneNascitaDto.CodiceComune;
						txtComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
						txtComuneNascita.ReadOnly = false;
					}
				}
			}

			CodiceFiscale.Text = DataSource.Codicefiscale;
			Telefono.Text = DataSource.Telefono;
			TelefonoCellulare.Text = DataSource.Telefonocellulare;
			Fax.Text = DataSource.Fax;
			email.Text = DataSource.Email;

			DataNascita.DateValue = (DataSource.Datanascita == DateTime.MinValue) ? (DateTime?)null : DataSource.Datanascita;

			Page.Validate();
		}

		private string CodiceProvinciaDaComune(string codiceComune)
		{
			var comune = _comuniService.GetDatiComune( codiceComune);

			if (comune == null) return String.Empty;

			return comune.SiglaProvincia;
		}

		/// <summary>
		/// Ottiene il valore SAFE di una DataColumn di una DataRow dato che i dataset tipizzati hanno qualche problema con i dbNull...
		/// </summary>
		/// <param name="dataRow">DataRow da cui prendere il dato</param>
		/// <param name="columnName">Nome della colonna che contiene il dato</param>
		/// <returns>Valore <see cref="string"/> contenuto nella colonna o String.Empty se DbNull</returns>
		protected string SafeValue(DataRow dataRow, string columnName)
		{
			return SafeValue(dataRow, columnName, String.Empty);
		}

		/// <summary>
		/// Ottiene il valore SAFE di una DataColumn di una DataRow dato che i dataset tipizzati hanno qualche problema con i dbNull...
		/// </summary>
		/// <param name="dataRow">DataRow da cui prendere il dato</param>
		/// <param name="columnName">Nome della colonna che contiene il dato</param>
		/// <param name="defaultValue">Valore di default da ritornare se DbNull</param>
		/// <returns>Valore <see cref="String"/> contenuto nella colonna o valore di default impostato se DbNull</returns>
		protected string SafeValue(DataRow dataRow, string columnName, string defaultValue)
		{
			if (dataRow[columnName] == DBNull.Value)
				return defaultValue;
			else
				return dataRow[columnName].ToString();
		}

		protected void backToHome(object sender, EventArgs e)
		{
            var newUrl = UrlBuilder.Url("~/Reserved/Default.aspx", x => {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
            });

            Response.Redirect(newUrl);
		}

		protected void cmdBackToHome_Click(object sender, EventArgs e)
		{
            backToHome(this, EventArgs.Empty);
		}

		protected void cmdCopiaResidenza_Click(object sender, EventArgs e)
		{
			hidComuneCorrispondenza.Value = hidComuneResidenza.Value;
			txtComuneCorrispondenza.Text = txtComuneResidenza.Text;
			IndirizzoCorrispondenza.Text = Indirizzo.Text;
			CittaCorrispondenza.Text = Citta.Text;
			CapCorrispondenza.Text = Cap.Text;
		}

		protected void cmdCancel_Click(object sender, EventArgs e)
		{
            var newUrl = UrlBuilder.Url("~/Reserved/Default.aspx", x => {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
            });

            Response.Redirect(newUrl);
        }

		protected void cmdConfirm_Click(object sender, EventArgs e)
		{
			AnagraficaUtente DataSource = UserAuthenticationResult.DatiUtente;

			var datiComuneNascita = _comuniService.GetDatiComune( hidComuneNascita.Value);

			if (datiComuneNascita == null)
			{
				Errori.Add("Il comune di nascita specificato non è valido");
				return;
			}

			var datiComuneResidenza = _comuniService.GetDatiComune( hidComuneResidenza.Value);

			if (datiComuneResidenza == null)
			{
				Errori.Add("Il comune di residenza specificato non è valido");
				return;
			}

			DataSource.Nominativo = NOMINATIVO.Text;
			DataSource.Nome = NOME.Text;
			DataSource.Titolo = Convert.ToInt32(TITOLO.SelectedValue);
			DataSource.Sesso = sesso.SelectedValue;
			DataSource.Codicecittadinanza = Convert.ToInt32(CodiceCittadinanza.SelectedValue);

			DataSource.Provincia = datiComuneResidenza.SiglaProvincia;
			DataSource.Comuneresidenza = datiComuneResidenza.CodiceComune;

			DataSource.Indirizzo = Indirizzo.Text;
			DataSource.Citta = Citta.Text;
			DataSource.Cap = Cap.Text;


			if (!String.IsNullOrEmpty(hidComuneCorrispondenza.Value))
			{
				var datiComuneCorrispondenza = _comuniService.GetDatiComune( hidComuneCorrispondenza.Value);

				if (datiComuneCorrispondenza != null)
				{
					DataSource.Provinciacorrispondenza = datiComuneCorrispondenza.SiglaProvincia;
					DataSource.Comunecorrispondenza = datiComuneCorrispondenza.CodiceComune;
				}
			}

			DataSource.Indirizzocorrispondenza = IndirizzoCorrispondenza.Text;

			CittaCorrispondenza.Text = DataSource.Cittacorrispondenza;

			DataSource.Capcorrispondenza = CapCorrispondenza.Text;

			DataSource.Codcomnascita = datiComuneNascita.CodiceComune;

			DataSource.Codicefiscale = CodiceFiscale.Text;
			DataSource.Telefono = Telefono.Text;
			DataSource.Telefonocellulare = TelefonoCellulare.Text;
			DataSource.Fax = Fax.Text;
			DataSource.Email = email.Text;

			DataSource.Datanascita = DataNascita.DateValue.GetValueOrDefault( DateTime.MinValue );

			try
			{
				//SmtpMailSender.SendRegistrationMessage(IdComune, Software, "Area Riservata - Richiesta modifica dati anagrafici", DataSource);
				_anagrafeRepository.ModificaDatianagrafici(IdComune, DataSource);
				multiView.ActiveViewIndex = 1;
			}
			catch (Exception ex)
			{
				Errori.Add("Errore durante l'invio dei dati");
				_log.ErrorFormat("Errore durante la modifica dei dati dell'utente: {0}", ex.ToString());
			}
		}
	}
}
