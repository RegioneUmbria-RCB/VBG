
using System;
using System.Text;
using VBG.DatiDinamici.Interfaces;


namespace Init.SIGePro.Data
{
    public partial class Dyn2CampiScript : IDyn2ScriptCampo
    {
        // <non mappata su una data property. Ne sono rimasti talmente pochi che non dovrebbe servire
        public string Checksum { get; set; } = "";

        public string GetTestoScript()
        {
            if (this.Script == null || this.Script.Length == 0) return String.Empty;
            return Encoding.UTF8.GetString(this.Script);
        }

        public void SetTestoScript(string script)
        {
            this.Script = Encoding.UTF8.GetBytes(script);
        }
    }
}
