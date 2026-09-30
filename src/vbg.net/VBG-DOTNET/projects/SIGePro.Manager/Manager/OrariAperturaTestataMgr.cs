using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per OrariAperturaMgr.\n	/// </summary>
    public class OrariAperturaTestataMgr : BaseManager
    {
        public OrariAperturaTestataMgr(DataBase dataBase) : base(dataBase) { }

        public OrariAperturaTestata GetById(String pID, String pIDCOMUNE)
        {
            OrariAperturaTestata retVal = new OrariAperturaTestata();
            retVal.ID = pID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public OrariAperturaTestata Insert(OrariAperturaTestata p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            p_class = this.ChildDataIntegrations(p_class);

            this.ChildInsert(p_class);

            return p_class;
        }

        private void Validate(OrariAperturaTestata p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(OrariAperturaTestata p_class)
        {
            #region ISTANZE.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("ISTANZE", "CODICEISTANZA", conditions) == 0)
                {
                    throw (new RecordNotfoundException("ORARIAPERTURATESTATA.CODICEISTANZA (" + p_class.CODICEISTANZA + ") non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region TIPIORARIO.TO_ID
            if (!String.IsNullOrEmpty(p_class.FKTOID))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("TO_ID", p_class.FKTOID),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };

                if (this.recordCount("TIPIORARIO", "TO_ID", conditions) == 0)
                {
                    throw (new RecordNotfoundException("ORARIAPERTURATESTATA.FKTOID (" + p_class.FKTOID + ") non trovato nella tabella TIPIORARIO"));
                }
            }
            #endregion


        }

        private OrariAperturaTestata ChildDataIntegrations(OrariAperturaTestata p_class)
        {
            OrariAperturaTestata retVal = (OrariAperturaTestata)p_class.Clone();

            #region ii. Integrazione delle classi figlio con i dati della classe padre

            #region 1.	OrariTestata

            foreach (OrariApertura orari in retVal.OrariApertura)
            {
                orari.OA_FKIDTESTATA = retVal.ID;

                if (String.IsNullOrEmpty(orari.IDCOMUNE))
                    orari.IDCOMUNE = retVal.IDCOMUNE;
                else if (orari.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper())
                    throw new Exceptions.IncongruentDataException("ORARIAPERTURA.IDCOMUNE diverso da ORARIAPERTURATESTATA.IDCOMUNE");
            }

            #endregion

            #endregion

            return retVal;
        }

        private void ChildInsert(OrariAperturaTestata p_class)
        {
            foreach (OrariApertura orari in p_class.OrariApertura)
            {
                OrariAperturaMgr pManager = new OrariAperturaMgr(this.db);
                pManager.Insert(orari);
            }
        }

        public void Delete(OrariAperturaTestata p_class)
        {
            this.VerificaRecordCollegati(p_class);

            this.EffettuaCancellazioneACascata(p_class);

            this.db.Delete(p_class);
        }

        private void VerificaRecordCollegati(OrariAperturaTestata cls)
        {
        }

        private void EffettuaCancellazioneACascata(OrariAperturaTestata cls)
        {
            #region ORARIAPERTURA
            OrariApertura oa = new OrariApertura();
            oa.IDCOMUNE = cls.IDCOMUNE;
            oa.OA_FKIDTESTATA = cls.ID;

            List<OrariApertura> lOrari = new OrariAperturaMgr(this.db).GetList(oa);
            foreach (OrariApertura orario in lOrari)
            {
                OrariAperturaMgr mgr = new OrariAperturaMgr(this.db);
                mgr.Delete(orario);
            }
            #endregion
        }

    }
}