// -----------------------------------------------------------------------
// <copyright file="NaturaEndoIncompatibileSpecification.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.IncompatibilitaNatura
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
	using Init.Sigepro.FrontEnd.Infrastructure;
using log4net;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class NaturaEndoCompatibileSpecification: ISpecification<Endoprocedimento>
	{
		private Endoprocedimento _endoPrincipale;
		private ILog _log = LogManager.GetLogger(typeof(NaturaEndoCompatibileSpecification));

		public NaturaEndoCompatibileSpecification(Endoprocedimento endoPrincipale)
		{
			this._endoPrincipale = endoPrincipale;
		}

		public bool IsSatisfiedBy(Endoprocedimento endo)
		{
			if (this._endoPrincipale == null)
				return true;

			if (this._endoPrincipale.Natura == null || endo.Natura == null)
				return true;

			_log.DebugFormat("Verifica della compatibilità tra l'endo principale {0}: cod natura={1}, descrizione={2}, binariodipendenze={3} e l'endo {4}, cod natura={5}, natura={6}",
								this._endoPrincipale.Descrizione, this._endoPrincipale.Natura.Codice, this._endoPrincipale.Natura.Descrizione,
								endo.Descrizione, endo.Natura.Codice, endo.Natura.Descrizione);

			if ((endo.Natura.Codice & this._endoPrincipale.BinarioDipendenze) != endo.Natura.Codice)
			{
				_log.DebugFormat(
						"L'endoprocedimento principale {0}(BD={1}) non è compatibile con l'endo {2} (BD={3}))",
						this._endoPrincipale.Descrizione, 
						this._endoPrincipale.BinarioDipendenze, 
						endo.Descrizione, endo.BinarioDipendenze);

				return false;
			}

			_log.Debug("Gli endo sono compatibili");

			return true;
		}
	}
}
