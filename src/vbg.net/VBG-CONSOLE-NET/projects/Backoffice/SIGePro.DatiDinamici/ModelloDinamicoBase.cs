using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using Init.SIGePro.DatiDinamici.Exceptions;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Properties;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.Utils;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;
using System.Text;
using log4net;

namespace Init.SIGePro.DatiDinamici
{
	public abstract class ModelloDinamicoBase
	{
		public class FlagsModello
		{
			public bool ReadOnlyWeb { get; private set; }
			public bool Storicizza { get; private set; }
			public bool ModelloMultiplo { get; private set; }

			public FlagsModello(bool readOnlyWeb, bool storicizza, bool modelloMultiplo)
			{
				this.ReadOnlyWeb = readOnlyWeb;
				this.Storicizza = storicizza;
				this.ModelloMultiplo = modelloMultiplo;
			}
		}

		public class ScriptsModello
		{
			public ScriptCampoDinamico ScriptCaricamento { get; private set; }
			public ScriptCampoDinamico ScriptSalvataggio { get; private set; }
			public ScriptCampoDinamico ScriptModifica { get; private set; }

			public ScriptsModello(ScriptCampoDinamico scriptCaricamento, ScriptCampoDinamico scriptSalvataggio, ScriptCampoDinamico scriptModifica)
			{
				this.ScriptCaricamento = scriptCaricamento;
				this.ScriptSalvataggio = scriptSalvataggio;
				this.ScriptModifica = scriptModifica; 
			}
		}

		public class StrutturaModello
		{
			internal readonly List<RigaModelloDinamico> RigheMultiple;
			internal readonly List<RigaModelloDinamico> Righe;
			internal readonly List<CampoDinamicoBase> Campi;
			internal readonly GruppoRigheCollection Gruppi;

			internal StrutturaModello(List<RigaModelloDinamico> righeMultiple, List<RigaModelloDinamico> righe, List<CampoDinamicoBase> campi, GruppoRigheCollection gruppi)
			{
				this.RigheMultiple = righeMultiple;
				this.Righe = righe;
				this.Gruppi = gruppi;
				this.Campi = campi;
			}
		}

		FlagsModello _flags;
		StrutturaModello _struttura;
		ContestoModelloDinamico _contesto;
		Dictionary<int, CampoDinamico> _campiModificati = new Dictionary<int, CampoDinamico>();
		List<ErroreValidazione> _erroriScript = new List<ErroreValidazione>();
		List<string> _debug = new List<string>();

		public IEnumerable<string> DebugMessages
		{
			get
			{
				return this._debug;
			}
		}

		/// <summary>
		/// Imposta o restutuisce l'id della versione storica del modello (se il modello è caricato da uno storico)
		/// </summary>
		protected int? IdVersioneStorico { get; private set; }

		/// <summary>
		/// Imposta o restutuisce il Token utilizzato per l'autenticazione
		/// </summary>
		internal string Token { get { return this._loader.Token; } }
		
		/// <summary>
		/// Imposta o restutuisce l'Alias del comune
		/// </summary>
		public string IdComune { get { return this._loader.Idcomune; } }

		/// <summary>
		/// Imposta o restutuisce l'Id del modello corrente
		/// </summary>
		public int IdModello { get; private set; }

		/// <summary>
		/// Imposta o restutuisce l'indice di molteplicità del modello (in caso di modello multiplo)
		/// </summary>
		public int IndiceModello { get; private set; }

		public string NomeModello
		{
			get
			{
				return this._loader.NomeModello;
			}
		}

		/// <summary>
		/// Imposta o restutuisce se il modello è un modello multiplo
		/// </summary>
		public bool ModelloMultiplo 
		{
			get
			{
				return this._flags.ModelloMultiplo;
			}
		}

		/// <summary>
		/// Imposta o restituisce se il modello è in modalità "sola lettura"
		/// </summary>
		protected bool ReadOnly{get;private set;}


		/// <summary>
		/// Lista dei campi contenuti nel modello
		/// </summary>
		public IEnumerable<CampoDinamicoBase> Campi
		{
			get
			{
				return this._struttura.Campi;
			}
		}

