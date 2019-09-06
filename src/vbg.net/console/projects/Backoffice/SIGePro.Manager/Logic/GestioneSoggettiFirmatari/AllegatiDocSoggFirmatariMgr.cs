using Init.SIGePro.Manager.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    internal class AllegatiDocSoggFirmatariMgr : BaseManager2
    {
        public AllegatiDocSoggFirmatariMgr(DataBase db, string idComune)
            :base(db, idComune)
        {

        }

        public Dictionary<int, List<AllegatiDocSoggFirmatari>> GetListByIdAllegati(IEnumerable<int> idAllegati)
        {
            if (idAllegati == null)
            {
                return new Dictionary<int, List<AllegatiDocSoggFirmatari>>();
            }

            var listaId = String.Join(",", idAllegati.Select(x => x.ToString()));

            var sql = PreparaQueryParametrica("SELECT * FROM allegati_doc_sogg_firmatari where idcomune = {0} and FK_ALLEGATI_ID in (" + listaId + ") order by FK_ALLEGATI_ID", "idComune");

            return ExecuteInConnection(() =>
            {
                var cmdParams = new[] {
                    new CommandParameter("idComune", base.IdComune)
                };

                using (var cmd = CreateCommand(sql, cmdParams))
                {
                    var soggFirmatari = base.Database.GetClassList<AllegatiDocSoggFirmatari>(cmd);
                    var rVal = new Dictionary<int, List<AllegatiDocSoggFirmatari>>();

                    foreach (var sogg in soggFirmatari)
                    {
                        var idAllegato = sogg.FkAllegatiId.Value;
                        var soggList = new List<AllegatiDocSoggFirmatari>();

                        if (!rVal.TryGetValue(idAllegato, out soggList))
                        {
                            soggList = new List<AllegatiDocSoggFirmatari>();
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
