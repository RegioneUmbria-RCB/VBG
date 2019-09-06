using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class RiepilogoDomandaPec : IstanzeStepPage
	{
		const int ID_VIEW_PREINVIO = 0;
		const int ID_VIEW_DETTAGLI = 1;
		const int ID_VIEW_ERRORI = 2;

		[Inject]
		public IComuniService _comuniService{ get; set; }
		[Inject]
		public AllegatiInterventoService _allegatiInterventoService { get; set; }

		[Inject]
		public InvioDomandaService _trasferimentoDomandaService { get; set; }

		[Inject]
		public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		#region Parametri letti dal file xml
		public string TestoIntestazioneInvio
		{
			get { return ltrIntestazioneInvio.Text; }
			set { ltrIntestazioneInvio.Text = value; }
		}

		public string TestoDownloadDomanda
		{
			get { return lblTestoDownloadDomanda.Text; }
			set { lblTestoDownloadDomanda.Text = value; }
		}

		public string TestoUploadDomanda
		{
			get { return lblTestoUploadDomanda.Text; }
			set { lblTestoUploadDomanda.Text = value; }
		}

		public string TestoSelezioneIndirizzoPEC
		{
			get { return lblSelezioneIndirizzoPEC.Text; }
			set { lblSelezioneIndirizzoPEC.Text = value; }
		}

		public string TestoIndirizzoSportelloComune
		{
			get { return lblIndirizzoSportelloComune.Text; }
			set { lblIndirizzoSportelloComune.Text = value; }
		}

		public string TestoBottoneInvio
		{
			get { return lblTestoBottoneInvio.Text; }
			set { lblTestoBottoneInvio.Text = value; }
		}

		public string TestoInvioEffettuato
		{
			get { return ltrInvioeffettuato.Text; }
			set { ltrInvioeffettuato.Text = value; }
		}
		#endregion




		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			multiView.ActiveViewIndex = ID_VIEW_PREINVIO;

			// Preparo il link per il download della domanda
			var rigaRiepilogo = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

            hlModelloDomanda.NavigateUrl = UrlBuilder.Url("~/Reserved/InserimentoIstanza/DownloadOggettoCompilabile.ashx", x =>
            {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
                x.Add(new QsCodiceOggetto(rigaRiepilogo.CodiceOggettoModello.Value));
                x.Add(new QsIdDomandaOnline(IdDomanda));
                x.Add("Fmt", "PDF");
            });

			// Popolo la combo con gli indirizzi pec dei richiedenti
			ddlIndirizzoPec.Items.Clear();

			var listaRichiedenti = ReadFacade.Domanda.Anagrafiche.GetRichiedenti();
			var tecnico = ReadFacade.Domanda.Anagrafiche.GetTecnico();

			var nomeFormatString = "{0} ({1}): {2}";

			foreach (var row in listaRichiedenti)
			{
				if (!String.IsNullOrEmpty(row.Contatti.Pec))
				{
					var li = new ListItem( String.Format( nomeFormatString , row.ToString(), row.TipoSoggetto.ToString(), row.Contatti.Pec ), row.Contatti.Pec);

					ddlIndirizzoPec.Items.Add(li);
				}
			}

			if (tecnico != null)
			{
				if (!String.IsNullOrEmpty(tecnico.Contatti.Pec))
				{
					var li = new ListItem(String.Format(nomeFormatString, tecnico.ToString(), tecnico.TipoSoggetto.ToString(), tecnico.Contatti.Pec), tecnico.Contatti.Pec);

					ddlIndirizzoPec.Items.Add(li);
				}
			}

			// Sostituisco il segnaposto dell'indirizzo PEC del comune
			const string SEGNAPOSTO_PEC_COMUNE = "{INDIRIZZO_PEC_SPORTELLO}";
			var pecSportello = _comuniService.GetPecComuneAssociato(Software, ReadFacade.Domanda.AltriDati.CodiceComune );

			if (!String.IsNullOrEmpty(pecSportello))
				pecSportello = pecSportello.ToUpper();

			lblIndirizzoSportelloComune.Text = lblIndirizzoSportelloComune.Text.Replace(SEGNAPOSTO_PEC_COMUNE, pecSportello);
			ltrEmailDestinatario.Text = pecSportello;
			ltrIntestazioneInvio.Text = ltrIntestazioneInvio.Text.Replace(SEGNAPOSTO_PEC_COMUNE, pecSportello);
		}

		protected void cmdUploadDomanda_Click(Object sender, EventArgs e)
		{
			var row = ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

			try
			{
				var file = new BinaryFile(fuRiepilogo, this._validPostedFileSpecification);

				try
				{
					_allegatiInterventoService.Salva( IdDomanda, row.Id, file);
				}
				catch(FirmaDigitaleNonValidaException ex)
				{
					this.Errori.Add(ex.Message);

					return;
				}

				InviaDomanda();
			}
			catch (Exception ex)
			{
				this.Errori.Add(ex.Message);
			}
		}

		/// <summary>
		/// Effettua la trasmisisone della domanda al backoffice
		/// </summary>
		private void InviaDomanda()
		{
			var esito = _trasferimentoDomandaService.Invia( IdDomanda , ddlIndirizzoPec.SelectedValue );

			Master.MostraPaginatoreSteps = false;

			// Esito della domanda positivo, 
			if (esito.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito || esito.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscitoNoBackend)
			{
				MostraViewInvioEffettuato(esito);
			}
			else
			{
				MostraViewErrori(esito);
			}

		}

		private void MostraViewErrori(InvioIstanzaResult esito)
		{
			multiView.ActiveViewIndex = ID_VIEW_ERRORI;
			lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore(IdDomanda);
		}

		private void MostraViewInvioEffettuato(InvioIstanzaResult esito)
		{
			this.Title = "Invio effettuato con successo";
			multiView.ActiveViewIndex = ID_VIEW_DETTAGLI;
		}
	}
}
