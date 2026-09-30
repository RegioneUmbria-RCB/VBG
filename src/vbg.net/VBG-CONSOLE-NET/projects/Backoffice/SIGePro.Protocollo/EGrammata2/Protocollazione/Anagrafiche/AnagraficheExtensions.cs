using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Anagrafiche
{
    public static class AnagraficheExtensions
    {
        public static Firm ToFirmFromAnagrafica(this ProtocolloAnagrafe anagrafica, AnagraficheService wrapper)
        { 
            var request = AnagraficheRequestAdapter.Adatta(new AnagraficaService(anagrafica));
            var response = wrapper.LeggiAnagrafica(request);

            if (response == null || response.Length == 0)
                return null;

            return new Firm
            {
                ItemsElementName = new ItemsChoiceType2[] { ItemsChoiceType2.IdAnag },
                Items = new object[] { response[0].codice },
                flgTpAnag = (FirmFlgTpAnag)Enum.Parse(typeof(FirmFlgTpAnag), response[0].tipo.ToString())
            };
        }

        public static Firm ToFirmFromAmministrazione(this Amministrazioni amm, AnagraficheService wrapper)
        {
            var request = AnagraficheRequestAdapter.Adatta(new AmministrazioneService(amm));
            var response = wrapper.LeggiAnagrafica(request);

            if (response == null || response.Length == 0)
            {
                return new Firm
                {
                    ItemsElementName = new ItemsChoiceType2[] { ItemsChoiceType2.RagioneSociale, ItemsChoiceType2.ParIva },
                    Items = new object[] { amm.AMMINISTRAZIONE, amm.PARTITAIVA },
                    flgTpAnag = FirmFlgTpAnag.D
                };
            }

            return new Firm
            {
                ItemsElementName = new ItemsChoiceType2[] { ItemsChoiceType2.IdAnag },
                Items = new object[] { response[0].codice },
                flgTpAnag = (FirmFlgTpAnag)Enum.Parse(typeof(FirmFlgTpAnag), response[0].tipo.ToString())
            };
        }

        public static EsibDest ToEsibDestFromAnagrafica(this ProtocolloAnagrafe anagrafica, AnagraficheService wrapper)
        {
            var request = AnagraficheRequestAdapter.Adatta(new AnagraficaService(anagrafica));
            var response = wrapper.LeggiAnagrafica(request);

            if (response == null || response.Length == 0)
                return null;

            return new EsibDest
            {
                ItemsElementName = new ItemsChoiceType1[] { ItemsChoiceType1.IdAnag },
                Items = new object[] { response[0].codice },
                flgTpAnag = (EsibDestFlgTpAnag)Enum.Parse(typeof(EsibDestFlgTpAnag), response[0].tipo.ToString())
            };
        }

        public static EsibDest ToEsibDestFromAmministrazione(this Amministrazioni amm, AnagraficheService wrapper)
        {
            var request = AnagraficheRequestAdapter.Adatta(new AmministrazioneService(amm));
            var response = wrapper.LeggiAnagrafica(request);

            var retVal = new EsibDest();

            if (response != null && response.Length > 0)
            {
                retVal = new EsibDest
                {
                    ItemsElementName = new ItemsChoiceType1[] { ItemsChoiceType1.IdAnag },
                    Items = new object[] { response[0].codice },
                    flgTpAnag = (EsibDestFlgTpAnag)Enum.Parse(typeof(EsibDestFlgTpAnag), response[0].tipo.ToString())
                };
            }
            else
            {
                retVal = new EsibDest
                {
                    ItemsElementName = new ItemsChoiceType1[] { ItemsChoiceType1.RagioneSociale, ItemsChoiceType1.ParIva },
                    Items = new object[] { amm.AMMINISTRAZIONE, amm.PARTITAIVA },
                    flgTpAnag = EsibDestFlgTpAnag.D
                };
            }

            if (!String.IsNullOrEmpty(amm.ModalitaTrasmissione))
            {
                var modalitaEnum = new EsibDestFlgDestCopia();
                var isParseble = Enum.TryParse(amm.ModalitaTrasmissione, out modalitaEnum);

                if (!isParseble)
                    throw new Exception(String.Format("IL VALORE {0} DELLA MODALITA' DI TRASMISSIONE DELL'AMMINISTRAZIONE {1} NON E' CORRETTO, SONO AMMESSI SOLO I VALORI -S- O -N-", amm.ModalitaTrasmissione, amm.AMMINISTRAZIONE));

                retVal.flgDestCopia = modalitaEnum;
            }

            return retVal;

        }
    }
}
