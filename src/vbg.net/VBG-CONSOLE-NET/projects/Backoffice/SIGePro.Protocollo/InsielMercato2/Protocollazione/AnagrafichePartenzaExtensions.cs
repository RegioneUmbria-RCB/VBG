using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.InsielMercato2.Services;
using Init.SIGePro.Protocollo.InsielMercato2.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.InsielMercato2.Protocollazione
{
    public static class AnagrafichePartenzaExtensions
    {
        public static recipient ToRecipientAnagrafe(this ProtocolloAnagrafe anagrafe, ProtocollazioneService wrapper, VerticalizzazioniConfiguration vert)
        {
            var res = new recipient
            {
                description = anagrafe.GetNomeCompleto(),
                referenceDate = DateTime.MaxValue,
                referenceDateSpecified = true,
            };

            if (!String.IsNullOrEmpty(anagrafe.ModalitaTrasmissione))
                res.transmissionMode = anagrafe.ModalitaTrasmissione;

            if (String.IsNullOrEmpty(anagrafe.Pec))
                return res;

            res.pecSend = true;
            res.pecSendSpecified = true;

            var descrizioneAnagrafica = String.Format("{0} ({1})", anagrafe.GetNomeCompleto(), anagrafe.Pec);

            var request = SearchAnagraficheRequestAdapter.Adatta(vert, descrizioneAnagrafica, anagrafe.Pec);
            var anagraficList = wrapper.SearchAnagrafica(request);

            if (anagraficList.anagraficList.Length > 0)
            {
                res.description = anagraficList.anagraficList[0].anagraficDescription;
                res.emailPec = anagraficList.anagraficList[0].emailPec;
                return res;
            }

            var requestPecVuota = SearchAnagraficheRequestAdapter.Adatta(vert, descrizioneAnagrafica, "");
            var anagraficListPecVuota = wrapper.SearchAnagrafica(requestPecVuota);

            if (anagraficListPecVuota.anagraficList.Length > 0)
            {
                res.description = descrizioneAnagrafica;
                res.emailPec = "";
                return res;
            }

            res.description = descrizioneAnagrafica;
            res.emailPec = anagrafe.Pec;
            return res;
        }

        public static recipient ToRecipientAmministrazione(this Amministrazioni amm, ProtocollazioneService wrapper, VerticalizzazioniConfiguration vert)
        {
            var res = new recipient
            {
                description = amm.AMMINISTRAZIONE,
                referenceDate = DateTime.MaxValue,
                referenceDateSpecified = true
            };

            if (!String.IsNullOrEmpty(amm.ModalitaTrasmissione))
                res.transmissionMode = amm.ModalitaTrasmissione;

            if (String.IsNullOrEmpty(amm.PEC))
                return res;

            res.pecSend = true;
            res.pecSendSpecified = true;

            var descrizioneAnagrafica = String.Format("{0} ({1})", amm.AMMINISTRAZIONE, amm.PEC);

            var request = SearchAnagraficheRequestAdapter.Adatta(vert, amm.AMMINISTRAZIONE, amm.PEC);
            var anagraficList = wrapper.SearchAnagrafica(request);

            if (anagraficList.anagraficList.Length > 0)
            {
                res.description = anagraficList.anagraficList[0].anagraficDescription;
                res.emailPec = anagraficList.anagraficList[0].emailPec;
                return res;
            }

            var requestPecVuota = SearchAnagraficheRequestAdapter.Adatta(vert, amm.AMMINISTRAZIONE, "");
            var anagraficListPecVuota = wrapper.SearchAnagrafica(requestPecVuota);

            if (anagraficListPecVuota.anagraficList.Length > 0)
            {
                res.description = descrizioneAnagrafica;
                res.emailPec = "";
            }

            res.description = descrizioneAnagrafica;
            res.emailPec = amm.PEC;

            return res;
        }

    }
}
