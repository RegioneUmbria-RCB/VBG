using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class CercaPraticheAdapter
    {
        private readonly ParametriRegoleInfo _parametri;
        private readonly long? _idProtocollo;
        private readonly int? _numeroProtocollo;
        private readonly DateTime? _dataProtocolloDa;
        private readonly DateTime? _dataProtocolloA;


        public CercaPraticheAdapter(ParametriRegoleInfo parametri, string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            _parametri = parametri;
            _idProtocollo = String.IsNullOrEmpty(idProtocollo) ? (long?)null : long.Parse(idProtocollo);
            _numeroProtocollo = String.IsNullOrEmpty(numeroProtocollo) ? (int?)null : int.Parse(numeroProtocollo);
            _dataProtocolloDa = String.IsNullOrEmpty(annoProtocollo) ? (DateTime?)null : DateTime.ParseExact($"01/01/{annoProtocollo}", "dd/MM/yyyy", null);
            _dataProtocolloA = String.IsNullOrEmpty(annoProtocollo) ? (DateTime?)null : DateTime.ParseExact($"31/12/{annoProtocollo}", "dd/MM/yyyy", null);
        }

        public CercaPraticheRequest Adatta()
        {
            return new CercaPraticheRequest
            {
                CodiceLivelloOrganigramma = _parametri.CodiceLivelloOrganigramma,
                IdPratica = _idProtocollo,
                NumeroProtocolloDal = _numeroProtocollo,
                NumeroProtocolloAl = _numeroProtocollo,
                DataProtocollazioneDa = _dataProtocolloDa.HasValue ? _dataProtocolloDa.Value.ToString("dd/MM/yyyy") : null,
                DataProtocollazioneA = _dataProtocolloA.HasValue ? _dataProtocolloA.Value.ToString("dd/MM/yyyy") : null,
                IdOperatore = _parametri.IdOperatore,
                Pagina = 1
            };
        }
    }
}
