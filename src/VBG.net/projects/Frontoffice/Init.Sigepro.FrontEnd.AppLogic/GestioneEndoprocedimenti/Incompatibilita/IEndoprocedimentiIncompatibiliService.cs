namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita
{
	using System.Collections.Generic;
	using System.Linq;

	public interface IEndoprocedimentiIncompatibiliService
	{
		IEnumerable<CodiciEndoIncompatibili> GetEndoprocedimentiIncompatibili(IEnumerable<int> codiciEndo);
	}

	public class EndoprocedimentiIncompatibiliService : IEndoprocedimentiIncompatibiliService
	{
		IEndoprocedimentiIncompatibiliRepository _repository;

		public EndoprocedimentiIncompatibiliService(IEndoprocedimentiIncompatibiliRepository repository)
		{
			this._repository = repository;
		}

		public IEnumerable<CodiciEndoIncompatibili> GetEndoprocedimentiIncompatibili(IEnumerable<int> codiciEndo)
		{
			var endoIncompatibili = this._repository.GetEndoprocedimentiIncompatibili(codiciEndo.ToArray());
			var dizionario = new Dictionary<int, CodiciEndoIncompatibili>();

			foreach (var e in endoIncompatibili)
			{
				CodiciEndoIncompatibili incomp = null;

				if (!dizionario.TryGetValue(e.CodiceEndoprocedimento, out incomp))
				{
					incomp = new CodiciEndoIncompatibili(e.CodiceEndoprocedimento);
					dizionario.Add(e.CodiceEndoprocedimento, incomp);
				}

				incomp.AggiungiEndoprocedimentoIncompatibile(e.CodiceEndoprocedimentoIncompatibile);
			}

			return dizionario.Select(x => x.Value);
		}
	}
}
