namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita
{
	using System.Collections.Generic;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

	public interface IEndoprocedimentiIncompatibiliRepository
	{
		IEnumerable<EndoprocedimentoIncompatibileDto> GetEndoprocedimentiIncompatibili(int[] listaIdEndoAttivati);
        string GetNaturaBaseDaidEndoprocedimento(int codiceInventario);

    }
}
