using log4net;
using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Logic.GestioneWorkflowDomandaOnLine
{
    public interface IWorkflowDomandaOnlineService
    {
        WorkflowV3Dto GetByIdIntervento(int idIntervento);
    }

    public class WorkflowDomandaOnlineService : IWorkflowDomandaOnlineService
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(WorkflowDomandaOnlineService));

        public WorkflowDomandaOnlineService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public void AggiornaWorkflowIntervento(int idIntervento, int codiceOggettoWorkflow)
        {
            this._log.Info($"Aggiornamento del workflow per l'intervento {idIntervento}. CodiceOggetto={codiceOggettoWorkflow}, idcomune={this._idComune}");

            var sql = $@"UPDATE alberoproc 
                         SET codiceoggetto_workflow={this._db.QueryParameter("codiceOggettoWorkflow")} 
                         WHERE 
                            idcomune={this._db.QueryParameter("idComune")} AND 
                            sc_id={this._db.QueryParameter("idIntervento")}";
            try
            {

                this._db.ExecuteNonQuery(sql, mp =>
                {
                    mp.Add("codiceOggettoWorkflow", codiceOggettoWorkflow);
                    mp.Add("idComune", this._idComune);
                    mp.Add("idIntervento", idIntervento);
                });

                this._log.Debug($"Aggiornamento del workflow per l'intervento {idIntervento} effettuato. CodiceOggetto={codiceOggettoWorkflow}, idcomune={this._idComune}");
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nell'aggiornamento del workflow per l'intervento {idIntervento} effettuato. CodiceOggetto={codiceOggettoWorkflow}, idcomune={this._idComune}:{ex}");

                throw;
            }
        }


        public WorkflowV3Dto GetByIdIntervento(int idIntervento)
        {
            AlberoProcMgr mgr = new AlberoProcMgr(this._db);

            var codiceOggetto = mgr.GetCodiceOggettoWorkflowDaIdIntervento(this._idComune, idIntervento);

            if (codiceOggetto == null)
            {
                var software = mgr.GetSoftwareByCodiceIntervento(this._idComune, idIntervento);

                var parametriArCfg = new FoArConfigurazioneMgr(this._db).LeggiDati(this._idComune, software);

                if (parametriArCfg?.CodiceoggettoWorkflow == null)
                {
                    return null;
                }

                codiceOggetto = parametriArCfg.CodiceoggettoWorkflow.Value;
            }



            OggettiMgr oggettiMgr = new OggettiMgr(this._db);

            var oggetto = oggettiMgr.GetById(this._idComune, codiceOggetto.Value);

            var xmlDeserializzato = oggetto.DeserializeXML<WorkflowStepsCollection>();

            return new WorkflowV3Dto(xmlDeserializzato);
        }
    }
}
