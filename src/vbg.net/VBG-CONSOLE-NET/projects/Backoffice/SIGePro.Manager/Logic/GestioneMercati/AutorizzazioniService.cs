using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Configuration;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
    public class AutorizzazioniService
    {
        AuthenticationInfo _ai = null;

        public AutorizzazioniService(AuthenticationInfo ai)
		{
            this._ai = ai;
		}

        public List<ListaAutorizzazioniItem> GetAutorizzazioni(string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceManifestazione, int? codiceUso)
        {
            if (registri == null)
                throw new ArgumentException("Non è possibile utilizzare il metodo Init.SIGePro.Manager.Logic.GestioneMercati.Autorizzazioni.GetAutorizzazioni senza impostare il parametro registri;");

            if ( string.IsNullOrEmpty( espressioneFormattazioneDati ) )
                throw new ArgumentException("Non è possibile utilizzare il metodo Init.SIGePro.Manager.Logic.GestioneMercati.Autorizzazioni.GetAutorizzazioni senza impostare il parametro espressioneFormattazioneDati;");

            using (var db = _ai.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();

                    var registriUniti = String.Join(",", registri);

                    var filtro = new Autorizzazioni
                    {
                        IDCOMUNE = _ai.IdComune,
                        FKCodiceAnagrafe = codiceAnagrafe,
                        FlagAttiva = 1
                    };
                    filtro.OthersWhereClause.Add("AUTORIZZAZIONI.FKIDREGISTRO IN (" + registriUniti + ")");

                    var autMgr = new AutorizzazioniMgr(db);

                    return autMgr.GetList(filtro).Select(x =>
                    {
                        // ...
                        var data = x.AUTORIZDATA.Value.ToString("dd/MM/yyyy");
                        var ente = GetEnteAutorizzazione(db, Convert.ToInt32(x.ID)).Descrizione;
                        var titolare = new AnagrafeMgr( db ).GetById( x.IDCOMUNE, x.FKCodiceAnagrafe.Value ).GetNomeCompleto();
                        var registro = new TipologiaRegistriMgr( db ).GetById( x.FKIDREGISTRO, x.IDCOMUNE ).TR_DESCRIZIONE;
                        int numPresenze = GetPresenze(x, codiceManifestazione, codiceUso);

                        return new ListaAutorizzazioniItem
                        {
                            Codice = Convert.ToInt32(x.ID),
                            Descrizione = String.Format(espressioneFormattazioneDati, x.AUTORIZNUMERO, data, ente, titolare, registro, numPresenze)
                        };
                    }).ToList<ListaAutorizzazioniItem>();
                }
                finally
                {
                    db.Connection.Close();
                }
            }
            
        }

        public List<ListaAutorizzazioniItem> GetAutorizzazioniConCodiceIntervento(string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceIntervento)
        {

            using (var db = _ai.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();
                    var albero = new AlberoProcMgr(db).GetById(codiceIntervento, _ai.IdComune);
					var scCodici = albero.GetListaScCodice().ToString();

                    int? codiceManifestazione = null;
                    int? codiceUso = null;
                    var cmdText = "SELECT FKCODICEMERCATO, FKIDMERCATIUSO FROM ALBEROPROC WHERE IDCOMUNE = '" + albero.Idcomune + "' AND SOFTWARE = '" + albero.SOFTWARE + "' AND SC_CODICE IN (" + scCodici + ") AND FKCODICEMERCATO IS NOT NULL ORDER BY SC_CODICE DESC";
                    using (var reader = db.CreateCommand(cmdText).ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            codiceManifestazione = Convert.ToInt32(reader["FKCODICEMERCATO"].ToString());
                            if (reader["FKIDMERCATIUSO"] != DBNull.Value)
                                codiceUso = Convert.ToInt32(reader["FKIDMERCATIUSO"].ToString());
                        }
                    }

					if (!codiceManifestazione.HasValue)
						throw new ConfigurationErrorsException(String.Format("L'intervento selezionato ({0}) non è associato ad una manifestazione", albero.Sc_id));

                    return GetAutorizzazioni(registri, codiceAnagrafe, espressioneFormattazioneDati, codiceManifestazione.Value, codiceUso);
                }
                finally
                {
                    db.Connection.Close();
                }
            }
        }

        public DettagliAutorizzazione GetAutorizzazione( int idAutorizzazione, int codiceManifestazione, int? codiceUso )
        {
            using (var db = _ai.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();

                    var aut = new AutorizzazioniMgr(db).GetById(idAutorizzazione.ToString(), _ai.IdComune);
                    if (aut == null)
                        throw new NullReferenceException(String.Format("Autorizzazione con IDCOMUNE: {0} e ID: {1} inesistente",_ai.IdComune,idAutorizzazione));

                    return new DettagliAutorizzazione
                    {
                        Codice = idAutorizzazione,
                        Data = aut.AUTORIZDATA.Value,
                        Ente = GetEnteAutorizzazione(db, idAutorizzazione),
                        Numero = aut.AUTORIZNUMERO,
                        NumeroPresenze = GetPresenze(aut, codiceManifestazione, codiceUso)
                    };
                }
                finally
                {
                    db.Connection.Close();
                }
            }
        }

        public DettagliAutorizzazione GetAutorizzazioneConCodiceIntervento(int idAutorizzazione, int codiceIntervento )
        {
            var db = _ai.CreateDatabase();
            db.Connection.Open();

            var albero = new AlberoProcMgr(db).GetById(codiceIntervento, _ai.IdComune);
            var scCodici= "";
            for (int i = 0; i <= albero.SC_CODICE.Length; i=i+2)
                scCodici += "'" + albero.SC_CODICE.Substring(0, i) + "',";
            scCodici = scCodici.Substring(0, scCodici.Length - 1);


            int? codiceManifestazione = null;
            int? codiceUso = null;
            var cmdText = "SELECT FKCODICEMERCATO, FKIDMERCATIUSO FROM ALBEROPROC WHERE IDCOMUNE = '" + albero.Idcomune + "' AND SOFTWARE = '" + albero.SOFTWARE + "' AND SC_CODICE IN (" + scCodici + ") AND FKCODICEMERCATO IS NOT NULL ORDER BY SC_CODICE DESC";
            using (var reader = db.CreateCommand(cmdText).ExecuteReader())
            {
                if (reader.Read())
                {
                    codiceManifestazione = Convert.ToInt32( reader["FKCODICEMERCATO"].ToString() );
                    if (reader["FKIDMERCATIUSO"] != DBNull.Value)
                        codiceUso = Convert.ToInt32( reader["FKIDMERCATIUSO"].ToString() );
                }
            }

            db.Connection.Close();
            db.Dispose();

            return GetAutorizzazione( idAutorizzazione, codiceManifestazione.Value, codiceUso);
        }

        private EnteAutorizzazione GetEnteAutorizzazione( DataBase db, int idAutorizzazione)
        {
            EnteAutorizzazione retVal = null;
            
            var cmdText = "SELECT VW_ENTILOCALI.CODICECOMUNE, VW_ENTILOCALI.COMUNE FROM AUTORIZZAZIONI, VW_ENTILOCALI WHERE VW_ENTILOCALI.CODICECOMUNE = AUTORIZZAZIONI.AUTORIZCOMUNE AND AUTORIZZAZIONI.IDCOMUNE = '" + _ai.IdComune + "' AND AUTORIZZAZIONI.ID = " + idAutorizzazione;
            using( var reader = db.CreateCommand( cmdText ).ExecuteReader() )
            {
                if( reader.Read() )
                {
                    retVal = new EnteAutorizzazione
                    {
                        Codice = reader["CODICECOMUNE"].ToString(),
                        Descrizione = reader["COMUNE"].ToString()
                    };
                }
            }

            return retVal;
        }

        public List<EnteAutorizzazione> GetEnti()
        {
            var db = _ai.CreateDatabase();
            db.Connection.Open();

            List<EnteAutorizzazione> retVal = null;

            var cmdText = "SELECT CODICECOMUNE, COMUNE FROM VW_ENTILOCALI ORDER BY COMUNE ASC";
            using (var reader = db.CreateCommand(cmdText).ExecuteReader())
            {
                if (reader.Read())
                    retVal = new List<EnteAutorizzazione>();

                while (reader.Read())
                    retVal.Add(new EnteAutorizzazione { Codice = reader["CODICECOMUNE"].ToString(), Descrizione = reader["COMUNE"].ToString() });
            }

            db.Connection.Close();

            return retVal;
        }

        private int GetPresenze(Autorizzazioni x, int codiceManifestazione, int? codiceUso)
        {
            return new MercatiService(_ai.Token).presenzeManifestazione(x.AUTORIZNUMERO, x.AUTORIZDATA.Value.ToString("dd/MM/yyyy"), x.AUTORIZCOMUNE, Convert.ToInt32(x.FKIDREGISTRO), "", codiceManifestazione, codiceUso, false);
        }

    }
}
