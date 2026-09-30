using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneSottoscriventi : IstanzeStepPage
	{
		[Inject]
		public IAnagraficheService AnagraficheService { get; set; }
		[Inject]
		public ProcureService ProcuratoriService { get; set; }

		public class DatiSoggettoSottoscrivente
		{
			public string CodiceFiscale { get; set; }
			public string Nominativo { get; set; }
			public string TipoSoggetto { get; set; }
			public bool Sottoscrivente { get; set; }
			public string AventeProcura { get; set; }
			public string NominativoAventeProcura { get; set; }
			public IEnumerable<ListItem> PossibiliProcuratori { get; set; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			// I services si occupano del salvataggio dati
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		#region ciclo di vita dello step

		public override void OnInitializeStep()
		{
			var listaRichiedenti = ReadFacade.Domanda.Anagrafiche.GetRichiedenti();

			if (listaRichiedenti.Count() == 1)
			{
				// Se esiste un solo richiedente ed è l'unica persona fisica all'interno della domanda 
				// allora svuoto la lista dei procuratori e inserisco l'unico richiedente come procuratore di se stesso
				var lista = from AnagraficaDomanda r in ReadFacade.Domanda.Anagrafiche.Anagrafiche
							where r.TipoPersona == TipoPersonaEnum.Fisica
							select new { r.Codicefiscale };

				if (lista.Distinct().Count() == 1)
				{
					var listaProcure = new List<ProcureService.DatiProcuratore>();

					listaProcure.Add( new AppLogic.Services.Domanda.ProcureService.DatiProcuratore
					{
						CodiceFiscaleProcuratore = String.Empty,
						CodiceFiscaleSottoscrivente = lista.ElementAt(0).Codicefiscale
					});

					ProcuratoriService.ImpostaProcuratori(IdDomanda, listaProcure);
				}
			}

		}

		public override bool CanEnterStep()
		{
			var richiedenti			= ReadFacade.Domanda.Anagrafiche.GetRichiedenti();
			var numeroAnagrafiche = ReadFacade.Domanda.Anagrafiche.Anagrafiche.Where(x => x.TipoPersona == TipoPersonaEnum.Fisica).Distinct().Count();

			if (richiedenti.Count() == 1 && numeroAnagrafiche == 1 )
				return false;

			return true;
		}

		public override void OnBeforeExitStep()
		{
			var listaProcure = new List<ProcureService.DatiProcuratore>();

			foreach (GridViewRow gvr in gvSottoscriventi.Rows)
			{
				var datiProcura = new ProcureService.DatiProcuratore
				{
					CodiceFiscaleSottoscrivente = gvSottoscriventi.DataKeys[gvr.RowIndex].Value.ToString(),
					CodiceFiscaleProcuratore = String.Empty
				};

				var chkSottoscrive = (CheckBox)gvr.FindControl("chkSottoscrive");

				if (!chkSottoscrive.Checked)
				{
					var ddlAventeProcura = (DropDownList)gvr.FindControl("ddlAventeProcura");
					datiProcura.CodiceFiscaleProcuratore = ddlAventeProcura.SelectedValue;
				}

				listaProcure.Add(datiProcura);
			}

			ProcuratoriService.ImpostaProcuratori(IdDomanda, listaProcure);
		}

		public override bool CanExitStep()
		{
			return true;
		}

		#endregion




		public override void DataBind()
		{
			var dataSource = new List<DatiSoggettoSottoscrivente>();

			var richiedenti = ReadFacade.Domanda.Anagrafiche.GetRichiedenti();

			foreach (var richiedente in richiedenti)
			{
				var sogg = new DatiSoggettoSottoscrivente
				{
					CodiceFiscale = richiedente.Codicefiscale,
					Nominativo = richiedente.ToString(),
					TipoSoggetto = richiedente.TipoSoggetto.ToString(),
					PossibiliProcuratori = ReadFacade.Domanda.Anagrafiche
															 .GetPossibiliProcuratoriDi( richiedente.Codicefiscale )
															 .Select( r => new ListItem( r.ToString() , r.Codicefiscale )),
					AventeProcura = ReadFacade.Domanda.Procure.GetCodiceFiscaleDelProcuratoreDi(richiedente.Codicefiscale),
					
				};

				sogg.Sottoscrivente = String.IsNullOrEmpty(sogg.AventeProcura);

				if (!sogg.Sottoscrivente)
				{
					sogg.NominativoAventeProcura = ReadFacade.Domanda
															 .Anagrafiche
															 .FindByRiferimentiSoggetto(TipoPersonaEnum.Fisica, sogg.AventeProcura)
															 .ToString();
				
				}

				dataSource.Add(sogg);
			}

			gvSottoscriventi.DataSource = dataSource;
			gvSottoscriventi.DataBind();

			//VerificaVisibilitaDiv();
		}

		protected void gvSottoscriventi_RowDataBound(object sender, GridViewRowEventArgs e)
		{
			if (e.Row.RowType == DataControlRowType.DataRow)
			{
				DropDownList ddlAventeProcura = (DropDownList)e.Row.FindControl("ddlAventeProcura");

				DatiSoggettoSottoscrivente soggetto = (DatiSoggettoSottoscrivente)e.Row.DataItem;

				ddlAventeProcura.Items.Clear();
				ddlAventeProcura.Items.AddRange( soggetto.PossibiliProcuratori.ToArray() );

				if (!soggetto.Sottoscrivente)
					ddlAventeProcura.SelectedValue = soggetto.AventeProcura;
				else
					ddlAventeProcura.SelectedIndex = 0;

				ddlAventeProcura.Visible = !soggetto.Sottoscrivente;
			}
		}

		protected void chkSottoscrive_CheckedChanged(object sender, EventArgs e)
		{
			CheckBox chkSottoscrive = (CheckBox)sender;
			DropDownList ddlAventeProcura = (DropDownList)((GridViewRow)chkSottoscrive.NamingContainer).FindControl("ddlAventeProcura");

			ddlAventeProcura.Visible = !chkSottoscrive.Checked;

			if (ddlAventeProcura.Items.Count > 0 )
				ddlAventeProcura.SelectedIndex = 0;
		}

	}
}
