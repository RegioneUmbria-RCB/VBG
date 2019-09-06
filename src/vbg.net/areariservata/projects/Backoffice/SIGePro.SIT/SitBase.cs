using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Collection;
using PersonalLib2.Data;
using System.Data;
using log4net;
using Init.SIGePro.Sit.ValidazioneFormale;
using CuttingEdge.Conditions;
using Init.Utils;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Errors;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Manager.DTO;

namespace Init.SIGePro.Sit
{
	public abstract class SitBase : Init.SIGePro.Sit.ISitApi
	{
		ILog _log = LogManager.GetLogger(typeof(SitBase));
		IValidazioneFormaleService _servizioValidazioneFormale;

		/// <summary>
		/// Connessione al database utilizzata internamente
		/// </summary>
		protected DataBase InternalDatabaseConnection;

		#region Proprietà

		private ModificaStradario _modificaStradario = ModificaStradario.Indirizzo;

		public Init.SIGePro.Sit.Data.Sit DataSit { get; set; }

		private string _codiceComune;
		public string CodiceComune
		{
			get
			{
				if (string.IsNullOrEmpty(_codiceComune))
				{
					if (!string.IsNullOrEmpty(IdComune))
					{
						var comuniAssociatiManager = new ComuniAssociatiMgr(Database);

						var filtro = new ComuniAssociati
						{
							IDCOMUNE = IdComune
						};

						var list = comuniAssociatiManager.GetList(filtro);

						if (list.Count != 0)
						{
							_codiceComune = list[0].CODICECOMUNE;
							return _codiceComune;
						}

						return string.Empty;
					}
					else
						return string.Empty;
				}
				else
					return _codiceComune;
			}
			set
			{
				_codiceComune = value;
			}
		}

		protected string IdComune { get; private set; }
        protected string IdComuneAlias { get; private set; }
		protected string Software { get; private set; }
		protected DataBase Database { get; private set; }
		#endregion

		internal SitBase(IValidazioneFormaleService servizioValidazioneFormale)
		{
			Condition.Requires(servizioValidazioneFormale, "servizioValidazioneFormale").IsNotNull();

			this._servizioValidazioneFormale = servizioValidazioneFormale;
		}

		#region Inizializzazione dei parametri di Sigepro
		public void InizializzaParametriSigepro( string idcomune , string idComuneAlias, string software , DataBase database)
		{
			this.IdComune = idcomune;
            this.IdComuneAlias = idComuneAlias;
			this.Software = software;
			this.Database = database;
		}
		#endregion

		#region Gestione dei messaggi di errore

		internal RetSit RestituisciErroreSit(string messaggioErrore, MessageCode codiceErrore, bool esito)
		{
			return RetSit.Errore(codiceErrore, messaggioErrore, esito);
		}

		internal RetSit RestituisciErroreSit(MessageCode messageCode, bool success)
		{
			return new ErrorMessage(messageCode).ToRetSit(success);
		}

		#endregion

		#region Utility

		protected bool IsCampiToponomasticaImmobileVuoti()
		{
			return string.IsNullOrEmpty(DataSit.Civico) &&
					string.IsNullOrEmpty(DataSit.Esponente) &&
					string.IsNullOrEmpty(DataSit.Interno) &&
					string.IsNullOrEmpty(DataSit.Scala) &&
					string.IsNullOrEmpty(DataSit.EsponenteInterno) &&
					string.IsNullOrEmpty(DataSit.CodCivico) &&
					string.IsNullOrEmpty(DataSit.Fabbricato) &&
					string.IsNullOrEmpty(DataSit.UI);
		}

		/// <summary>
		/// Verifica che la connessione interna al database sia aperta. In caso contrario la apre
		/// </summary>
		protected void EnsureConnectionIsOpen()
		{
			if (InternalDatabaseConnection.Connection.State == ConnectionState.Closed)
				InternalDatabaseConnection.Connection.Open();
		}

		protected void CloseInternalConnection()
		{
			if (InternalDatabaseConnection.Connection.State != ConnectionState.Closed)
				InternalDatabaseConnection.Connection.Close();
		}

		#endregion

