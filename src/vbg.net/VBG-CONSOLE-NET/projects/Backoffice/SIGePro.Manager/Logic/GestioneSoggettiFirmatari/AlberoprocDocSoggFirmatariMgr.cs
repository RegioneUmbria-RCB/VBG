using Init.SIGePro.Data;
using Init.SIGePro.Manager.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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

            var sql = PreparaQueryParametrica("SELECT * FROM alberoproc_doc_sogg_firmatari where idcomune = {0} and FK_ALBEROPROC_DOCUMENTI in (" + listaId + ") order by fk_alberoproc_documenti", "idComune");

            return ExecuteInConnection(() =>
            {
                var cmdParams = new[] {
                    new CommandParameter("idComune", base.IdComune)
                };

                using (var cmd = CreateCommand(sql, cmdParams))
                {
                    var soggFirmatari = base.Database.GetClassList<AlberoprocDocSoggFirmatari>(cmd);
                    var rVal = new Dictionary<int, List<AlberoprocDocSoggFirmatari>>();

                    foreach (var sogg in soggFirmatari)
                    {
                        var idAllegato = sogg.FkAlberoprocDocumenti.Value;
                        var soggList = new List<AlberoprocDocSoggFirmatari>();

                        if (!rVal.TryGetValue(idAllegato, out soggList))
                        {
                            soggList = new List<AlberoprocDocSoggFirmatari>();
                            rVal.Add(idAllegato, soggList);
                        }

                        soggList.Add(sogg);
                    }

                    return rVal;
                }
            });
        }
    }
}
