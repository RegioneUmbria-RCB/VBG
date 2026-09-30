using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{

    public class MittentiDestinatariArrivo : MittentiDestinatariBase, IFlussoMittentiDestinatari
    {
        public MittentiDestinatariArrivo(IDatiProtocollo datiProto, string codiceAmministrazione, string codiceAoo) : base(datiProto, codiceAmministrazione, codiceAoo)
        {

        }

        public MittenteMulti[] GetMittenti()
        {
            try
            {
                var mittentiAmministrazioni = DatiProto.AmministrazioniProtocollo.Select(x => x.ToMittentiMultipli());
                var mittentiAnagrafiche = DatiProto.AnagraficheProtocollo.Select(x => x.ToMittentiMultipli());

                var mittenti = new List<MittenteMulti>();

                mittenti.AddRange(mittentiAmministrazioni);
                mittenti.AddRange(mittentiAnagrafiche);

                mittenti.First().TipoMittente = TipoMittente.Principale;

                return mittenti.ToArray();
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA VALIDAZIONE DEI MITTENTI", ex);
            }
        }

        public DestinataroMulti[] GetDestinatari()
        {
             return null;
        }

        public Flusso FlussoProtocollo
        {
            get { return Flusso.E; }
        }

        public Mittente MittentePrincipale
        {
            get 
            { 
                var firstMittente = GetMittenti().First();
                return new Mittente
                {
                    Persona = firstMittente.Persona,
                    IndirizzoPostale = firstMittente.IndirizzoPostale
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
                        Denominazione = DatiProto.Amministrazione.AMMINISTRAZIONE, 
                        UnitaOrganizzativa = new UnitaOrganizzativa 
                        { 
                            id = DatiProto.Amministrazione.PROT_UO
                        } 
                    },
                    AOO = new AOO{ CodiceAOO = CodiceAoo }
                };
            }
        }
    }
}
