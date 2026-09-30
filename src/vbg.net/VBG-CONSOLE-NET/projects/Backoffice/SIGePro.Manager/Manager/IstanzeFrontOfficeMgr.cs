using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IstanzeFrontOfficeMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<IstanzeFrontOffice> FindIstanzeConErrori(string token, string software, string nominativo, string codiceDomanda)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            IstanzeFrontOfficeMgr mgr = new IstanzeFrontOfficeMgr(authInfo.CreateDatabase());

            IstanzeFrontOffice filtro = new IstanzeFrontOffice();
            IstanzeFrontOffice filtroCompare = new IstanzeFrontOffice();

            filtro.Software = software;
            filtro.Idcomune = authInfo.IdComune;
            filtro.Codicedomanda = codiceDomanda;
            filtro.Richiedente = nominativo;
            filtro.OthersWhereClause.Add("CODICEISTANZA is null");
            filtro.OrderBy = "dataPresentazione asc";

            filtroCompare.Codicedomanda = "like";
            filtroCompare.Richiedente = "like";

            List<IstanzeFrontOffice> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare, false);

            return list;
        }

    }
}
