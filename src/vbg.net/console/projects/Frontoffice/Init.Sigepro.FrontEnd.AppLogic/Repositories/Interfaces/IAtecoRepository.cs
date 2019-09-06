using System;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IAtecoRepository
	{
		int[] CaricaGerarchia(string aliasComune, int id);
		NodoAlberoInterventiDto GetAlberoProc(string aliasComune, int id, AmbitoRicerca ambitoRicerca);
		Ateco GetDettagli(string aliasComune, int id);
		Ateco[] GetNodiFiglio(string aliasComune, int? idPadre);
		Ateco[] RicercaAteco(string aliasComune, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca);
		bool EsistonoInterventiCollegati(string aliasComune, string software, int idAteco, IAmbitoRicercaIntervento ambitoRicerca);
	}
}
