using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.NominativoCongruente
{
	internal interface INominativoCongruenteSpecification
	{
		bool IsVerified(AnagraficaDomanda anagraficaDomanda);
		string GetTestoUltimoErrore();
	}
}
