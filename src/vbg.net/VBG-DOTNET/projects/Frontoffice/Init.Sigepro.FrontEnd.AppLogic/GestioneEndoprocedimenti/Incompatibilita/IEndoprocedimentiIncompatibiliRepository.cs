namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita
{
    using Init.Sigepro.FrontEnd.AppLogic.WsEndoprocedimenti;
    using Init.SIGePro.Manager.DTO.Endoprocedimenti;
    using System.Collections.Generic;

	public interface IEndoprocedimentiIncompatibiliRepository
	{
		IEnumerable<EndoprocedimentoIncompatibileDto> GetEndoprocedimentiIncompatibili(int[] listaIdEndoAttivati);
        string GetNaturaBaseDaidEndoprocedimento(int codiceInventario);

    }
}
