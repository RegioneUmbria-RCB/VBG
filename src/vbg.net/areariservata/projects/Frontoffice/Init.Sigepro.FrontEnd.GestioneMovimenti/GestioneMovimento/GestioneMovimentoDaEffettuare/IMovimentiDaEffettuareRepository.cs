using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare
{
    public interface IMovimentiDaEffettuareRepository
    {
        /// <summary>
        /// Ottiene i dati di un movimento da efettuare
        /// </summary>
        /// <param name="id">Id del movimento frontoffice</param>
        /// <returns>Dati del movimento corrispondente all'id specificato o null se l'id non è stato trovato</returns>
        MovimentoDaEffettuare GetById(int id);

        /// <summary>
        /// Salva i dati di un movimento da effettuare nel frontoffice
        /// </summary>
        /// <param name="movimentoDaEffettuare">Dati del movimento da effettuare</param>
        void Save(MovimentoDaEffettuare movimentoDaEffettuare);

        DocumentiIstanzaSostituibili GetDocumentiSostituibili(int idMovimentoDaEffettuare);

        ConfigurazioneMovimentoDaEffettuare GetConfigurazioneMovimento(int idMovimento);
    }
}
