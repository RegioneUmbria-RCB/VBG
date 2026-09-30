using Init.SIGePro.Protocollo.MittentiDestinatari;

namespace Init.SIGePro.Protocollo.Test.MittentiDestinatari
{
    internal class FakeQualificaDittaIndividualeService : IQualificaDittaIndividualeService
    {
        private readonly bool _dittaIndividuale = true;

        public FakeQualificaDittaIndividualeService()
        {

        }

        public FakeQualificaDittaIndividualeService(bool dittaIndividuale)
        {
            this._dittaIndividuale = dittaIndividuale;
        }

        public bool DittaIndividuale()
        {
            return this._dittaIndividuale;
        }
    }
}
