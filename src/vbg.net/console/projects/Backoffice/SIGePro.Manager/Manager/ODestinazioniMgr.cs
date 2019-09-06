using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.Utils.Sorting;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class ODestinazioniMgr
    {
        private void VerificaRecordCollegati(ODestinazioni cls)
        {
            if (recordCount("O_TABELLAABC", "FK_ODE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_ODE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");


            if (recordCount("O_TABELLAD", "FK_ODE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_ODE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            if (recordCount("O_ICALCOLO_DETTAGLIOT", "FK_ODE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_ODE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLO_DETTAGLIOT");

            if (recordCount("O_ICALCOLOCONTRIBR", "FK_ODE_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_ODE_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBR");
 
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<ODestinazioni> Find(string token, int? codice , string destinazione, string destinazioneBase, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            ODestinazioni filtro = new ODestinazioni();
            ODestinazioni filtroCompare = new ODestinazioni();
            
            filtro.Idcomune = authInfo.IdComune;
			filtro.Id = codice;
            filtro.Destinazione = destinazione;
            filtro.FkOccbdeId = destinazioneBase;
            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Destinazione = "LIKE";

            List<ODestinazioni> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false, true).ToList<ODestinazioni>();
            ListSortManager<ODestinazioni>.Sort(list, sortExpression);

            return list;
        }

		public List<ODestinazioni> GetListByBaseDestinazione(string idComune, string idDestinazioneBase)
		{
			ODestinazioni filtro = new ODestinazioni();
			filtro.Idcomune   = idComune;
			filtro.FkOccbdeId = idDestinazioneBase;
			filtro.OrderBy = "Ordinamento asc";

			return GetList(filtro);
		}
    }
}
