using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena;
using Xunit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneLocalizzazioni.Modena
{
    public class LocalizzazioniMappaModenaSelezionateTests
    {
        [Fact]
        public void Deserializzazione_da_stringa_json()
        {
            var stringaJson = @"{ ""particelleGrafiche"":
                                    [
                                        {
                                            ""codCatastale"":""F257"",
                                            ""foglio"":""7"",
                                            ""particella"":""4""
                                        },
                                        {
                                            ""codCatastale"":""F257"",
                                            ""foglio"":""7"",
                                            ""particella"":""16""
                                        }
                                    ],
                                    ""particelleManuali"":[
                                        {
                                            ""codCatastale"":""F257"",
                                            ""foglio"":""7"",
                                            ""particella"":""ZZ""
                                        },
                                    ]}";

            var result = LocalizzazioniMappaModenaSelezionate.FromJsonString(stringaJson);

            Assert.Equal(2, result.ParticelleGrafiche.Length);
            Assert.Single(result.ParticelleManuali);

            Assert.Equal("F257", result.ParticelleGrafiche[0].CodCatastale);
            Assert.Equal("7", result.ParticelleGrafiche[0].Foglio);
            Assert.Equal("4", result.ParticelleGrafiche[0].Particella);

            Assert.Equal("F257", result.ParticelleGrafiche[1].CodCatastale);
            Assert.Equal("7", result.ParticelleGrafiche[1].Foglio);
            Assert.Equal("16", result.ParticelleGrafiche[1].Particella);

            Assert.Equal("F257", result.ParticelleManuali[0].CodCatastale);
            Assert.Equal("7", result.ParticelleManuali[0].Foglio);
            Assert.Equal("ZZ", result.ParticelleManuali[0].Particella);
        }

        [Fact]
        public void No_npe_su_ParticelleTotali_se_non_sono_presenti_particelle()
        {
            var stringaJson = @"{ ""particelleGrafiche"": [], ""particelleManuali"":[]}";

            var result = LocalizzazioniMappaModenaSelezionate.FromJsonString(stringaJson);

            Assert.Empty(result.ParticelleTotali);
        }

        [Fact]
        public void No_npe_su_ParticelleTotali_se_non_e_presente_un_elemento()
        {
            var stringaJson = @"{ ""particelleGrafiche"": [{
                                            ""codCatastale"":""F257"",
                                            ""foglio"":""7"",
                                            ""particella"":""4""
                                        }]}";

            var result = LocalizzazioniMappaModenaSelezionate.FromJsonString(stringaJson);

            Assert.Single(result.ParticelleTotali);
        }
    }
}
