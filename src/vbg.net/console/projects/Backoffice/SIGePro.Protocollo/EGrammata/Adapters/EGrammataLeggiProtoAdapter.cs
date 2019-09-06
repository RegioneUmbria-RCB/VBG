using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.GetProtoOutput;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.EGrammata.Adapters
{
    internal class EGrammataLeggiProtoAdapter
    {
        public static class Constants
        {
            public const string ENTRATA = "E";
            public const string USCITA = "U";
            public const string INTERNO = "I";
        }

        DatiUD _response;
        public readonly DatiProtocolloLetto DatiProtocollo;

        public EGrammataLeggiProtoAdapter(DatiUD response)
        {
            _response = response;
            DatiProtocollo = CreaDatiProtocollo();    
        }

        private DatiProtocolloLetto CreaDatiProtocollo()
        {
            var datiRes = new DatiProtocolloLetto();

            datiRes.AnnoProtocollo = _response.DataEOraRegistrazione.ToString("yyyy");
            datiRes.DataProtocollo = _response.DataEOraRegistrazione.ToString("dd/MM/yyyy");

            if (_response.EstremiRegistrazione != null && _response.EstremiRegistrazione.Length > 0)
                 datiRes.NumeroProtocollo = _response.EstremiRegistrazione[0].Numero;

            datiRes.Oggetto = _response.OggettoUD;
            datiRes.Origine = GetFlusso();

            if (_response.Originale != null && _response.Originale.ClassifFascicolo != null && _response.Originale.ClassifFascicolo.Classificazione != null)
            {
                datiRes.Classifica = String.Concat(_response.Originale.ClassifFascicolo.Classificazione.Classe, ".", _response.Originale.ClassifFascicolo.Classificazione.Sottoclasse, ".", _response.Originale.ClassifFascicolo.Classificazione.Titolo);
                datiRes.Classifica_Descrizione = _response.Originale.ClassifFascicolo.Classificazione.Descrizione;
            }

            if (_response.TipoProvenienza == Constants.ENTRATA)
            {
                if (_response.Item != null)
                {
                    var datiEntrata = (DatiUDDatiEntrata)_response.Item;
                    if (datiEntrata.MittenteEsterno != null)
                    {
                        datiRes.MittentiDestinatari = datiEntrata.MittenteEsterno.Select(x => new MittDestOut
                        {
                            CognomeNome = String.Concat(x.Denominazione_Cognome, " ", x.Nome),
                            IdSoggetto = x.IdInAnagrafeProt
                        }).ToArray();
                    }
                }

                if (_response.Originale != null && _response.Originale.Assegnatario != null)
                {
                    datiRes.InCaricoA = _response.Originale.Assegnatario.IdUO;
                    datiRes.InCaricoA_Descrizione = _response.Originale.Assegnatario.DenominazioneUO;

                    /*var uoList = _response.DestinatarioInterno.ToList();
                    uoList.ForEach(x =>
                    {
                        datiRes.InCaricoA = String.Format("{0} ({1})", x.IdUO, x.LivelloUO);
                        datiRes.InCaricoA_Descrizione = x.DenominazioneUO;
                    });*/
                }

            }
            else
            {
                if (_response.Item != null)
                {
                    var datiUoUscita = (DatiUDDatiProduzione)_response.Item;
                    
                    if (datiUoUscita.Provenienza != null)
                    {
                        datiRes.InCaricoA = datiUoUscita.Provenienza.IdUO;
                        datiRes.InCaricoA_Descrizione = datiUoUscita.Provenienza.DenominazioneUO;
                    }

                    if (_response.TipoProvenienza == Constants.USCITA)
                    {
                        if (_response.DatiUscita != null && _response.DatiUscita.DestinatarioEsterno != null)
                        {
                            datiRes.MittentiDestinatari = _response.DatiUscita.DestinatarioEsterno.Select(x => new MittDestOut
                            {
                                CognomeNome = String.Concat(x.Denominazione_Cognome, " ", x.Nome),
                                IdSoggetto = x.IdInAnagrafeProt
                            }).ToArray();
                        }
                    }

                    if (_response.TipoProvenienza == Constants.INTERNO)
                    {
                        if (_response.DestinatarioInterno != null)
                        {
                            datiRes.MittentiDestinatari = _response.DestinatarioInterno.Select(x => new MittDestOut
                            {
                                CognomeNome = x.DenominazioneUO,
                                IdSoggetto = String.Format("{0} ({1})", x.IdUO, x.LivelloUO)
                            }).ToArray();
                        }
                    }
                }
            }

            if (_response.AllegatoUD != null)
            {
                datiRes.Allegati = _response.AllegatoUD.Select(x => new AllOut 
                { 
                    Commento = x.DesAllegato,
                    IDBase = x.VersioneElettronica,
                    TipoFile = x.TipoDocAllegato,
                    Serial = x.VersioneElettronica
                }).ToArray();
            }
            
            return datiRes;

        }

        private string GetFlusso()
        {
            string flusso = String.Empty;

            if (_response.TipoProvenienza == Constants.USCITA)
                flusso = ProtocolloConstants.COD_PARTENZA;
            else if (_response.TipoProvenienza == Constants.ENTRATA)
                flusso = ProtocolloConstants.COD_ARRIVO;
            else
                flusso = ProtocolloConstants.COD_INTERNO;

            return flusso;
        }

    }
}
