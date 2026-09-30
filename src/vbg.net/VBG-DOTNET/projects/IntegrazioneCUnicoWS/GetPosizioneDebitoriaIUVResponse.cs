using IntegrazioneCUnicoWS.CUnicoWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace IntegrazioneCUnicoWS
{
    public class GetPosizioneDebitoriaIUVResponse
    {
        public double Imposta { get; private set; } 
        public double Interessi { get; private set; }
        public double Sanzione { get; private set; }
        public double Totale { get; private set; }
        public double ImportoPagato { get; private set; }
        public DateTime? DataPagamento { get; private set; }
        public Esito Esito { get; private set; }

        internal static GetPosizioneDebitoriaIUVResponse FromposizioneDebitoriaIUVResponse(posizioneDebitoriaIUVResponse risposta)
        {
            if (risposta is null)
            {
                throw new ArgumentNullException(nameof(risposta));
            }

            return new GetPosizioneDebitoriaIUVResponse
            {
                Esito = new Esito
                {
                    Ok = risposta.esito.esito1 == 0,
                    Codice = risposta.esito.faultCode,
                    Messaggio = risposta.esito.faultString,
                },
                Imposta = risposta.impDoc,
                Interessi = risposta.impInt,
                Sanzione = risposta.impSan,
                Totale = risposta.totDoc,
                ImportoPagato = risposta.impPag,
                DataPagamento = risposta.datPag > 0 ? DateUtils.CunicoWSDateToDate(risposta.datPag) : (DateTime?)null
            };
        }
    }
}
