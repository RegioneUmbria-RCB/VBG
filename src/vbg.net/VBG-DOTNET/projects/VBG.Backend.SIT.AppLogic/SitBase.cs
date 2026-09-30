using Init.Utils;
using log4net;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Data;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Errors;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;

namespace VBG.Backend.SIT.AppLogic
{
    public abstract class SitBase : VBG.Backend.SIT.AppLogic.ISitApi
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SitBase));
        private readonly IValidazioneFormaleService _servizioValidazioneFormale;

        /// <summary>
        /// Connessione al database utilizzata internamente
        /// </summary>
        protected DataBase InternalDatabaseConnection;

        #region Proprietà

        private ModificaStradario _modificaStradario = ModificaStradario.Indirizzo;

        public VBG.Backend.SIT.AppLogic.Data.Sit DataSit { get; set; }

        protected string IdComune { get; private set; }
        protected string IdComuneAlias { get; private set; }
        protected string Software { get; private set; }
        #endregion

        internal SitBase(IValidazioneFormaleService servizioValidazioneFormale)
        {

            this._servizioValidazioneFormale = servizioValidazioneFormale ?? throw new ArgumentNullException(nameof(servizioValidazioneFormale));
        }

        #region Inizializzazione dei parametri di Sigepro
        public void InizializzaParametriSigepro(string idcomune, string alias, string software)
        {
            this.IdComune = idcomune;
            this.IdComuneAlias = alias;
            this.Software = software;
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
            return string.IsNullOrEmpty(this.DataSit.Civico) &&
                    string.IsNullOrEmpty(this.DataSit.Esponente) &&
                    string.IsNullOrEmpty(this.DataSit.Interno) &&
                    string.IsNullOrEmpty(this.DataSit.Scala) &&
                    string.IsNullOrEmpty(this.DataSit.EsponenteInterno) &&
                    string.IsNullOrEmpty(this.DataSit.CodCivico) &&
                    string.IsNullOrEmpty(this.DataSit.Fabbricato) &&
                    string.IsNullOrEmpty(this.DataSit.UI);
        }

        /// <summary>
        /// Verifica che la connessione interna al database sia aperta. In caso contrario la apre
        /// </summary>
        protected void EnsureConnectionIsOpen()
        {
            if (this.InternalDatabaseConnection.Connection.State == ConnectionState.Closed)
                this.InternalDatabaseConnection.Connection.Open();
        }

        protected void CloseInternalConnection()
        {
            if (this.InternalDatabaseConnection.Connection.State != ConnectionState.Closed)
                this.InternalDatabaseConnection.Connection.Close();
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
            return this.LeftTrim(value, null);
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
            return this.RightTrim(value, null);
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
                return (string.IsNullOrEmpty(value) ? String.Empty : this.ToSqlSafeString(value.TrimEnd()));
            else
                return (string.IsNullOrEmpty(value) ? String.Empty : this.ToSqlSafeString(value.TrimEnd(trimChars)));
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
            return this.LeftPad(value, totalWidth, '0');
        }
        #endregion

        #region Metodi di validazione
        public RetSit CodiceViaValidazione()
        {
            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;

                if (!String.IsNullOrEmpty(this.DataSit.CodVia))
                {
                    var rVal = this.VerificaCodiceVia();

                    if (rVal.ReturnValue)
                        this.GetSit(this.DataSit);

                    return rVal;
                }

                return new RetSit(true);

            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del codice viario " + this.DataSit.CodVia + ". Metodo: CodiceViaValidazione, modulo: SitMgr. " + ex.Message);
            }
        }


        public virtual RetSit CivicoValidazione()
        {
            if (String.IsNullOrEmpty(this.DataSit.Civico))
                return new RetSit(true);

            try
            {
                var rval = this.VerificaCivico();

                if (rval.ReturnValue)
                    this.GetSit(this.DataSit);

                return rval;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del civico " + this.DataSit.Civico + ". Metodo: CivicoValidazione, modulo: SitMgr. " + ex.Message, ex);
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
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Km))
                {
                    pRetSit = this.VerificaKm();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del km " + this.DataSit.Km + ". Metodo: KmValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public virtual RetSit EsponenteValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Esponente))
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

                    pRetSit = this.VerificaEsponente();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione dell'esponente " + this.DataSit.Esponente + ". Metodo: EsponenteValidazione, modulo: SitMgr. " + ex.Message);
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
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Colore))
                {
                    pRetSit = this.VerificaColore();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del colore " + this.DataSit.Colore + ". Metodo: ColoreValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit ScalaValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Scala))
                {
                    pRetSit = this.VerificaScala();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione della scala " + this.DataSit.Scala + ". Metodo: ScalaValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit InternoValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Interno))
                {
                    pRetSit = this.VerificaInterno();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione dell' interno " + this.DataSit.Interno + ". Metodo: InternoValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit EsponenteInternoValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.EsponenteInterno))
                {
                    pRetSit = this.VerificaEsponenteInterno();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione dell'esponente interno " + this.DataSit.EsponenteInterno + ". Metodo: EsponenteInternoValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit CAPValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.CAP))
                {
                    pRetSit = this.VerificaCAP();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del CAP " + this.DataSit.CAP + ". Metodo: CAPValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit FrazioneValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Frazione))
                {
                    pRetSit = this.VerificaFrazione();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione della frazione " + this.DataSit.Frazione + ". Metodo: FrazioneValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit CircoscrizioneValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Circoscrizione))
                {
                    pRetSit = this.VerificaCircoscrizione();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione della circoscrizione " + this.DataSit.Circoscrizione + ". Metodo: CircoscrizioneValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit FabbricatoValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.Fabbricato))
                {
                    pRetSit = this.VerificaFabbricato();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del fabbricato " + this.DataSit.Fabbricato + ". Metodo: FabbricatoValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        private void GetSit(VBG.Backend.SIT.AppLogic.Data.Sit pSit)
        {
            if (this._modificaStradario == ModificaStradario.Indirizzo)
            {
                this.GetIndirizzo(pSit);
                this.GetCatasto(pSit);
            }
            if (this._modificaStradario == ModificaStradario.Catasto)
            {
                this.GetCatasto(pSit);
                this.GetIndirizzo(pSit);
            }
        }

        private void GetIndirizzo(VBG.Backend.SIT.AppLogic.Data.Sit pSit)
        {
            if (string.IsNullOrEmpty(pSit.CodVia))
                pSit.CodVia = this.GetCodVia();

            if (string.IsNullOrEmpty(pSit.Civico))
                pSit.Civico = this.GetCivico();

            if (string.IsNullOrEmpty(pSit.Km))
                pSit.Km = this.GetKm();

            if (string.IsNullOrEmpty(pSit.Esponente))
                pSit.Esponente = this.GetEsponente();

            if (string.IsNullOrEmpty(pSit.Colore))
                pSit.Colore = this.GetColore();

            //if (string.IsNullOrEmpty(pSit.CodCivico))
            pSit.CodCivico = this.GetCodCivico();

            if (string.IsNullOrEmpty(pSit.Scala))
                pSit.Scala = this.GetScala();

            if (string.IsNullOrEmpty(pSit.Interno))
                pSit.Interno = this.GetInterno();

            if (string.IsNullOrEmpty(pSit.EsponenteInterno))
                pSit.EsponenteInterno = this.GetEsponenteInterno();

            if (string.IsNullOrEmpty(pSit.Fabbricato))
                pSit.Fabbricato = this.GetCodFabbricato();

            if (string.IsNullOrEmpty(pSit.UI))
                pSit.UI = this.GetUI();

            if (string.IsNullOrEmpty(pSit.CAP))
                pSit.CAP = this.GetCAP();

            if (string.IsNullOrEmpty(pSit.Frazione))
                pSit.Frazione = this.GetFrazione();

            if (string.IsNullOrEmpty(pSit.Circoscrizione))
                pSit.Circoscrizione = this.GetCircoscrizione();
            if (string.IsNullOrEmpty(pSit.Quartiere))
                pSit.Quartiere = this.GetQuartiere();
        }

        private void GetCatasto(VBG.Backend.SIT.AppLogic.Data.Sit pSit)
        {
            if (string.IsNullOrEmpty(pSit.TipoCatasto))
                pSit.TipoCatasto = this.GetTipoCatasto();

            if (string.IsNullOrEmpty(pSit.Sezione))
                pSit.Sezione = this.GetSezione();

            if (string.IsNullOrEmpty(pSit.Foglio))
                pSit.Foglio = this.GetFoglio();

            if (string.IsNullOrEmpty(pSit.Particella))
                pSit.Particella = this.GetParticella();

            if (string.IsNullOrEmpty(pSit.Fabbricato))
                pSit.Fabbricato = this.GetCodFabbricato();

            if (string.IsNullOrEmpty(pSit.Sub))
                pSit.Sub = this.GetSub();

            if (string.IsNullOrEmpty(pSit.UI))
                pSit.UI = this.GetUI();
        }

        public RetSit SezioneValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Catasto;
                if (!String.IsNullOrEmpty(this.DataSit.Sezione))
                {
                    pRetSit = this.VerificaSezione();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione della sezione " + this.DataSit.Sezione + ". Metodo: SezioneValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit TipoCatastoValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Catasto;
                if (!String.IsNullOrEmpty(this.DataSit.TipoCatasto))
                {
                    pRetSit = this.VerificaTipoCatasto();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del catasto " + this.DataSit.TipoCatasto + ". Metodo: TipoCatastoValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit FoglioValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Catasto;
                if (!String.IsNullOrEmpty(this.DataSit.Foglio))
                {
                    pRetSit = this.VerificaFoglio();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del foglio " + this.DataSit.Foglio + ". Metodo: FoglioValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit ParticellaValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Catasto;
                if (!String.IsNullOrEmpty(this.DataSit.Particella))
                {
                    pRetSit = this.VerificaParticella();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione della particella " + this.DataSit.Particella + ". Metodo: ParticellaValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit SubValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Catasto;
                if (!String.IsNullOrEmpty(this.DataSit.Sub))
                {
                    pRetSit = this.VerificaSub();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del sub " + this.DataSit.Sub + ". Metodo: SubValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit UIValidazione()
        {
            RetSit pRetSit = null;

            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;
                if (!String.IsNullOrEmpty(this.DataSit.UI))
                {
                    pRetSit = this.VerificaUI();

                    if (pRetSit.ReturnValue)
                        this.GetSit(this.DataSit);
                }
                else
                {
                    pRetSit = new RetSit(true);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione dell'unità immobiliare " + this.DataSit.UI + ". Metodo: UIValidazione, modulo: SitMgr. " + ex.Message);
            }

            return pRetSit;
        }

        public RetSit PianoValidazione()
        {
            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;

                if (String.IsNullOrEmpty(this.DataSit.Piano))
                    return new RetSit(true);

                var rVal = this.VerificaPiano();

                if (rVal.ReturnValue)
                    this.GetSit(this.DataSit);

                return rVal;

            }
            catch (Exception ex)
            {
                const string errMsg = "Errore durante la validazione del piano {0} utilizzando il sit {1}: {2}";

                var fullErrMsg = String.Format(errMsg, this.DataSit.Piano, this.GetType().Name, ex.Message);

                this._log.ErrorFormat("{0}, Dettagli eccezione: {1}", fullErrMsg, ex.ToString());

                throw new Exception(fullErrMsg);
            }
        }

        public RetSit QuartiereValidazione()
        {
            try
            {
                this._modificaStradario = ModificaStradario.Indirizzo;

                if (String.IsNullOrEmpty(this.DataSit.Quartiere))
                    return new RetSit(true);

                var rVal = this.VerificaQuartiere();

                if (rVal.ReturnValue)
                    this.GetSit(this.DataSit);

                return rVal;

            }
            catch (Exception ex)
            {
                const string errMsg = "Errore durante la validazione del quartiere {0} utilizzando il sit {1}: {2}";

                var fullErrMsg = String.Format(errMsg, this.DataSit.Quartiere, this.GetType().Name, ex.Message);

                this._log.ErrorFormat("{0}, Dettagli eccezione: {1}", fullErrMsg, ex.ToString());

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
            return this.RestituisciErroreSit(MessageCode.DettaglioFabbricato, true);
        }

        /// <summary>
        /// Restituisce le informazioni dell'unità immobiliare
        /// </summary>
        /// <returns></returns>
        public virtual RetSit DettaglioUI()
        {
            return this.RestituisciErroreSit(MessageCode.DettaglioUI, true);
        }
        #endregion

        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo

        public virtual RetSit ElencoCodVia()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoCodiciVia, true);
        }

        /// <summary>
        /// Restituisce la lista degli identificativi di civico (non i numeri civici) e cioè un codice che identifica il numero civico e l'esponente
        /// </summary>
        /// <returns>lista degli identificativi di civico (non i numeri civici) e cioè un codice che identifica il numero civico e l'esponente</returns>
        /// <remarks>Occorre conoscere il codice via o il codice fabbricato</remarks>
        public virtual RetSit ElencoCodCivici()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoCodiciCivici, true);
        }

        /// <summary>
        /// Restituisce la lista dei numeri civici
        /// </summary>
        /// <returns>lista dei numeri civici</returns>
        /// /// <remarks>Occorre conoscere il codice via o il codice fabbricato</remarks>
        public virtual RetSit ElencoCivici()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoCivici, true);
        }

        /// <summary>
        /// Restituisce la lista dei km di una via
        /// </summary>
        /// <returns></returns>
        /// <remarks>Occorre conoscere il codice via o il codice fabbricato</remarks>
        public virtual RetSit ElencoKm()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoKm, true);
        }

        /// <summary>
        /// Restituisce la lista degli esponenti di un civico
        /// </summary>
        /// <returns></returns>
        /// <remarks>Occorre conoscere il codice via ed il civico oppure il codice fabbricato ed il civico</remarks>
        public virtual RetSit ElencoEsponenti()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoEsponenti, true);
        }

        /// <summary>
        /// Restituisce la lista di colori di un civico
        /// </summary>
        /// <returns></returns>
        /// <remarks>Occorre conoscere il codice via ed il civico oppure il codice fabbricato ed il civico</remarks>
        public virtual RetSit ElencoColori()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoColori, true);
        }

        /// <summary>
        /// Restituisce la lista delle scale di un civico
        /// </summary>
        /// <returns></returns>
        /// <remarks>Occorre conoscere il codice via, il civico e l'esponente->CodCivico oppure il codice fabbricato, il civico e l'esponente</remarks>
        public virtual RetSit ElencoScale()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoScale, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoInterni, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoEsponentiInterno, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoCAP, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoFrazioni, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoCircoscrizioni, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoFabbricati, true);
        }


        /// <summary>
        /// Restituisce l'elenco delle sezioni catastali
        /// </summary>
        /// <returns></returns>
        public virtual RetSit ElencoSezioni()
        {
            return this.RestituisciErroreSit(MessageCode.ElencoSezioni, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoFogli, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoParticelle, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoSub, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoUI, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoVincoli, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoZone, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoSottoZone, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoDatiUrbanistici, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoPiani, true);
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
            return this.RestituisciErroreSit(MessageCode.ElencoQuartieri, true);
        }
        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo
        protected virtual string GetKm()
        {
            return this.DataSit.Km;
        }

        protected virtual RetSit VerificaKm()
        {
            return this.RestituisciErroreSit(MessageCode.KmValidazione, true);
        }

        protected virtual string GetEsponente()
        {
            return this.DataSit.Esponente;
        }

        protected virtual RetSit VerificaEsponente()
        {
            var pRetSit = this.RestituisciErroreSit(MessageCode.EsponenteValidazione, true);
            return pRetSit;
        }

        protected virtual string GetColore()
        {
            return this.DataSit.Colore;
        }

        protected virtual RetSit VerificaColore()
        {
            var pRetSit = this.RestituisciErroreSit(MessageCode.ColoreValidazione, true);
            return pRetSit;
        }

        protected virtual string GetScala()
        {
            return this.DataSit.Scala;
        }

        protected virtual RetSit VerificaScala()
        {
            var pRetSit = this.RestituisciErroreSit(MessageCode.ScalaValidazione, true);
            return pRetSit;
        }

        protected virtual string GetInterno()
        {
            return this.DataSit.Interno;
        }

        protected virtual RetSit VerificaInterno()
        {
            var pRetSit = this.RestituisciErroreSit(MessageCode.InternoValidazione, true);
            return pRetSit;
        }

        protected virtual string GetEsponenteInterno()
        {
            return this.DataSit.EsponenteInterno;
        }

        protected virtual RetSit VerificaEsponenteInterno()
        {
            return this.RestituisciErroreSit(MessageCode.EsponenteInternoValidazione, true);
        }

        protected virtual RetSit VerificaFabbricato()
        {
            return this.RestituisciErroreSit(MessageCode.FabbricatoValidazione, true);
        }

        protected virtual string GetTipoCatasto()
        {
            if (String.IsNullOrEmpty(this.DataSit.TipoCatasto))
                return "F";

            return this.DataSit.TipoCatasto;
        }


        protected virtual RetSit VerificaTipoCatasto()
        {
            return this.RestituisciErroreSit(MessageCode.TipoCatastoValidazione, true);
        }

        protected virtual string GetSezione()
        {
            return this.DataSit.Sezione;
        }

        protected virtual RetSit VerificaSezione()
        {
            return this.RestituisciErroreSit(MessageCode.SezioneValidazione, true);
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
            return this.RestituisciErroreSit(MessageCode.SubValidazione, true);
        }


        protected virtual string GetUI()
        {
            return this.DataSit.UI;
        }


        protected virtual RetSit VerificaUI()
        {
            return this.RestituisciErroreSit(MessageCode.UIValidazione, true);
        }

        protected virtual string GetPiano()
        {
            return this.DataSit.Piano;
        }

        protected virtual RetSit VerificaPiano()
        {
            return this.RestituisciErroreSit(MessageCode.PianoValidazione, true);
        }

        protected virtual string GetQuartiere()
        {
            return this.DataSit.Quartiere;
        }

        protected virtual RetSit VerificaQuartiere()
        {
            return this.RestituisciErroreSit(MessageCode.QuartiereValidazione, true);
        }

        protected virtual string GetCivico()
        {
            return this.DataSit.Civico;
        }

        protected virtual RetSit VerificaCivico()
        {
            return this.RestituisciErroreSit(MessageCode.CivicoValidazione, true);
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
            return this.RestituisciErroreSit(MessageCode.CodiceViaValidazione, true);
        }

        protected virtual string GetCAP()
        {
            return this.DataSit.CAP;
        }

        protected virtual RetSit VerificaCAP()
        {
            return this.RestituisciErroreSit(MessageCode.CAPValidazione, true);
        }

        protected virtual string GetCircoscrizione()
        {
            return this.DataSit.Circoscrizione;
        }

        protected virtual RetSit VerificaCircoscrizione()
        {
            return this.RestituisciErroreSit(MessageCode.CircoscrizioneValidazione, true);
        }

        protected virtual string GetFrazione()
        {
            return this.DataSit.Frazione;
        }

        protected virtual RetSit VerificaFrazione()
        {
            return this.RestituisciErroreSit(MessageCode.FrazioneValidazione, true);
        }

        #endregion

        #region metodi astratti da implementare nelle classi derivate

        /// <summary>
        /// Inizializzai parametri del sit leggendoli dalla specifica verticalizzazione
        /// </summary>
        public abstract void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory);

        #endregion

        #region validazione formale
        public bool ValidaDatiSit(VBG.Backend.SIT.AppLogic.Data.Sit sit)
        {
            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Validazione della classe sit: {0}", StreamUtils.SerializeClass(sit));
            try
            {
                var esito = this._servizioValidazioneFormale.Valida(sit);

                this._log.DebugFormat("Esito validazione: {0}", esito);

                return esito;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la validazione della classe sit: {0}", ex.ToString());

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