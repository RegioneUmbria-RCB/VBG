using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCDestinazioniMgr
    {
        private void VerificaRecordCollegati(CCDestinazioni cls)
        {
            var conditionsCoeffContributo = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCDE_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_COEFFCONTRIBUTO", "FK_CCDE_ID", conditionsCoeffContributo) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIBUTO");

            var conditionsCoeffContribAttivita = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCDE_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_COEFFCONTRIB_ATTIVITA", "FK_CCDE_ID", conditionsCoeffContribAttivita) > 0)
                throw new ReferentialIntegrityException("CC_COEFFCONTRIB_ATTIVITA");

            var conditionsICalcoloTContributo = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_CCDE_ID", cls.Id.ToString())
            };
            if (this.recordCount("CC_ICALCOLO_TCONTRIBUTO", "FK_CCDE_ID", conditionsICalcoloTContributo) > 0)
                throw new ReferentialIntegrityException("CC_ICALCOLO_TCONTRIBUTO");
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCDestinazioni> Find(string token, int? id, string destinazione, string destinazioneBase, string software, string sortExpression)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            var filtro = new CCDestinazioni();
            var filtroCompare = new CCDestinazioni();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Destinazione = destinazione;
            filtro.FkOccbdeId = destinazioneBase;
            filtro.Software = software;
            filtro.Id = id;
            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Destinazione = "LIKE";

            var list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<CCDestinazioni>();

            ListSortManager<CCDestinazioni>.Sort(list, sortExpression);

            return list;
        }

        public List<OCCBaseDestinazioni> GetBaseDestinazioniList(string idcomune)
        {
            FormattableString sql = $@"
            select distinct 
                OCC_BASEDESTINAZIONI.ID, 
                OCC_BASEDESTINAZIONI.DESTINAZIONE 
            from 
                OCC_BASEDESTINAZIONI, 
                CC_DESTINAZIONI 
            where 
                OCC_BASEDESTINAZIONI.ID=CC_DESTINAZIONI.FK_OCCBDE_ID and 
                CC_DESTINAZIONI.IDCOMUNE={idcomune}
            order by 
                OCC_BASEDESTINAZIONI.DESTINAZIONE asc";

            return this.db.GetClassList<OCCBaseDestinazioni>(sql);
        }


        public IEnumerable<CCDestinazioni> GetListByDestinazioneBase(string idComune, string idDestinazioneBase)
        {
            FormattableString sql = $"select * from CC_DESTINAZIONI where IDCOMUNE = {idComune} and FK_OCCBDE_ID = {idDestinazioneBase}";

            return this.db.GetClassList<CCDestinazioni>(sql);
        }
    }
}
