using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OValiditaCoefficientiMgr
    {
        public List<OValiditaCoefficienti> GetList(string idComune, string software, string codice, string descrizione)
        {

            int id = int.MinValue;

            if (!String.IsNullOrEmpty(codice))
            {
                if (!int.TryParse(codice, out id))
                    id = int.MinValue;
            }

            return this.GetList(idComune, id, descrizione, DateTime.MinValue, software);
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OValiditaCoefficienti> Find(string token, string software, int? codice, string descrizione)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            OValiditaCoefficienti filtro = new OValiditaCoefficienti();
            OValiditaCoefficienti filtroCompare = new OValiditaCoefficienti();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Id = codice;
            filtro.Descrizione = descrizione;
            filtro.Software = software;

            filtroCompare.Descrizione = "Like";

            return authInfo.CreateDatabase().GetClassList(filtro);
        }

        public OValiditaCoefficienti GetCoefficienteAllaData(string idComune, string software, DateTime data)
        {
            List<OValiditaCoefficienti> coeff = this.GetList(idComune, software, null, null);

            OValiditaCoefficienti ret = null;

            foreach (OValiditaCoefficienti c in coeff)
            {
                if (c.Datainiziovalidita.GetValueOrDefault(DateTime.MinValue) > data.Date) continue;

                if (ret == null || c.Datainiziovalidita >= ret.Datainiziovalidita)
                    ret = c;
            }

            return ret;
        }

        private void VerificaRecordCollegati(OValiditaCoefficienti cls)
        {
            if (this.recordCount("O_TABELLAABC", "FK_OVC_ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' and FK_OVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");

            if (this.recordCount("O_TABELLAD", "FK_OVC_ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' and FK_OVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            if (this.recordCount("O_ICALCOLOTOT", "FK_OVC_ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' and FK_OVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOTOT");
        }
    }
}
