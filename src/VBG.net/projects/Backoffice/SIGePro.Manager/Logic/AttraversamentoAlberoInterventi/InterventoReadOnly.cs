using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi
{
	internal class InterventoReadOnly : IIntervento
	{
		AlberoProc _intervento;

		public InterventoReadOnly(AlberoProc intervento)
		{
			if (intervento == null)
				throw new ArgumentNullException("intervento");

			if (!intervento.Sc_id.HasValue)
				throw new ArgumentException("L'intervento non può avere un id nullo");

			this._intervento = intervento;
		}

		public int Id
		{
			get { return this._intervento.Sc_id.Value; }
		}



		public FlagPubblicazione Pubblica
		{
			get 
			{
				switch(this._intervento.SC_PUBBLICA)
				{
					case "0":
						return FlagPubblicazione.NonPubblicare;
					case "1":
						return FlagPubblicazione.AreaRiservataEFrontoffice;
					case "2":
						return FlagPubblicazione.AreaRiservata;
					case "3":
						return FlagPubblicazione.Frontoffice;
				}

				return FlagPubblicazione.EreditaDalPadre;			
			}
		}


		public DateTime? DataInizioAttivazione
		{
			get
			{
				return this._intervento.InizioValidita;
			}
			
		}

		public DateTime? DataFineAttivazione
		{
			get
			{
				return this._intervento.FineValidita;
			}			
		}


        public int? LivelloAutenticazione
        {
            get { return this._intervento.LivelloAutenticazione; }
        }
    }
}
