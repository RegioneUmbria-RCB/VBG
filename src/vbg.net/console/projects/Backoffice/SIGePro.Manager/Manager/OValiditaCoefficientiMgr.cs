using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions;

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

			return GetList(idComune, id, descrizione, DateTime.MinValue, software);
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

			return authInfo.CreateDatabase().GetClassList(filtro).ToList < OValiditaCoefficienti>();
        }

		public OValiditaCoefficienti GetCoefficienteAllaData(string idComune, string software, DateTime data)
		{
			List<OValiditaCoefficienti> coeff = GetList(idComune, software , null , null);

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
            if (recordCount("O_TABELLAABC", "FK_OVC_ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' and FK_OVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");

            if (recordCount("O_TABELLAD", "FK_OVC_ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' and FK_OVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            if (recordCount("O_ICALCOLOTOT", "FK_OVC_ID", "WHERE IDCOMUNE = '" + cls.Idcomune + "' and FK_OVC_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOTOT");
        }
	}
}
