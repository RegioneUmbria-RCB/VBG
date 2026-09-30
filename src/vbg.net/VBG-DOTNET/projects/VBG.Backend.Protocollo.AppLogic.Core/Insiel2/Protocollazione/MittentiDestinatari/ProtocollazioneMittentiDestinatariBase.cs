using ProtocolloInsielService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneMittentiDestinatariBase
    {
        protected ProtocolloLogs Logs { get; private set; }
        protected IDatiProtocollo DatiProto { get; private set; }

        public ProtocollazioneMittentiDestinatariBase(IDatiProtocollo datiProto, ProtocolloLogs logs)
        {
            this.Logs = logs;
            this.DatiProto = datiProto;
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
                localita = anag.ComuneResidenza != null ? anag.ComuneResidenza.DenominazioneComune : null,
                provincia = anag.ComuneResidenza != null ? anag.ComuneResidenza.SiglaProvincia : null,
                piva = !String.IsNullOrEmpty(anag.PARTITAIVA) ? (anag.PARTITAIVA.Length == 11 ? anag.PARTITAIVA : null) : null
            };
        }

        protected DatiAnagrafica GetDatiAmministrazione(ProtocolloAmministrazioni amm)
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