		#region Metodi per la gestione delle stringhe
		/// <summary>
		/// Converte una stringa utilizzabile come parametro di una query in una stringa sql-safe (x es sostituisce l'apice singolo con un apice doppio)
		/// </summary>
		/// <param name="value">valore da convertire in stringa sql-safe</param>
		/// <returns>valore convertito in stringa sql-safe</returns>
		protected string ToSqlSafeString(string value)
		{
			return value.Replace("'", "''");
		}

		/// <summary>
		/// Rimuove tutte le occorrenze di spazi all'inizio della stringa passata come "value", inoltre converte la stringa in stringa sql-safe.
		/// NOTE: Il trim inizia a partire dall'inizio della stringa
		/// </summary>
		/// <param name="value">stringa a cui vanno sostituiti i caratteri iniziali</param>
		/// <returns></returns>
		/// <remarks>Il trim inizia a partire dall'inizio della stringa</remarks>
		protected string LeftTrim(string value)
		{
			return LeftTrim(value, null);
		}

		/// <summary>
		/// Rimuove tutte le occorrenze di spazi e di tutti i caratteri specificati nel parametro "trimChars" 
		/// allinizio della stringa passata come "value", inoltre converte la stringa in stringa sql-safe.
		/// NOTE: Il trim inizia a partire dall'inizio della stringa, vengono comunque rimossi tutti gli spazi iniziali
		/// </summary>
		/// <param name="value">stringa a cui vanno sostituiti i caratteri iniziali</param>
		/// <param name="trimChars">caratteri da eliminare, se null verranno comunque eliminati tutti gli spazi</param>
		/// <returns></returns>
		/// <remarks>Il trim inizia a partire dall'inizio della stringa, vengono comunque rimossi tutti gli spazi iniziali</remarks>
		protected string LeftTrim(string value, char[] charsToTrim)
		{
			if (charsToTrim == null)
				return (string.IsNullOrEmpty(value) ? value : value.TrimStart());
			else
				return (string.IsNullOrEmpty(value) ? value : value.TrimStart(charsToTrim));
		}



		/// <summary>
		/// Rimuove tutte le occorrenze di spazi alla fine della stringa passata come "value", inoltre converte la stringa in stringa sql-safe.
		/// NOTE: Il trim inizia a partire dalla fine della stringa
		/// </summary>
		/// <param name="value">stringa a cui vanno sostituiti i caratteri finali</param>
		/// <returns></returns>
		/// <remarks>Il trim inizia a partire dalla fine della stringa</remarks>
		protected string RightTrim(string value)
		{
			return RightTrim(value, null);
		}

		/// <summary>
		/// Rimuove tutte le occorrenze di spazi e di tutti i caratteri specificati nel parametro "trimChars" 
		/// nella stringa passata come "value". Inoltre converte la stringa in stringa sql-safe.
		/// NOTE: Il trim inizia a partire dalla fine della stringa, vengono comunque rimossi tutti gli spazi finali
		/// </summary>
		/// <param name="value">stringa a cui vanno sostituiti i caratteri finali</param>
		/// <param name="trimChars">caratteri da eliminare, se null verranno comunque eliminati tutti gli spazi</param>
		/// <returns></returns>
		/// <remarks>Il trim inizia a partire dalla fine della stringa, vengono comunque rimossi tutti gli spazi finali</remarks>
		protected string RightTrim(string value, char[] trimChars)
		{
			if (trimChars == null)
				return (string.IsNullOrEmpty(value) ? String.Empty : ToSqlSafeString(value.TrimEnd()));
			else
				return (string.IsNullOrEmpty(value) ? String.Empty : ToSqlSafeString(value.TrimEnd(trimChars)));
		}



		/// <summary>
		/// Effettua il padding a sinistra della stringa passata come value
		/// </summary>
		/// <param name="value">Valore di cui effettuare il padding</param>
		/// <param name="totalWidth">Lunghezza totale della stringa da raggiungere tramite padding</param>
		/// <param name="paddingChar">Carattere da utilizzare per il padding</param>
		/// <returns>Stringa a cui è stato applicato il padding o valore originale se la lunghezza era maggiore di totalWidth</returns>
		protected string LeftPad(string value, int totalWidth, char paddingChar)
		{
			return (string.IsNullOrEmpty(value) ? String.Empty : value.PadLeft(totalWidth, paddingChar));
		}

