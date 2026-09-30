namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti
{
	using System.Collections.Generic;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;

	public interface IEndoprocedimentiReadInterface
	{
		Endoprocedimento Principale { get; }

		IEnumerable<Endoprocedimento> NonAcquisiti { get; }

		IEnumerable<Endoprocedimento> Acquisiti { get; }

		IEnumerable<Endoprocedimento> Endoprocedimenti { get; }

		IEnumerable<Endoprocedimento> SecondariNonPresenti { get; }

		IEnumerable<Endoprocedimento> Secondari { get; }

		IEnumerable<EndoprocedimentoIncompatibile> GetEndoprocedimentiIncompatibili(IEndoprocedimentiIncompatibiliService endoprocedimentiIncompatibiliService);
	}
}
