using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per EsportazioniMgr.
    /// </summary>
    public class EsportazioniMgr : BaseManager
    {
        public EsportazioniMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public ESPORTAZIONI GetById(String pIDCOMUNE, String pID)
        {
            return this.GetById(pIDCOMUNE, pID, false);
        }

        public ESPORTAZIONI GetById(String pIDCOMUNE, String pID, bool AddChild)
        {
            ESPORTAZIONI retVal = new ESPORTAZIONI
            {
                IDCOMUNE = pIDCOMUNE,
                ID = pID
            };

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
            {
                retVal = mydc[0];

                if (AddChild)
                {
                    PARAMETRIESPORTAZIONE pe = new PARAMETRIESPORTAZIONE
                    {
                        IDCOMUNE = pIDCOMUNE,
                        FK_ESP_ID = pID
                    };
                    retVal.Parametri = new ParametriEsportazioneMgr(this.db).GetList(pe);

                    TRACCIATI t = new TRACCIATI
                    {
                        IDCOMUNE = pIDCOMUNE,
                        FK_ESP_ID = pID
                    };
                    retVal.Tracciati = new TracciatiMgr(this.db).GetList(t, AddChild);

                }

                return retVal;
            }

            return null;
        }

        public ESPORTAZIONI GetByClass(ESPORTAZIONI p_class)
        {
            var mydc = this.db.GetClassList(p_class, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<ESPORTAZIONI> GetList(ESPORTAZIONI p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<ESPORTAZIONI> GetList(ESPORTAZIONI p_class, ESPORTAZIONI p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass);
        }


        public ESPORTAZIONI Insert(ESPORTAZIONI p_class)
        {
            p_class.ID = (this.findMax("ESPORTAZIONI", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1).ToString();
            this.db.Insert(p_class);
            return p_class;
        }

        public ESPORTAZIONI InsertAll(ESPORTAZIONI p_class)
        {
            p_class = this.Insert(p_class);

            foreach (PARAMETRIESPORTAZIONE pe in p_class.Parametri)
            {
                pe.IDCOMUNE = p_class.IDCOMUNE;
                pe.FK_ESP_ID = p_class.ID;

                new ParametriEsportazioneMgr(this.db).Insert(pe);
            }

            foreach (TRACCIATI t in p_class.Tracciati)
            {
                t.IDCOMUNE = p_class.IDCOMUNE;
                t.FK_ESP_ID = p_class.ID;

                new TracciatiMgr(this.db).InsertAll(t);
            }

            return p_class;
        }

        public ESPORTAZIONI Delete(ESPORTAZIONI p_class)
        {
            this.db.BeginTransaction();

            //cancello i parametri
            PARAMETRIESPORTAZIONE pe = new PARAMETRIESPORTAZIONE
            {
                IDCOMUNE = p_class.IDCOMUNE,
                FK_ESP_ID = p_class.ID
            };

            ParametriEsportazioneMgr pem = new ParametriEsportazioneMgr(this.db);
            List<PARAMETRIESPORTAZIONE> parametri = pem.GetList(pe);
            foreach (PARAMETRIESPORTAZIONE parametro in parametri)
                pem.Delete(parametro);


            //cancello i tracciati
            TRACCIATI t = new TRACCIATI
            {
                IDCOMUNE = p_class.IDCOMUNE,
                FK_ESP_ID = p_class.ID
            };

            TracciatiMgr tm = new TracciatiMgr(this.db);
            List<TRACCIATI> tracciati = tm.GetList(t);
            foreach (TRACCIATI tracciato in tracciati)
                tm.Delete(tracciato);

            //cancello l'esportazione
            this.db.Delete(p_class);

            this.db.CommitTransaction();

            return p_class;
        }

        public ESPORTAZIONI Update(ESPORTAZIONI p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }

        public ESPORTAZIONI ReplicaEsportazione(String idEnteOrigine, String idExportOrigine, String idEnteDestinazione)
        {
            this.db.BeginTransaction();

            //inserisco la nuova esportazione copiandola dall'ente di origine
            ESPORTAZIONI exp = this.GetById(idEnteOrigine, idExportOrigine);
            exp.IDCOMUNE = idEnteDestinazione;
            exp.ID = null;
            exp = this.Insert(exp);

            //replico i parametri nella nuova esportazione
            ParametriEsportazioneMgr pem = new ParametriEsportazioneMgr(this.db);
            PARAMETRIESPORTAZIONE pe = new PARAMETRIESPORTAZIONE
            {
                IDCOMUNE = idEnteOrigine,
                FK_ESP_ID = idExportOrigine
            };
            List<PARAMETRIESPORTAZIONE> parametri = pem.GetList(pe);
            foreach (PARAMETRIESPORTAZIONE par in parametri)
            {
                par.IDCOMUNE = exp.IDCOMUNE;
                par.FK_ESP_ID = exp.ID;
                par.ID = null;

                pem.Insert(par);
            }

            //replico i tracciati nella nuova esportazione
            TracciatiMgr tm = new TracciatiMgr(this.db);
            TRACCIATI t = new TRACCIATI
            {
                IDCOMUNE = idEnteOrigine,
                FK_ESP_ID = idExportOrigine
            };
            List<TRACCIATI> tracciati = tm.GetList(t);
            foreach (TRACCIATI trac in tracciati)
            {
                //inserisco il tracciato
                TRACCIATI newTrac = (TRACCIATI)trac.Clone();
                newTrac.IDCOMUNE = exp.IDCOMUNE;
                newTrac.FK_ESP_ID = exp.ID;
                newTrac.ID = null;
                newTrac = tm.Insert(newTrac);

                //recupero i suoi dettagli
                TRACCIATIDETTAGLIO td = new TRACCIATIDETTAGLIO
                {
                    IDCOMUNE = trac.IDCOMUNE,
                    FK_TRACCIATI_ID = trac.ID
                };
                TracciatiDettMgr tdm = new TracciatiDettMgr(this.db);
                List<TRACCIATIDETTAGLIO> dettagli = tdm.GetList(td);

                //e li inserisco nel nuovo tracciato
                foreach (TRACCIATIDETTAGLIO dett in dettagli)
                {
                    dett.IDCOMUNE = newTrac.IDCOMUNE;
                    dett.FK_TRACCIATI_ID = newTrac.ID;
                    dett.ID = null;

                    tdm.Insert(dett);
                }
            }

            this.db.CommitTransaction();
            return exp;
        }

        #endregion
    }
}
