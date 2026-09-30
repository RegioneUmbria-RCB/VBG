using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class ODestinazioniMgr
    {
        private void VerificaRecordCollegati(ODestinazioni cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_ODE_ID", cls.Id.ToString())
            };

            if (this.recordCount("O_TABELLAABC", "FK_ODE_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");

            if (this.recordCount("O_TABELLAD", "FK_ODE_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            if (this.recordCount("O_ICALCOLO_DETTAGLIOT", "FK_ODE_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLO_DETTAGLIOT");

            if (this.recordCount("O_ICALCOLOCONTRIBR", "FK_ODE_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBR");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<ODestinazioni> Find(string token, int? codice, string destinazione, string destinazioneBase, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            ODestinazioni filtro = new ODestinazioni();
            ODestinazioni filtroCompare = new ODestinazioni();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Id = codice;
            filtro.Destinazione = destinazione;
            filtro.FkOccbdeId = destinazioneBase;
            filtro.Software = software;
            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Destinazione = "LIKE";

            List<ODestinazioni> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<ODestinazioni>();
            ListSortManager<ODestinazioni>.Sort(list, sortExpression);

            return list;
        }

        public List<ODestinazioni> GetListByBaseDestinazione(string idComune, string idDestinazioneBase)
        {
            ODestinazioni filtro = new ODestinazioni();
            filtro.Idcomune = idComune;
            filtro.FkOccbdeId = idDestinazioneBase;
            filtro.OrderBy = "Ordinamento asc";

            return this.GetList(filtro);
        }
    }
}
