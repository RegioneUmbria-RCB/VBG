using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;
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
            if (this.recordCount("CC_COEFFCONTRIBUTO", "FK_CCVC_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIBUTO");

            if (this.recordCount("CC_COEFFCONTRIB_ATTIVITA", "FK_CCVC_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIB_ATTIVITA");

            if (this.recordCount("CC_ICALCOLOTOT", "FK_CCVC_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_CCVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLOTOT");
        }


        public List<CCValiditaCoefficienti> GetList(string idComune, string software)
        {
            CCValiditaCoefficienti c = new CCValiditaCoefficienti();
            c.Idcomune = idComune;
            c.Software = software;
            c.OrderBy = "DATAINIZIOVALIDITA DESC";

            return this.GetList(c);
        }

        public CCValiditaCoefficienti GetCoefficienteAllaData(string idComune, string software, DateTime data)
        {
            List<CCValiditaCoefficienti> coeff = this.GetList(idComune, software);

            CCValiditaCoefficienti ret = null;

            foreach (CCValiditaCoefficienti c in coeff)
            {
                if (c.Datainiziovalidita.GetValueOrDefault(DateTime.MinValue).Date > data.Date) continue;

                if (ret == null || c.Datainiziovalidita >= ret.Datainiziovalidita)
                    ret = c;
            }

            return ret;
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCValiditaCoefficienti> Find(string token, int? id, string descrizione, DateTime dataDa, DateTime dataA, float costomq_da, float costomq_a, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCValiditaCoefficienti filtro = new CCValiditaCoefficienti();
            CCValiditaCoefficienti filtroCompare = new CCValiditaCoefficienti();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            filtro.Software = software;
            filtro.Id = id;
            filtro.Data_da = dataDa;
            filtro.Data_a = dataA;
            filtro.Costomq_da = costomq_da;
            filtro.Costomq_a = costomq_a;

            filtroCompare.Descrizione = "LIKE";

            List<CCValiditaCoefficienti> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false);
            ListSortManager<CCValiditaCoefficienti>.Sort(list, sortExpression);

            return list;
        }
    }
}
