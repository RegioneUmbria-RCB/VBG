using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{
    public class DummyScriptModelloMgr : IDyn2ScriptModelloManager
    {
        public IDyn2ScriptModello GetById(string idComune, int idModello, SIGePro.DatiDinamici.Scripts.TipoScriptEnum contesto)
        {
            return null;
        }
    }
}