		/// <summary>
		/// Effettua il padding a sinistra della stringa passata come value utilizzando il carattere '0'
		/// </summary>
		/// <param name="value">Valore di cui effettuare il padding</param>
		/// <param name="totalWidth">Lunghezza totale della stringa da raggiungere tramite padding</param>
		/// <returns>Stringa a cui è stato applicato il padding o valore originale se la lunghezza era maggiore di totalWidth</returns>
		protected string LeftPad(string value, int totalWidth)
		{
			return LeftPad(value, totalWidth, '0');
		}
		#endregion

		#region Metodi di validazione
		public RetSit CodiceViaValidazione()
		{
			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;

				if (!String.IsNullOrEmpty(DataSit.CodVia))
				{
					var rVal =  VerificaCodiceVia();

					if (rVal.ReturnValue)
						GetSit(DataSit);

					return rVal;
				}

				return new RetSit(true);

			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del codice viario " + DataSit.CodVia + ". Metodo: CodiceViaValidazione, modulo: SitMgr. " + ex.Message);
			}
		}


		public virtual RetSit CivicoValidazione()
		{
			if( String.IsNullOrEmpty(DataSit.Civico) )
				return new RetSit(true);

			try
			{
				var rval = VerificaCivico();

				if (rval.ReturnValue)
					GetSit(DataSit);

				return rval;
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del civico " + DataSit.Civico + ". Metodo: CivicoValidazione, modulo: SitMgr. " + ex.Message, ex);
			}

			/*
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Civico))
				{
					pRetSit = VerificaCivico();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del civico " + DataSit.Civico + ". Metodo: CivicoValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;*/
		}