		/// <summary>
		/// Righe all'interno del modello
		/// </summary>
		public IEnumerable<RigaModelloDinamico> Righe
		{
			get { return this._struttura.Righe; }
		}

		/// <summary>
		/// Contesto del modello
		/// </summary>
		public ContestoModelloDinamico Contesto
		{
			get
			{
				EnsureContesto();
				return _contesto;
			}
			protected set { _contesto = value; }
		}


		/// <summary>
		/// Dictionary che contiene tutti i raggruppamenti presenti nel modello
		/// </summary>
		internal GruppoRigheCollection Gruppi { get { return this._struttura.Gruppi; } }

		/// <summary>
		/// Imposta o rilegge se la scheda viene utilizzata come modello per il frontoffice
		/// </summary>
		public bool ModelloFrontoffice { get; set; }

		/// <summary>
		/// Oggetto che fornisce l'accesso ai dati per il caricamento/salvataggio/aggiornamento
		/// dei campi
		/// </summary>
		internal IDyn2DataAccessProvider DataAccessProvider 
		{ 
			get 
			{ 
				return this._loader.DataAccessProvider; 
			} 
		}

        public bool SupportaScriptModifica
        {
            get { return this.ScriptModifica != null; }
        }


		public IModelloDinamicoLoader Loader
		{
			get { return this._loader; }
			set { this._loader = value; }
		}

		internal ScriptCampoDinamico ScriptCaricamento { get; private set; }
		internal ScriptCampoDinamico ScriptSalvataggio { get; private set; }
		internal ScriptCampoDinamico ScriptModifica { get; private set; }

		/// <summary>
		/// Lista dei campi che sono stati modificati dall'ultimo salvataggio
		/// </summary>
		public List<CampoDinamico> CampiModificati
		{
			get
			{
				List<CampoDinamico> ret = new List<CampoDinamico>();

				foreach (int key in _campiModificati.Keys)
					ret.Add(_campiModificati[key]);

				return ret;
			}
		}

		IModelloDinamicoLoader _loader;
        ILog _log = LogManager.GetLogger(typeof(ModelloDinamicoBase));

		/// <summary>
		/// Crea un'istanza della classe
		/// </summary>
		/// <param name="idComune">alias del comune</param>
		/// <param name="idModello">id del modello</param>
		/// <param name="indiceModello">indice della molteplicità del modello</param>
		/// <param name="readOnly">imposta se il modello deve essere in sola lettura</param>
		/// <param name="idVersioneStorico">Se impostato specifica quale versione dello storico deve essere visualizzata</param>
		/// <param name="dataAccessProvider">Oggetto che fornisce l'accesso dati</param>
		protected ModelloDinamicoBase(int idModello, int indiceModello, bool readOnly, int? idVersioneStorico, IModelloDinamicoLoader loader)
		{
            _log.InfoFormat("Caricamento scheda {0}, indice modello {1}", idModello, indiceModello);

			IdVersioneStorico = idVersioneStorico;
			IdModello = idModello;
			IndiceModello = indiceModello;
			ReadOnly = readOnly;

			this._loader = loader;
			this._loader.SetModello(this);
		}

