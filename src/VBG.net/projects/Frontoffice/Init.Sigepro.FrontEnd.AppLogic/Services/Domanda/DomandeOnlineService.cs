using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using log4net;
using System.Text;

using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using System.IO;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{


	public class DomandeOnlineService
	{
		ILog _log = LogManager.GetLogger(typeof(DomandeOnlineService)); 

		ISalvataggioDomandaStrategy _salvataggioStrategy;
		IAliasSoftwareResolver _aliasSoftwareResolver;
		IAuthenticationDataResolver _authenticationDataResolver;
		
		IDatiDomandaFoRepository _datiDomandaFoRepository;

		public DomandeOnlineService(ISalvataggioDomandaStrategy salvataggioStrategy, IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver, IDatiDomandaFoRepository datiDomandaFoRepository)
		{
			_salvataggioStrategy = salvataggioStrategy;
			_aliasSoftwareResolver = aliasSoftwareResolver;
			_authenticationDataResolver = authenticationDataResolver;
			_datiDomandaFoRepository = datiDomandaFoRepository;
		}

		public int GetProssimoIdDomanda()
		{
			return _datiDomandaFoRepository.GeneraProssimoIdDomanda(_aliasSoftwareResolver.AliasComune);
		}

		public DomandaOnline GetById( int idPresentazione )
		{
			var domanda = _salvataggioStrategy.GetById( idPresentazione );

			if (domanda == null)
				throw new ArgumentException("Impossibile caricare la domanda con id " + idPresentazione + " e alias " + _aliasSoftwareResolver.AliasComune);

			return domanda;
		}

		public int SalvaDomanda(DomandaOnline domanda)
		{
			_salvataggioStrategy.Salva(domanda);

			return domanda.DataKey.IdPresentazione;
		}

		public object LeggiDomandeDaSottoscrivere(string codiceFiscale)
		{
			throw new NotImplementedException();
		}

		public List<FoDomande> GetDomandeInSospeso(int codiceAnagrafe)
		{
			return _datiDomandaFoRepository.LeggiDomandeInSospeso(_aliasSoftwareResolver.AliasComune,
																	_aliasSoftwareResolver.Software,
																	codiceAnagrafe);
		}

		public void Elimina(int idDomanda, string codiceFiscaleUtenteCheEliminaDomanda)
		{
			try
			{
				_log.DebugFormat("Inizio dell'eliminazione della domanda {0} da parte dell'utente {1}", idDomanda, codiceFiscaleUtenteCheEliminaDomanda);

				var domanda = GetById( idDomanda );

				if( !domanda.UtentePuoAccedere( codiceFiscaleUtenteCheEliminaDomanda ) )
					throw new InvalidOperationException("L'utente " + codiceFiscaleUtenteCheEliminaDomanda + " non dispone di diritti sufficienti per eliminare la domanda");

				// HACK: imposto l'id intervento a -1 in modo da eliminare tutti i files della domanda
				domanda.WriteInterface.AltriDati.ImpostaIntervento(-1 , string.Empty , null, null );

				_salvataggioStrategy.Elimina(domanda);

				_log.DebugFormat("Eliminazione della domanda terminato");
			}
			catch(Exception ex)
			{
				_log.ErrorFormat("Errore durante l'eliminazione della domanda: {0}", ex.ToString());

				throw;
			}
		}

		public void DumpDomanda(int idDomanda)
		{
			var basePath	= HttpContext.Current.Server.MapPath("~/");
			var outPath		= Path.Combine( basePath , "Logs");
			var outfile		= Path.Combine( outPath , "dumpDomanda.xml" );

			var domanda = GetById(idDomanda);

			var dati = domanda.SerializeTo(new V4DataSetSerializer());

			if( File.Exists( outfile ) )
				File.Delete( outfile );

			using (var file = File.OpenWrite(outfile))
				file.Write(dati, 0, dati.Length);
			
		}
	}
}
