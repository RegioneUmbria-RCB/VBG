using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Insiel2.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneMittentiDestinatariBase
    {
        protected ProtocolloLogs Logs { get; private set; }
        protected IDatiProtocollo DatiProto { get; private set; }

        public ProtocollazioneMittentiDestinatariBase(IDatiProtocollo datiProto, ProtocolloLogs logs)
        {
            Logs = logs;
            DatiProto = datiProto;
        }

        protected DatiAnagrafica GetDatiAnagrafici(ProtocolloAnagrafe anag)
        {
            return new DatiAnagrafica()
            {
                cap = anag.CAP,
                codfis = !String.IsNullOrEmpty(anag.CODICEFISCALE) ? (anag.CODICEFISCALE.Length == 16 ? anag.CODICEFISCALE : null) : null,
                cognome = anag.TIPOANAGRAFE == "F" ? anag.NOMINATIVO.Replace("  ", " ") : null,
                nome = anag.TIPOANAGRAFE == "F" ? anag.NOME.Replace("  ", " ") : null,
                denominaz = anag.TIPOANAGRAFE == "G" ? anag.NOMINATIVO.Replace("  ", " ") : null,
                indirizzo = anag.INDIRIZZO,
                localita = anag.ComuneResidenza != null ? anag.ComuneResidenza.COMUNE : null,
                provincia = anag.ComuneResidenza != null ? anag.ComuneResidenza.SIGLAPROVINCIA : null,
                piva = !String.IsNullOrEmpty(anag.PARTITAIVA) ? (anag.PARTITAIVA.Length == 11 ? anag.PARTITAIVA : null) : null
            };
        }

        protected DatiAnagrafica GetDatiAmministrazione(Amministrazioni amm)
        {
            return new DatiAnagrafica
            {
                cap = amm.CAP,
                denominaz = amm.AMMINISTRAZIONE.Replace("  ", " "),
                indirizzo = amm.INDIRIZZO,
                localita = amm.CITTA,
                piva = !String.IsNullOrEmpty(amm.PARTITAIVA) ? (amm.PARTITAIVA.Length == 11 ? amm.PARTITAIVA : null) : null
            };
        }

    }
}
