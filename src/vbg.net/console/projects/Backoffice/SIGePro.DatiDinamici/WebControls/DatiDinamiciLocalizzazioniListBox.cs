using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using Init.SIGePro.DatiDinamici.Statistiche;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public partial class DatiDinamiciLocalizzazioniListBox : DatiDinamiciListBox
	{
		public static new TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
		{
			return new TipoConfrontoFiltroEnum[] { 
							TipoConfrontoFiltroEnum.Equal, 
							TipoConfrontoFiltroEnum.NotEqual, 
							TipoConfrontoFiltroEnum.Null, 
							TipoConfrontoFiltroEnum.NotNull };
		}

		public static string HelpControllo()
		{
			return @"
Il controllo consente di riportare in una casella di selezione tutte le localizzazioni dell'istanza corrente.<br />
La proprietà <b>""Formato indirizzo""</b> specifica il formato in cui l'indirizzo deve essere visualizzato tramite segnaposto. <br />
I possibili valori dei segnaposto (che devono essere contenuti tra parentesi graffe) sono:
<ul>
<li><b>{indirizzo}</b>: prefisso + nome via</li>
<li><b>{civico}</b>: numero civico</li>
<li><b>{esponente}</b>: esponente</li>
<li><b>{scala}</b>: scala</li>
<li><b>{piano}</b>: piano</li>
<li><b>{interno}</b>: interno</li>
<li><b>{esponenteinterno}</b>: esponenteinterno</li>
<li><b>{km}</b>: km</li>
<li><b>{tipo}</b>: tipo localizzazione</li>
<li><b>{coordinate}</b>: longitudine e latitudine</li>
<li><b>{mappali}</b>: riferimenti catastali</li>
</ul>
";
		}

		/// <summary>
		/// Ritorna la lista di proprieta valorizzabili tramite la pagina di editing dei campi
		/// </summary>
		/// <returns>lista di proprieta valorizzabili tramite la pagina di editing dei campi</returns>
		public static new ProprietaDesigner[] GetProprietaDesigner()
		{
			return new ProprietaDesigner[]{
						new ProprietaDesigner("Obbligatorio","Obbligatorio",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
						new ProprietaDesigner("IgnoraObbligatorietaSuAttivita","Ignora obbligatorietà su schede attività",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
						new ProprietaDesigner("TipoLocalizzazione", "Tipo localizzazione",""),
						new ProprietaDesigner("FormatoIndirizzo", "Formato indirizzo","")};
		}

		#region gestione della query di selezione
		public string TipoLocalizzazione
		{
			get { object o = this.ViewState["TipoLocalizzazione"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["TipoLocalizzazione"] = value; }
		}

		public string FormatoIndirizzo
		{
			get { object o = this.ViewState["FormatoIndirizzo"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["FormatoIndirizzo"] = value; }
		}

		
		#endregion

		// Override solo per evitare che compaia nella lista delle proprietà
		public override string ElementiLista
		{
			get
			{
				return base.ElementiLista;
			}
			set
			{
				base.ElementiLista = value;
			}
		}

		public override string Valore
		{/*
			get { object o = this.ViewState["Valore"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Valore"] = value; }*/
			get { return InnerControl.SelectedValue; }
			set { InnerControl.SelectedValue = value; }
		}


		internal DatiDinamiciLocalizzazioniListBox()
		{
			InnerControl.SelectedIndexChanged += new EventHandler(InnerControl_SelectedIndexChanged);
		}

		public DatiDinamiciLocalizzazioniListBox(CampoDinamicoBase campo)
			: base(campo)
		{
			InnerControl.SelectedIndexChanged += new EventHandler(InnerControl_SelectedIndexChanged);
		}

		void InnerControl_SelectedIndexChanged(object sender, EventArgs e)
		{
			this.Valore = InnerControl.SelectedValue;
		}

		public override void DataBind()
		{
			var query = DataAccessProvider.GetQueryLocalizzazioni();

			var localizzazioni = query.Execute(TipoLocalizzazione, FormatoIndirizzo).ToList();
			localizzazioni.Insert(0, RiferimentiLocalizzazione.Vuota());

			InnerControl.DataTextField = "Descrizione";
			InnerControl.DataValueField = "Codice";
			InnerControl.DataSource = localizzazioni;
			InnerControl.DataBind();			
		}

	}
}
