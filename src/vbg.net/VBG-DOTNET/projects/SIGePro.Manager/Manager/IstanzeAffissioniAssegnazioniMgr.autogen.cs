using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

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

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }


        public List<IstanzeAffissioniAssegnazioni> GetList(IstanzeAffissioniAssegnazioni p_class)
        {
            return this.db.GetClassList(p_class);
        }

        public void Delete(IstanzeAffissioniAssegnazioni p_class)
        {
            this.db.Delete(p_class);
        }

        public IstanzeAffissioniAssegnazioni Insert(IstanzeAffissioniAssegnazioni p_class)
        {

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private void Validate(IstanzeAffissioniAssegnazioni p_class, AmbitoValidazione ambitoValidazione)
        {

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);

        }

        private void ForeignValidate(IstanzeAffissioniAssegnazioni p_class)
        {

        }

    }
}