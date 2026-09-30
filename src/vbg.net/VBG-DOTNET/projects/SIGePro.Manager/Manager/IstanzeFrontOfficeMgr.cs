using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using PersonalLib2.Data;
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
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

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

            List<IstanzeFrontOffice> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<IstanzeFrontOffice>();

            return list;
        }

    }
}
