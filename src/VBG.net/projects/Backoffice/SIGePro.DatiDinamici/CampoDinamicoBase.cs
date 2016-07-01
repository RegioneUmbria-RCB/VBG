using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;
using Init.SIGePro.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici
{
	public class CampoDinamicoBase
	{
		/// <summary>
		/// Etichetta del campo, ad esempio "Superficie totale:"
		/// </summary>
		public string Etichetta { get; set; }

		/// <summary>
		/// Descrizione estesa del campo
		/// </summary>
		public string Descrizione { get; protected set; }

		/// <summary>
		/// Id univoco del campo nel database
		/// </summary>
		public int Id { get; protected set; }

		/// <summary>
		/// Nome del campo all'interno del form
		/// </summary>
		public string NomeCampo { get; protected set; }

		/// <summary>
		/// Se impostato a true sarà obbligatorio compilare il campo
		/// </summary>
		public bool Obbligatorio { get; protected set; }

		public bool EtichettaADestra { get; protected set; }

		/// <summary>
		/// Se impostato a true il campo non sarà obbligatorio nelle schede delle attività, anche se il flag obbligatorio è impostato a true
		/// </summary>
		public bool IgnoraObbligatorietaSuAttivita { get; protected set; }
		
		public TipoControlloEnum TipoCampo { get; protected set; }

		/// <summary>
		/// Lista di proprietà da assegnare al controllo che renderizzerà il campo
		/// </summary>
		public List<KeyValuePair<string, string>> ProprietaControlloWeb { get; protected set; }

		public ListaValoriDatiDinamici ListaValori { get; private set; }

		internal List<ErroreValidazione> ErroriScript { get; private set; }

		internal IEnumerable<ErroreValidazione> ErroriValidazioneDaScript { get; private set; }

		public int PosizioneOrizzontale { get; protected set; }

		public int PosizioneVericale { get; protected set; }

		public string ClientScript { get; set; }

		public double? ValidationMinValue { get; protected set; }

		public double? ValidationMaxValue { get; protected set; }

		public string EspressioneRegolare { get; protected set; }

		public bool TipoNumerico { get; set; }
		
		/// <summary>
		/// Riferimento al modello in cui il campo è contenuto
		/// </summary>
		public ModelloDinamicoBase ModelloCorrente { get; protected set; }

		/// <summary>
		/// Script da eseguire all'aggiornamento del modello
		/// </summary>
		public ScriptCampoDinamico ScriptCaricamento { get; set; }

		/// <summary>
		/// Script da eseguire al variare del valore utente
		/// </summary>
		public ScriptCampoDinamico ScriptModifica { get; set; }

		/// <summary>
		/// Script da eseguire all'inserimento del valore utente
		/// </summary>
		public ScriptCampoDinamico ScriptSalvataggio { get; set; }

		public bool HaIntestazione
		{
			get { return this.TipoCampo != TipoControlloEnum.Titolo && this.TipoCampo != TipoControlloEnum.Label; }
		}

		/// <summary>
		/// Valore del campo immesso dall'utente
		/// </summary>
		/// <remarks>La proprietà è obsoleta, utilizzare ListaValori[x].Valore</remarks>
		[Obsolete("La proprietà è obsoleta, utilizzare ListaValori[x].Valore", false)]
		public string Valore
		{
			get
			{
				if (this.ListaValori.Count == 0)
				{
					this.ListaValori.IncrementaMolteplicita();
				}

				if (this.ListaValori.Count > 1)
				{
					throw new Exception("Impossibile utilizzare la proprietà VALORE perchè il campo contiene valori multipli");
				}

				return this.ListaValori[0].Valore;
			}

			set
			{
				if (this.ListaValori.Count == 0)
				{
					this.ListaValori.IncrementaMolteplicita();
				}

				if (this.ListaValori.Count > 1)
				{
					throw new Exception("Impossibile utilizzare la proprietà VALORE perchè il campo contiene valori multipli");
				}

				this.ListaValori[0].Valore = value;
			}
		}

		protected CampoDinamicoBase(ModelloDinamicoBase modello)
		{
			this.ErroriScript = new List<ErroreValidazione>();
			this.ModelloCorrente = modello;
			this.Obbligatorio = false;
			this.ClientScript = string.Empty;
			this.PosizioneOrizzontale = -1;
			this.PosizioneVericale = -1;
			this.ProprietaControlloWeb = new List<KeyValuePair<string, string>>();

			this.ListaValori = new ListaValoriDatiDinamici(this);
		}

		/// <summary>
		/// Contiene il valore decodificato del campo.
		/// Data: mostra la data nel formato dd/MM/yyyy al posto della data invertita.
		/// Lista valori: mostra il testo associato al valore selezionato.
		///	Lista valori sigepro: mostra il testo associato al valore selezionato.
		/// Ricerca sigepro: mostra il testo corrispondente al valore selezionato.
		/// Tutti gli altri campi: il valore decodificato è == al valore.
		/// </summary>
		/// <remarks>La proprietà è obsoleta, utilizzare ListaValori[x].ValoreDecodificato</remarks>
		[Obsolete("La proprietà è obsoleta, utilizzare ListaValori[x].ValoreDecodificato", false)]
		public string ValoreDecodificato
		{
			get
			{
				if (this.ListaValori.Count == 0)
				{
					this.ListaValori.IncrementaMolteplicita();
				}

				if (this.ListaValori.Count > 1)
				{
					throw new Exception("Impossibile utilizzare la proprietà VALOREDECODIFICATO perchè il campo contiene valori multipli");
				}

				return this.ListaValori[0].ValoreDecodificato;
			}

			set
			{
				if (this.ListaValori.Count == 0)
				{
					this.ListaValori.IncrementaMolteplicita();
				}

				if (this.ListaValori.Count > 1)
				{
					throw new Exception("Impossibile utilizzare la proprietà VALOREDECODIFICATO perchè il campo contiene valori multipli");
				}

				this.ListaValori[0].ValoreDecodificato = value;
			}
		}

		internal void EseguiScriptModifica()
		{
			this.EseguiScript(this.ScriptModifica);
		}

		internal void EseguiScriptCaricamento()
		{
			this.EseguiScript(this.ScriptCaricamento);
		}

		internal void EseguiScriptSalvataggio()
		{
			this.EseguiScript(this.ScriptSalvataggio);
		}

		private void EseguiScript(ScriptCampoDinamico script)
		{
			this.ErroriScript.Clear();
			this.ErroriValidazioneDaScript = new List<ErroreValidazione>();

			try
			{
				if (script != null)
				{
					script.Esegui(null);
				}
			}
			catch (TargetInvocationException ex)
			{
				this.ErroriScript.Add(new ErroreValidazione(ex.InnerException.Message, -1, -1));
			}
			catch (Exception ex)
			{
				this.ErroriScript.Add(new ErroreValidazione(ex.Message, -1, -1));
			}

			if (script != null)
			{
				this.ErroriValidazioneDaScript = script.GetErroriValidazione();
			}
		}

		/// <summary>
		/// Disabilita tutti gli script del campo
		/// utilizzato quando il modello a cui appartiene è in sola lettura
		/// </summary>
		internal void DisabilitaScript()
		{
			this.ScriptCaricamento = null;
			this.ScriptSalvataggio = null;
			this.ScriptModifica = null;
		}

		/// <summary>
		/// Restituisce il valore del campo, se il valore è null restituisce il valore specificato nel parametro valoreDefault
		/// </summary>
		/// <typeparam name="T">Tipo in cui deve essere convertito il valore</typeparam>
		/// <param name="valoreDefault">Valore da utilizzare se il valore del campo è null</param>
		/// <returns>Valore del campo o valoreDefault se il campo è null</returns>
		/// <remarks>ATTENZIONE: Il metodo è obsoleto, utilizzare ListaValori[x].GetValoreODefault. La conversione utilizza internamente Convert.ChangeType, verificare che una conversione sia possibile.</remarks>
		[Obsolete("La proprietà è obsoleta, utilizzare ListaValori[x].GetValoreODefault", false)]
		public T GetValoreODefault<T>(T valoreDefault)
		{
			if (this.ListaValori.Count == 0)
			{
				this.ListaValori.IncrementaMolteplicita();
			}

			if (this.ListaValori.Count > 1)
			{
				throw new Exception("Impossibile utilizzare il metodo GetValoreODefault<T> perchè il campo contiene valori multipli");
			}

			return this.ListaValori.GetValoreODefault<T>(0, valoreDefault);
		}

		/// <summary>
		/// Effettua la validazione dei valori del campo
		/// </summary>
		/// <returns>Lista di errori di validazione del campo</returns>
		public virtual IEnumerable<ErroreValidazione> Valida() 
		{ 
			return Enumerable.Empty<ErroreValidazione>(); 
		}

		protected void ImpostaProprietaRiservate(string key, string val)
		{
			if (key == "Obbligatorio")
			{
				this.Obbligatorio = Convert.ToBoolean(val);
				return;
			}
			else if (key == "IgnoraObbligatorietaSuAttivita")
			{
				this.IgnoraObbligatorietaSuAttivita = Convert.ToBoolean(val);
				return;
			}
			else if (key == "ValidationMinValue")
			{
				this.ValidationMinValue = Convert.ToDouble(val);
				return;
			}
			else if (key == "ValidationMaxValue")
			{
				this.ValidationMaxValue = Convert.ToDouble(val);
				return;
			}
			else if (key == "EspressioneRegolare")
			{
				this.EspressioneRegolare = val;
				return;
			}
			else if (key == "AutoPostBack")
			{
				// Hack, ci potrebbero essere campi con il vecchi autopostback a true
				return;
			}
			else if (key == "TipoNumerico")
			{
				this.TipoNumerico = Convert.ToBoolean(val);
				return;
			}
			else if (key == "EtichettaADestra")
			{
				this.EtichettaADestra = Convert.ToBoolean(val);
				return;
			}
		}

	}
}
