using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;

namespace AreaRiservataCore.AppLogic.GestioneMovimenti
{
    public class IdMovimentoResolverService : IIdMovimentoResolver
    {
        private int _idMovimentoId;
        public int IdMovimento { get => _idMovimentoId; }

        public void SetIdMovimento(int idMovimento)
        {
            _idMovimentoId = idMovimento;
        }
    }
}
