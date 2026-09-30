using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class AmministrazioneEsternaAdapter
    {
        Amministrazioni _amministrazione;
        public AmministrazioneEsternaAdapter(Amministrazioni amm)
        {
            _amministrazione = amm;
        }

        public MittenteMulti AdattaMittente()
        {
            return new MittenteMulti
            {
                Persona = new Persona
                {
                    id = _amministrazione.PARTITAIVA,
                    Denominazione = _amministrazione.AMMINISTRAZIONE
                },
                IndirizzoPostale = new IndirizzoPostale
                {
                    CAP = Convert.ToInt32(_amministrazione.CAP),
                    Comune = _amministrazione.CITTA,
                    Denominazione = _amministrazione.INDIRIZZO,
                    Provincia = _amministrazione.PROVINCIA
                },
                TipoMittente = TipoMittente.AltriMitt,
                TipoMittenteSpecified = true
            };
        }

        public DestinataroMulti AdattaDestinatario()
        {
            var mezzoValidator = new MezzoValidator(_amministrazione.Mezzo);
            mezzoValidator.Validate();

            var retVal = new DestinataroMulti
            {
                Persona = new Persona
                {
                    id = _amministrazione.PARTITAIVA,
                    Denominazione = _amministrazione.AMMINISTRAZIONE
                },
                IndirizzoPostale = new IndirizzoPostale
                {
                    CAP = Convert.ToInt32(_amministrazione.CAP),
                    Comune = _amministrazione.CITTA,
                    Denominazione = _amministrazione.INDIRIZZO,
                    Provincia = _amministrazione.PROVINCIA
                },
                TipoDestinatario = TipoDestinatario.Conoscenza,
                TipoDestinatarioSpecified = true
            };

            if (!String.IsNullOrEmpty(_amministrazione.Mezzo))
                retVal.ModalitaInvio = (ModalitaInvio)Enum.Parse(typeof(ModalitaInvio), _amministrazione.Mezzo);

            return retVal;
        }

    }
}
