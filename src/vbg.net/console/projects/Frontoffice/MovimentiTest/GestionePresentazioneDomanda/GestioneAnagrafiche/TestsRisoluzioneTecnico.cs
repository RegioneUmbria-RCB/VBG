using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    [TestClass]
    public class TestsRisoluzioneTecnico
    {
        public class MockUserCredentialsStorage : IUserCredentialsStorage
        {
            UserAuthenticationResult _temp;

            public void Set(UserAuthenticationResult uar)
            {
                this._temp = uar;
            }

            public UserAuthenticationResult Get()
            {
                return this._temp;
            }
        }

        private UserAuthenticationResult CreaAuthenticationResult(string codiceFiscale)
        {
            var datiUtente = new AnagraficaUtente
            {
                Nome = "Utente di test",
                Codicefiscale = codiceFiscale
            };
            return new UserAuthenticationResult("abcd", "E256", datiUtente, LivelloAutenticazioneEnum.Identificato);
        }

        private AnagraficaDomanda CreaAnagrafica(string nome, string codiceFiscale, string flagTipoSoggetto)
        {
            var t = new PresentazioneIstanzaDbV2.ANAGRAFEDataTable();
            var row = t.NewANAGRAFERow();

            row.FORMAGIURIDICA = 1;
            row.TIPOSOGGETTO = 1;
            row.DescrSoggetto = "Tipo soggetto";
            row.FlagTipoSoggetto = flagTipoSoggetto;

            row.TIPOANAGRAFE = "F";
            row.NOMINATIVO = nome;
            row.CODICEFISCALE = codiceFiscale;
            row.NOME = nome;

            return AnagraficaDomanda.FromAnagrafeRow(row);
        }


        [TestMethod]
        public void Nessuna_anagrafica_restituisce_null()
        {
            var cf = "123456";
            var storage = new MockUserCredentialsStorage();
            var tested = new LogicaRisoluzioneTecnico(storage);
                        
            storage.Set(CreaAuthenticationResult(cf));

            var result = tested.Risolvi(new List<AnagraficaDomanda>());

            Assert.IsNull(result);
        }

        [TestMethod]
        public void Se_anagrafiche_null_restituisce_null()
        {
            var cf = "123456";
            var storage = new MockUserCredentialsStorage();
            var tested = new LogicaRisoluzioneTecnico(storage);

            storage.Set(CreaAuthenticationResult(cf));

            var result = tested.Risolvi(null);

            Assert.IsNull(result);
        }

        [TestMethod]
        public void Nessuna_anagrafica_trovata_restituisce_primo_soggetto()
        {
            var cf = "123456";

            var storage = new MockUserCredentialsStorage();
            var tested = new LogicaRisoluzioneTecnico(storage);

            storage.Set(CreaAuthenticationResult(cf));

            var anag1 = CreaAnagrafica("Anagrafica test 1", "non_trovato_1", "T");
            var anag2 = CreaAnagrafica("Anagrafica test 2", "non_trovato_2", "T");

            var list = new AnagraficaDomanda[]{
                anag1,
                anag2
            };

            var result = tested.Risolvi(list);

            Assert.IsNotNull(result);
            Assert.AreEqual(anag1.Codicefiscale, result.Codicefiscale);
        }

        [TestMethod]
        public void Ricerca_solo_soggetti_tecnici()
        {
            var cf = "123456";

            var storage = new MockUserCredentialsStorage();
            var tested = new LogicaRisoluzioneTecnico(storage);

            storage.Set(CreaAuthenticationResult(cf));

            var anag1 = CreaAnagrafica("Anagrafica test 1", cf, "R");
            var anag2 = CreaAnagrafica("Anagrafica test 2", cf, "R");

            var list = new AnagraficaDomanda[]{
                anag1,
                anag2
            };

            var result = tested.Risolvi(list);

            Assert.IsNull(result);
        }

        [TestMethod]
        public void Trova_soggetto_loggato()
        {
            var cf = "123456";

            var storage = new MockUserCredentialsStorage();
            var tested = new LogicaRisoluzioneTecnico(storage);

            storage.Set(CreaAuthenticationResult(cf));

            var anag1 = CreaAnagrafica("Anagrafica test 1", "non_trovato", "T");
            var anag2 = CreaAnagrafica("Anagrafica test 2", cf, "T");

            var list = new AnagraficaDomanda[]{
                anag1,
                anag2
            };

            var result = tested.Risolvi(list);

            Assert.IsNotNull(result);
            Assert.AreEqual(anag2.Codicefiscale, result.Codicefiscale);
        }

        [TestMethod]
        public void Se_piu_di_uno_trovato_restituisce_primo()
        {
            var cf = "123456";

            var storage = new MockUserCredentialsStorage();
            var tested = new LogicaRisoluzioneTecnico(storage);

            storage.Set(CreaAuthenticationResult(cf));

            var anag1 = CreaAnagrafica("Anagrafica test 1", "non_trovato", "T");
            var anag2 = CreaAnagrafica("Anagrafica test 2", cf, "T");
            var anag3 = CreaAnagrafica("Anagrafica test 3", cf, "T");

            var list = new AnagraficaDomanda[]{
                anag1,
                anag2,
                anag3
            };

            var result = tested.Risolvi(list);

            Assert.IsNotNull(result);
            Assert.AreEqual(anag2.Codicefiscale, result.Codicefiscale);
        }
    }
}