		/// <summary>
		/// Effettua l'inizializzazone del modello.
		/// - Verifica che sia presente un contesto
		/// - Carica i dati del modello
		/// - Carica e organizza i campi del modello
		/// - Se il modello non è in sola lettura carica i valori dei campi
		/// - Se il modello proviene dallo storico carica i valori dallo storico
		/// </summary>
		protected void Inizializza()
		{
			using (var cp = new CodeProfiler("ModelloDinamicoBase.EnsureContesto"))
			{
				EnsureContesto();
			}

			using (var cp = new CodeProfiler("ModelloDinamicoBase.CaricaModello"))
			{
				this._flags = _loader.GetFlags();
				this.ModelloFrontoffice = _loader.GetModelloFrontoffice();

				if (!this.ReadOnly)
				{
					var scripts = _loader.GetScripts();

					this.ScriptCaricamento = scripts.ScriptCaricamento;
					this.ScriptModifica = scripts.ScriptModifica;
					this.ScriptSalvataggio = scripts.ScriptSalvataggio;
				}
			}

			using (var cp = new CodeProfiler("ModelloDinamicoBase.PrecaricaCampi"))
			{
				this._struttura = _loader.GetStrutturaModello();
			}

			if (!ReadOnly)
			{
				PopolaValoriCampi();
				AgganciaChangedHandler();
			}

			if (IdVersioneStorico.HasValue)
				PopolaValoriCampiStorico();

			this._visibilitaCampiService = new VisibilitaCampiService(this.Campi);

			if (!ReadOnly || IdVersioneStorico.HasValue)
			{
				foreach (var gruppo in Gruppi.GetRaggruppamenti())
					gruppo.VerificaConsistenzaMolteplicita();
			}

			if (ReadOnly)
			{
				SetReadOnly();
			}
		}


		/// <summary>
		/// Aggancia a tutti i campi dinamici del modello l'handler delle modifiche.
		/// L'handler delle modifiche viene invocato ogni volta che un campo del modello viene modificato,
		/// serve ad eseguire gli script di modifica dei campi e a tenere traccia dei campi che hanno subito modifiche
		/// </summary>
		private void AgganciaChangedHandler()
		{
			foreach (CampoDinamicoBase campo in Campi)
			{
				if (!(campo is CampoDinamico))
					continue;

				campo.ListaValori.ValoreModificato += delegate(ListaValoriDatiDinamici sender)
				{
					EseguiScriptModifica((CampoDinamico)sender.Campo);
				};
			}
		}
		
		///// <summary>
		///// 
		///// </summary>
		public bool ReadOnlyWeb 
		{ 
			get 
			{
				return _flags.ReadOnlyWeb;
				// return _modello.FlgReadonlyWeb.GetValueOrDefault(0) == 1; 
			} 
		}		
		
		/// <summary>
		/// Verifica che esista un contesto e nel caso in cui ancora non esista ne forza la creazione
		/// </summary>
		private void EnsureContesto()
		{
			if (_contesto == null)
				SetContesto();
		}

		/// <summary>
		/// Effettua la validazione formale del modello
		/// </summary>
		public void ValidaModello()
		{			
			var erroriValidazione =  Campi.Where(x => x is CampoDinamico).SelectMany(x => x.Valida());

			if (erroriValidazione.Count() > 0)
				throw new ValidazioneModelloDinamicoException(erroriValidazione);
		}

		/// <summary>
		/// Disabilita la storicizzazione del modello
		/// </summary>
		public void DisabilitaStoricizzazione()
		{
			var oldFlags = this._flags;
			this._flags = new FlagsModello(oldFlags.ReadOnlyWeb, false, oldFlags.ModelloMultiplo);
		}
		
		/// <summary>
		/// Effettua il salvataggio del modello eseguendo lo script di salvataggio.
		/// <remarks>Il salvataggio viene effettuato solamente se non si sono verificati errori negli script</remarks>
		/// </summary>
		public void Salva()
		{
			this.Salva(true);
		}

		/// <summary>
		/// Effettua il salvataggio del modello specificando se eseguire gli script di salvataggio.
		/// <remarks>Il salvataggio viene effettuato solamente se non si sono verificati errori negli script (nel caso in cui gli script vengano eseguiti)</remarks>
		/// </summary>
		/// <param name="eseguiScriptSalvataggio"></param>
		public void Salva(bool eseguiScriptSalvataggio)
		{
            this._log.InfoFormat("Salvataggio della scheda {0}", this.IdModello);

			using (var cp = new CodeProfiler("ModelloDinamicoBase.Salva"))
			{
				// Esegue gli script di salvataggio
				if (eseguiScriptSalvataggio)
					EseguiScriptSalvataggio();

				var salvaStorico = false;

				if (ErroriScript.Count() == 0)
				{
					try
					{
						// Se il modello non è in sola lettura, esistono campi modificati e il modello supporta la storicizzazione
						// Allora salvo lo storico dei valori allo stato attuale
						if (!ReadOnly && CampiModificati.Count > 0 && this._flags.Storicizza/*_modello.FlgStoricizza.GetValueOrDefault(0) == 1*/)
							salvaStorico = true;

						// Salva i valori nel db
						var campiDaSalvare = this.Campi.Where(c => c is CampoDinamico)
													   .Select(c => c as CampoDinamico);

						SalvaValoriCampi(salvaStorico, campiDaSalvare);

						SvuotaCampiModificati();
					}
					catch (Exception)
					{
						throw;
					}
                    finally
                    {
                        this._log.InfoFormat("Salvataggio della scheda {0} completato", this.IdModello);
                    }
				}
			}
		}

