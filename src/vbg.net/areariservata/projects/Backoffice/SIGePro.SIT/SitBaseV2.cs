// -----------------------------------------------------------------------
// <copyright file="SitBaseV2.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Sit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Sit.Data;
	using Init.SIGePro.Sit.ValidazioneFormale;
	using log4net;
	using Init.Utils;
	using PersonalLib2.Data;
	using Init.SIGePro.Sit.Manager;
	using Init.SIGePro.Manager.DTO;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public abstract class SitBaseV2 : ISitApi
	{
		Data.Sit _datiLocalizzazione;
		ILog _log = LogManager.GetLogger(typeof(SitBaseV2));
		IValidazioneFormaleService _servizioValidazioneFormale;

		protected string IdComune { get; private set; }
		protected string Alias { get; private set; }
		protected string Software { get; private set; }
		protected DataBase Database { get; private set; }


		public Data.Sit DataSit
		{
			get
			{
				return _datiLocalizzazione;
			}
			set
			{
				_datiLocalizzazione = value;
			}
		}

		internal SitBaseV2(IValidazioneFormaleService servizioValidazioneFormale)
		{
			this._servizioValidazioneFormale = servizioValidazioneFormale;
		}

		public virtual RetSit CAPValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit CodiceViaValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit CivicoValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit ColoreValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit EsponenteInternoValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit EsponenteValidazione()
		{
			return new RetSit(true);
		}

        public virtual RetSit AccessoTipoValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit AccessoNumeroValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit AccessoDescrizioneValidazione()
        {
            return new RetSit(true);
        }

        public virtual RetSit FabbricatoValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit FoglioValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit InternoValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit KmValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit ParticellaValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit ScalaValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit SezioneValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit SubValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit UIValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit FrazioneValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit CircoscrizioneValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit TipoCatastoValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit PianoValidazione()
		{
			return new RetSit(true);
		}

		public virtual RetSit QuartiereValidazione()
		{
			return new RetSit(true);
		}



		public virtual RetSit DettaglioFabbricato()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit DettaglioUI()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoCodVia()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoCivici()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoColori()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoEsponentiInterno()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoEsponenti()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoFabbricati()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoFogli()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoInterni()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoKm()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoParticelle()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoScale()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoSezioni()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoSub()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoUI()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoCAP()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoFrazioni()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoCircoscrizioni()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoVincoli()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoZone()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoSottoZone()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoDatiUrbanistici()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoPiani()
		{
			throw new NotImplementedException();
		}

		public virtual RetSit ElencoQuartieri()
		{
			throw new NotImplementedException();
		}

        public virtual RetSit ElencoAccessoTipo()
        {
            throw new NotImplementedException();
        }

        //public virtual RetSit ElencoAccessoNumero()
        //{
        //    throw new NotImplementedException();
        //}

        //public virtual RetSit ElencoAccessoDescrizione()
        //{
        //    throw new NotImplementedException();
        //}

        public bool ValidaDatiSit(Data.Sit sitClass)
		{
			if (_log.IsDebugEnabled)
				_log.DebugFormat("Validazione della classe sit: {0}", StreamUtils.SerializeClass(sitClass));
			try
			{
				var esito = this._servizioValidazioneFormale.Valida(sitClass);

				_log.DebugFormat("Esito validazione: {0}", esito);

				return esito;
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la validazione della classe sit: {0}", ex.ToString());

				throw;
			}
		}

		public virtual DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
		{
			return new DettagliVia[0];
		}

		public abstract void SetupVerticalizzazione();

		public abstract string[] GetListaCampiGestiti();


		public void InizializzaParametriSigepro(string idComune, string alias, string software, DataBase dataBase)
		{
			this.IdComune = idComune;
			this.Alias = alias;
			this.Software = software;
			this.Database = dataBase;
		}


		public virtual BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice()
		{
			return new BaseDto<SitFeatures.TipoVisualizzazione, string>[0] { };
		}


		public virtual BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
		{
			return new BaseDto<SitFeatures.TipoVisualizzazione, string>[0] { };
		}
    }
}
