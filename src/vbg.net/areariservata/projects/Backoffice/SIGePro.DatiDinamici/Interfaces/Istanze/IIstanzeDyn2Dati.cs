using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Istanze
{
	public interface IIstanzeDyn2Dati
	{
		string Idcomune{get;set;}
		int? Codiceistanza{get;set;}
		int? FkD2cId{get;set;}
		int? Indice{get;set;}
		int? IndiceMolteplicita{get;set;}
		string Valore{get;set;}
		string Valoredecodificato{get;set;}
	}
}
