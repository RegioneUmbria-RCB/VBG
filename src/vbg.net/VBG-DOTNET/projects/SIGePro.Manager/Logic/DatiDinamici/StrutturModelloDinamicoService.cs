using PersonalLib2.Data;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Init.SIGePro.Manager.Logic.DatiDinamici
{
    public class StrutturModelloDinamicoService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public StrutturModelloDinamicoService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public StrutturaModelloDinamicoSerializzabileDto GetStrutturModello(int idModello)
        {
            var idComune = this._idComune;

            return new StrutturaModelloDinamicoSerializzabileDto
            {
                Modello = new Dyn2ModelliTMgr(this._db).GetModelloById(idComune, idModello),
                Struttura = new Dyn2ModelliDMgr(this._db).GetStrutturaModelloByIdModello(idComune, idModello),
                ScriptsModello = new Dyn2ModelliScriptMgr(this._db).GetScriptsModello(idComune, idModello),
                ScriptsCampi = new Dyn2CampiScriptMgr(this._db).GetListaScriptDaIdModello(idComune, idModello),
                CampiDinamici = new Dyn2CampiMgr(this._db).GetCampiDinamiciByIdModello(idComune, idModello),
                ProprietaCampiDinamici = new Dyn2CampiProprietaMgr(this._db).GetProprietaCampiDaIdModello(idComune, idModello),
                Testi = new Dyn2ModelliDTestiMgr(this._db).GetTestiDtoByIdModello(idComune, idModello)
            };
        }
    }
}
