using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{

    public class Mercati_DMgr : BaseManager
    {
        public Mercati_DMgr(DataBase dataBase) : base(dataBase) { }

        public Mercati_D GetByClass(Mercati_D cls)
        {
            List<Mercati_D> mydc = this.db.GetClassList(cls, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Mercati_D;

            return null;
        }

        public Mercati_D GetById(int pIDPOSTEGGIO, String pIDCOMUNE)
        {
            Mercati_D retVal = new Mercati_D();
            retVal.IDPOSTEGGIO = pIDPOSTEGGIO;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<Mercati_D> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Mercati_D;

            return null;
        }

        public List<Mercati_D> GetList(Mercati_D p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<Mercati_D> GetList(Mercati_D p_class, Mercati_D p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false);
        }

        public void Delete(Mercati_D p_class)
        {
            this.db.Delete(p_class);
        }

        public Mercati_D Insert(Mercati_D p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            p_class = this.ChildDataIntegrations(p_class);

            this.ChildInsert(p_class);

            return p_class;
        }

        private Mercati_D DataIntegrations(Mercati_D p_class)
        {
            Mercati_D retVal = (Mercati_D)p_class.Clone();

            if (this.IsStringEmpty(retVal.IDCOMUNE))
                throw new RequiredFieldException("MERCATI.IDCOMUNE obbligatorio");

            if (retVal.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) == int.MinValue)
                throw new RequiredFieldException("MERCATI_D.FKCODICEMERCATO obbligatorio");

            if (this.IsStringEmpty(retVal.CODICEPOSTEGGIO))
                throw new RequiredFieldException("MERCATI_D.CODICEPOSTEGGIO obbligatorio");

            if (this.IsStringEmpty(retVal.DISABILITATO))
                retVal.DISABILITATO = "0";

            return retVal;
        }

        private Mercati_D ChildDataIntegrations(Mercati_D p_class)
        {
            Mercati_D retVal = (Mercati_D)p_class.Clone();

            #region ii. Integrazione delle classi figlio con i dati della classe padre

            #region 1.	Mercati_DAttivitaIstat

            foreach (Mercati_DAttivitaIstat p_att in retVal.AttivitaIstat)
            {
                if (this.IsStringEmpty(p_att.IDCOMUNE))
                    p_att.IDCOMUNE = retVal.IDCOMUNE;
                else if (p_att.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper())
                    throw new Exceptions.IncongruentDataException("MERCATI_DATTIVITAISTAT.IDCOMUNE diverso da MERCATI_D.IDCOMUNE");


                if (p_att.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
                    p_att.FKCODICEMERCATO = retVal.FKCODICEMERCATO;

                if (p_att.FKIDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue)
                    p_att.FKIDPOSTEGGIO = retVal.IDPOSTEGGIO;

            }

            #endregion

            #region 2.	Mercati_DConti

            foreach (Mercati_DConti conto in retVal.Conti)
            {
                if (string.IsNullOrEmpty(retVal.IDCOMUNE))
                    conto.Idcomune = retVal.IDCOMUNE;
                else if (conto.Idcomune.ToUpper() != retVal.IDCOMUNE.ToUpper())
                    throw new Exceptions.IncongruentDataException("MERCATI_D_CONTI.IDCOMUNE diverso da MERCATI_D.IDCOMUNE");

                if (conto.FkMdId.GetValueOrDefault(int.MinValue) == int.MinValue)
                    conto.FkMdId = retVal.IDPOSTEGGIO;
                else if (conto.FkMdId != retVal.IDPOSTEGGIO)
                    throw new Exceptions.IncongruentDataException("MERCATI_D_CONTI.FK_MDID diverso da MERCATI_D.IDPOSTEGGIO");

            }

            #endregion


            #endregion

            return retVal;
        }

        private void ChildInsert(Mercati_D p_class)
        {
            for (int i = 0; i < p_class.AttivitaIstat.Count; i++)
            {
                Mercati_DAttivitaIstatMgr pManager = new Mercati_DAttivitaIstatMgr(this.db);
                pManager.Insert(p_class.AttivitaIstat[i]);
            }

            for (int i = 0; i < p_class.Conti.Count; i++)
            {
                Mercati_DContiMgr pManager = new Mercati_DContiMgr(this.db);
                pManager.Insert(p_class.Conti[i]);
            }
        }

        private void Validate(Mercati_D p_class, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(Mercati_D p_class)
        {
            #region MERCATI_D.FKCODICEMERCATO
            if (p_class.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("MERCATI", "CODICEMERCATO", "WHERE CODICEMERCATO = " + p_class.FKCODICEMERCATO.ToString() + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_D.FKCODICEMERCATO (" + p_class.FKCODICEMERCATO.ToString() + ") non trovato nella tabella MERCATI"));
                }
            }
            #endregion

            #region MERCATI_D.FKCODICETIPOSPAZIO
            if (!this.IsStringEmpty(p_class.FKCODICETIPOSPAZIO))
            {
                if (this.recordCount("POSTEGGITIPOSPAZIO", "CODICE", "WHERE CODICE = " + p_class.FKCODICETIPOSPAZIO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_D.FKCODICETIPOSPAZIO (" + p_class.FKCODICETIPOSPAZIO + ") non trovato nella tabella POSTEGGITIPOSPAZIO"));
                }
            }
            #endregion
        }

        public List<Occupanti> GetOccupanti(string idComune, int idMercato, int idPosteggio, int idUso)
        {
            List<Occupanti> retVal = new List<Occupanti>();

            string cmdText = "select " +
                                 "istanze.codicerichiedente, richiedente.nominativo || ' ' || richiedente.nome as richiedente, " +
                                 "istanze.codicetitolarelegale, azienda.nominativo || ' ' || azienda.nome as azienda, " +
                                 "mercati_uso.id,  mercati_uso.descrizione " +
                             "from " +
                                "istanze, istanzeconcessioni, concessioni, mercati_uso, anagrafe richiedente, anagrafe azienda " +
                             "where " +
                                "richiedente.idcomune = istanze.idcomune and " +
                                "richiedente.codiceanagrafe = istanze.codicerichiedente and " +
                                "azienda.idcomune(+) = istanze.idcomune and " +
                                "azienda.codiceanagrafe(+) = istanze.codicetitolarelegale and " +
                                "mercati_uso.idcomune = concessioni.idcomune and " +
                                "mercati_uso.id = concessioni.fk_idmercatiuso and " +
                                "istanzeconcessioni.idcomune = istanze.idcomune and " +
                                "istanzeconcessioni.fkcodiceistanza = istanze.codiceistanza and " +
                                "istanzeconcessioni.fkcodicecausalestorico is null and " +
                                "concessioni.idcomune = istanzeconcessioni.idcomune and " +
                                "concessioni.id = istanzeconcessioni.fkidconcessione and " +
                                "concessioni.idcomune = '" + idComune + "' and " +
                                "concessioni.fk_codicemercato = " + idMercato.ToString() + " and " +
                                "concessioni.fk_idposteggio = " + idPosteggio.ToString() + " and " +
                                "concessioni.attiva = 1";
            if (idUso > int.MinValue)
            {
                cmdText += " and concessioni.fk_idmercatiuso= " + idUso.ToString();
            }

            this.db.Connection.Open();

            using (IDataReader rd = (this.db.CreateCommand(cmdText).ExecuteReader()))
            {
                while (rd.Read())
                {
                    Occupanti o = new Occupanti();
                    if (rd["codicetitolarelegale"] == DBNull.Value)
                    {
                        o.CodiceAnagrafe = Convert.ToInt32(rd["codicerichiedente"].ToString());
                        o.Nominativo = rd["richiedente"].ToString();
                    }
                    else
                    {
                        o.CodiceAnagrafe = Convert.ToInt32(rd["codicetitolarelegale"].ToString());
                        o.Nominativo = rd["azienda"].ToString();
                    }

                    o.CodiceUso = Convert.ToInt32(rd["id"].ToString());
                    o.Uso = rd["descrizione"].ToString();

                    retVal.Add(o);
                }
            }

            this.db.Connection.Close();

            return retVal;
        }
    }

    public class Occupanti
    {
        private int? m_codiceanagrafe = null;
        public int? CodiceAnagrafe
        {
            get { return this.m_codiceanagrafe; }
            set { this.m_codiceanagrafe = value; }
        }

        private string m_nominativo = null;
        public string Nominativo
        {
            get { return this.m_nominativo; }
            set { this.m_nominativo = value; }
        }

        private int? m_codiceuso = null;
        public int? CodiceUso
        {
            get { return this.m_codiceuso; }
            set { this.m_codiceuso = value; }
        }

        private string m_uso = null;
        public string Uso
        {
            get { return this.m_uso; }
            set { this.m_uso = value; }
        }

    }
}