using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using PersonalLib2.Data;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class ResolveDatiProtocollazioneService
    {
        public string IdComune { get; private set; }
        public string IdComuneAlias { get; private set; }
        public string Software { get; private set; }
        public Istanze Istanza { get; private set; }
        public Movimenti Movimento { get; private set; }
        public string CodiceComune { get; private set; }
        public ProtocolloEnum.AmbitoProtocollazioneEnum TipoAmbito { get; private set; }
        public string CodiceIstanza { get { return Istanza != null ? Istanza.CODICEISTANZA : ""; } }
        public string CodiceMovimento { get { return Movimento != null ? Movimento.CODICEMOVIMENTO : ""; } }
        public int? CodiceInterventoProc
        {
            get
            {
                if (Istanza == null)
                    return (int?)null;
                else
                    if (String.IsNullOrEmpty(Istanza.CODICEINTERVENTOPROC))
                        throw new Exception("CODICEINTERVENTOPROC NON VALORIZZATO");

                return Convert.ToInt32(Istanza.CODICEINTERVENTOPROC);
            }

        }
        public DataBase Db { get; set; }
        public int? CodiceResponsabile { get; set; }
        public int? CodiceResponsabileProcedimentoIstanza { get { return Istanza != null && !String.IsNullOrEmpty(Istanza.CODICERESPONSABILEPROC) ? Convert.ToInt32(Istanza.CODICERESPONSABILEPROC) : (int?)null; } }
        public string NumeroIstanza { get { return Istanza != null ? Istanza.NUMEROISTANZA : ""; } }
        public string Token { get; private set; }

        public ResolveDatiProtocollazioneService(string idComune, string idComuneAlias, string software, DataBase db, Istanze istanza, Movimenti movimento, int? codiceOperatore, ProtocolloEnum.AmbitoProtocollazioneEnum ambito, string token, string codiceComune = "")
        {
            IdComune = idComune;
            IdComuneAlias = idComuneAlias;
            Software = software;
            Istanza = istanza;
            Movimento = movimento;
            CodiceResponsabile = codiceOperatore;
            CodiceComune = codiceComune;
            TipoAmbito = ambito;
            Db = db;
            Token = token;
        }
    }
}
