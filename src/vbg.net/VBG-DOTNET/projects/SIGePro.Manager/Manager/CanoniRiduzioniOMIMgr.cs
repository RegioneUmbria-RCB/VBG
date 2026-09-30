using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Validator;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CanoniRiduzioniOMIMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CanoniRiduzioniOMI> Find(string token, string software)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

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
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }

        private void VerificaRecordCollegati(CanoniRiduzioniOMI cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CRID", cls.Id.ToString())
            };
            if (this.recordCount("PERTINENZE_COEFFICIENTI", "FK_CRID", conditions) > 0)
                throw new ReferentialIntegrityException("PERTINENZE_COEFFICIENTI");
        }
    }
}
