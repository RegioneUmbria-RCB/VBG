using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OTipiOneriMgr
    {
        private void VerificaRecordCollegati(OTipiOneri cls)
        {
            if (recordCount("O_TABELLAABC", "FK_OTO_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_OTO_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAABC");

            if (recordCount("O_TABELLAD", "FK_OTO_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_OTO_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_TABELLAD");

            if (recordCount("O_ICALCOLOCONTRIBR", "FK_OTO_ID", "where IDCOMUNE = '" + cls.Idcomune + "' and FK_OTO_ID = " + cls.Id.ToString()) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBR");

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OTipiOneri> Find(string token, int? codice, string descrizione, string descrizioneEstesa, string baseTipoOnere, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

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
