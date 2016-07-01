using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager
{

    public class IstanzeAffissioniAssegnazioniMgr : BaseManager
    {

        public IstanzeAffissioniAssegnazioniMgr(DataBase dataBase) : base(dataBase) { }


        public IstanzeAffissioniAssegnazioni GetById(string IdComune, int CodiceIstanza, int FkIstanzeAffissioniId, int FkImpiantoPubblicitario, int FkImpiantoPubblicitarioDet)
        {
            IstanzeAffissioniAssegnazioni retVal = new IstanzeAffissioniAssegnazioni();

            retVal.IDCOMUNE = IdComune;
            retVal.CODICEISTANZA = CodiceIstanza.ToString();
            retVal.FK_ISTANZEAFFISSIONIID = FkIstanzeAffissioniId.ToString();
            retVal.FK_IMPIANTOPUBBLICITARIO = FkImpiantoPubblicitario.ToString();
            retVal.FK_IMPIANTOPUBBLICITARIOIDDETT = FkImpiantoPubblicitarioDet.ToString();

            DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeAffissioniAssegnazioni;

            return null;
        }


        public List<IstanzeAffissioniAssegnazioni> GetList(IstanzeAffissioniAssegnazioni p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<IstanzeAffissioniAssegnazioni> GetList(IstanzeAffissioniAssegnazioni p_class, IstanzeAffissioniAssegnazioni p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeAffissioniAssegnazioni>();
        }

        public void Delete(IstanzeAffissioniAssegnazioni p_class)
        {
            db.Delete(p_class);
        }

        public IstanzeAffissioniAssegnazioni Insert(IstanzeAffissioniAssegnazioni p_class)
        {

            Validate(p_class, AmbitoValidazione.Insert);

            db.Insert(p_class);

            return p_class;
        }

        private void Validate(IstanzeAffissioniAssegnazioni p_class, AmbitoValidazione ambitoValidazione)
        {

            RequiredFieldValidate(p_class, ambitoValidazione);

            ForeignValidate(p_class);

        }

        private void ForeignValidate(IstanzeAffissioniAssegnazioni p_class)
        {

        }

    }
}