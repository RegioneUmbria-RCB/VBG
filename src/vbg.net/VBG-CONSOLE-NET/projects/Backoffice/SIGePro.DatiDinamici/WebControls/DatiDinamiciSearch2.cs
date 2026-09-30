using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.DatiDinamici.Statistiche;
using System.Web;
using Init.SIGePro.DatiDinamici.Properties;


namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ControlValueProperty("Valore")]
	public class DatiDinamiciSearch2 : DatiDinamiciBaseControl<TextBox>
	{
		private static class Constants
		{
			public const string ClasseCssControllo = "d2Search";
			public const string NomeAttributoIdCampo = "idCampoDinamico";
			public const string NomeAttributoDescriptionBoxColumns = "data-columns";
			public const string NomeEventoModifica = "onchange";
			public const string ChiaveClientScript = "ChiaveClientScriptd2s";
            public const string DipendeDaAttribute = "data-dipende-da";
		}

		public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
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
<b>Campi della select</b><br />
Campi da utilizzare nell'espressione select della query, se un campo è il risultato di una funzione allora occorre assegnargli un alias.<br />
Es:<br />
<i>col1 || '-' || col2 as codice, col3 as descrizione</i><br />
<br />    
<b>Tabelle della select</b><br />
Nomi delle tabelle nelle quali effettuare le ricerche (separati da una virgola)<br />
<br />
<b>Condizioni di join</b><br />
Condizioni di join tra le tabelle elencate in <b>Tabelle della select</b><br />
<br />
<b>Condizioni where dipendenti da altri campi</b><br />
Condizioni di filtro che dipendono da altri campi, scritte nel formato CAMPO_DB1={ID_CAMPO_DINAMICO_1} and CAMPO_DB2={ID_CAMPO_DINAMICO_2} <br />
i nomi dei campi possono contenere solo lettere maiuscole e tratti bassi. Se il nome di un campo contiene altri caratteri questo verrà sostituito con un tratto basso<br />
<br />
<b>Nome campo valore</b><br />
Nome della colonna o alias dell'espressione presente in <b>Campi della select</b> da utilizzare per leggere il codice dell'elemento della lista<br />
Es.<br />
Se il valore di <b>Campi della select</b> è <i>col1 || '-' || col2 as codice, col3 as descrizione</i>
allora il valore del parametro dovrebbe essere <i>codice</i><br />
<br />
<b>Nome campo testo</b><br />
Nome della colonna o alias dell'espressione presente in <b>Campi della select</b> da utilizzare per leggere il testo dell'elemento della lista<br />        
Es.<br />
Se il valore di <b>Campi della select</b> è <i>col1 || '-' || col2 as codice, col3 as descrizione</i>
allora il valore del parametro dovrebbe essere <i>descrizione</i><br />
<br />
<b>Campo di ricerca del valore</b><br />
Nome della colonna o espressione sql da utilizzare come condizione di ricerca per ricercare il valore immesso dall'utente come codice<br />
<br />
<b>Campo di ricerca del valore</b><br />
Nome della colonna o espressione sql da utilizzare come condizione di ricerca per ricercare il valore immesso dall'utente come descrizione";
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
					new ProprietaDesigner("ValueBoxColumns", "Larghezza campo codice","6"),
					new ProprietaDesigner("DescriptionBoxColumns", "Larghezza campo descrizione","40"),
					new ProprietaDesigner("TipoRicerca","Tipo ricerca",TipoControlloEditEnum.ListBox,"Mostra tutti i risultati che contengono il testo ricercato=0,Mostra tutti i risultati che iniziano con il testo ricercato=1","0"),	
					new ProprietaDesigner("CampiSelect", "Campi della select",""),
					new ProprietaDesigner("TabelleSelect", "Tabelle della select",""),
					new ProprietaDesigner("CondizioniJoin", "Condizioni di join",""),
					new ProprietaDesigner("CondizioniWhere", "Condizioni where",""),
                    new ProprietaDesigner("CondizioniWhereAltriCampi", "Condizioni where dipendenti da altri campi",""),
					new ProprietaDesigner("NomeCampoValore", "Nome campo valore",""),
					new ProprietaDesigner("NomeCampoTesto", "Nome campo testo",""),
					new ProprietaDesigner("CampoRicercaCodice", "Campo di ricerca del valore",""),
					new ProprietaDesigner("CampoRicercaDescrizione", "Campo di ricerca della descrizione",""),
					new ProprietaDesigner("CompletionSetCount", "Numero massimo di righe ritornate","50")					
			};
		}

		public override string Valore
		{
			get
			{
				return InnerControl.Text;
			}
			set
			{
				InnerControl.Text = value;
			}
		}

        public string CondizioniWhereAltriCampi
        {
            get { object o = this.ViewState["CondizioniWhereAltriCampi"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CondizioniWhereAltriCampi"] = value; }
        }


		public string DescriptionBoxColumns
		{
			get { return InnerControl.Attributes[Constants.NomeAttributoDescriptionBoxColumns]; }
			set { InnerControl.Attributes[Constants.NomeAttributoDescriptionBoxColumns] = value; }
		}

		protected string Token
		{
			get { return HttpContext.Current.Items["Token"].ToString(); }
		}


		public DatiDinamiciSearch2()
			: base()
		{
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
		}

		protected override string GetNomeEventoModifica()
		{
			return "change";
		}

		protected override string GetNomeTipoControllo()
		{
			return "d2Search";
		}

		public DatiDinamiciSearch2(CampoDinamicoBase campo)
			: base(campo)
		{
			InnerControl.CausesValidation = false;
			this.RichiedeNotificaSuModificaValoreDecodificato = true;
		}

		protected override void OnPreRender(EventArgs e)
		{
			this.InnerControl.CssClass = Constants.ClasseCssControllo;
			this.InnerControl.Attributes.Add(Constants.NomeAttributoIdCampo, base.IdCampoCollegato.ToString());

            if (!String.IsNullOrEmpty(this.CondizioniWhereAltriCampi))
            {
                this.InnerControl.Attributes.Add(Constants.DipendeDaAttribute, new CondizioneWhereToNomiCampi(this.CondizioniWhereAltriCampi).GetListaNomiCampi());
            }


			base.OnPreRender(e);
		}

	}
}
