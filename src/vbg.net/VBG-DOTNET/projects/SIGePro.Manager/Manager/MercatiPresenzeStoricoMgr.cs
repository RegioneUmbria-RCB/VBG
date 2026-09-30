using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class MercatiPresenzeStoricoMgr
    {
        #region parametri per la configurazione di alcune procedure automatiche all'interno del manager
        //per default teniamo conto della configurazione di sigepro dal web.
        private bool _insertanagrafe = false;
        private bool _updateanagrafe = false;
        private bool m_escludiControlliSuAnagraficheDisabilitate = false;
        private bool m_ForzaInserimentoAnagrafe = false;

        /// <summary>
        /// Se impostato a true non effettua verifiche dei dati sulle anagrafiche disabilitate nei metodi Insert e Extract
        /// </summary>
        public bool EscludiControlliSuAnagraficheDisabilitate
        {
            get { return this.m_escludiControlliSuAnagraficheDisabilitate; }
            set { this.m_escludiControlliSuAnagraficheDisabilitate = value; }
        }


        /// <summary>
        /// Se True, inserisce le anagrafiche (tabella ANAGRAFE) non presenti in SIGePro
        /// </summary>
        public bool InsertAnagrafe
        {
            get { return this._insertanagrafe; }
            set { this._insertanagrafe = value; }
        }

        /// <summary>
        /// Se True, aggiorna le anagrafiche (tabella ANAGRAFE) quando già presenti in SIGePro.
        /// </summary>
        public bool UpdateAnagrafe
        {
            get { return this._updateanagrafe; }
            set { this._updateanagrafe = value; }
        }

        public bool ForzaInserimentoAnagrafe
        {
            get { return this.m_ForzaInserimentoAnagrafe; }
            set { this.m_ForzaInserimentoAnagrafe = value; }
        }

        private bool _ricercasolocfpiva = false;
        public bool RicercaSoloCF_PIVA
        {
            get { return this._ricercasolocfpiva; }
            set { this._ricercasolocfpiva = value; }
        }

        private bool _escludiverificaincongruenze = false;
        public bool EscludiVerificaIncongruenze
        {
            get { return this._escludiverificaincongruenze; }
            set { this._escludiverificaincongruenze = value; }
        }
        #endregion


        private void Validate(MercatiPresenzeStorico cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
            this.ForeignValidate(cls);
        }

        private void ForeignValidate(MercatiPresenzeStorico mps)
        {
            #region MERCATIPRESENZE_STORICO.FKCODICEMERCATO
            if (mps.Fkcodicemercato.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var cond1 = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDCOMUNE", mps.Idcomune),
                    new KeyValuePair<string, string>("CODICEMERCATO", mps.Fkcodicemercato.ToString())
                };
                if (this.recordCount("MERCATI", "CODICEMERCATO", cond1) == 0)
                {
                    throw (new RecordNotfoundException("MERCATIPRESENZE_STORICO.FKCODICEMERCATO (" + mps.Fkcodicemercato.ToString() + ") non trovato nella tabella MERCATI"));
                }
            }
            #endregion

            #region MERCATIPRESENZE_STORICO.FKIDMERCATIUSO
            if (mps.Fkidmercatiuso.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var cond2 = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDCOMUNE", mps.Idcomune),
                    new KeyValuePair<string, string>("ID", mps.Fkidmercatiuso.ToString())
                };
                if (this.recordCount("MERCATI_USO", "ID", cond2) == 0)
                {
                    throw (new RecordNotfoundException("MERCATIPRESENZE_STORICO.FKIDMERCATIUSO (" + mps.Fkidmercatiuso.ToString() + ") non trovato nella tabella MERCATI_USO"));
                }
            }
            #endregion

            #region MERCATIPRESENZE_STORICO.CODICEANAGRAFE
            if (mps.Codiceanagrafe.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var cond3 = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDCOMUNE", mps.Idcomune),
                    new KeyValuePair<string, string>("CODICEANAGRAFE", mps.Codiceanagrafe.ToString())
                };
                if (this.recordCount("ANAGRAFE", "CODICEANAGRAFE", cond3) == 0)
                {
                    throw (new RecordNotfoundException("MERCATIPRESENZE_STORICO.CODICEANAGRAFE (" + mps.Codiceanagrafe.ToString() + ") non trovato nella tabella ANAGRAFE"));
                }
            }
            #endregion

        }

        private MercatiPresenzeStorico DataIntegrations(MercatiPresenzeStorico cls)
        {
            #region 1.	Anagrafe.
            if (cls.Codiceanagrafe.GetValueOrDefault(int.MinValue) == int.MinValue && cls.Anagrafe != null)
            {
                if (!string.IsNullOrEmpty(cls.Anagrafe.IDCOMUNE) && cls.Anagrafe.IDCOMUNE != cls.Idcomune)
                    throw new Exceptions.IncongruentDataException("ANAGRAFE.IDCOMUNE diverso da MERCATIPRESENZE_STORICO.IDCOMUNE");

                if (string.IsNullOrEmpty(cls.Anagrafe.IDCOMUNE))
                    cls.Anagrafe.IDCOMUNE = cls.Idcomune;

                if (string.IsNullOrEmpty(cls.Anagrafe.TIPOLOGIA))
                    cls.Anagrafe.TIPOLOGIA = "0";

                Anagrafe ana = this.InsertUpdateAnagrafeDataClass(cls.Anagrafe);

                if (ana != null && !string.IsNullOrEmpty(ana.CODICEANAGRAFE))
                {
                    cls.Codiceanagrafe = Convert.ToInt32(ana.CODICEANAGRAFE);
                }
                else
                {
                    cls.Codiceanagrafe = null;
                }

            }



            #endregion

            if (cls.Fkidmercatiuso.GetValueOrDefault(int.MinValue) == int.MinValue && cls.Fkcodicemercato > int.MinValue)
            {
                Mercati_Uso m = new Mercati_Uso();
                m.IdComune = cls.Idcomune;
                m.FkCodiceMercato = cls.Fkcodicemercato;

                List<Mercati_Uso> l = new Mercati_UsoMgr(this.db).GetList(m);
                if (l.Count == 1)
                    cls.Fkidmercatiuso = l[0].Id;

            }

            return cls;
        }

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
                richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, this.UpdateAnagrafe);

                if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
                {
                    //Se l'anagrafica non è stata trovata viene inserita.
                    richiedente = anagrafeMgr.Insert(anagrafeDataClass);
                }
            }
            catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew)
            {
                if (this.ForzaInserimentoAnagrafe)
                {
                    Anagrafe anagDisabilitata = (anagrafeDataClass.Clone() as Anagrafe);
                    anagDisabilitata.FLAG_DISABILITATO = "1";
                    anagDisabilitata.DATA_DISABILITATO = DateTime.Now.Date;

                    try
                    {
                        richiedente = anagrafeMgr.Extract(anagDisabilitata, false, this.UpdateAnagrafe);

                        if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
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
    }
}
