using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Anagrafiche
{
    public class PersonaGiuridica : IPersonaFisicaGiuridica
    {
        ProtocolloAnagrafe _anagrafica;
        AnagraficheService _wrapper;

        public PersonaGiuridica(ProtocolloAnagrafe anagrafica, AnagraficheService wrapper)
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
                    ItemsElementName = new ItemsChoiceType2[] { ItemsChoiceType2.RagioneSociale, ItemsChoiceType2.ParIva },
                    Items = new object[] { _anagrafica.NOMINATIVO, _anagrafica.PARTITAIVA },
                    flgTpAnag = FirmFlgTpAnag.D
                };

                if (String.IsNullOrEmpty(_anagrafica.PARTITAIVA))
                    retVal = new Firm
                    {
                        ItemsElementName = new ItemsChoiceType2[] { ItemsChoiceType2.RagioneSociale, ItemsChoiceType2.CodFis },
                        Items = new object[] { String.Format("{0} {1}", _anagrafica.NOMINATIVO, _anagrafica.NOME).TrimEnd(), _anagrafica.CODICEFISCALE },
                        flgTpAnag = FirmFlgTpAnag.D
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
                    ItemsElementName = new ItemsChoiceType1[] { ItemsChoiceType1.RagioneSociale, ItemsChoiceType1.ParIva },
                    Items = new object[] { _anagrafica.NOMINATIVO, _anagrafica.PARTITAIVA },
                    flgTpAnag = EsibDestFlgTpAnag.D
                };

                if (String.IsNullOrEmpty(_anagrafica.PARTITAIVA))
                {
                    retVal = new EsibDest
                    {
                        ItemsElementName = new ItemsChoiceType1[] { ItemsChoiceType1.RagioneSociale, ItemsChoiceType1.CodFis },
                        Items = new object[] { String.Format("{0} {1}", _anagrafica.NOMINATIVO, _anagrafica.NOME).TrimEnd(), _anagrafica.CODICEFISCALE },
                        flgTpAnag = EsibDestFlgTpAnag.D
                    };
                }
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
