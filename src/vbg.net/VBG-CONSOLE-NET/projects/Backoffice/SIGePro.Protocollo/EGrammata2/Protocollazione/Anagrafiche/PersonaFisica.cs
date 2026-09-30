using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Anagrafiche
{
    public class PersonaFisica : IPersonaFisicaGiuridica
    {
        ProtocolloAnagrafe _anagrafica;
        AnagraficheService _wrapper;

        public PersonaFisica(ProtocolloAnagrafe anagrafica, AnagraficheService wrapper)
        {
            _anagrafica = anagrafica;
            _wrapper = wrapper;
        }

        public Firm GetFirm()
        {
            var retVal = _anagrafica.ToFirmFromAnagrafica(_wrapper);

            if (retVal == null)
            {
                retVal = new Firm
                {
                    ItemsElementName = new ItemsChoiceType2[] { ItemsChoiceType2.Cognome, ItemsChoiceType2.Nome, ItemsChoiceType2.CodFis },
                    Items = new object[] { _anagrafica.NOMINATIVO, _anagrafica.NOME, _anagrafica.CODICEFISCALE },
                    flgTpAnag = FirmFlgTpAnag.P
                };
            }

            return retVal;
        }


        public EsibDest GetEsibDest()
        {
            var retVal = _anagrafica.ToEsibDestFromAnagrafica(_wrapper);

            if (retVal == null)
            {
                retVal = new EsibDest
                {
                    ItemsElementName = new ItemsChoiceType1[] { ItemsChoiceType1.Cognome, ItemsChoiceType1.Nome, ItemsChoiceType1.CodFis },
                    Items = new object[] { _anagrafica.NOMINATIVO, _anagrafica.NOME, _anagrafica.CODICEFISCALE },
                    flgTpAnag = EsibDestFlgTpAnag.P
                };
            }

            if (!String.IsNullOrEmpty(_anagrafica.ModalitaTrasmissione))
            {
                var modalitaEnum = new EsibDestFlgDestCopia();
                var isParseble = Enum.TryParse(_anagrafica.ModalitaTrasmissione, out modalitaEnum);

                if (!isParseble)
                    throw new Exception(String.Format("IL VALORE {0} DELLA MODALITA' DI TRASMISSIONE DELL'ANAGRAFICA {1} NON E' CORRETTO, SONO AMMESSI SOLO I VALORI -S- O -N-", _anagrafica.ModalitaTrasmissione, _anagrafica.NOMINATIVO));

                retVal.flgDestCopia = modalitaEnum;
            }

            return retVal;
        }
    }
}
