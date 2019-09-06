using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.Utils.Sorting;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CCDetermTipoCalcoloMgr
    {
        private void VerificaRecordCollegati(CCDetermTipoCalcolo cls)
        {
            
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CCDetermTipoCalcolo> Find(string token, int? id, string FkOccbtiId, string FkOccbdeId, string FkCcbtcId, string software , string sortExpression)
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
			List<CCDetermTipoCalcolo> list = authInfo.CreateDatabase().GetClassList(filtro).ToList < CCDetermTipoCalcolo>();

			ListSortManager<CCDetermTipoCalcolo>.Sort(list, sortExpression);
			// fine gestione ordinamento

			return list;
        }
    }
}
