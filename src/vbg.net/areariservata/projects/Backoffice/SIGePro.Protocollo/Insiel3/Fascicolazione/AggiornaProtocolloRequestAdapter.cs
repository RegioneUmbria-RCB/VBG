using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class AggiornaProtocolloRequestAdapter
    {
        public AggiornaProtocolloRequestAdapter()
        {

        }

        public AggiornamentoProtocolloRequest Adatta(FascicolazioneInfo info, long progDoc, string progMovi)
        {
            return new AggiornamentoProtocolloRequest
            {
                registrazione = new ProtocolloRequest
                {
                    Item = new ProtocolloRequestIdentificatoreProt
                    {
                        anno = info.AnnoProtocollo,
                        numero = info.NumeroProtocollo,
                        codiceRegistro = info.CodiceRegistro,
                        codiceUfficio = info.CodiceUfficio,
                        verso = info.Flusso == "A" ? verso.A : verso.P
                    }
                },
                classifiche = new ClassificheAgg { statoParziale = true, statoParzialeSpecified = true },
                destinatari = new DestinatariAgg { statoParziale = true, statoParzialeSpecified = true },
                documenti = new DocumentiAgg { statoParziale = true, statoParzialeSpecified = true },
                mittenti = new MittentiAgg { statoParziale = true, statoParzialeSpecified = true },
                uffici = new UfficiAgg { statoParziale = true, statoParzialeSpecified = true },

                pratiche = new PraticheAgg
                {
                    statoParziale = false,
                    statoParzialeSpecified = true,
                    pratica = new PraticaAgg[]
                    {
                        new PraticaAgg
                        {
                            Item = new IdProtocollo
                            {
                                progDoc = progDoc,
                                progMovi = progMovi
                            }
                        }
                    }
                }
            };
        }
    }
}
