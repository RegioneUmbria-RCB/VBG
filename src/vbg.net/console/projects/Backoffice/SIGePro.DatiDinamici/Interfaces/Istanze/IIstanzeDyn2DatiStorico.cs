using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Istanze
{
	public interface IIstanzeDyn2DatiStorico
	{
		string Idcomune{get;set;}
		int? Idversione{get;set;}
		int? Codiceistanza{get;set;}
		int? FkD2mtId{get;set;}
		int? FkD2cId{get;set;}
		int? Indice{get;set;}
		int? IndiceMolteplicita{get;set;}
		string Valore{get;set;}
	}
}
