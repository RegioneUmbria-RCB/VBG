using Init.SIGePro.Protocollo.Auriga.UD;
using Init.SIGePro.Protocollo.Auriga.UD.AddUD;
using Init.SIGePro.Protocollo.Auriga.UD.AddUD.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using Xunit;

namespace Init.SIGePro.Protocollo.Test.ProtocolloAuriga
{
    public class AddUDResponseInfoTest
    {

        [Fact]
        public void ToDatiProtocolloRes_adatta_nuova_classe()
        {
            var v = new ResponseInfo
            {
                ServiceResponse = new ServiceResponseInfo
                {
                    IdUD = "123",
                    RegistrazioneDataUD = new[] {
                        new EstremiRegNumType
                        {
                            AnnoReg = "2019",
                            NumReg = "123456"
                        }
                    }
                }
            };

            var result = v.ToDatiProtocolloRes();

            Assert.Equal(v.ServiceResponse.RegistrazioneDataUD[0].NumReg, result.NumeroProtocollo);
            Assert.Equal(v.ServiceResponse.RegistrazioneDataUD[0].AnnoReg, result.AnnoProtocollo);
        }

        [Fact]
        public void ToDatiProtocolloRes_solleva_eccezione_se_null_result()
        {
            var v = new ResponseInfo
            {
                ServiceResponse = null
            };

            Assert.Throws<InvalidOperationException>(() => v.ToDatiProtocolloRes());
        }

        [Fact]
        public void ToDatiProtocolloRes_imposta_errore()
        {
            var v = new ResponseInfo
            {
                WsError = "errore!!!!!!",
                ServiceResponse = null
            };

            var result = v.ToDatiProtocolloRes();

            Assert.Equal(v.WsError, result.Errore.Descrizione);
        }

        [Fact]
        public void InvioMailAPiuDestinatari()
        {
            var elencoSoggetti = new List<SoggettoNotifica>
            {
                new SoggettoNotifica { Mezzo = "PEC", Pec = "test.test@test.it" },
                new SoggettoNotifica { Mezzo = "PEC", Pec = "test3.test3@test.it" }
            };

            var attributi = new List<AttributoAddizionaleType>();

            if (elencoSoggetti.Any(x => x.Mezzo == "PEC"))
            {
                attributi.Add(new AttributoAddizionaleType
                {
                    Nome = "INDIRIZZO_EMAIL_DEST_Ud",
                    Item = new AttributoAddizionaleTypeLista
                    {
                        Riga = elencoSoggetti
                                    .Where(x => x.Mezzo == "PEC")
                                    .Select(x =>
                                             new AttributoAddizionaleTypeListaRiga
                                             {
                                                 Colonna = new AttributoAddizionaleTypeListaRigaColonna { Nro = "1", Text = new[] { x.Pec } }
                                             })
                                    .ToArray()
                    }
                });
            }

            var pippo = attributi.ToXmlString();


            Assert.Single(attributi);
            Assert.Equal(2, ((AttributoAddizionaleTypeLista)attributi.First().Item).Riga.Count());
        }
    }
}
