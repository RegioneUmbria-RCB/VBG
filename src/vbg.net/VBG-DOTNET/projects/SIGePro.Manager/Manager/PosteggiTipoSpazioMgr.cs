using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class PosteggiTipoSpazioMgr : BaseManager
    {

        public PosteggiTipoSpazioMgr(DataBase dataBase) : base(dataBase) { }

        public PosteggiTipoSpazio GetById(String pCODICE, String pIDCOMUNE)
        {
            PosteggiTipoSpazio retVal = new PosteggiTipoSpazio();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.CODICE = pCODICE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }


        public List<PosteggiTipoSpazio> GetList(PosteggiTipoSpazio p_class)
        {
            return this.db.GetClassList(p_class).ToList<PosteggiTipoSpazio>();
        }

        public void Delete(PosteggiTipoSpazio p_class)
        {
            this.db.Delete(p_class);
        }

        public PosteggiTipoSpazio Insert(PosteggiTipoSpazio p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private PosteggiTipoSpazio DataIntegrations(PosteggiTipoSpazio p_class)
        {
            PosteggiTipoSpazio retVal = (PosteggiTipoSpazio)p_class.Clone();

            if (String.IsNullOrEmpty(retVal.IDCOMUNE))
                throw new RequiredFieldException("POSTEGGITIPOSPAZIO.IDCOMUNE obbligatorio");

            return retVal;
        }



        private void Validate(PosteggiTipoSpazio p_class, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);
        }
    }
}