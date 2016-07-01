using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Utils.Web.UI;

using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneEndoPresenti : IstanzeStepPage
	{
		[Inject]
		public IEndoprocedimentiService _endoprocedimentiService { get; set; }


		private Dictionary<int, List<TipiTitoloDto>> _tipiTitoloDictionary;

		public Dictionary<int, List<TipiTitoloDto>> TipiTitoloDictionary
		{
			get 
			{ 
				if(_tipiTitoloDictionary == null)
				{
					var q = from r in ReadFacade.Domanda.Endoprocedimenti.Secondari
							where r.PermetteVerificaAcquisizione
							select r.Codice;

					_tipiTitoloDictionary = _endoprocedimentiService.TipiTitoloWhereCodiceInventarioIn(IdComune, q.ToList());
				}


				return _tipiTitoloDictionary; 			
			}
			
		}




		protected void Page_Load(object sender, EventArgs e)
		{
			// Il service si occupa del salvataggio dei dati
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		#region Ciclo di vita dello step

		public override void OnInitializeStep()
		{
		}

		public override bool CanEnterStep()
		{
			return ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Where(x => x.PermetteVerificaAcquisizione).Count() > 0;
		}


		public override bool CanExitStep()
		{
			return this.Errori.Count == 0;
		}

		public override void OnBeforeExitStep()
		{
			var listaEndoPresenti = new List<DatiEndoprocedimentoPresente>();

			foreach (RepeaterItem rptItem in rptEndo.Items)
			{
				var hidIdEndo = (HiddenField)rptItem.FindControl("hidIdEndo");
				var chkPresente = (CheckBox)rptItem.FindControl("chkPresente");
				var txtNumeroAtto = (TextBox)rptItem.FindControl("txtNumeroAtto");
				var txtDataAtto = (DateTextBox)rptItem.FindControl("txtDataAtto");
				var ltrNomeEndo = (Literal)rptItem.FindControl("ltrNomeEndo");
				var ddlTipiTitolo = (DropDownList)rptItem.FindControl("ddlTipiTitolo");
				var txtRilasciatoDa = (TextBox)rptItem.FindControl("txtRilasciatoDa");
				var txtNote = (TextBox)rptItem.FindControl("txtNote");

				if (!chkPresente.Checked)
					continue;

				listaEndoPresenti.Add( new DatiEndoprocedimentoPresente(
											chkPresente.Checked,
											Convert.ToInt32(hidIdEndo.Value),
											ltrNomeEndo.Text,
											ddlTipiTitolo.SelectedValue,
											ddlTipiTitolo.SelectedItem.Text,
											txtNumeroAtto.Text,
											txtDataAtto.Text,
											txtRilasciatoDa.Text,
											txtNote.Text) );
			}

			var errori = _endoprocedimentiService.VerificaCorrettezzaListaEndoPresenti(listaEndoPresenti);

			if (errori.Count() > 0)
			{
				this.Errori.AddRange(errori);
				return;
			}

			_endoprocedimentiService.ImpostaEndoPresenti(IdDomanda, listaEndoPresenti);
		}
		#endregion

		public class EndoPresenteBindingItem
		{
			public int CodiceInventario { get; set; }
			public string Descrizione { get; set; }
			public bool Presente { get; set; }
			public IEnumerable<TipiTitoloDto> TitoliSupportati { get; set; }
			public int IdTipoTitoloSelezionato { get; set; }
			public string NumeroAtto { get; set; }
			public string DataAtto { get; set; }
			public string RilasciatoDa { get; set; }
			public string Note { get; set; }
			public string StileCss { get; set; }

		}

		protected void rptEndo_ItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType != ListItemType.Item && e.Item.ItemType != ListItemType.AlternatingItem)
				return;

			var row = (PresentazioneIstanzaDataSet.ISTANZEPROCEDIMENTIRow)e.Item.DataItem;

			var ddlTipiTitolo = (DropDownList)e.Item.FindControl("ddlTipiTitolo");
			var ltrTipoTitolo = (Literal)e.Item.FindControl("ltrTipoTitolo");

			var listaTipiTitolo = TipiTitoloDictionary[ row.CODICEINVENTARIO ];

			if (listaTipiTitolo.Count == 0)
			{
				ddlTipiTitolo.Visible = false;
				ltrTipoTitolo.Visible = false;
			}
			else
			{
				listaTipiTitolo.Insert(0, new TipiTitoloDto { Codice = -1, Descrizione = "" });

				if (!row.IsTipoTitoloNull())
					ddlTipiTitolo.SelectedValue = row.TipoTitolo.ToString();

				ddlTipiTitolo.DataSource = listaTipiTitolo;
				ddlTipiTitolo.DataBind();
			}
		}

		protected string VisualizzaAsterisco(int binarioDipendenzeEndoPrincipale, int binarioDipendenzeEndo)
		{
			if ( binarioDipendenzeEndoPrincipale < 0 )
				return String.Empty;

			if ((binarioDipendenzeEndoPrincipale & binarioDipendenzeEndo) != binarioDipendenzeEndo)
				return "*";

			return String.Empty;
		}

		private IEnumerable<TipiTitoloDto> GetTipiTitoloPerEndo(int codiceInventario)
		{
			var listaTitoli = TipiTitoloDictionary[codiceInventario];

			listaTitoli.Insert(0, new TipiTitoloDto { Codice = -1, Descrizione = String.Empty });
			listaTitoli.Sort( (x, y) => x.Descrizione.CompareTo( y.Descrizione));

			return listaTitoli;
		}

		public override void DataBind()
		{
			var endoPrincipale = ReadFacade.Domanda.Endoprocedimenti.Principale;

			var q = from r in ReadFacade.Domanda.Endoprocedimenti.Secondari
					where r.PermetteVerificaAcquisizione
					select new EndoPresenteBindingItem
					{
						CodiceInventario = r.Codice,
						DataAtto = r.Riferimenti == null || !r.Riferimenti.DataAtto.HasValue? String.Empty : r.Riferimenti.DataAtto.Value.ToString("dd/MM/yyyy"),
						Descrizione = VisualizzaAsterisco( endoPrincipale == null ? -1 : endoPrincipale.BinarioDipendenze , r.BinarioDipendenze ) + " " + r.Descrizione,
						IdTipoTitoloSelezionato = r.Riferimenti == null ? -1 : r.Riferimenti.TipoTitolo.Codice,
						Note = r.Riferimenti == null ? String.Empty : r.Riferimenti.Note,
						NumeroAtto = r.Riferimenti == null ? String.Empty : r.Riferimenti.NumeroAtto,
						Presente = r.Presente,
						RilasciatoDa = r.Riferimenti == null ? String.Empty : r.Riferimenti.RilasciatoDa,
						TitoliSupportati = GetTipiTitoloPerEndo( r.Codice )
					};

			rptEndo.DataSource = q;
			rptEndo.DataBind();
		}
	}
}
