using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using ProtocolloInsielService2;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneInputMittentiDestinatariArrivo : ProtocollazioneMittentiDestinatariBase, IProtocollazioneMittentiDestinatari
    {
        public ProtocollazioneInputMittentiDestinatariArrivo(IDatiProtocollo datiProto, ProtocolloLogs logs) : base(datiProto, logs)
        {
            
        }

        public MittenteInsProto[] GetMittenti()
        {
            var listAnagrafica = DatiProto.AnagraficheProtocollo.Select(x => new MittenteInsProto
            {
                descrizione = x.GetNomeCompleto().Replace("  ", " "),
                dati_anagrafica = GetDatiAnagrafici(x),
                inserisci = true,
                inserisciSpecified = true
            });


            var listAmministrazioni = DatiProto.AmministrazioniEsterne.Select(x => new MittenteInsProto()
            {
                dati_anagrafica = GetDatiAmministrazione(x),
                descrizione = x.AMMINISTRAZIONE.Replace("  ", " "),
                inserisci = true,
                inserisciSpecified = true
            });

            var list = listAnagrafica.Union(listAmministrazioni);

            return list.ToArray();
        }

        public DestinatarioIOPInsProto[] GetDestinatari()
        {
            return null;
        }

        public verso Flusso
        {
            get { return verso.A; }
        }

        public UfficioInsProto[] GetUffici()
        {
            return new UfficioInsProto[] { new UfficioInsProto { codice = DatiProto.Uo, giaInviato = true, giaInviatoSpecified = true } };
        }
    }
}
