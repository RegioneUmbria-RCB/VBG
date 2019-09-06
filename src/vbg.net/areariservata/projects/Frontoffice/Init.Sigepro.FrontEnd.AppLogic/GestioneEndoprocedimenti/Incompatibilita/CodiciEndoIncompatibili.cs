namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita
{
	using System.Collections.Generic;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CodiciEndoIncompatibili
	{
		public int Endo { get; private set; }

		public IEnumerable<int> EndoIncompatibili 
		{ 
			get
			{
				return this._codiciEndoIncompatibili;
			} 
		}

		List<int> _codiciEndoIncompatibili;

		public CodiciEndoIncompatibili(int codiceEndo)
		{
			this.Endo = codiceEndo;
			this._codiciEndoIncompatibili = new List<int>();
		}

		public void AggiungiEndoprocedimentoIncompatibile(int codiceEndoIncompatibile)
		{
			this._codiciEndoIncompatibili.Add(codiceEndoIncompatibile);
		}
	}
}
