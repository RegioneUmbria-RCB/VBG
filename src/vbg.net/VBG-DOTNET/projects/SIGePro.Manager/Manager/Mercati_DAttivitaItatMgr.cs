using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class Mercati_DAttivitaIstatMgr : BaseManager
    {

        public Mercati_DAttivitaIstatMgr(DataBase dataBase) : base(dataBase) { }

        public Mercati_DAttivitaIstat GetById(int pFKCODICEMERCATO, int pFKIDPOSTEGGIO, String pFKCODICEATTIVITAISTAT, String pIDCOMUNE)
        {
            Mercati_DAttivitaIstat retVal = new Mercati_DAttivitaIstat();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.FKCODICEMERCATO = pFKCODICEMERCATO;
            retVal.FKIDPOSTEGGIO = pFKIDPOSTEGGIO;
            retVal.FkCodiceAttivitaIstat = pFKCODICEATTIVITAISTAT;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }


        public List<Mercati_DAttivitaIstat> GetList(Mercati_DAttivitaIstat p_class, useForeignEnum foreignEnum)
        {
            p_class.UseForeign = foreignEnum;
            return this.db.GetClassList(p_class);
        }

        public List<Mercati_DAttivitaIstat> GetList(Mercati_DAttivitaIstat p_class)
        {
            return this.GetList(p_class, useForeignEnum.Yes);
        }

        public void Delete(Mercati_DAttivitaIstat p_class)
        {
            this.db.Delete(p_class);
        }

        public Mercati_DAttivitaIstat Insert(Mercati_DAttivitaIstat p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private Mercati_DAttivitaIstat DataIntegrations(Mercati_DAttivitaIstat p_class)
        {
            Mercati_DAttivitaIstat retVal = (Mercati_DAttivitaIstat)p_class.Clone();

            if (String.IsNullOrEmpty(retVal.IDCOMUNE))
                throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.IDCOMUNE obbligatorio");

            if (retVal.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) == int.MinValue)
                throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.FKCODICEMERCATO obbligatorio");

            if (retVal.FKIDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue)
                throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.FKIDPOSTEGGIO obbligatorio");

            if (String.IsNullOrEmpty(retVal.FkCodiceAttivitaIstat))
                throw new RequiredFieldException("MERCATI_DATTIVITAISTAT.FKCODICEATTIVITAISTAT obbligatorio");

            if (String.IsNullOrEmpty(retVal.Flag_Consentito))
                retVal.Flag_Consentito = "1";

            return retVal;
        }



        private void Validate(Mercati_DAttivitaIstat p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(Mercati_DAttivitaIstat p_class)
        {
            #region MERCATI_DATTIVITAISTAT.FKCODICEMERCATO
            if (p_class.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEMERCATO", p_class.FKCODICEMERCATO.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("MERCATI", "CODICEMERCATO", conditions) == 0)
                {
                    throw (new RecordNotfoundException($"MERCATI_DATTIVITAISTAT.FKCODICEMERCATO ({p_class.FKCODICEMERCATO}) non trovato nella tabella MERCATI"));
                }
            }
            #endregion

            #region MERCATI_DATTIVITAISTAT.FKIDPOSTEGGIO
            if (p_class.FKIDPOSTEGGIO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDPOSTEGGIO", p_class.FKIDPOSTEGGIO.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("MERCATI_D", "IDPOSTEGGIO", conditions) == 0)
                {
                    throw (new RecordNotfoundException($"MERCATI_DATTIVITAISTAT.FKIDPOSTEGGIO ({p_class.FKIDPOSTEGGIO}) non trovato nella tabella MERCATI_D"));
                }
            }
            #endregion

            #region MERCATI_DATTIVITAISTAT.FKCODICEATTIVITAISTAT
            if (!String.IsNullOrEmpty(p_class.FkCodiceAttivitaIstat))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTAT", p_class.FkCodiceAttivitaIstat),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ATTIVITA", "CODICEISTAT", conditions) == 0)
                {
                    throw (new RecordNotfoundException($"MERCATI_DATTIVITAISTAT.FKCODICEATTIVITAISTAT ({p_class.FkCodiceAttivitaIstat}) non trovato nella tabella ATTIVITA"));
                }
            }
            #endregion
        }

    }
}