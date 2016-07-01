using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;


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

            RequiredFieldValidate(cls, ambitoValidazione);
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CanoniTipiSuperfici> Find(string token, string software, string tipoSuperficie, string tipoCalcolo, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CanoniTipiSuperfici filtro = new CanoniTipiSuperfici();
            CanoniTipiSuperfici filtroCompare = new CanoniTipiSuperfici();

            filtro.Idcomune = authInfo.IdComune;
            filtro.TipoSuperficie = tipoSuperficie;
            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;
            filtro.Tipocalcolo = tipoCalcolo;

            filtroCompare.TipoSuperficie = "LIKE";

            List<CanoniTipiSuperfici> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<CanoniTipiSuperfici>();
            ListSortManager<CanoniTipiSuperfici>.Sort(list, sortExpression);

            return list;
        }

        private void VerificaRecordCollegati(CanoniTipiSuperfici cls)
        {
            if (recordCount("CANONI_COEFFICIENTI", "FK_TSID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_TSID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CANONI_COEFFICIENTI");

            if (recordCount("PERTINENZE_COEFFICIENTI", "FK_TSID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_TSID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("PERTINENZE_COEFFICIENTI");

            if (recordCount("ISTANZECALCOLOCANONI_D", "FK_TSID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_TSID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("ISTANZECALCOLOCANONI_D");
        }

        public bool isUsed(string idComune, string id)
        {
            if (recordCount("CANONI_COEFFICIENTI", "FK_TSID", "where IDCOMUNE = '" + idComune + "' and FK_TSID = " + id) > 0)
                return true;

            if (recordCount("PERTINENZE_COEFFICIENTI", "FK_TSID", "where IDCOMUNE = '" + idComune + "' and FK_TSID = " + id) > 0)
                return true;

            return false;
        }
    }
}
