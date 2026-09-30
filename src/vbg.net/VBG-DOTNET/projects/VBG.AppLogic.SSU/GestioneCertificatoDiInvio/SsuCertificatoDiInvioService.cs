namespace VBG.AppLogic.SSU.GestioneCertificatoDiInvio
{
    public class SsuCertificatoDiInvioService
    {
        /// <summary>
        /// Genera il certificato di invio per la domanda SSU e lo carica nel gestore oggetti
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        /// <exception cref="NotImplementedException"></exception>
        public Task<int> GeneraCertificatoDiInvioECreaOggettoAsync(int idDomanda)
        {
            // Legge il certificato di invio dall'api SSU
            // Usa IIstanzaSigeproAdapterService per convertire la domanda in xml VBG
            //     Es. this._istanzaSigeproAdapterService.ToIstanzaBackoffice(domanda.ReadInterface).ToXmlModelloRiepilogo(<<QUI VA INSERITO IL CODICE UNIVOCO DELLA PRATICA RESTITUITO DA SSU>>);
            // Applica la trasformazione XSLT per generare l'html del certificato 
            //     Es. new XslFile(xslTemplate).Trasforma(xmlIstanza))
            // Usa IHtmlToPdfAsyncFileConverter per convertire l'html in PDF
            // Usa IOggettiService per caricare il PDF e restituire il codice oggetto
            // Salva il codice oggetto della ricevuta (valutare come...)
            // Restituisce il codice oggetto

            // Domande:
            // - Ci sono molte operazioni che potrebbero fallire: come vogliamo gestire gli errori?
            // - Dove salviamo il codice oggetto del certificato di invio generato?
            // - Come recuperiamo il codice univoco della pratica da passare alla trasformazione XSLT?

            throw new NotImplementedException();
        }
    }
}
