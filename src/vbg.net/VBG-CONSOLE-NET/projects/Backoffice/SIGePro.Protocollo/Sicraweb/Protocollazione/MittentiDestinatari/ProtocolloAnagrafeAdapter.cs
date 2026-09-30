using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class ProtocolloAnagrafeAdapter
    {
        ProtocolloAnagrafe _anagrafica;

        public ProtocolloAnagrafeAdapter(ProtocolloAnagrafe anag)
        {
            _anagrafica = anag;
        }

        public MittenteMulti AdattaMittente()
        {
            var comune = _anagrafica.GetComune();

            return new MittenteMulti
            {
                Persona = new Persona
                {
                    Cognome = _anagrafica.GetCognome(),
                    Denominazione = _anagrafica.GetDenominazione(),
                    Nome = _anagrafica.NOME,
                    id = _anagrafica.GetId(),
                },
                IndirizzoPostale = new IndirizzoPostale
                {
                    CAP = Convert.ToInt32(comune.CAP),
                    Comune = comune.COMUNE,
                    Provincia = comune.SIGLAPROVINCIA,
                    Denominazione = _anagrafica.INDIRIZZO
                },
                TipoMittente = TipoMittente.AltriMitt,
                TipoMittenteSpecified = true
            };
        }

        public DestinataroMulti AdattaDestinatario()
        {
            var comune = _anagrafica.GetComune();

            var mezzoValidator = new MezzoValidator(_anagrafica.Mezzo);
            mezzoValidator.Validate();

            var retVal = new DestinataroMulti
            {
                Persona = new Persona
                {
                    Cognome = _anagrafica.GetCognome(),
                    Denominazione = _anagrafica.GetDenominazione(),
                    Nome = _anagrafica.NOME,
                    id = _anagrafica.GetId()
                },
                IndirizzoPostale = new IndirizzoPostale
                {
                    CAP = Convert.ToInt32(comune.CAP),
                    Comune = comune.COMUNE,
                    Provincia = comune.SIGLAPROVINCIA,
                    Denominazione = _anagrafica.INDIRIZZO
                },
                TipoDestinatario = TipoDestinatario.Conoscenza,
                TipoDestinatarioSpecified = true
            };

            if (!String.IsNullOrEmpty(_anagrafica.Mezzo))
                retVal.ModalitaInvio = (ModalitaInvio)Enum.Parse(typeof(ModalitaInvio), _anagrafica.Mezzo);

            return retVal;
        }
    }
}
