using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare
{
    public class MovimentiDaEffettuareRepository : IMovimentiDaEffettuareRepository
    {
        IGestioneMovimentiDataContext _dataContext;
        IMovimentiBackofficeService _movimentiBackofficeService;
        IScadenzeService _scadenzeService;

        public MovimentiDaEffettuareRepository(IScadenzeService scadenzeService, IGestioneMovimentiDataContext dataContext, 
                                                IMovimentiBackofficeService movimentiBackofficeService)
        {
            this._scadenzeService = scadenzeService;
            this._dataContext = dataContext;
            this._movimentiBackofficeService = movimentiBackofficeService;
        }

        #region IMovimentiReadRepository Members

        public MovimentoDaEffettuare GetById(int id)
        {
            var mov = this._dataContext.GetDataStore().MovimentoDaEffettuare;

            if (mov == null || mov.Id != id)
            {
                return null;
            }

            if (!mov.Version.HasValue || mov.Version.Value != SerializationSettings.CurrentVersion)
            {
                return null;
            }

            //var scadenza = this._scadenzeService.GetById(mov.Id);

            //mov.ImpostaMovimentoDiOrigine(this._movimentiBackofficeService.GetById(Convert.ToInt32(scadenza.CodMovimento)));

            return mov;
        }

        public void Save(MovimentoDaEffettuare movimentoDaEffettuare)
        {
            this._dataContext.GetDataStore().MovimentoDaEffettuare = movimentoDaEffettuare;
        }

        public DocumentiIstanzaSostituibili GetDocumentiSostituibili(int idMovimentoDaEffettuare)
        {
            var documentiSostituibili = this._movimentiBackofficeService.GetDocumentiSostituibili(idMovimentoDaEffettuare);

            if (documentiSostituibili.DocumentiIntervento == null)
            {
                documentiSostituibili.DocumentiIntervento = new ListaDocumentiSostituibili();
            }

            if (documentiSostituibili.DocumentiIntervento.Documenti == null)
            {
                documentiSostituibili.DocumentiIntervento.Documenti = new DocumentoSostituibileMovimentoDto[0];
            }

            if (documentiSostituibili.DocumentiEndo == null)
            {
                documentiSostituibili.DocumentiEndo = new ListaDocumentiSostituibili[0];
            }

            return documentiSostituibili;
        }

        public ConfigurazioneMovimentoDaEffettuare GetConfigurazioneMovimento(int idMovimento)
        {
            return this._movimentiBackofficeService.GetConfigurazioneMovimento(idMovimento);
        }


        #endregion

    }
}
