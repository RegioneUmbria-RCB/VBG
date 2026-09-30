using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class ConcessioniUsoMgr : BaseManager
    {

        public ConcessioniUsoMgr(DataBase dataBase) : base(dataBase) { }

        public ConcessioniUso GetById(String pCODICE, String pIDCOMUNE)
        {
            ConcessioniUso retVal = new ConcessioniUso();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.CODICE = pCODICE;

            List<ConcessioniUso> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as ConcessioniUso;

            return null;
        }


        public void Delete(ConcessioniUso p_class)
        {
            this.db.Delete(p_class);
        }

        public ConcessioniUso Insert(ConcessioniUso p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private ConcessioniUso DataIntegrations(ConcessioniUso p_class)
        {
            ConcessioniUso retVal = (ConcessioniUso)p_class.Clone();

            if (this.IsStringEmpty(retVal.IDCOMUNE))
                throw new RequiredFieldException("CONCESSIONIUSO.IDCOMUNE obbligatorio");

            if (this.IsStringEmpty(retVal.SOFTWARE))
                throw new RequiredFieldException("CONCESSIONIUSO.SOFTWARE obbligatorio");

            return retVal;
        }



        private void Validate(ConcessioniUso p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);
        }
    }
}