using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class DomandeFrontEndoMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<DomandeFrontEndo> Find(string token, int idDomanda)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            DomandeFrontMgr mgr = new DomandeFrontMgr(authInfo.CreateDatabase());

            DomandeFrontEndo filtro = new DomandeFrontEndo();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Codicedomanda = idDomanda;
            filtro.UseForeign = useForeignEnum.Yes;

            List<DomandeFrontEndo> list = authInfo.CreateDatabase().GetClassList(filtro, false);

            return list;
        }


        public List<DomandeFrontEndo> GetByIdDomanda(string idComune, int idDomanda)
        {
            DomandeFrontEndo filtro = new DomandeFrontEndo();
            filtro.Idcomune = idComune;
            filtro.Codicedomanda = idDomanda;

            return this.GetList(filtro);
        }
    }
}
