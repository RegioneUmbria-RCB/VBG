using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.AlboPretorioService;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IAlboPretorioRepository
	{
		List<ListaCategorie> GetCategorie(string idComune, string software);
		DettaglioPubblicazioneResponse GetDettaglioPubblicazioni(DettaglioPubblicazioneRequest dp, string idComune, string software);
		List<ListaPubblicazioniValideAl> GetPubblicazioni(PubblicazioniValideAlRequest l, string idComune, string software);
	}
}
