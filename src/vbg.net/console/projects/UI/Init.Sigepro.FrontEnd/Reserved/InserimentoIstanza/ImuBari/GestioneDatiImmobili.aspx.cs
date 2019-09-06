using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Imu;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using log4net;
using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
using Init.Sigepro.FrontEnd.Bari.IMU;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ImuBari
{
	public partial class GestioneDatiImmobili : IstanzeStepPage
	{
		[Inject]
		protected ImuBariService _service { get; set; }

		[Inject]
		protected IComuniService _comuniService { get; set; }


		protected ILog _log = LogManager.GetLogger(typeof(GestioneDatiImmobili));

		public int CodiceTipoSoggettoPersonaGiuridica
		{
			get
			{
				object o = this.ViewState["CodiceTipoSoggettoPersonaGiuridica"];

				if (o == null)
					throw new Exception("Impostare un codice tipo soggetto per persona giuridica");

				return (int)o;
			}
			set { this.ViewState["CodiceTipoSoggettoPersonaGiuridica"] = value; }
		}

		public int CodiceTipoSoggettoPersonaFisica
		{
			get
			{
				object o = this.ViewState["CodiceTipoSoggettoPersonaFisica"];

				if (o == null)
					throw new Exception("Impostare un codice tipo soggetto per persona fisica");

				return (int)o;
			}
			set { this.ViewState["CodiceTipoSoggettoPersonaFisica"] = value; }
		}

		public string TestoRicerca
		{
			get { return ltrTestoRicerca.Text; }
			set { ltrTestoRicerca.Text = value; }
		}

		public string TestoDettaglio
		{
			get { return ltrTestoDettaglio.Text; }
			set { ltrTestoDettaglio.Text = value; }
		}

		#region errori

		public string ErrNessunaAbitazioneSpecificata
		{
			get { object o = this.ViewState["ErrNessunaAbitazioneSpecificata"]; return o == null ? "ErrNessunaAbitazioneSpecificata" : (string)o; }
			set { this.ViewState["ErrNessunaAbitazioneSpecificata"] = value; }
		}

		public string ErrTroppeAbitazioniSelezionate
		{
			get { object o = this.ViewState["ErrTroppeAbitazioniSelezionate"]; return o == null ? "ErrTroppeAbitazioniSelezionate" : (string)o; }
			set { this.ViewState["ErrTroppeAbitazioniSelezionate"] = value; }
		}

		public string ErrTroppePertinenzeSelezionate
		{
			get { object o = this.ViewState["ErrTroppePertinenzeSelezionate"]; return o == null ? "ErrTroppePertinenzeSelezionate" : (string)o; }
			set { this.ViewState["ErrTroppePertinenzeSelezionate"] = value; }
		}

		public string ErrTroppiElementiPerCategoria
		{
			get { object o = this.ViewState["ErrTroppiElementiPerCategoria"]; return o == null ? "ErrTroppiElementiPerCategoria" : (string)o; }
			set { this.ViewState["ErrTroppiElementiPerCategoria"] = value; }
		}

		#endregion





		private static class Constants
		{
			public const int IdViewRicerca = 0;
			public const int IdViewDettaglio = 1;
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
			if (ReadFacade.Domanda.ImuBari.DatiImmobili != null)
				MostraVistaDettaglio();
			else
				MostraVistaRicerca();
		}

		private void MostraVistaDettaglio()
		{
			multiView.ActiveViewIndex = Constants.IdViewDettaglio;

			datiImmobili.DataSource = ReadFacade.Domanda.ImuBari.DatiImmobili;
			datiImmobili.DataBind();

			this.Master.MostraBottoneAvanti = true;
		}

		private void MostraVistaRicerca()
		{
			multiView.ActiveViewIndex = Constants.IdViewRicerca;
			this.Master.MostraBottoneAvanti = false;

			cmdAnnullaRicerca.Visible = false;
			datiImmobiliCercati.Visible = false;

			if (ReadFacade.Domanda.ImuBari.DatiImmobili != null)
			{
				cmdAnnullaRicerca.Visible = true;
				txtCfUtenza.Text = ReadFacade.Domanda.ImuBari.DatiImmobili.CodiceFiscale;
				cmdCerca_Click(this, EventArgs.Empty);
			}
		}

		protected void cmdCerca_Click(object sender, EventArgs e)
		{
			try
			{
				var cfOperatore = UserAuthenticationResult.DatiUtente.Codicefiscale;
				var cfUtenza = txtCfUtenza.Text;

				var datiTrovati = this._service.TrovaUtenze(cfOperatore, cfUtenza);

				datiImmobiliCercati.DataSource = datiTrovati;
				datiImmobiliCercati.DataBind();

				datiImmobiliCercati.Visible = datiTrovati != null;

				//datiImmobiliCercati.Visible = true;
			}
			catch (Exception ex)
			{
				this.Errori.Add(ex.Message);
				datiImmobiliCercati.Visible = false;
			}
		}

		protected void cmdAnnullaRicerca_Click(object sender, EventArgs e)
		{
			MostraVistaDettaglio();
		}

		protected void cmdSelzionaAltraUtenza_Click(object sender, EventArgs e)
		{
			MostraVistaRicerca();
		}

		public bool OnValidazioneDatiSelezionati(object sender, IEnumerable<ImmobileImuDto> datiContribuente)
		{
			var errori = ValidaDatiContribuente(datiContribuente);

			Errori.AddRange(errori);

			return Errori.Count == 0;
		}

		private IEnumerable<string> ValidaDatiContribuente(IEnumerable<ImmobileImuDto> datiContribuente)
		{
			// TODO: Riattivare le verifiche
			// return Enumerable.Empty<string>();

            if (!new AlmenoUnaAbitazioneSelezionataSpecification().IsSatisfiedBy(datiContribuente))
                yield return ErrNessunaAbitazioneSpecificata;

            if (!new SoloUnaAbitazioneSelezionataSpecification().IsSatisfiedBy(datiContribuente))
                yield return ErrTroppeAbitazioniSelezionate;

            if (!new SelezionateMaxTrePertinenzeSpecification().IsSatisfiedBy(datiContribuente))
                yield return ErrTroppePertinenzeSelezionate;


            var spec = new SelezionateMaxUnaPertinenzaPerCategoriaCatastale();

            if (!spec.IsSatisfiedBy(datiContribuente))
            {
                foreach (var categoria in spec.PertinenzeMultiple)
                {
                    yield return String.Format("Sono state selezionate troppe pertinenze per la categoria catastale {0}. E' possibile selezionare una sola pertinenza per ciascuna categoria.", categoria);
                }
            }
		}

		void spec_ErroreSuCategoriaCatastale(object sender, string categoria)
		{
			throw new NotImplementedException();
		}

		protected void OnImmobiliSelezionati(object sender, DatiContribuenteImuDto datiContribuente)
		{
			try
			{
				var tipoSoggetto = datiContribuente.TipoPersona == DatiContribuenteImuDto.TipoPersonaEnum.Fisica ? CodiceTipoSoggettoPersonaFisica : CodiceTipoSoggettoPersonaGiuridica;

				var cmd = new ImpostaDatiContribuenteImuCommand(IdDomanda, datiContribuente, tipoSoggetto, this._comuniService);

				this._service.ImpostaDatiImmobili(cmd);

				MostraVistaDettaglio();
			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore imprevisto durante la lettura degli immobili selezionati, riprovare in un secondo momento. Se il problema persiste contattare gli uffici comunali");

				this._log.ErrorFormat("Errore durante l'assegnazione degli immobili selezionati alla domanda: {0}", ex.ToString());
			}
		}
	}
}