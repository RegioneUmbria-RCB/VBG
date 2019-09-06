using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Collections;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
	public class FormLocalizzazioni
	{
		private static class Constants
		{
			public const int IdCampoCivico = 0;
			public const int IdCampoEsponente = 1;
			public const int IdCampoColore = 2;
			public const int IdCampoScala = 3;
			public const int IdCampoPiano = 4;
			public const int IdCampoInterno = 5;
			public const int IdCampoEsponenteInterno = 6;
			public const int IdCampoFabbricato = 7;
			public const int IdCampoKm = 8;
			public const int IdCampoLongitudine = 9;
			public const int IdCampoLatitudine = 10;
			public const int IdCampoTipoCatasto = 11;
			public const int IdCampoFoglio = 12;
			public const int IdCampoParticella = 13;
			public const int IdCampoSub = 14;
			public const int IdCampoNote = 15;
			public const int IdCampoCodCivico = 16;
			public const int IdCampoCodViario = 17;
			public const int IdCampoSezione = 18;
            public const int IdCampoAccessoTipo = 19;
            public const int IdCampoAccessoNumero = 20;
            public const int IdCampoAccessoDescrizione = 21;
        }

		#region Grande muro di proprieta
		
		public ICampoLocalizzazioni Civico
		{
			get { return this._campi[Constants.IdCampoCivico]; }
			set
			{
				this._campi[Constants.IdCampoCivico] = value;
				this._campi[Constants.IdCampoCivico].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Esponente
		{
			get { return this._campi[Constants.IdCampoEsponente]; }
			set
			{
				this._campi[Constants.IdCampoEsponente] = value;
				this._campi[Constants.IdCampoEsponente].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Colore
		{
			get { return this._campi[Constants.IdCampoColore]; }
			set
			{
				this._campi[Constants.IdCampoColore] = value;
				this._campi[Constants.IdCampoColore].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Scala
		{
			get { return this._campi[Constants.IdCampoScala]; }
			set
			{
				this._campi[Constants.IdCampoScala] = value;
				this._campi[Constants.IdCampoScala].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Piano
		{
			get { return this._campi[Constants.IdCampoPiano]; }
			set
			{
				this._campi[Constants.IdCampoPiano] = value;
				this._campi[Constants.IdCampoPiano].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Interno
		{
			get { return this._campi[Constants.IdCampoInterno]; }
			set
			{
				this._campi[Constants.IdCampoInterno] = value;
				this._campi[Constants.IdCampoInterno].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni EsponenteInterno
		{
			get { return this._campi[Constants.IdCampoEsponenteInterno]; }
			set
			{
				this._campi[Constants.IdCampoEsponenteInterno] = value;
				this._campi[Constants.IdCampoEsponenteInterno].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Fabbricato
		{
			get { return this._campi[Constants.IdCampoFabbricato]; }
			set
			{
				this._campi[Constants.IdCampoFabbricato] = value;
				this._campi[Constants.IdCampoFabbricato].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Km
		{
			get { return this._campi[Constants.IdCampoKm]; }
			set
			{
				this._campi[Constants.IdCampoKm] = value;
				this._campi[Constants.IdCampoKm].StateBag = this._stateBag;
			}
		}

		public ICampoLocalizzazioni Note
		{
			get { return this._campi[Constants.IdCampoNote]; }
			set
			{
				this._campi[Constants.IdCampoNote] = value;
				this._campi[Constants.IdCampoNote].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Longitudine
		{
			get { return this._campi[Constants.IdCampoLongitudine]; }
			set
			{
				this._campi[Constants.IdCampoLongitudine] = value;
				this._campi[Constants.IdCampoLongitudine].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Latitudine
		{
			get { return this._campi[Constants.IdCampoLatitudine]; }
			set
			{
				this._campi[Constants.IdCampoLatitudine] = value;
				this._campi[Constants.IdCampoLatitudine].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni TipoCatasto
		{
			get { return this._campi[Constants.IdCampoTipoCatasto]; }
			set
			{
				this._campi[Constants.IdCampoTipoCatasto] = value;
				this._campi[Constants.IdCampoTipoCatasto].StateBag = this._stateBag;
			}
		}

		public ICampoLocalizzazioni CodiceCivico
		{
			get { return this._campi[Constants.IdCampoCodCivico]; }
			set
			{
				this._campi[Constants.IdCampoCodCivico] = value;
				this._campi[Constants.IdCampoCodCivico].StateBag = this._stateBag;
			}
		}

		public ICampoLocalizzazioni CodiceViario
		{
			get { return this._campi[Constants.IdCampoCodViario]; }
			set
			{
				this._campi[Constants.IdCampoCodViario] = value;
				this._campi[Constants.IdCampoCodViario].StateBag = this._stateBag;
			}
		}

		public ICampoLocalizzazioni Sezione
		{
			get { return this._campi[Constants.IdCampoSezione]; }
			set
			{
				this._campi[Constants.IdCampoSezione] = value;
				this._campi[Constants.IdCampoSezione].StateBag = this._stateBag;
			}
		}

		public ICampoLocalizzazioni Foglio
		{
			get { return this._campi[Constants.IdCampoFoglio]; }
			set
			{
				this._campi[Constants.IdCampoFoglio] = value;
				this._campi[Constants.IdCampoFoglio].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Particella
		{
			get { return this._campi[Constants.IdCampoParticella]; }
			set
			{
				this._campi[Constants.IdCampoParticella] = value;
				this._campi[Constants.IdCampoParticella].StateBag = this._stateBag;
			}
		}
		
		public ICampoLocalizzazioni Sub
		{
			get { return this._campi[Constants.IdCampoSub]; }
			set
			{
				this._campi[Constants.IdCampoSub] = value;
				this._campi[Constants.IdCampoSub].StateBag = this._stateBag;
			}
		}

        public ICampoLocalizzazioni AccessoTipo
        {
            get { return this._campi[Constants.IdCampoAccessoTipo]; }
            set
            {
                this._campi[Constants.IdCampoAccessoTipo] = value;
                this._campi[Constants.IdCampoAccessoTipo].StateBag = this._stateBag;
            }
        }

        public ICampoLocalizzazioni AccessoNumero
        {
            get { return this._campi[Constants.IdCampoAccessoNumero]; }
            set
            {
                this._campi[Constants.IdCampoAccessoNumero] = value;
                this._campi[Constants.IdCampoAccessoNumero].StateBag = this._stateBag;
            }
        }

        public ICampoLocalizzazioni AccessoDescrizione
        {
            get { return this._campi[Constants.IdCampoAccessoDescrizione]; }
            set
            {
                this._campi[Constants.IdCampoAccessoDescrizione] = value;
                this._campi[Constants.IdCampoAccessoDescrizione].StateBag = this._stateBag;
            }
        }
        #endregion

        Dictionary<int, ICampoLocalizzazioni> _campi = new Dictionary<int, ICampoLocalizzazioni>();
		IDictionary _stateBag;

		public FormLocalizzazioni(IDictionary stateBag)
		{
			this._stateBag = stateBag;
		}

		public IEnumerable<string> GetErroriEspressioniRegolari()
		{
			foreach (var key in this._campi.Keys)
			{
				var campo = this._campi[key];

				if (!new RegExMatchedSpecification().IsSatisfiedBy(campo))
					yield return String.Format("Il campo \"{0}\" contiene caratteri non validi", campo.Etichetta);
			}
		}
		
		internal IEnumerable<string> GetErroriValidazioneRange()
		{
			foreach (var key in this._campi.Keys)
			{
				var campo = this._campi[key];

				if (!new ValoreInRangeSpecification().IsSatisfiedBy(campo))
					yield return String.Format("Il campo \"{0}\" contiene un valore non valido. Il valore deve essere compreso tra {1} e {2}", campo.Etichetta, campo.ValoreMin, campo.ValoreMax);
			}
		}

		public IEnumerable<string> GetErroriValidazione()
		{
			var subObbligatorio = this.Sub.Obbligatorio;
			var verificaSub = this.TipoCatasto.Valore == "F";

			foreach (var key in this._campi.Keys)
			{
				var campo = this._campi[key];

				if (campo == this.Sub && !verificaSub)
					continue;

				if (! new CampoCompilatoSpecification().IsSatisfiedBy( campo ))
					yield return String.Format("Il campo {0} è obbligatorio", campo.Etichetta);
			}

			var coordinate = new RaggruppamentoDiCampiVerificabile(new ICampoLocalizzazioni[]{
				this.Longitudine,
				this.Latitudine
			});

			if (!new CampoCompilatoSpecification().IsSatisfiedBy(coordinate))
			{
				yield return "Compilare tutti i campi del blocco \"Coordinate\"";
			}
		}

		internal void SvuotaCampiEdit()
		{
			foreach (var key in this._campi.Keys)
				this._campi[key].SvuotaCampo();
		}

		internal NuovaLocalizzazione GetLocalizzazione(int codStradario, string nomeVia, string tipoLocalizzazione)
		{
			return new NuovaLocalizzazione(codStradario, nomeVia, this.Civico.Valore)
			{
				Colore = Colore.Valore,
				Esponente = Esponente.Valore,
				EsponenteInterno = EsponenteInterno.Valore,
				Interno = Interno.Valore,
				Note = Note.Valore,
				Scala = Scala.Valore,
				Piano = Piano.Valore,
				Fabbricato = Fabbricato.Valore,
				Km = Km.Valore,
				Longitudine = Longitudine.Valore,
				Latitudine = Latitudine.Valore,
				TipoLocalizzazione = tipoLocalizzazione,
				//Sezione = Sezione.Valore,
				CodiceCivico = CodiceCivico.Valore,
				CodiceViario = CodiceViario.Valore,
                AccessoTipo = this.AccessoTipo.Valore,
                AccessoNumero = this.AccessoNumero.Valore,
                AccessoDescrizione = this.AccessoDescrizione.Valore
			};			
		}

		internal NuovoRiferimentoCatastale GetRiferimentiCatastali()
		{
            var datiCatastaliHasValue = !String.IsNullOrEmpty(Foglio.Valore) || !String.IsNullOrEmpty(Particella.Valore) || !String.IsNullOrEmpty(Sub.Valore);

            if (!this.TipoCatasto.Visibile && !datiCatastaliHasValue)
                return null;

			return new NuovoRiferimentoCatastale(TipoCatasto.Valore, TipoCatasto.Descrizione, Foglio.Valore, Particella.Valore, Sub.Valore);
		}
	}
}