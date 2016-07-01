using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
    public partial class IstanzeEventiMgr
    {
        #region parametri per la configurazione di alcune procedure automatiche all'interno del manager
        bool _insertanagrafe = false;

        /// <summary>
        /// Se True, inserisce le anagrafiche (tabella ANAGRAFE) non presenti in SIGePro
        /// </summary>
        public bool InsertAnagrafe
        {
            set { _insertanagrafe = value; }
            get { return _insertanagrafe; }
        }
        bool _updateanagrafe = false;
        /// <summary>
        /// Se True, aggiorna le anagrafiche (tabella ANAGRAFE) quando già presenti in SIGePro.
        /// </summary>
        public bool UpdateAnagrafe
        {
            get { return _updateanagrafe; }
            set { _updateanagrafe = value; }
        }

        bool m_escludiControlliSuAnagraficheDisabilitate = false;
        /// <summary>
        /// Se impostato a true non effettua verifiche dei dati sulle anagrafiche disabilitate nei metodi Insert e Extract
        /// </summary>
        public bool EscludiControlliSuAnagraficheDisabilitate
        {
            get { return m_escludiControlliSuAnagraficheDisabilitate; }
            set { m_escludiControlliSuAnagraficheDisabilitate = value; }
        }

        bool _ricercasolocfpiva = false;
        public bool RicercaSoloCF_PIVA
        {
            get { return _ricercasolocfpiva; }
            set { _ricercasolocfpiva = value; }
        }

        bool _escludiverificaincongruenze = false;
        public bool EscludiVerificaIncongruenze
        {
            get { return _escludiverificaincongruenze; }
            set { _escludiverificaincongruenze = value; }
        }

        bool m_ForzaInserimentoAnagrafe = false;
        public bool ForzaInserimentoAnagrafe
        {
            get { return m_ForzaInserimentoAnagrafe; }
            set { m_ForzaInserimentoAnagrafe = value; }
        }
        #endregion

        /// <summary>
        /// Inserisce o aggiorna una anagrafica nella tabella ANAGRAFE nel rispetto delle regole impostate
        /// nelle proprietà UpdateAnagrafe e InsertAnagrafe della classe IstanzeMGR
        /// </summary>
        /// <param name="anagrafeDataClass">E' la classe di tipo Anagrafe da inserire</param>
        /// <returns>Ritorna la classe Anagrafe inserita o aggiornata. Se la classe è vuota CODICEANAGRAFE="" o null allora l'inserimento o l'aggiornamento non è andato a buon fine.</returns>
        private Anagrafe InsertUpdateAnagrafeDataClass(Anagrafe anagrafeDataClass)
        {
            Anagrafe richiedente = null;
            AnagrafeMgr anagrafeMgr = new AnagrafeMgr(this.db);

            anagrafeMgr.EscludiControlliSuAnagraficheDisabilitate = this.EscludiControlliSuAnagraficheDisabilitate;
            anagrafeMgr.ForzaInserimentoAnagrafe = this.ForzaInserimentoAnagrafe;
            anagrafeMgr.RicercaSoloCF_PIVA = this.RicercaSoloCF_PIVA;
            anagrafeMgr.EscludiVerificaIncongruenze = this.EscludiVerificaIncongruenze;

            //cerca l'anagrafica e se la trova la aggiorna in sigepro
            try
            {
                richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, UpdateAnagrafe);

                if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && InsertAnagrafe)
                {
                    //Se l'anagrafica non è stata trovata viene inserita.
                    richiedente = anagrafeMgr.Insert(anagrafeDataClass);
                }
            }
            catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew)
            {
                if (ForzaInserimentoAnagrafe)
                {
                    Anagrafe anagDisabilitata = (anagrafeDataClass.Clone() as Anagrafe);
                    anagDisabilitata.FLAG_DISABILITATO = "1";
                    anagDisabilitata.DATA_DISABILITATO = DateTime.Now.Date;

                    try
                    {
                        richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, UpdateAnagrafe);

                        if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && InsertAnagrafe)
                        {
                            //Se l'anagrafica non è stata trovata viene inserita.
                            richiedente = anagrafeMgr.Insert(anagDisabilitata);
                        }
                    }
                    catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew2)
                    {
                        //esiste un'anagrafica disabilitata ma anche in questo caso alcuni dei dati
                        //non corrispondono, l'inserimento dell'anagrafica disabilitata viene forzato
                        richiedente = anagrafeMgr.Insert(anagDisabilitata, false);
                    }

                }
                else
                {
                    throw oew;
                }
            }

            return richiedente;
        }

        private IstanzeEventi DataIntegrations(IstanzeEventi p_class)
        {
            IstanzeEventi retVal = (IstanzeEventi)p_class.Clone();

            if (retVal.Codiceanagrafe.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Anagrafe != null)
            {
                retVal.Codiceanagrafe = Convert.ToInt32(InsertUpdateAnagrafeDataClass(retVal.Anagrafe).CODICEANAGRAFE);
            }

            return retVal;
        }
        private void Validate(IstanzeEventi cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);

            ForeignValidate(cls);
        }

        private void ForeignValidate(IstanzeEventi p_class)
        {
            #region ISTANZEEVENTI.CODICEANAGRAFE
            if (p_class.Codiceanagrafe.HasValue && p_class.Codiceanagrafe.Value > int.MinValue)
            {
                if (this.recordCount("ANAGRAFE", "CODICEANAGRAFE", "WHERE IDCOMUNE = '" + p_class.Idcomune + "' AND CODICEANAGRAFE = " + p_class.Codiceanagrafe.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEEVENTI.CODICEANAGRAFE (" + p_class.Codiceanagrafe.ToString() + ") non trovato nella tabella ANAGRAFE"));
                }
            }
            #endregion

            #region ISTANZEEVENTI.CODICEISTANZA
            if (p_class.Codiceistanza.HasValue && p_class.Codiceistanza.Value > int.MinValue)
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE IDCOMUNE = '" + p_class.Idcomune + "' AND CODICEISTANZA = " + p_class.Codiceistanza.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEEVENTI.CODICEISTANZA (" + p_class.Codiceistanza.ToString() + ") non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZEEVENTI.FKIDCATEGORIAEVENTO
            if ( ! string.IsNullOrEmpty( p_class.Fkidcategoriaevento ) )
            {
                if (this.recordCount("CATEGORIEEVENTIBASE", "ID", "WHERE ID = '" + p_class.Fkidcategoriaevento + "'") == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEEVENTI.FKIDCATEGORIAEVENTO (" + p_class.Fkidcategoriaevento + ") non trovato nella tabella CATEGORIEEVENTIBASE"));
                }
            }
            #endregion
        }
    }
}
