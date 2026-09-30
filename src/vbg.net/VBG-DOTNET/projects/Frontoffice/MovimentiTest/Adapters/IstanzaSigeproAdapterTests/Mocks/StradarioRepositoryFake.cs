using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class StradarioRepositoryFake : IStradarioRepository
    {
        public StradarioEstesoDto GetByCodiceStradario(string aliasComune, int codiceStradario)
        {
            return new StradarioEstesoDto
            {
                CodiceStradario = codiceStradario,
                //CodiceComune = aliasComune,
                Prefisso = "Via",
                Descrizione = "Le mani dal naso"
            };
        }

        public StradarioEstesoDto GetByCodiceStradario(int codiceStradario)
        {
            throw new NotImplementedException();
        }

        public StradarioDto GetByCodViario(string alias, string codViario)
        {
            throw new NotImplementedException();
        }

        public StradarioEstesoDto GetByIndirizzo(string aliasComune, string codiceComune, string indirizzo)
        {
            throw new NotImplementedException();
        }

        public List<StradarioDto> GetByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            throw new NotImplementedException();
        }

        public ValueTask<List<StradarioDto>> GetByMatchParzialeAsync(string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            throw new NotImplementedException();
        }

        public ValueTask<List<StradarioDto>> GetByMatchParzialeAsyncIncludiDisabilitateAsync(string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<DatiComuneCompatto> GetComuniStradario(string codiceComune)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<ColoreStradarioDto> GetListaColori(string aliasComune)
        {
            throw new NotImplementedException();
        }
    }
}
