using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocEr.Autenticazione;

namespace Init.SIGePro.Protocollo.DocEr.Fascicolazione
{
    public class FascicolazioneFactory
    {
        public static IFascicolazione Create(string idProtocollo, string numeroProtocollo, string annoProtocollo, FascicolazioneConfiguration conf)
        {
            if (conf.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                idProtocollo = conf.DatiIstanza.FKIDPROTOCOLLO;
                if (String.IsNullOrEmpty(idProtocollo))
                    idProtocollo = conf.DocWrapper.CercaProtocollo(conf.DatiIstanza.NUMEROPROTOCOLLO, conf.DatiIstanza.DATAPROTOCOLLO.Value.Year.ToString(), conf.Vert.CodiceEnte, conf.Vert.CodiceAoo, conf.Vert.PadNumeroProtocolloLength, conf.Vert.PadNumeroProtocolloChar);
                
                return new FascicolazioneIstanza(Convert.ToInt32(idProtocollo), conf.Vert, conf.DocWrapper, conf.FascWrapper, conf.DatiFascicolo, conf.MovimentiProtocollati, conf.Auth);
            }
            else if (conf.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                idProtocollo = conf.DatiMovimento.FKIDPROTOCOLLO;
                if (String.IsNullOrEmpty(conf.DatiMovimento.FKIDPROTOCOLLO))
                    idProtocollo = conf.DocWrapper.CercaProtocollo(conf.DatiMovimento.NUMEROPROTOCOLLO, conf.DatiMovimento.DATAPROTOCOLLO.Value.Year.ToString(), conf.Vert.CodiceEnte, conf.Vert.CodiceAoo, conf.Vert.PadNumeroProtocolloLength, conf.Vert.PadNumeroProtocolloChar);

                return new FascicolazioneMovimento(Convert.ToInt32(idProtocollo), conf.DatiFascicolo, conf.FascWrapper);
            }
            else if (conf.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO)
            {
                if (String.IsNullOrEmpty(idProtocollo))
                    idProtocollo = conf.DocWrapper.CercaProtocollo(numeroProtocollo, annoProtocollo, conf.Vert.CodiceEnte, conf.Vert.CodiceAoo, conf.Vert.PadNumeroProtocolloLength, conf.Vert.PadNumeroProtocolloChar);

                return new FascicolazioneNessuno(Convert.ToInt32(idProtocollo), conf.Vert, conf.DocWrapper, conf.FascWrapper, conf.DatiFascicolo, conf.Auth);
            }
            else
                throw new Exception("AMBITO NON AMMESSO");
        }
    }
}
