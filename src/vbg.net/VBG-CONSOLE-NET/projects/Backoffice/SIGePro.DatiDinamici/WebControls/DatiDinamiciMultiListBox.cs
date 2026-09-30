using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using Init.SIGePro.DatiDinamici.Statistiche;
using System.Web.UI.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public partial class DatiDinamiciMultiListBox : DatiDinamiciBaseControl<ListBox>
	{

		public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
		{
			return new TipoConfrontoFiltroEnum[] { 
							TipoConfrontoFiltroEnum.Equal, 
							TipoConfrontoFiltroEnum.NotEqual, 
							TipoConfrontoFiltroEnum.Null, 
							TipoConfrontoFiltroEnum.NotNull };
		}

		/// <summary>
		/// Ritorna la lista di proprieta valorizzabili tramite la pagina di editing dei campi
		/// </summary>
		/// <returns>lista di proprieta valorizzabili tramite la pagina di editing dei campi</returns>
		public static ProprietaDesigner[] GetProprietaDesigner()
		{
			return new ProprietaDesigner[]{
						new ProprietaDesigner("Obbligatorio","Obbligatorio",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
						new ProprietaDesigner("IgnoraObbligatorietaSuAttivita","Ignora obbligatorietà su schede attività",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
                        new ProprietaDesigner("ElementiLista", "Elementi della lista (separati da \";\")","") ,
                        new ProprietaDesigner("IgnoraErroriBinding", "Ignora errori di binding" , TipoControlloEditEnum.ListBox , "No=false,Si=true","false"),
                        new ProprietaDesigner("Multiselezione", "Abilita la multiselezione" , TipoControlloEditEnum.ListBox , "No=false,Si=true","false"),
						new ProprietaDesigner("RigheVisualizzate", "Numero righe visualizzate","4")};
		}

		string m_bindValue = null;

		public override string Valore
		{
			get
			{
				string valore = "";
				bool isFirst = true;

				foreach (ListItem li in InnerControl.Items)
				{
					if (!li.Selected) continue;

					if (!isFirst)
						valore += ";";

					valore += li.Value;

					isFirst = false;
				}

				return valore;
			}
			set
			{
				bool elFound = false;


				string[] valori = value.Split(';');

				for (int i = 0; i < valori.Length; i++)
				{
					foreach (ListItem li in InnerControl.Items)
					{
						if (li.Value == valori[i])
						{
							elFound = true;
							li.Selected = true;
							break;
						}
					}
				}

				if (!elFound)
				{
					if (IgnoraErroriBinding)
					{
						m_bindValue = value;
						return;
					}

					string errMsg = "Si è tentato di impostare un valore non valido al controllo \"{0}\". Valori possibili: \"{1}\". Valore impostato:\"{2}\"";
					this.InnerControl.SelectedIndex = 0;
					throw new ArgumentException(String.Format(errMsg, this.Descrizione, this.ElementiLista, value));
				}
			}
		}

		public virtual string ElementiLista
		{
			get
			{
				if (InnerControl.Items.Count == 0) return String.Empty;

				string[] elementi = new string[InnerControl.Items.Count];

				for (int i = 0; i < InnerControl.Items.Count; i++)
				{
					if (String.IsNullOrEmpty(InnerControl.Items[i].Value.ToString()))
						elementi[i] = InnerControl.Items[i].Value.ToString();
				}

				return String.Join(";", elementi);
			}
			set
			{
				string[] elementi = value.Split(';');

				InnerControl.Items.Clear();

				for (int i = 0; i < elementi.Length; i++)
					InnerControl.Items.Add(new ListItem(elementi[i].Trim()));

				InnerControl.Items.Insert(0, new ListItem(""));
			}
		}


		internal DatiDinamiciMultiListBox()
		{
		}


		public DatiDinamiciMultiListBox(CampoDinamicoBase campo)
			: base(campo)
		{
		}

		public override void DataBind()
		{
			base.DataBind();
		}

		public bool Multiselezione
		{
			get { return InnerControl.SelectionMode == ListSelectionMode.Multiple; }
			set { InnerControl.SelectionMode = value ? ListSelectionMode.Multiple : ListSelectionMode.Single; }
		}

		public int RigheVisualizzate
		{
			get { return InnerControl.Rows; }
			set { InnerControl.Rows = value; }
		}

		public bool IgnoraErroriBinding
		{
			get { object o = this.ViewState["IgnoraErroriBinding"]; return o == null ? false : (bool)o; }
			set { this.ViewState["IgnoraErroriBinding"] = value; }
		}

		protected override void OnPreRender(EventArgs e)
		{
			if (IgnoraErroriBinding && !String.IsNullOrEmpty(m_bindValue))
			{
				string script = "var {0} = document.getElementById('{0}');{0}.bindValue = '{1}';";
				script = string.Format(script, this.InnerControl.ClientID, m_bindValue);
				Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID + "_safeBinding", script, true);
			}

			base.OnPreRender(e);
		}

		protected override string GetNomeEventoModifica()
		{
			return "click";
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2MultiListBox";
		}
	}
}
