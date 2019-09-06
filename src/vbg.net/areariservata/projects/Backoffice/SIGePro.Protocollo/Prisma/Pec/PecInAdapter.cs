using Init.SIGePro.Protocollo.InvioPecAdsService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Pec
{
    public class PecInAdapter
    {
        public string[] DestinatariConPec { get; private set; }

        public PecInAdapter()
        {

        }

        public ParametriIngressoPG Adatta(PecInfo info)
        {
            try
            {
                var dic = info.Destinatari.Where(x => !String.IsNullOrEmpty(x.Pec)).GroupBy(x => x.Pec.ToUpperInvariant());
                var destinatariConPec = dic.Select(x => x.First());

                if (destinatariConPec.Count() == 0)
                {
                    throw new Exception("NON E' PRESENTE NESSUN DESTINATARIO CON UNA PEC VALIDA");
                }

                this.DestinatariConPec = destinatariConPec.Select(x => $"{x.NomeCognome} - {x.Pec}").ToArray();

                var destinatariPec = String.Join("###", destinatariConPec.Select(x => x.Pec));

                var param = new ParametriIngressoPG
                {
                    anno = info.AnnoProtocollo,
                    numero = info.NumeroProtocollo,
                    listaDestinatari = destinatariPec,
                    utente_creazione = info.Utente,
                    tipoRegistro = info.TipoRegistro
                };

                return param;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA CREAZIONE DELLA RICHIESTA PER LA PEC DEL PROTOCOLLO NUMERO {info.NumeroProtocollo.ToString()}, ANNO {info.AnnoProtocollo.ToString()}, ERRORE: {ex.Message}", ex);
            }
        }
    }
}
