using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{
    public partial class CCICalcoloDContribAttivMgr
    {
        public void DeleteByIdTContributo(string idComune, int idTContributo)
        {
            var filtro = new CCICalcoloDContribAttiv();
            filtro.Idcomune = idComune;
            filtro.FkCcictcId = idTContributo;

            var lst = this.GetList(filtro);

            foreach (var dca in lst)
                this.Delete(dca);
        }

        public CCICalcoloDContribAttiv GetByIdTContributoIdAttivita(string idComune, int idTContributo, int idAttivita)
        {
            var filtro = new CCICalcoloDContribAttiv();
            filtro.Idcomune = idComune;
            filtro.FkCcictcId = idTContributo;
            filtro.FkCcccaId = idAttivita;

            return this.db.GetClass(filtro);
        }

        public CCICalcoloDContribAttiv? GetByIdSettore(string idComune, int idTContributo, string codiceSettore)
        {
            FormattableString sql = $@"SELECT 
							  CC_ICALCOLO_DCONTRIBATTIV.* 
							FROM
							  CC_ICALCOLO_DCONTRIBATTIV,
							  CC_CONDIZIONI_ATTIVITA,
							  attivita
							WHERE
							  CC_ICALCOLO_DCONTRIBATTIV.IDCOMUNE    = CC_CONDIZIONI_ATTIVITA.IDCOMUNE AND
							  CC_ICALCOLO_DCONTRIBATTIV.FK_CCCCA_ID = CC_CONDIZIONI_ATTIVITA.ID AND
							  CC_CONDIZIONI_ATTIVITA.IDCOMUNE       = ATTIVITA.IDCOMUNE AND
							  CC_CONDIZIONI_ATTIVITA.FK_AT_CODICEISTAT = ATTIVITA.CODICEISTAT and
							  attivita.idcomune = {idComune} AND
							  attivita.codicesettore = {codiceSettore} AND
							  CC_ICALCOLO_DCONTRIBATTIV.fk_ccictc_id = {idTContributo}";

            return this.db.GetClassList<CCICalcoloDContribAttiv>(sql, new GetClassListFlags
            {
                UseForeign = PersonalLib2.Sql.useForeignEnum.No,
                SingleRowException = true
            }).FirstOrDefault();
        }

        internal IEnumerable<CCICalcoloDContribAttiv> GetListByIdTestataContributo(string idcomune, int idTestataContributo)
        {
            FormattableString sql = $"SELECT * FROM CC_ICALCOLO_DCONTRIBATTIV WHERE IDCOMUNE = {idcomune} AND FK_CCICTC_ID = {idTestataContributo}";

            return this.db.GetClassList<CCICalcoloDContribAttiv>(sql);
        }
    }
}
