using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.SIGePro.Manager.DTO.Visura;
using System;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class StatiIstanzaRepositoryFake : IStatiIstanzaRepository
    {
        public StatoIstanzaDto GetById(string software, string codiceStato)
        {
            return new StatoIstanzaDto
            {
                CodiceStato = "ST1",
                Stato = "STATO 1"
            };
        }

        public StatoIstanzaDto[] GetList(string software)
        {
            throw new NotImplementedException();
        }
    }
}
