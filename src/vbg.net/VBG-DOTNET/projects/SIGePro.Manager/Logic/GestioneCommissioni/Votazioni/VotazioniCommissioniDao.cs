using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione;
using Init.SIGePro.Manager.Logic.GestioneSequenceTable;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Votazioni
{
    public class VotazioniCommissioniDao : IVotazioniCommissioniDao
    {
        private static class Constants
        {
            public const string SequenceName = "COMMEDILIZIE_VOTAZIONI.ID";
        }

        private readonly DataBase _db;
        private readonly string _idComune;

        public VotazioniCommissioniDao(DataBase db, string idComune)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            this._db = db ?? throw new ArgumentNullException(nameof(db));
            this._idComune = idComune;
        }

        public void AggiornaAnagraficaAppello(int idAppello, int codiceAnagrafe)
        {
            var sql = $"UPDATE commedilizie_appello SET codiceanagrafe={this._db.QueryParameter("codiceAnagrafe")} " +
                      $"WHERE idcomune={this._db.QueryParameter("idComune")} AND id={this._db.QueryParameter("idAppello")}";

            this._db.ExecuteNonQuery(sql, mp => mp.Add("codiceAnagrafe", codiceAnagrafe)
                                                  .Add("idComune", this._idComune)
                                                  .Add("idAppello", idAppello));
        }

        public bool CaricaDellUtentePermetteVoto(int idAppello)
        {
            var sql = $@"SELECT 
    dirittovoto 
FROM 
    commedilizie_appello

    INNER JOIN commedilizie_carica ON
        commedilizie_carica.idcomune = commedilizie_appello.idcomune AND
        commedilizie_carica.codice = commedilizie_appello.codicecarica
WHERE 
    commedilizie_appello.idcomune = {this._db.QueryParameter("idcomune")} AND
    commedilizie_appello.id = {this._db.QueryParameter("idAppello")}";

            return this._db.ExecuteScalar(sql, 0, mp =>
            {
                mp.Add("idcomune", this._idComune);
                mp.Add("idAppello", idAppello);
            }) == 1;
        }

        public int GetIdRigaByCodiceIstanza(int idCommissione, int codiceIstanza)
        {
            var sql = $@"SELECT
    commissioniedilizie_r.id
FROM
    commissioniedilizie_r

    INNER JOIN movimenti ON
        movimenti.idcomune = commissioniedilizie_r.idcomune AND
        movimenti.codicemovimento = commissioniedilizie_r.codicemovimento AND
        movimenti.codiceistanza = {this._db.QueryParameter("codiceIstanza")}

WHERE 
    commissioniedilizie_r.idcomune = {this._db.QueryParameter("idComune")} AND
    commissioniedilizie_r.codicecommissione = {this._db.QueryParameter("idCommissione")}";


            return this._db.ExecuteScalar(sql, -1,
                mp => mp.Add("codiceIstanza", codiceIstanza)
                        .Add("idComune", this._idComune)
                        .Add("idCommissione", idCommissione));
        }

        public IEnumerable<CommissioniVotiBaseDto> GetListaPareri()
        {
            var sql = "SELECT id, descrizione FROM commedilizie_voti_base order by descrizione";

            return this._db.ExecuteReader(sql, mp => { }, dr => new CommissioniVotiBaseDto
            {
                Id = dr.GetInt("id").Value,
                Descrizione = dr.GetString("descrizione")
            });
        }

        public VotazionePraticaCommissioneDto GetVotoUtente(int idCommissione, int codiceRiga, int idAppello)
        {
            return new VotazionePraticaCommissioneDto
            {
                RichiedeFirmaDigitale = this.RichiedeFileFirmato(idCommissione),
                Voto = this.GetVotoUtenteInternal(idCommissione, codiceRiga, idAppello)
            };
        }

        private int NextId()
        {
            return new SequenceTableService(this._db, this._idComune).NextId(Constants.SequenceName);
        }

        public void InserisciVoto(int idCommissione, int idRiga, int codiceParere, int idAppello, string note, int? codiceOggetto, IEstremiProtocollazioneCommissione estremiProtocollazione)
        {
            var dataProtocollo = String.IsNullOrEmpty(estremiProtocollazione?.DataProtocollo) ? (DateTime?)null : DateTime.ParseExact(estremiProtocollazione.DataProtocollo, "dd/MM/yyyy", null);
            var idProtocollo = estremiProtocollazione?.IdProtocollo;
            var numeroProtocollo = estremiProtocollazione?.NumeroProtocollo;
            var nextId = this.NextId();

            FormattableString sql = $@"INSERT INTO commedilizie_votazioni (
                id,
                idcomune, 
                codicecommissione, 
                codiceresponsabile, 
                codiceriga,
                presente, 
                voto, 
                idappello,
                parere, 
                codiceoggetto,
                voto_fo,
                numeroprotocollo, 
                dataprotocollo, 
                fkidprotocollo
            ) VALUES (
                {nextId},
                {this._idComune}, 
                {idCommissione}, 
                null, 
                {idRiga},
                1,
                {codiceParere}, 
                {idAppello}, 
                {note}, 
                {codiceOggetto},
                1,
                {numeroProtocollo},
                {dataProtocollo},
                {idProtocollo}
            )";

            this._db.ExecuteNonQuery(sql);
        }

        private VotoPraticaCommissioneDto? GetVotoUtenteInternal(int idCommissione, int codiceRiga, int idAppello)
        {
            FormattableString sql = $@"SELECT
    commedilizie_votazioni.id as id,
    commedilizie_voti_base.id AS CodiceParere,
    commedilizie_voti_base.descrizione AS DescrizioneParere,
    commedilizie_votazioni.parere AS Note,
    commedilizie_votazioni.numeroprotocollo AS numeroprotocollo,
    commedilizie_votazioni.dataprotocollo AS dataprotocollo,
    oggetti.codiceoggetto as CodiceOggetto,
    oggetti.nomefile AS nomefile 
FROM 
    commedilizie_votazioni

    left OUTER JOIN commedilizie_voti_base ON
        commedilizie_voti_base.id = commedilizie_votazioni.voto

    left OUTER JOIN oggetti ON
        oggetti.idcomune = commedilizie_votazioni.idcomune AND
        oggetti.codiceoggetto = commedilizie_votazioni.codiceoggetto
WHERE 
    commedilizie_votazioni.idcomune = {this._idComune} AND                               
    commedilizie_votazioni.codicecommissione = {idCommissione} and  
    commedilizie_votazioni.codiceriga = {codiceRiga} and 
    commedilizie_votazioni.idappello = {idAppello} and
    commedilizie_votazioni.voto IS NOT NULL";

            return this._db.ExecuteReader(sql,
                dr => new VotoPraticaCommissioneDto
                {
                    Id = dr.GetInt("id")!.Value,
                    CodiceOggetto = dr.GetInt("CodiceOggetto"),
                    CodiceParere = dr.GetInt("CodiceParere"),
                    DescrizioneParere = dr.GetString("DescrizioneParere"),
                    NomeFile = dr.GetString("nomefile"),
                    Note = dr.GetString("Note"),
                    NumeroProtocollo = dr.GetString("numeroprotocollo"),
                    DataProtocollo = dr.GetDateTime("dataprotocollo")?.ToString("dd/mm/yyyy")
                }).FirstOrDefault();
        }

        private bool RichiedeFileFirmato(int idCommissione)
        {
            var sql = $@"SELECT 
    flag_upload_doc_parere 
FROM 
    commissioniedilizie_t

    INNER JOIN commedilizie_tipologie ON
        commedilizie_tipologie.idcomune = commissioniedilizie_t.idcomune AND
        commedilizie_tipologie.codcommtipologia = commissioniedilizie_t.codcommtipologia
WHERE
    commissioniedilizie_t.idcomune={this._db.QueryParameter("idcomune")} AND
    commissioniedilizie_t.codicecommissione = {this._db.QueryParameter("idCommissione")}";

            return this._db.ExecuteScalar(sql, 0, mp =>
            {
                mp.Add("idcomune", this._idComune);
                mp.Add("idCommissione", idCommissione);
            }) == 1;
        }
    }
}
