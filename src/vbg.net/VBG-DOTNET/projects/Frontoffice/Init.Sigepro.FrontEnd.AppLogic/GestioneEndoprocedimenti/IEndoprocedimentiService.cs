namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    using System.Collections.Generic;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using Init.Sigepro.FrontEnd.AppLogic.WsEndoprocedimenti;
    using Init.SIGePro.Manager.DTO.Common;
    using Init.SIGePro.Manager.DTO.Endoprocedimenti;



    public interface IEndoprocedimentiService
	{

        bool AlmenoUnEndoPresente(int idDomanda);

        void SalvaEndoSelezionati(int idDomanda, IEnumerable<SubEndoprocedimentoSelezionato> nuoviEndoprocedimenti);

		EndoprocedimentiAreaRiservataDto GetListaEndoDaIdIntervento(string codiceComune, int codIntervento);
		
		EndoprocedimentoDto GetById(int codiceEndoprocedimento, AmbitoRicerca ambitoRicerca = AmbitoRicerca.AreaRiservata);

		void RimuoviAllegatoDaEndo(int idDomanda, int codiceInventario);
        	
		IEnumerable<EndoprocedimentoIncompatibile> GetEndoprocedimentiIncompatibili(int idDomanda);
		
        void ImpostaNaturaBaseDomanda(int idDomanda, string naturaBase);

        EndoprocedimentoMappatoDto GetByIdEndoMappato(string idEndoMappato);

        StrutturaSubEndo GetSubEndoSelezionati(int idDomanda);
    }
}
