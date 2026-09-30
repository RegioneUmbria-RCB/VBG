using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsAtecoRepository : Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces.IAtecoRepository
	{
		AreaRiservataServiceCreator _serviceCreator;

		public WsAtecoRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}


		public  Ateco[] GetNodiFiglio(string aliasComune, int? idPadre)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetNodiFiglioAteco(ws.Token, idPadre);
			}
		}

		public  Ateco GetDettagli(string aliasComune, int id)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetDettagliAteco(ws.Token, id);
			}
		}

		public  Ateco[] RicercaAteco(string aliasComune, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.RicercaAteco(ws.Token, matchParziale, matchCount, modoRicerca, tipoRicerca);
			}
		}

		public  NodoAlberoInterventiDto GetAlberoProc(string aliasComune, int id, AmbitoRicerca ambitoRicerca)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetAlberoProcDaIdAteco(ws.Token, id, ambitoRicerca);
			}
		}

		public  int[] CaricaGerarchia(string aliasComune, int id)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.CaricaListaIdGerarchiaAteco(ws.Token, id);
			}
		}


		public bool EsistonoInterventiCollegati(string aliasComune, string software, int idAteco, IAmbitoRicercaIntervento ambitoRicerca)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.VerificaEsistenzaNodiCollegatiAIdAteco(ws.Token, software , idAteco , ambitoRicerca.GetAmbito());
			}
		}
	}
}
