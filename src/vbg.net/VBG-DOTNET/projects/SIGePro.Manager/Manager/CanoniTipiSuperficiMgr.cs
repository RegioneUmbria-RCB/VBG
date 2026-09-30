using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Validator;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CanoniTipiSuperficiMgr
    {
        private CanoniTipiSuperfici DataIntegrations(CanoniTipiSuperfici cls)
        {
            if (cls.Pertinenza.GetValueOrDefault(int.MinValue) == int.MinValue)
                cls.Pertinenza = 0;

            return cls;
        }

        private void Validate(CanoniTipiSuperfici cls, AmbitoValidazione ambitoValidazione)
        {
            if (cls.Pertinenza != 0 && cls.Pertinenza != 1)
                throw new IncongruentDataException("CANONI_TIPISUPERFICI.PERTINENZA = " + cls.Pertinenza.ToString());

            this.RequiredFieldValidate(cls, ambitoValidazione);
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CanoniTipiSuperfici> Find(string token, string software, string tipoSuperficie, string tipoCalcolo, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CanoniTipiSuperfici filtro = new CanoniTipiSuperfici();
            CanoniTipiSuperfici filtroCompare = new CanoniTipiSuperfici();

            filtro.Idcomune = authInfo.IdComune;
            filtro.TipoSuperficie = tipoSuperficie;
            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;
            filtro.Tipocalcolo = tipoCalcolo;

            filtroCompare.TipoSuperficie = "LIKE";

            List<CanoniTipiSuperfici> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CanoniTipiSuperfici>();
            ListSortManager<CanoniTipiSuperfici>.Sort(list, sortExpression);

            return list;
        }

        private void VerificaRecordCollegati(CanoniTipiSuperfici cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_TSID", cls.Id.ToString())
            };
            if (this.recordCount("CANONI_COEFFICIENTI", "FK_TSID", conditions) > 0)
                throw new ReferentialIntegrityException("CANONI_COEFFICIENTI");

            if (this.recordCount("PERTINENZE_COEFFICIENTI", "FK_TSID", conditions) > 0)
                throw new ReferentialIntegrityException("PERTINENZE_COEFFICIENTI");

            if (this.recordCount("ISTANZECALCOLOCANONI_D", "FK_TSID", conditions) > 0)
                throw new ReferentialIntegrityException("ISTANZECALCOLOCANONI_D");
        }

        public bool isUsed(string idComune, string id)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", idComune),
                new KeyValuePair<string, string>("FK_TSID", id)
            };
            if (this.recordCount("CANONI_COEFFICIENTI", "FK_TSID", conditions) > 0)
                return true;

            if (this.recordCount("PERTINENZE_COEFFICIENTI", "FK_TSID", conditions) > 0)
                return true;

            return false;
        }
    }
}
