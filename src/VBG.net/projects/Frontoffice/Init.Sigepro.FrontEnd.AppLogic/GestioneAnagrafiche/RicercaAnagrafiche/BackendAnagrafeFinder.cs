// -----------------------------------------------------------------------
// <copyright file="BackendAnagrafeFinder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Adapters;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
	using log4net;
	using Init.Utils;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

	internal class BackendAnagrafeFinder : AbstractAnagrafeFinder
	{
		IAnagraficheService _anagrafeRepository;
		string				_aliasComune;
		ILog				_log = LogManager.GetLogger(typeof(BackendAnagrafeFinder));


		internal BackendAnagrafeFinder(string aliasComune, IAnagraficheService anagrafeRepository)
		{
			this._anagrafeRepository = anagrafeRepository;
			this._aliasComune = aliasComune;
		}


		internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
		{
			_log.DebugFormat("Inizio ricerca anagrafica con tipo persona {0} e codice fiscale/piva {1}", tipoPersona, codiceFiscalePartitaIva);

			var anagraficaTrovata = _anagrafeRepository.RicercaAnagraficaBackoffice(this._aliasComune, tipoPersona, codiceFiscalePartitaIva);

			if (anagraficaTrovata == null || String.IsNullOrEmpty(anagraficaTrovata.NOMINATIVO))
			{
				_log.DebugFormat("Anagrafica non trovata");
				return null;
			}

			if (_log.IsDebugEnabled)
				_log.DebugFormat("Anagrafica trovata: {0}", StreamUtils.SerializeClass(anagraficaTrovata));

			// HACK: il ws di ricerca anagrafiche di Perugia non restituisce il tipo persona 
			// se il tipo persona non è presente lo imposto uguale al tipo persona passato
			if (String.IsNullOrEmpty(anagraficaTrovata.TIPOANAGRAFE))
			{
				anagraficaTrovata.TIPOANAGRAFE = tipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
			}

			var row = new AnagrafeAdapter(anagraficaTrovata).ToAnagrafeRow();

			return AnagraficaDomanda.FromAnagrafeRow(row);
		}
	}
}
