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
	public partial class DatiDinamiciListBox : DatiDinamiciBaseControl<DropDownList>
	{
        public static class Constants
        {
            public const string NascondiValoriSuRiepilogo = "NascondiValoriSuRiepilogo";
        }

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
						new ProprietaDesigner("ElementiLista", "Elementi della lista separati da \";\",per specificare un valore diverso dal testo utilizzare \"$\"","") ,
						new ProprietaDesigner("IgnoraErroriBinding", "Ignora errori di binding" , TipoControlloEditEnum.ListBox , "No=false,Si=true","false"),
                        new ProprietaDesigner(Constants.NascondiValoriSuRiepilogo, "Nascondi lista valori su riepilogo", TipoControlloEditEnum.ListBox, "No=false,Si=true", "false"),
                         };

        }

        string _bindValue = null;

		public override string Valore
		{
			get
			{
				return this.InnerControl.SelectedValue;
			}
			set
			{
				bool elFound = false;

				for (int i = 0; i < this.InnerControl.Items.Count; i++)
				{
					if (this.InnerControl.Items[i].Value == value)
					{
						elFound = true;
						break;
					}
				}

				if (!elFound)
				{
					if (IgnoraErroriBinding)
					{
						_bindValue = value;
						return;
					}

					string errMsg = "Si è tentato di impostare un valore non valido al controllo \"{0}\". Valori possibili: \"{1}\". Valore impostato:\"{2}\"";
					this.InnerControl.SelectedIndex = 0;

					var listaValoriPossibili = String.Join(", ", this.InnerControl.Items.Cast<ListItem>().Select(x => x.Value));
					throw new ArgumentException(String.Format(errMsg, this.Descrizione, listaValoriPossibili, value));
				}

				this.InnerControl.SelectedValue = value;
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
					{
						string testo = InnerControl.Items[i].Text;
						string valore = InnerControl.Items[i].Value.ToString();

						if (testo != valore)
							elementi[i] = valore + "$" + testo;
						else
							elementi[i] = testo;
					}
				}

				return String.Join(";", elementi);
			}
			set
			{
				string[] elementi = value.Split(';');

				InnerControl.Items.Clear();

				for (int i = 0; i < elementi.Length; i++)
				{
					string testo = elementi[i].Trim();
					string valore = testo;

					if (testo.IndexOf("$") != -1)
					{
						string[] parts = testo.Split('$');
						testo = parts[1];
						valore = parts[0];
					}


					InnerControl.Items.Add(new ListItem(testo, valore));
				}

				InnerControl.Items.Insert(0, new ListItem(""));
			}
		}

		public override void DataBind()
		{
			base.DataBind();
		}


		internal DatiDinamiciListBox()
		{
			this.InnerControl.CausesValidation = false;
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
		}

		public DatiDinamiciListBox(CampoDinamicoBase campo):base( campo )
		{
			this.InnerControl.CausesValidation = false;
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
		}

		public bool IgnoraErroriBinding
		{
			get { object o = this.ViewState["IgnoraErroriBinding"]; return o == null ? false : (bool)o; }
			set { this.ViewState["IgnoraErroriBinding"] = value; }
		}

		protected override void OnPreRender(EventArgs e)
		{
			if (IgnoraErroriBinding && !String.IsNullOrEmpty(_bindValue))
			{
				string script = "var {0} = document.getElementById('{0}');{0}.bindValue = '{1}';";
				script = string.Format(script, this.InnerControl.ClientID, _bindValue);
				Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID + "_safeBinding", script, true);
			}

			base.OnPreRender(e);
		}

		protected override string GetNomeEventoModifica()
		{
			return "change";
		}

		protected override string  GetNomeTipoControllo()
		{
			return "d2ListBox";
		}
	
	}
}
