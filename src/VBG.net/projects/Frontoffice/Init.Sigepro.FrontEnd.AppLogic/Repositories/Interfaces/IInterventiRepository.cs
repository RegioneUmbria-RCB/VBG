using System;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IInterventiRepository
	{
		string EstraiDescrizioneEstesa( int idIntervento);
		NodoAlberoInterventiDto GetAlberaturaNodoDaId(string aliasComune, int idNodo);
		NodoAlberoInterventiDto GetAlberoInterventi(string aliasComune, string software);
		InterventoDto GetDettagliIntervento(string aliasComune, int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti);
		InterventoDto[] GetSottonodi(string aliasComune, string software, int idnodo, IAmbitoRicercaIntervento ambitoRicerca, LivelloAutenticazioneEnum livelloAutenticazione);
        InterventoDto[] GetSottonodiDaIdAteco(string aliasComune, string software, int idNodoPadre, int idAteco, IAmbitoRicercaIntervento ambitoRicerca, LivelloAutenticazioneEnum livelloAutenticazione);
		BaseDtoOfInt32String[] RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca);
		List<int> GetIdNodiPadre(string aliasComune, int idNodo);
		int? GetCodiceOggettoCertificatoDiInvioDaIdIntervento(int idIntervento);
		int? GetidDocumentoRiepilogoDaIdIntervento(int idIntervento);
		int? GetCodiceOggettoWorkflow(int idIntervento);
		bool EsistonoVociAttivabiliTramiteAreaRiservata(string idComune, string software);
        bool InterventoAttivo(int idIntervento, LivelloAutenticazioneEnum livelloAutenticazione, bool utenteTester);
	}
}
