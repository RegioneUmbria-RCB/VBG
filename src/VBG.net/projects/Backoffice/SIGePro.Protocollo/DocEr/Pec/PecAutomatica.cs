using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public class PecAutomatica : IDatiPec
    {
        IDatiProtocollo _datiProto;
        MittDestType[] _destinatari;

        public PecAutomatica(IDatiProtocollo datiProto)
        {
            _datiProto = datiProto;

            var anagrafiche = datiProto.AnagraficheProtocollo.Where(x => !String.IsNullOrEmpty(x.Pec)).Select(y => y.ToMittDestTypeFromAnagrafica());
            var amministrazioni = datiProto.AmministrazioniEsterne.Where(x => !String.IsNullOrEmpty(x.PEC)).Select(y => y.ToMittDestTypeFromAmministrazione());

             _destinatari = anagrafiche.Union(amministrazioni).ToArray();
        }

        public string Oggetto
        {
            get { return _datiProto.ProtoIn.Oggetto; }
        }

        public FlussoType Flusso
        {
            get { return FlussoPecAdapter.Adatta(); }
        }

        public IntestazioneTypeDestinatari Destinatari
        {
            get { return new IntestazioneTypeDestinatari { Destinatario = _destinatari }; }
        }
    }
}
