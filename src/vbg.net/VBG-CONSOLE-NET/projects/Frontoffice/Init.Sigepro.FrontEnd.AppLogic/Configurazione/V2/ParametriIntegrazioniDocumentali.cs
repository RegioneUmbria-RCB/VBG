using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriIntegrazioniDocumentali : IParametriConfigurazione
    {
        public class FlagsMovimentoDaEffettuare
        {
            public readonly bool InibisciNoteMovimento;
            public readonly bool InibisciNoteAllegati;
			public readonly bool BloccaUploadAllegati;
	        public readonly bool BloccaUploadRiepiloghiSchede;

            public FlagsMovimentoDaEffettuare(bool inibisciNoteMovimento, bool inibisciNoteAllegati, bool bloccaUploadAllegati, bool bloccaUploadRiepiloghiSchede)
            {
                this.InibisciNoteAllegati = inibisciNoteAllegati;
                this.InibisciNoteMovimento = inibisciNoteMovimento;
				this.BloccaUploadAllegati = bloccaUploadAllegati;
				this.BloccaUploadRiepiloghiSchede = bloccaUploadRiepiloghiSchede;
            }
        }

        public readonly bool NascondiNoteMovimento;
        public readonly FlagsMovimentoDaEffettuare MovimentoDaEffettuare;
        

        public ParametriIntegrazioniDocumentali(bool nascondiNoteMovimento, bool inibisciNoteMovimentoDaEffettuare, bool inibisciNoteAllegatiDaEffettuare, bool bloccaUploadAllegati, bool bloccaUploadRiepiloghiSchede)
        {
            this.NascondiNoteMovimento = nascondiNoteMovimento;
            this.MovimentoDaEffettuare = new FlagsMovimentoDaEffettuare(inibisciNoteMovimentoDaEffettuare, inibisciNoteAllegatiDaEffettuare, bloccaUploadAllegati, bloccaUploadRiepiloghiSchede);
        }
    }
}
