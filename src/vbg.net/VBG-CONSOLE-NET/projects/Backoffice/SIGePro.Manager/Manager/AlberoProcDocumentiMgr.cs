using Init.SIGePro.Data;
using log4net;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AlberoProcDocumentiMgr.\n	/// </summary>
    public class AlberoProcDocumentiMgr : BaseManager
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AlberoProcDocumentiMgr));
        public AlberoProcDocumentiMgr(DataBase dataBase) : base(dataBase) { }

        public List<AlberoProcDocumenti> GetListDaCodiceIntervento(string idComune, int scId, AmbitoRicerca ambitoRicercaDocumenti)
        {
            AlberoProcMgr alberoProcMgr = new AlberoProcMgr(this.db);
            AlberoProc ramo = alberoProcMgr.GetById(scId, idComune);

            if (ramo == null)
                throw new ArgumentException("il codice intervento " + scId + " non è valido per l'id comune " + idComune);

            string whereScId = ramo.GetListaScCodice().ToString();

            string sql = @"SELECT 
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
						  alberoproc_documenti.pubblica in (" + FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti) + ")";

            sql = this.PreparaQueryParametrica(sql, "idComune", "software");

            using (IDbCommand cmd = this.db.CreateCommand(sql))
            {
                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"GetListDaCodiceIntervento: \r\n{sql}\r\n\r\nidComune={idComune}\r\nsoftware={ramo.SOFTWARE}");
                }

                cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("software", ramo.SOFTWARE));

                List<AlberoProcDocumenti> documenti = this.db.GetClassList<AlberoProcDocumenti>(cmd, new GetClassListFlags(useForeignEnum.Yes));

                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"GetListDaCodiceIntervento: {documenti.Count} trovati");
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

            List<AlberoProcDocumenti> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as AlberoProcDocumenti;

            return null;
        }


        #endregion
    }
}
