using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : MittentiDestinatariBase, IFlussoMittentiDestinatari
    {

        public MittentiDestinatariPartenza(IDatiProtocollo datiProto, string codiceAmministrazione, string codiceAoo) : base(datiProto, codiceAmministrazione, codiceAoo)
        {

        }

        public MittenteMulti[] GetMittenti()
        {
            return null;
        }

        public DestinataroMulti[] GetDestinatari()
        {
            try
            {
                var destinatariAmministrazioni = DatiProto.AmministrazioniProtocollo.Select(x => x.ToDestinatariMultipli());
                var destinatriAnagrafiche = DatiProto.AnagraficheProtocollo.Select(x => x.ToDestinatariMultipli());

                var destinatari = new List<DestinataroMulti>();

                destinatari.AddRange(destinatariAmministrazioni);
                destinatari.AddRange(destinatriAnagrafiche);

                destinatari.First().TipoDestinatario = TipoDestinatario.Principale;

                return destinatari.ToArray();
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA VALIDAZIONE DEI DESTINATARI", ex);
            }
        }


        public Flusso FlussoProtocollo
        {
            get { return Flusso.U; }
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
                var firstDestinatario = GetDestinatari().First();
                return new Destinatario
                {
                    Persona = firstDestinatario.Persona,
                    IndirizzoPostale = firstDestinatario.IndirizzoPostale,
                    ModalitaInvio = firstDestinatario.ModalitaInvio
                };
            }

        }

    }
}
