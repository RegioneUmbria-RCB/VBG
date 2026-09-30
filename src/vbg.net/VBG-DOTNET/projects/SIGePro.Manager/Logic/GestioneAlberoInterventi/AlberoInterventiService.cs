using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneAlberoInterventi
{
    public class AlberoInterventiService : IAlberoInterventiService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public AlberoInterventiService(DataBase db, string idComune)
        {
            _db = db;
            _idComune = idComune;
        }

        public AlberoProc GetById(int idIntervento)
        {
            var mgr = new AlberoProcMgr(this._db);

            return mgr.GetById(idIntervento, this._idComune);
        }
    }
}
