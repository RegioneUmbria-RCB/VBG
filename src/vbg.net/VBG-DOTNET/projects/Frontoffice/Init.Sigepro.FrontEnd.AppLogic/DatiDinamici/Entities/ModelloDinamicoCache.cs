using System.Collections.Generic;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities
{
    public class ModelloDinamicoCache
    {
        public IDyn2Modello Modello { get; set; }
        public Dictionary<TipoScriptEnum, IDyn2ScriptModello> ScriptsModello { get; set; }
        public List<IDyn2DettagliModello> Struttura { get; set; }
        public SerializableDictionary<int, IDyn2Campo> ListaCampiDinamici { get; set; }
        public SerializableDictionary<int, IDyn2TestoModello> ListaTesti { get; set; }
        public Dictionary<int, Dictionary<TipoScriptEnum, IDyn2ScriptCampo>> ScriptsCampiDinamici { get; set; }
        public Dictionary<int, List<IDyn2ProprietaCampo>> ProprietaCampiDinamici { get; set; }
    }
}
