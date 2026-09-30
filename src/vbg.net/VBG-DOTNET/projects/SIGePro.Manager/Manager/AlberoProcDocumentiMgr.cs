using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Common;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AlberoProcDocumentiMgr.\n	/// </summary>
    public class AlberoProcDocumentiMgr : BaseManager
    {
        public AlberoProcDocumentiMgr(DataBase dataBase) : base(dataBase) { }

        public List<AlberoProcDocumenti> GetListDaCodiceIntervento(string idComune, int scId, AmbitoRicerca ambitoRicercaDocumenti)
        {

            var alberoProcMgr = new AlberoProcMgr(this.db);
            var ramo = alberoProcMgr.GetById(scId, idComune);

            if (ramo == null)
                throw new ArgumentException("il codice intervento " + scId + " non è valido per l'id comune " + idComune);

            var whereScId = ramo.GetListaScCodice().ToString();

            var sql = @"SELECT 
						  alberoproc_documenti.*
						FROM 
						  alberoproc_documenti,
						  alberoproc
						WHERE
						  alberoproc_documenti.idcomune  = alberoproc.idcomune AND
						  alberoproc_documenti.sm_fkscid = alberoproc.sc_id AND
						  alberoproc.idcomune = {0} AND
						  alberoproc.software = {1} AND
						  alberoproc.sc_codice IN (" + whereScId + @") and
						  alberoproc_documenti.pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti) + @")
                          ORDER BY alberoproc.sc_codice desc";

            sql = this.PreparaQueryParametrica(sql, "idComune", "software");

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("software", ramo.SOFTWARE));

                var documenti = this.db.GetClassList<AlberoProcDocumenti>(cmd, new GetClassListFlags
                {
                    UseForeign = PersonalLib2.Sql.useForeignEnum.Yes,
                    SingleRowException = false
                });

                var riepiloghi = documenti.Where(x => x.FLG_DOMANDAFO.GetValueOrDefault(0) == 1).ToList();

                if (riepiloghi.Count <= 1)
                {
                    return documenti;
                }

                // Se sono presenti più di un riepilogo, li filtro per tenere solo quello con il codice intervento più alto

                var riepiloghiDaEliminare = riepiloghi.Skip(1);
                foreach (var riepilogo in riepiloghiDaEliminare)
                {
                    documenti.Remove(riepilogo);
                }

                return documenti;
            }
        }

        #region Metodi per l'accesso di base al DB

        public AlberoProcDocumenti GetById(String pSM_ID, String pIDCOMUNE)
        {
            AlberoProcDocumenti retVal = new AlberoProcDocumenti();
            retVal.SM_ID = pSM_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        #endregion
    }
}
