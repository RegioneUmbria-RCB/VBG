using Init.Sigepro.FrontEnd.GestioneMovimenti.Converters;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine
{
    public class MovimentiDiOrigineRepository : IMovimentiDiOrigineRepository
    {
        private readonly MovimentiBackofficeServiceCreator _serviceCreator;

        public MovimentiDiOrigineRepository(MovimentiBackofficeServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public MovimentoDiOrigine GetById(MovimentoDaEffettuare movimentoDaeffettuare)
        {
            var movimentoDiOrigine = this.GetById(movimentoDaeffettuare.IdMovimentoDiOrigine);
            var movimentoDaEffettuare = this.GetById(movimentoDaeffettuare.Id);

            if (movimentoDaEffettuare.PubblicaSchede)
            {
                movimentoDiOrigine.SchedeDinamiche = movimentoDaEffettuare.SchedeDinamiche;
            }

            return movimentoDiOrigine;
        }

        public MovimentoDiOrigine GetByIdHackUsaSoloPerCreazioneMovimento(int idMovimento)
        {
            return this.GetById(idMovimento);
        }

        private MovimentoDiOrigine GetById(int idMovimento)
        {
            using (var svc = this._serviceCreator.CreateClient())
            {
                var movimento = svc.Service.GetMovimento(svc.Token, idMovimento.ToString());
                return movimento.ToMovimentoDiOrigine();
            }
        }

    }
}
