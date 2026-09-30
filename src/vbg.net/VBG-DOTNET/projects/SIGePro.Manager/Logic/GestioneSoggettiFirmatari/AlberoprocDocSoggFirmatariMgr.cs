using Init.SIGePro.Manager.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    public class AlberoprocDocSoggFirmatariMgr : BaseManager2
    {
        public AlberoprocDocSoggFirmatariMgr(DataBase db, string idComune)
            : base(db, idComune)
        {

        }

        public Dictionary<int, List<AlberoprocDocSoggFirmatari>> GetListByIdAllegati(IEnumerable<int> idDocumenti)
        {
            var listaId = String.Join(",", idDocumenti.Select(x => x.ToString()));

            var sql = "SELECT * FROM alberoproc_doc_sogg_firmatari where idcomune = {0} and FK_ALBEROPROC_DOCUMENTI in (" + listaId + ") order by fk_alberoproc_documenti";

            var query = FormattableStringFactory.Create(sql, base.IdComune);

            var soggFirmatari = this.Database.GetClassList<AlberoprocDocSoggFirmatari>(query);

            return soggFirmatari.GroupBy(x => x.FkAlberoprocDocumenti!.Value).ToDictionary(x => x.Key, x => x.ToList());
        }
    }
}
