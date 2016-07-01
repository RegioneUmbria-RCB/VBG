using System;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Descrizione di riepilogo per ComboSettori.
	/// </summary>
	[ToolboxData("<{0}:ComboSettori runat=server></{0}:ComboSettori>")]
	public class ComboSettori : FilteredDropDownList
	{
		/// <summary>
		/// Nome della <see cref="ComboAttivita"/> collegata
		/// </summary>
		public String NomeComboCollegata
		{
			set { this.ViewState["NomeComboCollegata"] = value; }
			get
			{
				object o = this.ViewState["NomeComboCollegata"];

				return (o == null) ? String.Empty : o.ToString();
			}
		}

		public ComboSettori()
		{
			EnsureChildControls();
		}


		/// <summary>
		/// Creazione dei controlli figlio e inizializzazione delle proprietà
		/// </summary>
		protected override void CreateChildControls()
		{
			this.AutoPostBack = true;

			this.DataTextField = "SETTORE";
			this.DataValueField = "CODICESETTORE";

			this.SelectedIndexChanged += new EventHandler(ComboSettori_SelectedIndexChanged);

			base.CreateChildControls();
		}

		private void ComboSettori_SelectedIndexChanged(object sender, EventArgs e)
		{
			ForceRebind();
		}


		/// <summary>
		/// Effettua il binding dei dati alla <see cref="ComboAttivita"/> collegata in base al valore selezionato
		/// </summary>
		public void ForceRebind()
		{
//			ComboAttivita comboAttivita = (ComboAttivita) this.Parent.FindControl(NomeComboCollegata);
//
//			if (this.Items.Count == 0)
//			{
//				this.DataBind();
//			}
//
//			if (comboAttivita != null)
//			{
//				if (this.SelectedValue == String.Empty)
//				{
//					comboAttivita.SelectedIndex = -1;
//					comboAttivita.Items.Clear();
//				}
//				else
//				{
//					AttivitaReader.AttivitaIterator it = new AttivitaReader( IdComune , Software ).ReadFromSettore(this.SelectedValue);
//
//					comboAttivita.Items.Clear();
//
//					while (it.Read())
//					{
//						comboAttivita.Items.Add(new ListItem(it.Item.Descrizione, it.Item.CodiceAttivita));
//					}
//
//					comboAttivita.DataBind();
//				}
//			}
		}


		/// <summary>
		/// Effettua il binding dei dati al controllo
		/// </summary>
		public override void DataBind()
		{
//			EnsureChildControls();
//
//			SettoriReader.SettoriIterator it = new SettoriReader( IdComune , Software ).ReadAll();
//
//			this.Items.Add(new ListItem("Selezionare il settore", String.Empty));
//
//			while (it.Read())
//			{
//				this.Items.Add(new ListItem(it.Item.Descrizione, it.Item.CodiceSettore));
//			}
//
//			base.DataBind();

		}

	}
}