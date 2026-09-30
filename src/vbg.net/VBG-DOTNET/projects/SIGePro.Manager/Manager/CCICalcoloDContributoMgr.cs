using Init.SIGePro.Data;
using System;
using System.Linq;

namespace Init.SIGePro.Manager
{
    public partial class CCICalcoloDContributoMgr
    {
        public CCICalcoloDContributo? GetByIdTContributo(string idComune, int idTContributo)
        {
            FormattableString query = $"SELECT * FROM CC_ICALCOLO_DCONTRIBUTO WHERE idcomune = {idComune} AND fk_ccictc_id = {idTContributo}";

            var filtro = new CCICalcoloDContributo();
            filtro.Idcomune = idComune;
            filtro.FkCcictcId = idTContributo;

            return this.db.GetClassList<CCICalcoloDContributo>(filtro).FirstOrDefault();
        }
    }
}
