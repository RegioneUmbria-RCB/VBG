
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella TIPIMOVIMENTO il 05/11/2008 11.33.53
    ///
    ///												ATTENZIONE!!!
    ///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
    /// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
    ///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    [DataTable("TIPIMOVIMENTO")]
    [Serializable]
    [DataContract]
    public partial class TipiMovimento : BaseDataClass
    {

        [DataMember]
        [XmlElement(Order = 1)]
        [KeyField("TIPOMOVIMENTO", Type = DbType.String, Size = 8)]
        public string Tipomovimento
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 2)]
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get;
            set;
        }


        [DataMember]
        [XmlElement(Order = 3)]
        [DataField("MOVIMENTO", Type = DbType.String, CaseSensitive = false, Size = 128)]
        public string Movimento
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 4)]
        [DataField("SISTEMA", Type = DbType.Decimal)]
        public int? Sistema
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 5)]
        [DataField("CODICELETTERA", Type = DbType.Decimal)]
        public int? Codicelettera
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 6)]
        [DataField("FLAG_RICHIESTAINTEGRAZIONE", Type = DbType.Decimal)]
        public int? FlagRichiestaintegrazione
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 7)]
        [DataField("FLAG_INTERRUZIONE", Type = DbType.Decimal)]
        public int? FlagInterruzione
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 8)]
        [DataField("TUTTELEAMMINISTRAZIONI", Type = DbType.Decimal)]
        public int? Tutteleamministrazioni
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 9)]
        [DataField("TIPOLOGIAESITO", Type = DbType.Decimal)]
        public int? Tipologiaesito
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 10)]
        [DataField("FLAG_PROROGA", Type = DbType.Decimal)]
        public int? FlagProroga
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 11)]
        [DataField("GGPROROGA", Type = DbType.Decimal)]
        public int? Ggproroga
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 12)]
        [DataField("FLAG_ENMAIL", Type = DbType.Decimal)]
        public int? FlagEnmail
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 13)]
        [DataField("FLAG_ENMOSTRA", Type = DbType.Decimal)]
        public int? FlagEnmostra
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 14)]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 15)]
        [DataField("FLAG_OPERANTE", Type = DbType.Decimal)]
        public int? FlagOperante
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 16)]
        [DataField("FLAG_NONOPERANTE", Type = DbType.Decimal)]
        public int? FlagNonoperante
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 17)]
        [DataField("FLAG_CDS", Type = DbType.Decimal)]
        public int? FlagCds
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 18)]
        [DataField("FLAG_REGISTRO", Type = DbType.Decimal)]
        public int? FlagRegistro
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 19)]
        [DataField("FKIDREGISTRO", Type = DbType.Decimal)]
        public int? Fkidregistro
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 20)]
        [DataField("FLAG_NOAMMINTERNA", Type = DbType.Decimal)]
        public int? FlagNoamminterna
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 21)]
        [DataField("FLAG_USADALPROTOCOLLO", Type = DbType.Decimal)]
        public int? FlagUsadalprotocollo
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 22)]
        [DataField("FLAG_PUBBLICAMOVIMENTO", Type = DbType.Decimal)]
        public int? FlagPubblicamovimento
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 23)]
        [DataField("FLAG_PUBBLICAPARERE", Type = DbType.Decimal)]
        public int? FlagPubblicaparere
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 24)]
        [DataField("FK_FO_SOGGETTIESTERNI", Type = DbType.Decimal)]
        public int? FkFoSoggettiesterni
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 25)]
        [DataField("FLAG_STC", Type = DbType.Decimal)]
        public int? FLAG_STC
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 26)]
        [DataField("FLAG_CAMCOM", Type = DbType.Decimal)]
        public int? FLAG_CAMCOM
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 27)]
        [DataField("FLAG_PUBBLICAALLEGATI", Type = DbType.Decimal)]
        public int? FlagPubblicaAllegati
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 28)]
        [DataField("FK_MAILTIPO_OGGPROT", Type = DbType.Decimal)]
        public int? FkMailTipoOggProt
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 29)]
        [DataField("FLAG_SOST_DOCUMENTALE", Type = DbType.Decimal)]
        public int? FlagSostDocumentale
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 30)]
        [DataField("FLAG_INTEGR_CHECK_FIRMA", Type = DbType.Decimal)]
        public int? FlagVerificaFirmaNelleIntegrazioni
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 31)]
        [DataField("FLAG_PUBBL_SCHEDE", Type = DbType.Decimal)]
        public int? FlagPubblicaSchede
        {
            get;
            set;
        }

        [DataMember]
        [XmlElement(Order = 32)]
        [DataField("FLAG_FO_RICHIAMA_SIT", Type = DbType.Decimal)]
        public int? FlagFoRichiamaSIT
        {
            get;
            set;
        }
    }
}
