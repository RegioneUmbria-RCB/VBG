using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocArea.Adapters;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ClassificazioneFascicolazione
{
    public class FascicolazioneAttiva : IClassificazioneFascicolazione
    {
        DocAreaVerticalizzazioneParametriAdapter _parametri;
        string _classifica;
        string _numeroFascicolo;
        string _annoFascicolo;
        string _flusso;
        ProtocolloEnum.TipoProvenienza _provenienza;
        ProtocolloEnum.Source _tipoInserimento;

        public FascicolazioneAttiva(string classifica, string flusso, DocAreaVerticalizzazioneParametriAdapter parametri, ProtocolloEnum.TipoProvenienza provenienza, ProtocolloEnum.Source tipoInserimento)
        {
            this._classifica = "";
            this._numeroFascicolo = "";
            this._annoFascicolo = "";
            this._flusso = flusso;
            this._provenienza = provenienza;
            this._tipoInserimento = tipoInserimento;
            this._parametri = parametri;

            if (!String.IsNullOrEmpty(classifica))
            {
                var classificaFascicolo = classifica.Split(parametri.SeparatoreFascicolo);
                if (classificaFascicolo.Count() == 1)
                {
                    this._classifica = classificaFascicolo[0];
                }

                if (classificaFascicolo.Count() == 2)
                {
                    this._classifica = classificaFascicolo[0];
                    this._numeroFascicolo = classificaFascicolo[1];
                }

                if (classificaFascicolo.Count() > 2)
                {
                    this._classifica = classificaFascicolo[0];
                    this._numeroFascicolo = classificaFascicolo[1];
                    this._annoFascicolo = classificaFascicolo[2];
                }
            }
        }

        public Classifica GetClassificazione()
        {
            if (String.IsNullOrEmpty(_classifica))
            {
                return null;
            }

            return new Classifica
            {
                CodiceTitolario = _classifica,
                CodiceAmministrazione = _parametri.CodiceAmministrazione,
                CodiceAOO = _parametri.CodiceAoo
            };
        }

        public Fascicolo GetFascicolo()
        {
            if (String.IsNullOrEmpty(this._annoFascicolo))
            {
                this._annoFascicolo = _parametri.AnnoFascicoloDefault;
            }

            if (String.IsNullOrEmpty(this._numeroFascicolo))
            {
                if (_flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
                {
                    if (this._provenienza == ProtocolloEnum.TipoProvenienza.BACKOFFICE && this._tipoInserimento != ProtocolloEnum.Source.ON_LINE)
                    {
                        throw new Exception($"NON E' STATO VALORIZZATO IL DATO RELATIVO AL PROGRESSIVO DEL FASCICOLO, INSERIRE IL PROGRESSIVO E L'ANNO NEL CAMPO CLASSIFICA, SEPARANDO I DATI DI CLASSIFICA, FASCICOLO E ANNO CON IL SEPARATORE {_parametri.SeparatoreFascicolo.ToString()} NEL SEGUENTE MODO: [CLASSIFICA]{_parametri.SeparatoreFascicolo.ToString()}[PROGRESSIVO FASCICOLO]{_parametri.SeparatoreFascicolo.ToString()}[ANNO FASCICOLO]");
                    }

                    if (String.IsNullOrEmpty(_parametri.NumeroFascicoloDefault))
                    {
                        throw new Exception("IL PARAMETRO NUMERO_FASCICOLO_DEFAULT NON E' STATO VALORIZZATO");
                    }

                    this._numeroFascicolo = _parametri.NumeroFascicoloDefault;
                }
                else
                {
                    return null;
                }

            }

            return new Fascicolo
            {
                anno = this._annoFascicolo,
                numero = this._numeroFascicolo
            };
        }
    }
}
