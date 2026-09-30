//using VBG.Frontend.AppLogic.WsAnagraficheService;
//using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
//using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
//using System.Collections.Generic;

//namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
//{
//    public interface IDatiDomandaFoRepository
//    {
//        void Elimina(int idDomanda);
//        PresentazioneIstanzaDbV2 LeggiDataSetDomanda(string aliasComune, int idDomanda);
//        List<FoDomande> LeggiDomandeInSospeso(string aliasComune, string software, int codiceAnagrafe);
//        EsitoSalvataggioDomandaOnline Salva(DomandaOnline domanda);

//        bool DomandaPresentata(string aliasComune, int idDomanda);
//        FoDomande LeggiDatiDomanda(string aliasComune, int idDomanda);
//        int GeneraProssimoIdDomanda(string aliasComune);

//        byte[] ConvertToXml(DomandaOnline domanda);
//        void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine);
//    }
//}