		/// <summary>
		/// Elimina un modello e tutti i relativi campi dalla base dati
		/// </summary>
		public void Elimina()
		{
			var campiDaEliminare = this.Campi.Where(c => c is CampoDinamico)
											 .Select(c => c as CampoDinamico);

			EliminaValoriCampi(campiDaEliminare);
		}


		/// <summary>
		/// Copia la struttura del modello da un modello di riferimento
		/// </summary>
		/// <param name="modelloRiferimento">Modello da usare come riferimento per la copia</param>
		public void CopiaDa(ModelloDinamicoBase modelloRiferimento)
		{
			foreach (CampoDinamicoBase campo in Campi)
			{
				CampoDinamicoBase rif = modelloRiferimento.TrovaCampoDaId(campo.Id);

				if (rif != null)
				{
					while (campo.ListaValori.Count < rif.ListaValori.Count)
						campo.ListaValori.IncrementaMolteplicita();

					for (int i = 0; i < rif.ListaValori.Count; i++)
					{
						campo.ListaValori[i].Valore = rif.ListaValori[i].Valore;
						campo.ListaValori[i].ValoreDecodificato = rif.ListaValori[i].ValoreDecodificato;
					}
				}
			}
		}

		/// <summary>
		/// Svuota la lista interna di campi modificati
		/// </summary>
		public void SvuotaCampiModificati()
		{
			_campiModificati.Clear();
		}

		/// <summary>
		/// Ricerca un campo in base al nome
		/// </summary>
		/// <param name="nomeCampo">nome del campo da cercare</param>
		/// <returns>Campo corrispondente al nome specificato o null se il campo non viene trovato</returns>
		public CampoDinamicoBase TrovaCampo(string nomeCampo)
		{
			return TrovaCampo(nomeCampo, false);
		}

		public CampoDinamicoBase TrovaCampo(string nomeCampo, bool ignoraEccezioni)
		{
			var campoTrovato = this.Campi.Where( x => x.NomeCampo == nomeCampo )
										 .FirstOrDefault();

			if (campoTrovato == null && !ignoraEccezioni)
				throw new Exception("Impossibile trovare il campo \"" + nomeCampo + "\" nel modello corrente");

			return campoTrovato;
		}

		/// <summary>
		/// Ricerca un campo in base all'id
		/// </summary>
		/// <param name="id">id del campo da cercare</param>
		/// <returns>Campo corrispondente all'id specificato o null se il campo non viene trovato</returns>
		public CampoDinamicoBase TrovaCampoDaId(int id)
		{
			// if (id < 0) return null;
			return this.Campi.Where(campo => campo.Id == id )
							 .FirstOrDefault();
		}

		/// <summary>
		/// Restituisce true se il modello contiene errori
		/// </summary>
		public bool HaErroriScript
		{
			get { return _erroriScript.Count > 0; }
		}

		/// <summary>
		/// Restituisce una lista contenente tutti gli errori del modello
		/// </summary>
		public IEnumerable<ErroreValidazione> ErroriScript
		{
			get
			{
				foreach(var errore in _erroriScript)
					yield return errore;

				foreach (var campo in this.Campi)
					foreach(var errore in campo.ErroriScript)
					yield return errore;
			}
		}

