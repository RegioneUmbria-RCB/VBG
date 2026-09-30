using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using System;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class FormeGiuridicheRepositoryFake : IFormeGiuridicheRepository
    {
        public FormaGiuridicaDto GetById(string id)
        {
            return new FormaGiuridicaDto
            {
                FormaGiuridica = "Forma giuridica",
                CodiceFormaGiuridica = id
            };
        }

        public FormaGiuridicaDto[] GetList(string aliasComune)
        {
            throw new NotImplementedException();
        }
    }
}
