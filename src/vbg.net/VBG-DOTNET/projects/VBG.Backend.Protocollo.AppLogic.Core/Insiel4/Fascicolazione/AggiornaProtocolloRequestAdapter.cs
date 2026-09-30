using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Collections.Generic;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
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
                Registrazione = new RegistrazioneID
                {
                    Estremi = new EstremiRegistrazioneProtocollo
                    {
                        Anno = info.AnnoProtocollo,
                        Numero = info.NumeroProtocollo,
                        Verso = EnumConverter.ConvertToVersoEnum(info.Flusso),
                        CodiceUfficio = info.CodiceUfficio,
                        CodiceRegistro = info.CodiceRegistro
                    },
                    //Id = new IdRegistrazioneProtocollo
                    //{
                    //    ProgressivoDocumento = progDoc.ToString(),
                    //    ProgressivoMovimento = progMovi
                    //}
                },
                Classifiche = new ClassificheAgg { StatoParziale = true },
                Destinatari = new DestinatariAgg { StatoParziale = true },
                Documenti = new DocumentiAgg { StatoParziale = true },
                Mittenti = new MittentiAgg { StatoParziale = true },
                Uffici = new UfficiAgg { StatoParziale = true },
                Precedenti = new PrecedentiAgg { StatoParziale = true },
                Fascicoli = new FascicoliAgg
                {
                    Fascicoli = new List<FascicoloAgg>() {
                        new FascicoloAgg() {
                            IdRegistrazione = new IdRegistrazione()
                            {
                                ProgressivoDocumento = progDoc.ToString(),
                                ProgressivoMovimento = progMovi,

                            }
                        }
                    }
                }
            };
        }
    }
}
