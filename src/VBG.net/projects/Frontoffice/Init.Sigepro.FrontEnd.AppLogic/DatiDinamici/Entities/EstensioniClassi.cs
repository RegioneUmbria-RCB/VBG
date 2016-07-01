using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;

/// In questo file sono contenute le estensioni delle classi generate automaticamente tramite "add service reference"
/// In questo modo è possibile dichiarare nella classe l'interfaccia che implementa. 
/// NOTA: le interfaccie non vengono generate in automatico se il servizio di cui si fa l'add reference non è WCF
namespace Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService
{
	public partial class Dyn2CampiProprieta : IDyn2ProprietaCampo
	{
	}



	public partial class Dyn2CampiScript : IDyn2ScriptCampo
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



	public partial class Dyn2Campi : IDyn2Campo
	{
	}


	public partial class Dyn2ModelliT : IDyn2Modello
	{
	}


	public partial class Dyn2ModelliD : IDyn2DettagliModello
	{
	}


	public partial class Dyn2TestoModello : IDyn2TestoModello
	{
	}


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



	public class IstanzeDyn2Dati : IIstanzeDyn2Dati
	{
		public string Idcomune { get; set; }
		public int? Codiceistanza { get; set; }
		public int? FkD2cId { get; set; }
		public int? Indice { get; set; }
		public int? IndiceMolteplicita { get; set; }
		public string Valore { get; set; }
		public string Valoredecodificato { get; set; }

	}
}
