using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo.SegnaturaResponse;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        RisultatoRicerca _response;

        public LeggiProtocolloResponseAdapter(RisultatoRicerca response)
        {
            _response = response;
        }

        public DatiProtocolloLettoResponseType Adatta()
        {
            LeggiProtocolloResponseValidation.Validate(_response);
            var doc = _response.Documento[0];

            var mittentiDestinatari = MittentiDestinatariFactory.Create(doc);

            var res = new DatiProtocolloLettoResponseType
            {
                IdProtocollo = doc.IdDoc,
                AnnoProtocollo = doc.RegPrimaria.EstremiReg.Anno,
                NumeroProtocollo = doc.RegPrimaria.EstremiReg.Nro,
                DataProtocollo = doc.DtReg,
                Oggetto = doc.Oggetto,
                Origine = mittentiDestinatari.Flusso,
                InCaricoA = mittentiDestinatari.InCaricoA,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                AnnoNumeroPratica = String.Format("{0}/{1}", doc.AnnoFasc, doc.ProgrFasc)
            };

            string classifica = "";

            if (String.IsNullOrEmpty(doc.Titolo))
                classifica += String.Format("Titolo: {0}", doc.Titolo);

            if (String.IsNullOrEmpty(doc.Classe))
                classifica += String.Format("Classe: {0}", doc.Classe);

            if (String.IsNullOrEmpty(doc.SottoClasse))
                classifica += String.Format("Sotto Classe: {0}", doc.SottoClasse);

            res.Classifica_Descrizione = classifica;

            return res;

        }
    }
}
