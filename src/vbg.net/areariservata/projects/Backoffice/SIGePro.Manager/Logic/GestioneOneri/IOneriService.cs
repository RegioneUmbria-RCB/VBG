using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneOneri
{
	public interface IOneriService
	{
		void Inserisci(int codiceIstanza, int idTipoCausale, double valore);
	}
}
