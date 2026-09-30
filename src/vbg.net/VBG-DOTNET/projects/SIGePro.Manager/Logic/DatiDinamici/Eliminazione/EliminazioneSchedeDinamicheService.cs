using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Eliminazione
{
    public class EliminazioneSchedeDinamicheService
    {
        private readonly string _idComune;
        private readonly DataBase _db;

        public EliminazioneSchedeDinamicheService(string idComune, DataBase dataBase)
        {
            this._idComune = idComune;
            this._db = dataBase;
        }

        public void Elimina(int idScheda)
        {
            try
            {
                this._db.BeginTransaction();
                var campiMgr = new Dyn2CampiMgr(this._db);
                var modelliMgr = new Dyn2ModelliTMgr(this._db);

                var campi = campiMgr.GetList(this._idComune, idScheda);
                var modello = modelliMgr.GetById(this._idComune, idScheda);

                modelliMgr.Delete(modello);

                foreach (var campo in campi)
                {
                    campiMgr.Delete(campo);
                }

                this._db.CommitTransaction();
            }
            catch (Exception)
            {
                this._db.RollbackTransaction();

                throw;
            }
        }
    }
}
