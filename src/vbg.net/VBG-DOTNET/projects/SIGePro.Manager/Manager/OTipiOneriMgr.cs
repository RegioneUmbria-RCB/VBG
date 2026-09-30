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
    public partial class OTipiOneriMgr
    {
        private void VerificaRecordCollegati(OTipiOneri cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("FK_OTO_ID", cls.Id.ToString())
            };

            if (this.recordCount("O_TABELLAABC", "FK_OTO_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");

            if (this.recordCount("O_TABELLAD", "FK_OTO_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            if (this.recordCount("O_ICALCOLOCONTRIBR", "FK_OTO_ID", conditions) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBR");

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OTipiOneri> Find(string token, int? codice, string descrizione, string descrizioneEstesa, string baseTipoOnere, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            OTipiOneri filtro = new OTipiOneri();
            OTipiOneri filtroCompare = new OTipiOneri();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Id = codice;
            filtro.Descrizione = descrizione;
            filtro.Descrizionelunga = descrizioneEstesa;
            filtro.FkBtoId = baseTipoOnere;

            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Descrizione = "Like";
            filtroCompare.Descrizionelunga = "Like";

            List<OTipiOneri> list = authInfo.CreateDatabase().GetClassList(filtro).ToList<OTipiOneri>();
            ListSortManager<OTipiOneri>.Sort(list, sortExpression);

            return list;
        }
    }
}
