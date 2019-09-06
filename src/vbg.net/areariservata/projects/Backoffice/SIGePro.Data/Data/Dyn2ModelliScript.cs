
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.DatiDinamici.Interfaces;


namespace Init.SIGePro.Data
{
    public partial class Dyn2ModelliScript : IDyn2ScriptModello
    {
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
				