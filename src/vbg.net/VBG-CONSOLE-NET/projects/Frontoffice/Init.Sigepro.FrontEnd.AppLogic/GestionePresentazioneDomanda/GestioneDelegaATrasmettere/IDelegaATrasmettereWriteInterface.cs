using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere
{
	public interface IDelegaATrasmettereWriteInterface
	{
		void EliminaDelegaATrasmettere();
		void SalvaAllegato(int codiceoggetto, string nomefile, bool isFirmatoDigitalmente);
        void SalvaDocumentoIdentita(int codiceoggetto, string nomefile, bool isFirmatoDigitalmente);
        void EliminaDocumentoIdentita();
    }
}
