using PersonalLib2.Data;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello
{
    public class ModelliManager : IDyn2ModelliManager
    {
        private readonly string _idComune;
        private readonly Dyn2ModelliTMgr _modelliMgr;

        public ModelliManager(DataBase db, string idComune)
        {
            this._idComune = idComune;
            this._modelliMgr = new Dyn2ModelliTMgr(db);
        }

        public IDyn2Modello GetById(int idModello) => this._modelliMgr.GetModelloById(this._idComune, idModello);

        public int? GetIdModelloDaCodice(string software, string codiceModello)
        {
            return this._modelliMgr.GetIdModelloDaCodice(this._idComune, software, codiceModello);
        }

        public bool VerificaEsistenzaModelloDinamico(int idModello)
        {
            return this._modelliMgr.VerificaEsistenzaModelloDinamico(this._idComune, idModello);
        }
    }
}
