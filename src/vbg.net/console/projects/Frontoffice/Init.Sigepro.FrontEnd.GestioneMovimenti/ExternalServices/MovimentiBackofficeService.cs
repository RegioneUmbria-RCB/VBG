// -----------------------------------------------------------------------
// <copyright file="MovimentiBackofficeService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
    using AutoMapper;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using ServiceStack.Text;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;


    /// <summary>
    /// Utilizza il web service di backoffice per permettere la lettura dei dati di un movimento effettuato nel backoffice
    /// </summary>
    public class MovimentiBackofficeService : IMovimentiBackofficeService
    {
        MovimentiBackofficeServiceCreator _serviceCreator;

        public MovimentiBackofficeService( MovimentiBackofficeServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        #region IMovimentiBackofficeService Members

        public GestioneMovimentiDataStore GetDataStore(int idMovimento)
        {
            using (var svc = this._serviceCreator.CreateClient())
            {
                var json = svc.Service.GetJsonMovimentoFrontoffice(svc.Token, idMovimento);

                return TypeSerializer.DeserializeFromString<GestioneMovimentiDataStore>(json);
            }
        }


        public void Save(int idMovimento, GestioneMovimentiDataStore dataStore)
        {
            using (var svc = this._serviceCreator.CreateClient())
            {
                var json = TypeSerializer.SerializeToString<GestioneMovimentiDataStore>(dataStore);

                svc.Service.SalvaJsonMovimentoFrontoffice(svc.Token, idMovimento, json);
            }
        }


        public void ImpostaComeTrasmesso(int idMovimento)
        {
            using (var svc = this._serviceCreator.CreateClient())
            {
                svc.Service.ImpostaFlagTrasmesso(svc.Token, idMovimento);
            }
        }


        public DocumentiIstanzaSostituibili GetDocumentiSostituibili(int idMovimento)
        {
            using (var svc = this._serviceCreator.CreateClient())
            {
                return svc.Service.GetDocumentiSostituibili(svc.Token, idMovimento);
            }
        }

        public ConfigurazioneMovimentoDaEffettuare GetConfigurazioneMovimento(int idMovimento)
        {
            using (var svc = this._serviceCreator.CreateClient())
            {
                return svc.Service.GetConfigurazioneMovimento(svc.Token, idMovimento);
            }
        }

        #endregion
    }
}
