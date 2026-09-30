using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.Utils.Sorting;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CanoniRiduzioniOMIMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CanoniRiduzioniOMI> Find(string token, string software)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CanoniRiduzioniOMI filtro = new CanoniRiduzioniOMI();
            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;
            filtro.OrderBy = "MQ_A ASC";
            CanoniRiduzioniOMIMgr mgr = new CanoniRiduzioniOMIMgr(authInfo.CreateDatabase());

            List<CanoniRiduzioniOMI> list = mgr.GetList(filtro);

            return list;
        }

        private void Validate(CanoniRiduzioniOMI cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }

        private void VerificaRecordCollegati(CanoniRiduzioniOMI cls)
        {
            if (recordCount("PERTINENZE_COEFFICIENTI", "FK_CRID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CRID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("PERTINENZE_COEFFICIENTI");
        }
    }
}
