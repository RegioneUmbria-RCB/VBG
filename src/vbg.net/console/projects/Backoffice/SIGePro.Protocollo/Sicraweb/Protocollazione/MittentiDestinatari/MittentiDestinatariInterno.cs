using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariInterno : MittentiDestinatariBase, IFlussoMittentiDestinatari
    {
        public MittentiDestinatariInterno(IDatiProtocollo datiProto, string codiceAmministrazione, string codiceAoo) : base(datiProto, codiceAmministrazione, codiceAoo)
        {

        }

        public MittenteMulti[] GetMittenti()
        {
            return null;
        }

        public DestinataroMulti[] GetDestinatari()
        {
            return null;
        }


        public Flusso FlussoProtocollo
        {
            get { return Flusso.I; }
        }

        public Mittente MittentePrincipale
        {
            get
            {
                return new Mittente
                {
                    Amministrazione = new Amministrazione
                    {
                        CodiceAmministrazione = CodiceAmministrazione,
                        Denominazione = DatiProto.Amministrazione.AMMINISTRAZIONE,
                        UnitaOrganizzativa = new UnitaOrganizzativa
                        {
                            id = DatiProto.Amministrazione.PROT_UO
                        }
                    },
                    AOO = new AOO { CodiceAOO = CodiceAoo }
                };
            }
        }

        public Destinatario DestinatarioPrincipale
        {
            get
            {
                return new Destinatario
                {
                    Amministrazione = new Amministrazione
                    {
                        CodiceAmministrazione = CodiceAmministrazione,
                        Denominazione = DatiProto.AmministrazioniInterne[0].AMMINISTRAZIONE,
                        UnitaOrganizzativa = new UnitaOrganizzativa
                        {
                            id = DatiProto.AmministrazioniInterne[0].PROT_UO
                        }
                    },
                    AOO = new AOO { CodiceAOO = CodiceAoo }
                };
            }
        }

    }
}
