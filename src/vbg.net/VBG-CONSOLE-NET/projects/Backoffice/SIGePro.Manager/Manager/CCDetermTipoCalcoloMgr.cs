using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.Utils.Sorting;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCDetermTipoCalcoloMgr
    {
        private void VerificaRecordCollegati(CCDetermTipoCalcolo cls)
        {

        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCDetermTipoCalcolo> Find(string token, int? id, string FkOccbtiId, string FkOccbdeId, string FkCcbtcId, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CCDetermTipoCalcolo filtro = new CCDetermTipoCalcolo();

            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;
            filtro.Id = id;
            filtro.FkOccbtiId = FkOccbtiId;
            filtro.FkOccbdeId = FkOccbdeId;
            filtro.FkCcbtcId = FkCcbtcId;



            // gestione delle foreign keys
            filtro.UseForeign = useForeignEnum.Yes;
            // fine gestione foreign keys


            // gestione ordinamento
            List<CCDetermTipoCalcolo> list = authInfo.CreateDatabase().GetClassList(filtro);

            ListSortManager<CCDetermTipoCalcolo>.Sort(list, sortExpression);
            // fine gestione ordinamento

            return list;
        }
    }
}
