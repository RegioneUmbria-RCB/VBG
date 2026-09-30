using System;
using Init.SIGePro.Sit.Data;
using System.Collections.Generic;
using PersonalLib2.Data;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Manager.DTO;

namespace Init.SIGePro.Sit
{
	interface ISitApi
	{
		RetSit CAPValidazione();
		RetSit CodiceViaValidazione();
		RetSit CivicoValidazione();
		RetSit ColoreValidazione();
		RetSit EsponenteInternoValidazione();
		RetSit EsponenteValidazione();
        RetSit AccessoTipoValidazione();
        RetSit AccessoNumeroValidazione();
        RetSit AccessoDescrizioneValidazione();
        RetSit FabbricatoValidazione();
		RetSit FoglioValidazione();
		RetSit InternoValidazione();
		RetSit KmValidazione();
		RetSit ParticellaValidazione();
		RetSit ScalaValidazione();
		RetSit SezioneValidazione();
		RetSit SubValidazione();
		RetSit UIValidazione();
		RetSit FrazioneValidazione();
		RetSit CircoscrizioneValidazione();
		RetSit TipoCatastoValidazione();
		RetSit PianoValidazione();
		RetSit QuartiereValidazione();
		Init.SIGePro.Sit.Data.Sit DataSit { set; }
		RetSit DettaglioFabbricato();
		RetSit DettaglioUI();
		RetSit ElencoCodVia();
		RetSit ElencoCivici();
		RetSit ElencoColori();
		RetSit ElencoEsponentiInterno();
		RetSit ElencoEsponenti();
		RetSit ElencoFabbricati();
		RetSit ElencoFogli();
		RetSit ElencoInterni();
		RetSit ElencoKm();
		RetSit ElencoParticelle();
		RetSit ElencoScale();
		RetSit ElencoSezioni();
		RetSit ElencoSub();
		RetSit ElencoUI();
		RetSit ElencoCAP();
		RetSit ElencoFrazioni();
		RetSit ElencoCircoscrizioni();
		RetSit ElencoVincoli();
		RetSit ElencoZone();
		RetSit ElencoSottoZone();
		RetSit ElencoDatiUrbanistici();
		RetSit ElencoPiani();
		RetSit ElencoQuartieri();
        RetSit ElencoAccessoTipo();
        string[] GetListaCampiGestiti();
		bool ValidaDatiSit(SIGePro.Sit.Data.Sit sitClass);


		DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni);

		void InizializzaParametriSigepro( string idComune, string alias, string software, DataBase dataBase);

		void SetupVerticalizzazione();

		BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice();
		BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice();
	}
}
