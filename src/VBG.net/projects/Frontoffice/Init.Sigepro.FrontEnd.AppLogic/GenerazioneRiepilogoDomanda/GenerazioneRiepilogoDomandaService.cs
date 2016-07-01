using System;
using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Utils;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda
{
	public class GenerazioneRiepilogoDomandaService
	{
		public enum FormatoConversioneModello
		{
			HTML,
			PDF
		}

		RiepilogoDomanda _riepilogoDomanda;
		FacSimileDomanda _facSimileDomanda;
		IAliasSoftwareResolver	 _aliasResolver;
		IInterventiAllegatiRepository _interventiAllegatiRepository;
		ILog _log = LogManager.GetLogger(typeof(GenerazioneRiepilogoDomandaService));

		public GenerazioneRiepilogoDomandaService(RiepilogoDomanda riepilogoDomanda, FacSimileDomanda facSimileDomanda, IAliasSoftwareResolver aliasResolver, IInterventiAllegatiRepository interventiAllegatiRepository)
		{
			this._riepilogoDomanda = riepilogoDomanda;
			this._facSimileDomanda = facSimileDomanda;
			this._aliasResolver = aliasResolver;
			this._interventiAllegatiRepository = interventiAllegatiRepository;
		}


		public BinaryFile GeneraRiepilogo(int codiceOggetto, DomandaOnline domanda , GenerazioneRiepilogoSettings settings )
		{
			try
			{
				return this._riepilogoDomanda.GeneraFileDaDomanda(domanda, codiceOggetto, settings);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la generazione del riepilogo della domanda con codiceOggetto={0}, idDomanda={1}, settings={2}: {3}", codiceOggetto, domanda.DataKey.ToSerializationCode(), StreamUtils.SerializeClass( settings ) , ex.ToString() );

				throw ex;
			}
		}


		public BinaryFile GeneraModelloDiDomanda(int idIntervento, IEnumerable<int> endoFacoltativiAttivati, FormatoConversioneModello formatoConversione)
		{
			var allegati = this._interventiAllegatiRepository.GetAllegatiDaIdintervento(idIntervento, AmbitoRicerca.AreaRiservata);

			var codiceOggettoModello = allegati.Where(x => x.RiepilogoDomanda).FirstOrDefault().CodiceOggettoModello.Value;

			var domanda = this._facSimileDomanda.Genera( this._aliasResolver.AliasComune, this._aliasResolver.Software, idIntervento , endoFacoltativiAttivati );

			return GeneraRiepilogo(codiceOggettoModello, domanda, new GenerazioneRiepilogoSettings { 
				AggiungiPdfSchedeAListaAllegati = false,
				FormatoConversione = formatoConversione.ToString(),
				IdComune = this._aliasResolver.AliasComune
			});
		}


	}
}
