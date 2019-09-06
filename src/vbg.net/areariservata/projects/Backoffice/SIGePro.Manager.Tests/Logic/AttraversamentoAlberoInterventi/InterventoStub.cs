using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione;

namespace SIGePro.Manager.Tests.Logic.AttraversamentoAlberoInterventi
{
	class InterventoStub : IIntervento
	{
		public int Id
		{
			get;
			set;
		}


		public FlagPubblicazione Pubblica
		{
			get;
			set;
		}


		public DateTime? DataInizioAttivazione
		{
			get;
			set;
		}


		public DateTime? DataFineAttivazione
		{
			get;
			set;
		}



        public int? LivelloAutenticazione
        {
            get;
            set;
        }
    }
}