		#region gestione dei campi visibili / non visibili
		VisibilitaCampiService _visibilitaCampiService;
		SessioneModificaVisibilitaCampi _sessioneModificaVisibilitaCampi;
		private SessioneModificaVisibilitaCampi IniziaModificheVisibilita()
		{
			_sessioneModificaVisibilitaCampi = this._visibilitaCampiService.IniziaModificaVisibilita();

			return _sessioneModificaVisibilitaCampi;
		}
		
		/// <summary>
		/// Restituisce la lista degli id dei campi dinamici che sono stati resi visibili successivamente alla chiamata a IniziaModificheVisibilita
		/// La lista comprende sia campi dinamici che campi testuali
		/// </summary>
		public IEnumerable<IdValoreCampo> GetIdCampiVisibiliDopoModifiche()
		{
			return this._sessioneModificaVisibilitaCampi.GetCampiVisibili();
		}

		/// <summary>
		/// Restituisce la lista degli id dei campi dinamici che sono stati resi non visibili successivamente alla chiamata a IniziaModificheVisibilita
		/// La lista comprende sia campi dinamici che campi testuali
		/// </summary>
		public IEnumerable<IdValoreCampo> GetIdCampiNonVisibiliDopoModifiche()
		{
			return this._sessioneModificaVisibilitaCampi.GetCampiNonVisibili();
		}


		/// <summary>
		/// Restituisce la 
		/// </summary>
		public IEnumerable<IdValoreCampo> GetStatoVisibilitaCampi(bool campiVisibili)
		{
			return this._visibilitaCampiService.GetIdCampiByStatoVisibilita(campiVisibili ? StatoVisibilitaCampoEnum.Visibile : StatoVisibilitaCampoEnum.NonVisibile);
		}
		#endregion 

		/// <summary>
		/// Esegue lo script di caricamento sul modello e su tutti i campi in esso contenuti
		/// </summary>
		public void EseguiScriptCaricamento()
		{
            this._log.InfoFormat("Esecuzione script caricamento della scheda {0}", IdModello);

			System.Diagnostics.Debug.WriteLine("Esecuzione script caricamento");

			try
			{
				// Viene richiamato all'interno dello script solamente qui
				this.BeginFrame();

				foreach (CampoDinamicoBase campo in Campi)
					campo.EseguiScriptCaricamento();

				EseguiScript(this.ScriptCaricamento);
			}
			finally
			{
				// Viene richiamato all'interno dello script solamente qui
				this.EndFrame();
				System.Diagnostics.Debug.WriteLine("Fine script caricamento");
                this._log.InfoFormat("Fine esecuzione script caricamento della scheda {0}", IdModello);
			}
		}

		private void EseguiScript(ScriptCampoDinamico script, CampoDinamicoBase campoModificato = null)
		{
			_erroriScript.Clear();
			_debug.Clear();

			try
			{
				if (script != null)
					script.Esegui(campoModificato);
			}
			catch (TargetInvocationException ex)
			{
				_erroriScript.Add( new ErroreValidazione( ex.InnerException.Message, -1,-1));
			}
			catch (Exception ex)
			{
				_erroriScript.Add(new ErroreValidazione( ex.Message, -1, -1));
			}

			if (script != null)
			{
				var erroriValidazione = script.GetErroriValidazione();

				foreach (var errore in erroriValidazione)
				{
					if(!_erroriScript.Contains( errore ))
						_erroriScript.Add(errore);
				}

			}
		}

		/// <summary>
		/// Esegue lo script di salvataggio sul modello e su tutti i campi in esso contenuti
		/// </summary>
		public void EseguiScriptSalvataggio()
		{
            this._log.InfoFormat("Esecuzione script salvataggio della scheda {0}", IdModello);
			try
			{
				System.Diagnostics.Debug.WriteLine("Esecuzione script salvataggio");

				foreach (CampoDinamicoBase campo in Campi)
					campo.EseguiScriptSalvataggio();

				this.EseguiScript(this.ScriptSalvataggio);
			}
			finally
			{
				System.Diagnostics.Debug.WriteLine("Fine esecuzione script salvataggio");
                this._log.InfoFormat("Fine esecuzione script salvataggio della scheda {0}", IdModello);
			}
		}

