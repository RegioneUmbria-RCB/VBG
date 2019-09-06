using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare
{
    public class CachedMovimentiDaEffettuareRepository : IMovimentiDaEffettuareRepository
    {
        MovimentiDaEffettuareRepository _repository;

        public CachedMovimentiDaEffettuareRepository(MovimentiDaEffettuareRepository repository)
        {
            this._repository = repository;
        }

        public MovimentoDaEffettuare GetById(int id)
        {
            throw new NotImplementedException();
        }

        public void Save(MovimentoDaEffettuare movimentoDaEffettuare)
        {
            throw new NotImplementedException();
        }


        public MovimentiWebService.DocumentiIstanzaSostituibili GetDocumentiSostituibili(int idMovimentoDaEffettuare)
        {
            throw new NotImplementedException();
        }


        public MovimentiWebService.ConfigurazioneMovimentoDaEffettuare GetConfigurazioneMovimento(int idMovimento)
        {
            throw new NotImplementedException();
        }
    }

    /*
        private class DatiMovimentoWrapper
        {
            IMovimentiDaEffettuareRepository _readRepository;
            int _idMovimento;
            MovimentoDaEffettuare _datiMovimento;

            public DatiMovimentoWrapper(IMovimentiDaEffettuareRepository readRepository, int idMovimento)
            {
                this._readRepository = readRepository;
                this._idMovimento = idMovimento;
            }

            public MovimentoDaEffettuare Get()
            {
                if (this._datiMovimento == null)
                    _datiMovimento = this._readRepository.GetById(this._idMovimento);

                return _datiMovimento;
            }

            public void InvalidaCache()
            {
                this._datiMovimento = null;
            }
        }
     * 
     */
}
