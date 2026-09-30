using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Export
{


    public class ExportSchedeDinamicheService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public ExportSchedeDinamicheService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public SchedaDinamicaEsportata Export(int idScheda)
        {
            var scheda = new SchedaDinamicaEsportata
            {
                Modello = new Dyn2ModelliTMgr(this._db).GetById(this._idComune, idScheda),
                Struttura = new Dyn2ModelliDMgr(this._db).GetStrutturaModello(this._idComune, idScheda),
                CampiDinamici = new Dyn2CampiMgr(this._db).GetList(this._idComune, idScheda),
                Testi = new Dyn2ModelliDTestiMgr(this._db).GetListByIdModello(this._idComune, idScheda),
                ProprietaCampiDinamici = new Dyn2CampiProprietaMgr(this._db).GetListByIdModello(this._idComune, idScheda),
                ScriptsModello = new Dyn2ModelliScriptMgr(this._db).GetList(this._idComune, idScheda)
            };

            return scheda;
        }


    }
}
