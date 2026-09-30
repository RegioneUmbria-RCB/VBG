using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Interfaces.Anagrafe
{
	public interface IAnagrafeDyn2DatiStorico
	{

		string Idcomune{get;set;}
		int? Idversione{get;set;}
		int? Codiceanagrafe{get;set;}
		int? FkD2mtId{get;set;}
		int? FkD2cId{get;set;}
		int? Indice{get;set;}
		int? IndiceMolteplicita{get;set;}
		string Valore { get; set; }
		
	}
}
