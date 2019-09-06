using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneUtenzeTares_DettagliUtenza : System.Web.UI.UserControl
	{
		public class MappaturaTipoUtenza
		{
			public readonly string IdentificativoUtenza;
			public readonly TipoUtenzaTaresEnum TipoUtenza;

			public MappaturaTipoUtenza(string identificativoUtenza, TipoUtenzaTaresEnum tipoUtenza)
			{
				this.IdentificativoUtenza = identificativoUtenza;
				this.TipoUtenza = tipoUtenza;
			}
		}

		public class UtenzaSelezionataeventArgs
		{
			public readonly int IdentificativoContribuente;
			public IEnumerable<MappaturaTipoUtenza> TipiUtenze;

			public UtenzaSelezionataeventArgs(int identificativoContribuente, IEnumerable<MappaturaTipoUtenza> tipiUtenze)
			{
				this.IdentificativoContribuente = identificativoContribuente;
				this.TipiUtenze = tipiUtenze;
			}
		}
		public delegate void UtenzaSelezionataDelegate(object sender, UtenzaSelezionataeventArgs e);
		public event UtenzaSelezionataDelegate UtenzaSelezionata;

		public delegate void ErroreDiSelezioneDelegate(object sender, string errore);
		public event ErroreDiSelezioneDelegate Errore;


		public int NumeroMassimoUtenzeGestibili
		{
			get { object o = this.ViewState["NumeroMassimoUtenzeGestibili"]; return o == null ? 3 : (int)o; }
			set { this.ViewState["NumeroMassimoUtenzeGestibili"] = value; }
		}

		public string MessaggioErroreLimiteUtenzeSuperato
		{
			get { object o = this.ViewState["MessaggioErroreLimiteUtenzeSuperato"]; return o == null ? "Numero massimo utenze superato" : (string)o; }
			set { this.ViewState["MessaggioErroreLimiteUtenzeSuperato"] = value; }
		}

		public bool MostraBottoneSeleziona
		{
			get { object o = this.ViewState["MostraBottoneSeleziona"]; return o == null ? true : (bool)o; }
			set { this.ViewState["MostraBottoneSeleziona"] = value; }
		}
		

		public int? IdentificativoContribuente
		{
			get { object o = this.ViewState["IdentificativoContribuente"]; return o == null ? (int?)null : (int)o; }
			set { this.ViewState["IdentificativoContribuente"] = value; }
		}


		public DatiContribuenteDto DataSource
		{
			get;
			set;
		}

		public IEnumerable<KeyValuePair<string, string>> ListaValoriCombo()
		{
			yield return new KeyValuePair<string, string>(TipoUtenzaTaresEnum.NonPresenteNellaDomanda.ToString(), String.Empty);
			yield return new KeyValuePair<string, string>(TipoUtenzaTaresEnum.Abitazione.ToString(), "Abitazione");
			yield return new KeyValuePair<string, string>(TipoUtenzaTaresEnum.Pertinenza1.ToString(), "Pertinenza 1");
			yield return new KeyValuePair<string, string>(TipoUtenzaTaresEnum.Pertinenza2.ToString(), "Pertinenza 2");
		}

		protected void Page_Load(object sender, EventArgs e)
		{
		}

		protected void OnItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType != ListItemType.Item && e.Item.ItemType != ListItemType.AlternatingItem)
				return;

			var ddlTipoUtenza = (DropDownList)e.Item.FindControl("ddlTipoUtenza");

			if (ddlTipoUtenza == null)
				return;

			var dataItem = (DatiUtenzaDto)e.Item.DataItem;

			ddlTipoUtenza.SelectedValue = dataItem.TipoUtenza.ToString();
			ddlTipoUtenza.DataSource = ListaValoriCombo();
			ddlTipoUtenza.DataBind();
		}


		public override void DataBind()
		{
			if (this.DataSource == null)
				return;

			rptIndirizziUtenze.DataSource = this.DataSource.ElencoUtenzeAttive;
			rptIndirizziUtenze.DataBind();

			ltrNominativo.Text = DataSource.DatiAnagraficiContribuente.NominativoCompleto;
			ltrCodiceFiscale.Text = DataSource.DatiAnagraficiContribuente.CodiceFiscale;
			ltrDatiNascita.Text = DataSource.DatiAnagraficiContribuente.DatiNascita;
			ltrIndirizzoResidenza.Text = DataSource.DatiResidenzaContribuente.ToString();
			IdentificativoContribuente = DataSource.IdentificativoContribuente;
			ltrIdentificativoUtenza.Text = DataSource.IdentificativoContribuente.ToString();

			if (this.DataSource.ElencoUtenzeAttive.Count() > this.NumeroMassimoUtenzeGestibili)
			{
				this.MostraBottoneSeleziona = false;
				this.ltrMessagigoErrore.Text = this.MessaggioErroreLimiteUtenzeSuperato;
				this.ltrMessagigoErrore.Visible = true;
			}
		}

		protected void cmdSeleziona_Click(object sender, EventArgs e)
		{
			try
			{
				if (this.UtenzaSelezionata != null)
				{
					var tipiUtenze = rptIndirizziUtenze.Items
														.Cast<RepeaterItem>()
														.Select(x =>
														{
															var ddlTipoUtenza = (DropDownList)x.FindControl("ddlTipoUtenza");
															var hidIdUtenza = (HiddenField)x.FindControl("hidIdUtenza");

															var identificativoUtenza = hidIdUtenza.Value;
															var tipoUtenza = (TipoUtenzaTaresEnum)Enum.Parse(typeof(TipoUtenzaTaresEnum), ddlTipoUtenza.SelectedValue);

															return new MappaturaTipoUtenza(identificativoUtenza, tipoUtenza);
														})
														.Where(x => x.TipoUtenza != TipoUtenzaTaresEnum.NonPresenteNellaDomanda);

					var abitazioni = tipiUtenze.Where(x => x.TipoUtenza == TipoUtenzaTaresEnum.Abitazione);

					if (abitazioni.Count() == 0)
						throw new Exception("Nessuna utenza identificata come abitazione");

					if (abitazioni.Count() > 1)
						throw new Exception("Una sola utenza può essere identificata come abitazione");

					var pertinenza1 = tipiUtenze.Where(x => x.TipoUtenza == TipoUtenzaTaresEnum.Pertinenza1);

					if (pertinenza1.Count() > 1)
						throw new Exception("Una sola utenza può essere identificata come Pertinanza 1");

					var pertinenza2 = tipiUtenze.Where(x => x.TipoUtenza == TipoUtenzaTaresEnum.Pertinenza2);

					if (pertinenza2.Count() > 1)
						throw new Exception("Una sola utenza può essere identificata come Pertinanza 2");

					if( pertinenza1.Count() == 0 && pertinenza2.Count() > 0 )
						throw new Exception("Pertinenza 1 non specificata");

					this.UtenzaSelezionata(this, new UtenzaSelezionataeventArgs(this.IdentificativoContribuente.Value, tipiUtenze));
				}
			}
			catch (Exception ex)
			{
				if (Errore != null)
					Errore(this, ex.Message);
				else
					throw;
			}
		}

		protected string DecodificaEnum(object strValue)
		{
			return String.Join( " ", Regex.Split(strValue.ToString(), "(?<!^)(?=[A-Z0-9][a-z]*)").Select((x, idx) => idx == 0 ? x : x.ToLower()).ToArray());
		}
	}

	
}