using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCValiditaCoefficientiMgr
    {
        private void VerificaRecordCollegati(CCValiditaCoefficienti cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCVC_ID", cls.Id.ToString())
            };

            if (this.db.LegacyRecordCount("CC_COEFFCONTRIBUTO", "FK_CCVC_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIBUTO");

            if (this.db.LegacyRecordCount("CC_COEFFCONTRIB_ATTIVITA", "FK_CCVC_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIB_ATTIVITA");

            if (this.db.LegacyRecordCount("CC_ICALCOLOTOT", "FK_CCVC_ID", conditions) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLOTOT");
        }


        public IEnumerable<CCValiditaCoefficienti> GetList(string idComune, string software)
        {
            FormattableString sql = $@"SELECT * FROM CC_VALIDITACOEFFICIENTI WHERE IDCOMUNE = {idComune} AND SOFTWARE={software} ORDER BY DATAINIZIOVALIDITA DESC";

            return this.db.GetClassList<CCValiditaCoefficienti>(sql);
        }

        public CCValiditaCoefficienti GetCoefficienteAllaData(string idComune, string software, DateTime data)
        {
            var coeff = this.GetList(idComune, software);

            CCValiditaCoefficienti ret = null;

            foreach (var c in coeff)
            {
                if (c.Datainiziovalidita.GetValueOrDefault(DateTime.MinValue).Date > data.Date) continue;

                if (ret == null || c.Datainiziovalidita >= ret.Datainiziovalidita)
                    ret = c;
            }

            return ret;
        }

        //[DataObjectMethod(DataObjectMethodType.Select)]
        //public static List<CCValiditaCoefficienti> Find(string token, int? id, string descrizione, DateTime dataDa, DateTime dataA, float costomq_da, float costomq_a, string software, string sortExpression)
        //{
        //    AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

        //    var filtro = new CCValiditaCoefficienti();
        //    var filtroCompare = new CCValiditaCoefficienti();

        //    filtro.Idcomune = authInfo.IdComune;
        //    filtro.Descrizione = descrizione;
        //    filtro.Software = software;
        //    filtro.Id = id;
        //    filtro.Data_da = dataDa;
        //    filtro.Data_a = dataA;
        //    filtro.Costomq_da = costomq_da;
        //    filtro.Costomq_a = costomq_a;

        //    filtroCompare.Descrizione = "LIKE";

        //    var list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CCValiditaCoefficienti>();
        //    ListSortManager<CCValiditaCoefficienti>.Sort(list, sortExpression);

        //    return list;
        //}
    }
}