		private void EseguiScriptModifica(CampoDinamico campo)
		{
            this._log.InfoFormat("Esecuzione script modifica della scheda {0}, campo {1}", IdModello, campo == null ? String.Empty : campo.NomeCampo);
			System.Diagnostics.Debug.WriteLine("Esecuzione script modifica per il campo " + campo.NomeCampo);

			try
			{
				if (campo == null)
					return;

				if (!_campiModificati.ContainsKey(campo.Id))
					_campiModificati.Add(campo.Id, campo);

//				this.BeginFrame();

				try
				{
					campo.EseguiScriptModifica();
				}
				catch (Exception ex)
				{
					_erroriScript.Add(new ErroreValidazione(ex.Message));
				}

				EseguiScript(this.ScriptModifica, campo);
			}
			finally
			{
//				this.EndFrame();

				System.Diagnostics.Debug.WriteLine("Fine esecuzione script modifica" + campo.NomeCampo);
                this._log.InfoFormat("Fine script modifica della scheda {0}, campo {1}", IdModello, campo == null ? String.Empty : campo.NomeCampo);
			}
		}


		/// <summary>
		/// Imposta il modello in modalità sola lettura
		/// </summary>
		internal void SetReadOnly()
		{
			this.ScriptCaricamento = null;
			this.ScriptSalvataggio = null;

			foreach (CampoDinamicoBase campo in Campi)
				campo.DisabilitaScript();
		}


		#region metodi astratti
		/// <summary>
		/// Imposta i valori relativi al contesto attuale
		/// </summary>
		protected abstract void SetContesto();

		/// <summary>
		/// Popola i valori dei campi con i dati letti dal db
		/// </summary>
		protected abstract void PopolaValoriCampi();


		/// <summary>
		/// Popola i valori dei campi con i dati letti dallo storico
		/// </summary>
		protected abstract void PopolaValoriCampiStorico();

		/// <summary>
		/// Salva il valore ddei campi del modello
		/// </summary>
		/// <param name="campo"></param>
		protected abstract void SalvaValoriCampi(bool salvaStorico, IEnumerable<CampoDinamico> campiDaSalvare);

		/// <summary>
		/// Elimina i valori dei campi dal database
		/// </summary>
		/// <param name="campiDaSalvare"></param>
		protected abstract void EliminaValoriCampi(IEnumerable<CampoDinamico> campiDaEliminare);

		#endregion


		internal void BeginFrame()
		{
			System.Diagnostics.Debug.WriteLine("ModelloDinamicoBase->BeginFrame");

			var sessioneModificaVisibilita = IniziaModificheVisibilita();
			var scripts = GetScriptsPresenti();

			foreach(var s in scripts)
				s.PreExecute(sessioneModificaVisibilita);
		}

		internal void EndFrame()
		{
			System.Diagnostics.Debug.WriteLine("ModelloDinamicoBase->EndFrame");

			var scripts = GetScriptsPresenti();

			foreach (var s in scripts)
				s.PostExecute();
		}

		private IEnumerable<ScriptCampoDinamico> GetScriptsPresenti()
		{
			if (this.ScriptCaricamento != null)
				yield return this.ScriptCaricamento;

			if (this.ScriptModifica != null)
				yield return this.ScriptModifica;

			if (this.ScriptSalvataggio != null)
				yield return this.ScriptSalvataggio;
		}

		public void Debug(string message)
		{
			this._debug.Add(message);
			System.Diagnostics.Debug.WriteLine(message);
		}

		public void Debug(CampoDinamicoBase campo)
		{
			var sb = new StringBuilder();

			sb.AppendFormat("Campo {0} ({1})\r\n", campo.Id, campo.NomeCampo);

			for(var i = 0; i < campo.ListaValori.Count; i++ )
			{
				sb.AppendFormat("valore {0}: \"{1}\" (\"{2}\")\r\n", i, campo.ListaValori[i].Valore, campo.ListaValori[i].ValoreDecodificato);
			}

			Debug(sb.ToString());
		}
	}
}
