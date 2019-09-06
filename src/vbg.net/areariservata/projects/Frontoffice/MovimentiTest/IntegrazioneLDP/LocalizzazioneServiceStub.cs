using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.IntegrazioneLDP
{
    internal class LocalizzazioneServiceStub : ILocalizzazioniService
    {
        public void AggiungiLocalizzazione(int idDomanda, AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni.NuovaLocalizzazione localizzazione, AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni.NuovoRiferimentoCatastale riferimentiCatastali = null)
        {
            throw new NotImplementedException();
        }

        public void AssegnaRiferimentiCatastaliALocalizzazione(int idDomanda, int idLocalizzazione, AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni.NuovoRiferimentoCatastale riferimentiCatastali)
        {
            throw new NotImplementedException();
        }

        public void EliminaLocalizzazione(int idDomanda, int idLocalizzazione)
        {
            throw new NotImplementedException();
        }

        public void EliminaLocalizzazioni(int idDomanda)
        {
            throw new NotImplementedException();
        }

        public void EliminaRiferimentiCatastali(int idDomanda, int idRiferimentoCatastale)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<AppLogic.AreaRiservataService.StradarioDto> FindByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            throw new NotImplementedException();
        }

        public StradarioDto GetById(int id)
        {
            if (id == 0)
            {
                return new StradarioDto
                {
                    CodiceStradario = 0,
                    CodViario = "",
                    NomeVia = "NON DEFINITA"
                };
            }

            return new StradarioDto
            {
                CodiceStradario = id,
                CodViario = id.ToString().PadLeft(6,'0'),
                NomeVia = "Via numero " + id.ToString()
            };
        }

        public StradarioDto GetIndirizzoByCodViario(string codViario)
        {
            return new StradarioDto
            {
                CodiceStradario = Convert.ToInt32(codViario),
                CodViario = codViario,
                NomeVia = "Via da codviario " + codViario
            };            
        }
    }
}
