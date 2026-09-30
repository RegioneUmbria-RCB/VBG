namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita
{
    using System;
    using System.Collections.Generic;

    public interface IEndoprocedimentiIncompatibiliService
	{
		IEnumerable<CodiciEndoIncompatibili> GetEndoprocedimentiIncompatibili(IEnumerable<int> codiciEndo);
        string GetNaturaBaseDaidEndoprocedimento(int codiceInventario);
    }
}