		public RetSit KmValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Km))
				{
					pRetSit = VerificaKm();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del km " + DataSit.Km + ". Metodo: KmValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public virtual RetSit EsponenteValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Esponente))
				{
                    // Per validare l'esponente ignoro i dati di cod civico, scala, piano, interno, esp.int, fabbricato, cap, circoscrizione, sezione, foglio, particella, sub
                    this.DataSit.CodCivico = String.Empty;
                    this.DataSit.Scala = String.Empty;
                    this.DataSit.Piano = String.Empty;
                    this.DataSit.Interno = String.Empty;
                    this.DataSit.EsponenteInterno = String.Empty;
                    this.DataSit.Fabbricato = String.Empty;
                    this.DataSit.CAP = String.Empty;
                    this.DataSit.Circoscrizione = String.Empty;
                    this.DataSit.TipoCatasto = "F"; // Ho un esponente...
                    this.DataSit.Sezione = String.Empty;
                    this.DataSit.Foglio = String.Empty;
                    this.DataSit.Particella = String.Empty;
                    this.DataSit.Sub = String.Empty;

					pRetSit = VerificaEsponente();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione dell'esponente " + DataSit.Esponente + ". Metodo: EsponenteValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

        public virtual RetSit AccessoTipoValidazione()
        {
            return null;
        }

		public RetSit ColoreValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Colore))
				{
					pRetSit = VerificaColore();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del colore " + DataSit.Colore + ". Metodo: ColoreValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit ScalaValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Scala))
				{
					pRetSit = VerificaScala();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione della scala " + DataSit.Scala + ". Metodo: ScalaValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit InternoValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Interno))
				{
					pRetSit = VerificaInterno();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione dell' interno " + DataSit.Interno + ". Metodo: InternoValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit EsponenteInternoValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.EsponenteInterno))
				{
					pRetSit = VerificaEsponenteInterno();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione dell'esponente interno " + DataSit.EsponenteInterno + ". Metodo: EsponenteInternoValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit CAPValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.CAP))
				{
					pRetSit = VerificaCAP();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del CAP " + DataSit.CAP + ". Metodo: CAPValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit FrazioneValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Frazione))
				{
					pRetSit = VerificaFrazione();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione della frazione " + DataSit.Frazione + ". Metodo: FrazioneValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit CircoscrizioneValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Circoscrizione))
				{
					pRetSit = VerificaCircoscrizione();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione della circoscrizione " + DataSit.Circoscrizione + ". Metodo: CircoscrizioneValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit FabbricatoValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.Fabbricato))
				{
					pRetSit = VerificaFabbricato();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del fabbricato " + DataSit.Fabbricato + ". Metodo: FabbricatoValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		private void GetSit(Init.SIGePro.Sit.Data.Sit pSit)
		{
			if (_modificaStradario == ModificaStradario.Indirizzo)
			{
				GetIndirizzo(pSit);
				GetCatasto(pSit);
			}
			if (_modificaStradario == ModificaStradario.Catasto)
			{
				GetCatasto(pSit);
				GetIndirizzo(pSit);
			}
		}

		private void GetIndirizzo(Init.SIGePro.Sit.Data.Sit pSit)
		{
			if (string.IsNullOrEmpty(pSit.CodVia))
				pSit.CodVia = GetCodVia();

			if (string.IsNullOrEmpty(pSit.Civico))
				pSit.Civico = GetCivico();

			if (string.IsNullOrEmpty(pSit.Km))
				pSit.Km = GetKm();

			if (string.IsNullOrEmpty(pSit.Esponente))
				pSit.Esponente = GetEsponente();

			if (string.IsNullOrEmpty(pSit.Colore))
				pSit.Colore = GetColore();

			//if (string.IsNullOrEmpty(pSit.CodCivico))
				pSit.CodCivico = GetCodCivico();

			if (string.IsNullOrEmpty(pSit.Scala))
				pSit.Scala = GetScala();

			if (string.IsNullOrEmpty(pSit.Interno))
				pSit.Interno = GetInterno();

			if (string.IsNullOrEmpty(pSit.EsponenteInterno))
				pSit.EsponenteInterno = GetEsponenteInterno();

			if (string.IsNullOrEmpty(pSit.Fabbricato))
				pSit.Fabbricato = GetCodFabbricato();

			if (string.IsNullOrEmpty(pSit.UI))
				pSit.UI = GetUI();

			if (string.IsNullOrEmpty(pSit.CAP))
				pSit.CAP = GetCAP();

			if (string.IsNullOrEmpty(pSit.Frazione))
				pSit.Frazione = GetFrazione();

			if (string.IsNullOrEmpty(pSit.Circoscrizione))
				pSit.Circoscrizione = GetCircoscrizione();
		}

		private void GetCatasto(Init.SIGePro.Sit.Data.Sit pSit)
		{
			if (string.IsNullOrEmpty(pSit.TipoCatasto))
				pSit.TipoCatasto = GetTipoCatasto();

			if (string.IsNullOrEmpty(pSit.Sezione))
				pSit.Sezione = GetSezione();

			if (string.IsNullOrEmpty(pSit.Foglio))
				pSit.Foglio = GetFoglio();

			if (string.IsNullOrEmpty(pSit.Particella))
				pSit.Particella = GetParticella();

			if (string.IsNullOrEmpty(pSit.Fabbricato))
				pSit.Fabbricato = GetCodFabbricato();

			if (string.IsNullOrEmpty(pSit.Sub))
				pSit.Sub = GetSub();

			if (string.IsNullOrEmpty(pSit.UI))
				pSit.UI = GetUI();
		}

		public RetSit SezioneValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Catasto;
				if (!String.IsNullOrEmpty(DataSit.Sezione))
				{
					pRetSit = VerificaSezione();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione della sezione " + DataSit.Sezione + ". Metodo: SezioneValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit TipoCatastoValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Catasto;
				if (!String.IsNullOrEmpty(DataSit.TipoCatasto))
				{
					pRetSit = VerificaTipoCatasto();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del catasto " + DataSit.TipoCatasto + ". Metodo: TipoCatastoValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit FoglioValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Catasto;
				if (!String.IsNullOrEmpty(DataSit.Foglio))
				{
					pRetSit = VerificaFoglio();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del foglio " + DataSit.Foglio + ". Metodo: FoglioValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit ParticellaValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Catasto;
				if (!String.IsNullOrEmpty(DataSit.Particella))
				{
					pRetSit = VerificaParticella();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione della particella " + DataSit.Particella + ". Metodo: ParticellaValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit SubValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Catasto;
				if (!String.IsNullOrEmpty(DataSit.Sub))
				{
					pRetSit = VerificaSub();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione del sub " + DataSit.Sub + ". Metodo: SubValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit UIValidazione()
		{
			RetSit pRetSit = null;

			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;
				if (!String.IsNullOrEmpty(DataSit.UI))
				{
					pRetSit = VerificaUI();

					if (pRetSit.ReturnValue)
						GetSit(DataSit);
				}
				else
				{
					pRetSit = new RetSit(true);
				}
			}
			catch (Exception ex)
			{
				throw new Exception("Errore durante la validazione dell'unità immobiliare " + DataSit.UI + ". Metodo: UIValidazione, modulo: SitMgr. " + ex.Message);
			}

			return pRetSit;
		}

		public RetSit PianoValidazione()
		{
			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;

				if (String.IsNullOrEmpty(DataSit.Piano))
					return new RetSit(true);

				var rVal = VerificaPiano();

				if (rVal.ReturnValue)
					GetSit(DataSit);

				return rVal;

			}
			catch (Exception ex)
			{
				const string errMsg = "Errore durante la validazione del piano {0} utilizzando il sit {1}: {2}";

				var fullErrMsg = String.Format( errMsg , DataSit.Piano , this.GetType().Name , ex.Message );

				_log.ErrorFormat( "{0}, Dettagli eccezione: {1}", fullErrMsg , ex.ToString());

				throw new Exception( fullErrMsg);
			}
		}

		public RetSit QuartiereValidazione()
		{
			try
			{
				_modificaStradario = ModificaStradario.Indirizzo;

				if (String.IsNullOrEmpty(DataSit.Quartiere))
					return new RetSit(true);

				var rVal = VerificaQuartiere();

				if (rVal.ReturnValue)
					GetSit(DataSit);

				return rVal;

			}
			catch (Exception ex)
			{
				const string errMsg = "Errore durante la validazione del quartiere {0} utilizzando il sit {1}: {2}";

				var fullErrMsg = String.Format(errMsg, DataSit.Quartiere, this.GetType().Name, ex.Message);

				_log.ErrorFormat("{0}, Dettagli eccezione: {1}", fullErrMsg, ex.ToString());

				throw new Exception(fullErrMsg);
			}
		}
		#endregion

		#region Metodi per estrarre il dettaglio di un oggetto territoriale
		/// <summary>
		/// Restituisce le informazioni del fabbricato
		/// </summary>
		/// <returns></returns>
		public virtual RetSit DettaglioFabbricato()
		{
			return RestituisciErroreSit(MessageCode.DettaglioFabbricato, true);
		}

		/// <summary>
		/// Restituisce le informazioni dell'unità immobiliare
		/// </summary>
		/// <returns></returns>
		public virtual RetSit DettaglioUI()
		{
			return RestituisciErroreSit(MessageCode.DettaglioUI, true);
		}
		#endregion

		#region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo

		public virtual RetSit ElencoCodVia()
		{
			return RestituisciErroreSit(MessageCode.ElencoCodiciVia, true);
		}

		/// <summary>
		/// Restituisce la lista degli identificativi di civico (non i numeri civici) e cioè un codice che identifica il numero civico e l'esponente
		/// </summary>
		/// <returns>lista degli identificativi di civico (non i numeri civici) e cioè un codice che identifica il numero civico e l'esponente</returns>
		/// <remarks>Occorre conoscere il codice via o il codice fabbricato</remarks>
		public virtual RetSit ElencoCodCivici()
		{
			return RestituisciErroreSit(MessageCode.ElencoCodiciCivici, true);
		}

		/// <summary>
		/// Restituisce la lista dei numeri civici
		/// </summary>
		/// <returns>lista dei numeri civici</returns>
		/// /// <remarks>Occorre conoscere il codice via o il codice fabbricato</remarks>
		public virtual RetSit ElencoCivici()
		{
			return RestituisciErroreSit(MessageCode.ElencoCivici, true);
		}

		/// <summary>
		/// Restituisce la lista dei km di una via
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere il codice via o il codice fabbricato</remarks>
		public virtual RetSit ElencoKm()
		{
			return RestituisciErroreSit(MessageCode.ElencoKm, true);
		}

		/// <summary>
		/// Restituisce la lista degli esponenti di un civico
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere il codice via ed il civico oppure il codice fabbricato ed il civico</remarks>
		public virtual RetSit ElencoEsponenti()
		{
			return RestituisciErroreSit(MessageCode.ElencoEsponenti, true);
		}

		/// <summary>
		/// Restituisce la lista di colori di un civico
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere il codice via ed il civico oppure il codice fabbricato ed il civico</remarks>
		public virtual RetSit ElencoColori()
		{
			return RestituisciErroreSit(MessageCode.ElencoColori, true);
		}

		/// <summary>
		/// Restituisce la lista delle scale di un civico
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere il codice via, il civico e l'esponente->CodCivico oppure il codice fabbricato, il civico e l'esponente</remarks>
		public virtual RetSit ElencoScale()
		{
			return RestituisciErroreSit(MessageCode.ElencoScale, true);
		}

		/// <summary>
		/// Restituisce l'elenco degli interni di un civico
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - il codice via, il civico e l'esponente
		/// oppure 
		/// - il CodCivico 
		/// oppure 
		/// - il codice fabbricato, il civico e l'esponente</remarks>
		public virtual RetSit ElencoInterni()
		{
			return RestituisciErroreSit(MessageCode.ElencoInterni, true);
		}

		/// <summary>
		/// Restituisce la lista degli esponenti di un interno
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - il codice via, il civico, l'esponente e l'interno
		/// oppure
		/// - il CodCivico e l'interno
		/// oppure 
		/// - il codice fabbricato, il civico, l'esponente e l'interno</remarks>
		public virtual RetSit ElencoEsponentiInterno()
		{
			return RestituisciErroreSit(MessageCode.ElencoEsponentiInterno, true);
		}


		/// <summary>
		/// Restituisce l'elenco dei cap in cui insiste un civico
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - il codice via e il civico
		/// oppure
		/// - il codice fabbricato ed il civico</remarks>
		public virtual RetSit ElencoCAP()
		{
			return RestituisciErroreSit(MessageCode.ElencoCAP, true);
		}


		/// <summary>
		/// Restituisce l'elenco delle frazioni in cui esiste un civico
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - il codice via e il civico
		/// oppure
		/// - il codice fabbricato ed il civico</remarks>
		public virtual RetSit ElencoFrazioni()
		{
			return RestituisciErroreSit(MessageCode.ElencoFrazioni, true);
		}


		/// <summary>
		/// Restituisce l'elenco delle circoscrizioni in cui esiste un civico
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - il codice via e il civico
		/// oppure
		/// - il codice fabbricato ed il civico</remarks>
		public virtual RetSit ElencoCircoscrizioni()
		{
			return RestituisciErroreSit(MessageCode.ElencoCircoscrizioni, true);
		}


		/// <summary>
		/// Restituisce l'elenco dei fabbricati contenuti in una via
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - il codice via
		/// oppure
		/// - la sezione (se il sistema la supporta), il foglio e la particella</remarks>
		public virtual RetSit ElencoFabbricati()
		{
			return RestituisciErroreSit(MessageCode.ElencoFabbricati, true);
		}


		/// <summary>
		/// Restituisce l'elenco delle sezioni catastali
		/// </summary>
		/// <returns></returns>
		public virtual RetSit ElencoSezioni()
		{
			return RestituisciErroreSit(MessageCode.ElencoSezioni, true);
		}

		
		/// <summary>
		/// Restituisce l'elenco di fogli contenuti in una sezione oppure il foglio a cui appartiene un fabbricato
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta)
		/// oppure
		/// - il codice fabbricato</remarks>
		public virtual RetSit ElencoFogli()
		{
			return RestituisciErroreSit(MessageCode.ElencoFogli, true);
		}


		/// <summary>
		/// Restituisce la lista di particelle che appartengono ad un foglio o la particella acui appartiene un fabbricato
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta) ed il foglio
		/// oppure
		/// - il codice fabbricato</remarks>
		public virtual RetSit ElencoParticelle()
		{
			return RestituisciErroreSit(MessageCode.ElencoParticelle, true);
		}


		/// <summary>
		/// Restituisce la lista dei subalterni contenuti in una particella oppure la particella a cui appartiene un fabbricato
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta), il foglio e la particella
		/// oppure
		/// - il codice fabbricato</remarks>
		public virtual RetSit ElencoSub()
		{
			return RestituisciErroreSit(MessageCode.ElencoSub, true);
		}


		/// <summary>
		/// Restituisce la lista delle unità immobiliari che appartengono ad una particella o l'UI a cui appartiene un fabbricato
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta), il foglio, la particella e il subalterno
		/// oppure
		/// - il codice fabbricato</remarks>
		public virtual RetSit ElencoUI()
		{
			return RestituisciErroreSit(MessageCode.ElencoUI, true);
		}

		/// <summary>
		/// Restituisce l'elenco dei vincoli della zona in cui insiste una particella, un fabbricato o una via
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta), il foglio, la particella
		/// oppure
		/// - il codice fabbricato
		/// oppure
		/// - il codice via</remarks>
		public virtual RetSit ElencoVincoli()
		{
			return RestituisciErroreSit(MessageCode.ElencoVincoli, true);
		}


		/// <summary>
		/// Restituisce l'elenco delle zone in cui insiste una particella, un fabbricato o una via.
		/// Il metodo è generico e potrebbe considerare zone prg, utoe ed altro
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta), il foglio, la particella
		/// oppure
		/// - il codice fabbricato
		/// oppure
		/// - il codice via</remarks>
		public virtual RetSit ElencoZone()
		{
			return RestituisciErroreSit(MessageCode.ElencoZone, true);
		}



		/// <summary>
		/// Restituisce l'elenco delle sottozone in cui insiste una particella, un fabbricato o una via.
		/// Il metodo è generico e potrebbe considerare zone prg, utoe ed altro
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta), il foglio, la particella
		/// oppure
		/// - il codice fabbricato
		/// oppure
		/// - il codice via</remarks>
		public virtual RetSit ElencoSottoZone()
		{
			return RestituisciErroreSit(MessageCode.ElencoSottoZone, true);
		}


		/// <summary>
		/// Restituisce l'elenco dei dati urbanistici in cui insiste una particella
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// - la sezione (se il sistema la supporta), il foglio, la particella
		/// </remarks>
		public virtual RetSit ElencoDatiUrbanistici()
		{
			return RestituisciErroreSit(MessageCode.ElencoDatiUrbanistici, true);
		}


		/// <summary>
		/// Restituisce l'elenco dei piani
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// ???
		/// </remarks>
		public virtual RetSit ElencoPiani()
		{
			return RestituisciErroreSit(MessageCode.ElencoPiani, true);
		}

		/// <summary>
		/// Restituisce l'elenco dei quartieri
		/// </summary>
		/// <returns></returns>
		/// <remarks>Occorre conoscere:
		/// ???
		/// </remarks>
		public virtual RetSit ElencoQuartieri()
		{
			return RestituisciErroreSit(MessageCode.ElencoQuartieri, true);
		}
		#endregion

		#region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo
		protected virtual string GetKm()
		{
			return this.DataSit.Km;
		}

		protected virtual RetSit VerificaKm()
		{
			return RestituisciErroreSit(MessageCode.KmValidazione, true);
		}

		protected virtual string GetEsponente()
		{
			return this.DataSit.Esponente;
		}

		protected virtual RetSit VerificaEsponente()
		{
			RetSit pRetSit = RestituisciErroreSit(MessageCode.EsponenteValidazione, true);
			return pRetSit;
		}

		protected virtual string GetColore()
		{
			return this.DataSit.Colore;
		}

		protected virtual RetSit VerificaColore()
		{
			RetSit pRetSit = RestituisciErroreSit(MessageCode.ColoreValidazione, true);
			return pRetSit;
		}

		protected virtual string GetScala()
		{
			return this.DataSit.Scala;
		}

		protected virtual RetSit VerificaScala()
		{
			RetSit pRetSit = RestituisciErroreSit(MessageCode.ScalaValidazione, true);
			return pRetSit;
		}

		protected virtual string GetInterno()
		{
			return this.DataSit.Interno;
		}

		protected virtual RetSit VerificaInterno()
		{
			RetSit pRetSit = RestituisciErroreSit(MessageCode.InternoValidazione, true);
			return pRetSit;
		}

		protected virtual string GetEsponenteInterno()
		{
			return this.DataSit.EsponenteInterno;
		}

		protected virtual RetSit VerificaEsponenteInterno()
		{
			return RestituisciErroreSit(MessageCode.EsponenteInternoValidazione, true);
		}

		protected virtual RetSit VerificaFabbricato()
		{
			return RestituisciErroreSit(MessageCode.FabbricatoValidazione, true);
		}

		protected virtual string GetTipoCatasto()
		{
			if (String.IsNullOrEmpty(this.DataSit.TipoCatasto))
				return "F";

			return this.DataSit.TipoCatasto;
		}


		protected virtual RetSit VerificaTipoCatasto()
		{
			return RestituisciErroreSit(MessageCode.TipoCatastoValidazione, true);
		}

		protected virtual string GetSezione()
		{
			return this.DataSit.Sezione;
		}

		protected virtual RetSit VerificaSezione()
		{
			return RestituisciErroreSit(MessageCode.SezioneValidazione, true);
		}

		protected abstract string GetFoglio();
		protected abstract string GetParticella();

		protected abstract RetSit VerificaFoglio();
		protected abstract RetSit VerificaParticella();


		protected virtual string GetSub()
		{
			return this.DataSit.Sub;
		}

		protected virtual RetSit VerificaSub()
		{
			return RestituisciErroreSit(MessageCode.SubValidazione, true);
		}


		protected virtual string GetUI()
		{
			return this.DataSit.UI;
		}


		protected virtual RetSit VerificaUI()
		{
			return RestituisciErroreSit(MessageCode.UIValidazione, true);
		}

		protected virtual string GetPiano()
		{
			return this.DataSit.Piano;
		}

		protected virtual RetSit VerificaPiano()
		{
			return RestituisciErroreSit(MessageCode.PianoValidazione, true);
		}

		protected virtual string GetQuartiere()
		{
			return this.DataSit.Quartiere;
		}

		protected virtual RetSit VerificaQuartiere()
		{
			return RestituisciErroreSit(MessageCode.QuartiereValidazione, true);
		}

		protected virtual string GetCivico()
		{
			return this.DataSit.Civico;
		}

		protected virtual RetSit VerificaCivico()
		{
			return RestituisciErroreSit(MessageCode.CivicoValidazione, true);
		}

		protected virtual string GetCodCivico()
		{
			return this.DataSit.CodCivico;
		}

		protected virtual string GetCodFabbricato()
		{
			return this.DataSit.Fabbricato;
		}

		protected abstract string GetCodVia();

		protected virtual RetSit VerificaCodiceVia()
		{
			return RestituisciErroreSit(MessageCode.CodiceViaValidazione, true);
		}

		protected virtual string GetCAP()
		{
			return this.DataSit.CAP;
		}

		protected virtual RetSit VerificaCAP()
		{
			return RestituisciErroreSit(MessageCode.CAPValidazione, true);
		}

		protected virtual string GetCircoscrizione()
		{
			return this.DataSit.Circoscrizione;
		}

		protected virtual RetSit VerificaCircoscrizione()
		{
			return RestituisciErroreSit(MessageCode.CircoscrizioneValidazione, true);
		}

		protected virtual string GetFrazione()
		{
			return this.DataSit.Frazione;
		}

		protected virtual RetSit VerificaFrazione()
		{
			return RestituisciErroreSit(MessageCode.FrazioneValidazione, true);
		}

		#endregion

		#region metodi astratti da implementare nelle classi derivate

		/// <summary>
		/// Inizializzai parametri del sit leggendoli dalla specifica verticalizzazione
		/// </summary>
		public abstract void SetupVerticalizzazione();

		#endregion

		#region validazione formale
		public bool ValidaDatiSit(Init.SIGePro.Sit.Data.Sit sit)
		{
			if (_log.IsDebugEnabled)
				_log.DebugFormat("Validazione della classe sit: {0}", StreamUtils.SerializeClass(sit));
			try
			{
				var esito = this._servizioValidazioneFormale.Valida(sit);

				_log.DebugFormat("Esito validazione: {0}", esito);

				return esito;
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la validazione della classe sit: {0}", ex.ToString());

				throw;
			}
		}
		#endregion

		public abstract string[] GetListaCampiGestiti();

		public virtual DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
		{
			return new DettagliVia[0];
		}


		public virtual BaseDto<Manager.SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice()
		{
			return new BaseDto<SitFeatures.TipoVisualizzazione, string>[0] { };
		}


        public virtual BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
		{
			return new BaseDto<SitFeatures.TipoVisualizzazione, string>[0] { };
		}

        public virtual RetSit AccessoNumeroValidazione()
        {
            throw new NotImplementedException();
        }

        public virtual RetSit AccessoDescrizioneValidazione()
        {
            throw new NotImplementedException();
        }

        public virtual RetSit ElencoAccessoTipo()
        {
            throw new NotImplementedException();
        }
    }
}