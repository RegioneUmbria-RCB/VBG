using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere
{
	public interface IDelegaATrasmettereWriteInterface
	{
		void Elimina();
		void Salva(int codiceoggetto, string nomefile, bool isFirmatoDigitalmente);
	}
}
