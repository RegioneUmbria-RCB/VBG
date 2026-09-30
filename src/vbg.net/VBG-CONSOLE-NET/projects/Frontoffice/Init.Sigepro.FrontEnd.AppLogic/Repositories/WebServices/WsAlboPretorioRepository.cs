using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AlboPretorioService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsAlboPretorioRepository : IAlboPretorioRepository
	{
		AlboPretorioServiceCreator _serviceCreator;

		public WsAlboPretorioRepository(AlboPretorioServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}


		public List<ListaCategorie> GetCategorie(string idComune, string software)
		{

			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				List<ListaCategorie> rVal = new List<ListaCategorie>();

				var l = new ListaCategorieRequest
				{
					token = ws.Token,
					software = software
				};

				rVal.AddRange(ws.Service.ListaCategorie(l));

				return rVal;
			}
		}

		public List<ListaPubblicazioniValideAl> GetPubblicazioni(PubblicazioniValideAlRequest l, string idComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				List<ListaPubblicazioniValideAl> rVal = new List<ListaPubblicazioniValideAl>();

				l.token = ws.Token;
				l.software = software;

				rVal.AddRange(ws.Service.PubblicazioniValideAl(l));

				return rVal;
			}
		}

		public DettaglioPubblicazioneResponse GetDettaglioPubblicazioni(DettaglioPubblicazioneRequest dp, string idComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				DettaglioPubblicazioneResponse rVal = new DettaglioPubblicazioneResponse();

				dp.token = ws.Token;
				dp.software = software;

				rVal = ws.Service.DettaglioPubblicazione(dp);

				return rVal;
			}
		}
	}
}
