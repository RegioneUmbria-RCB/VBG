using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriIntegrazioniDocumentaliBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriIntegrazioniDocumentali>
    {
        public ParametriIntegrazioniDocumentaliBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo)
            : base(aliasSoftwareResolver, repo)
        {

        }

        public ParametriIntegrazioniDocumentali Build()
        {
            var config = GetConfig();
            var nascondiNoteMovimento = config.NascondiNoteMovimento;
            var inibisciNoteMovimentoDaEffettuare = false;
            var inibisciNoteAllegatiDaEffettuare = false;
            var bloccaUploadAllegati = false;
            var bloccaCaricamentoRiepiloghi = false;

            
            if (config.IntegrazioniDocumentali != null)
            {
                bloccaUploadAllegati = config.IntegrazioniDocumentali.BloccaUploadAllegati;
                bloccaCaricamentoRiepiloghi = config.IntegrazioniDocumentali.BloccaUploadRiepiloghiSchedeDinamiche;
				inibisciNoteMovimentoDaEffettuare = config.IntegrazioniDocumentali.IntegrazioniNoInserimentoNote;
				inibisciNoteAllegatiDaEffettuare = config.IntegrazioniDocumentali.IntegrazioniNoNomiAllegati;
            }
			
			return new ParametriIntegrazioniDocumentali(nascondiNoteMovimento, inibisciNoteMovimentoDaEffettuare, inibisciNoteAllegatiDaEffettuare, bloccaUploadAllegati, bloccaCaricamentoRiepiloghi);        }
    }
}
