using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneOneri
{
	public interface IOneriService
	{
		IEnumerable<IstanzeOneri> Inserisci(IEnumerable<IstanzeOneri> oneri);
		int Inserisci(int codiceIstanza, int idTipoCausale, double valore);
		int Inserisci(int codiceIstanza, int idTipoCausale, double valore, DateTime dataScadenza, int numeroRata = 1);
        void Elimina(IEnumerable<int> idOneri);
		void Elimina(int id);
	}
}
