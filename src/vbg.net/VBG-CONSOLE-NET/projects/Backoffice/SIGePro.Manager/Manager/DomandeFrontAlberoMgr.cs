
using Init.SIGePro.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class DomandeFrontAlberoMgr
    {
        #region metodi utilizzati per esporre i dati al ws usato dal frontoffice
        public class InfoDomandaAreaTematica
        {
            private int m_codice;
            private string m_descrizione;

            public string Descrizione
            {
                get { return this.m_descrizione; }
                set { this.m_descrizione = value; }
            }

            public int Codice
            {
                get { return this.m_codice; }
                set { this.m_codice = value; }
            }

            public InfoDomandaAreaTematica()
            {

            }

            public InfoDomandaAreaTematica(int codice, string descrizione)
            {
                this.m_codice = codice;
                this.m_descrizione = descrizione;
            }
        }
        public class InfoAreaTematica
        {
            private int m_codice;
            private string m_descrizione;
            private int m_nFigli;

            private int m_nDomande;

            public int NDomande
            {
                get { return this.m_nDomande; }
                set { this.m_nDomande = value; }
            }


            public int NFigli
            {
                get { return this.m_nFigli; }
                set { this.m_nFigli = value; }
            }


            public string Descrizione
            {
                get { return this.m_descrizione; }
                set { this.m_descrizione = value; }
            }

            public int Codice
            {
                get { return this.m_codice; }
                set { this.m_codice = value; }
            }

            public InfoAreaTematica(int codice, string descrizione, int nFigli, int nDomande)
            {
                this.Codice = codice;
                this.Descrizione = descrizione;
                this.NFigli = nFigli;
                this.NDomande = nDomande;
            }

            public InfoAreaTematica()
            {
            }

        }
        public class InfoEndo
        {
            private int m_codice;
            private string m_descrizione;

            public string Descrizione
            {
                get { return this.m_descrizione; }
                set { this.m_descrizione = value; }
            }

            public int Codice
            {
                get { return this.m_codice; }
                set { this.m_codice = value; }
            }

            public InfoEndo()
            {

            }

            public InfoEndo(int codice, string descrizione)
            {
                this.m_codice = codice;
                this.m_descrizione = descrizione;
            }
        }


        public List<InfoAreaTematica> GetInfoAreeTematiche(string idComune, string software, int idPadre)
        {
            string sql = @"SELECT 
							  domandefrontalbero.id,
							  domandefrontalbero.descrizione,
							  (
								SELECT 
								  count(*) 
								FROM 
								  domandefrontalbero A 
								WHERE 
								  A.idComune=domandefrontalbero.idcomune AND 
								  A.software=domandefrontalbero.software AND 
								  A.idpadre = domandefrontalbero.id AND
								  A.disattiva <> 1
							  ) cntAreeFiglio,
							  (
								select 
									count(*)
								from
									domandefront DF
								where 
								    DF.idcomune = domandefrontalbero.idcomune AND 
									DF.fk_dfa_id = domandefrontalbero.id
							   ) cntDomande
							FROM 
							  domandefrontalbero
							WHERE 
							  idComune = {0} AND
							  software = {1} AND
							  idPadre = {2} and
							  disattiva <> 1";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("software"),
                                        this.db.Specifics.QueryParameterName("idPadre"));



            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("software", software));
                    cmd.Parameters.Add(this.db.CreateParameter("idPadre", idPadre));

                    List<InfoAreaTematica> rVal = new List<InfoAreaTematica>();

                    using (IDataReader rd = cmd.ExecuteReader())
                    {
                        while (rd.Read())
                        {
                            int codice = Convert.ToInt32(rd["id"]);
                            string desc = rd["descrizione"].ToString();
                            int nFigli = Convert.ToInt32(rd["cntAreeFiglio"]);
                            int nDomande = Convert.ToInt32(rd["cntDomande"]);

                            rVal.Add(new InfoAreaTematica(codice, desc, nFigli, nDomande));
                        }
                    }

                    return rVal;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }




        }

        public List<InfoDomandaAreaTematica> GetDomandeAreaTematica(string idComune, int idArea)
        {
            string sql = @"SELECT 
						codicedomanda,
						domanda
					FROM 
						domandefront
					WHERE
						idcomune = {0} AND
						fk_dfa_id = {1}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IdComune"), this.db.Specifics.QueryParameterName("idArea"));


            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.CommandText = sql;
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idArea", idArea));

                    using (IDataReader rd = cmd.ExecuteReader())
                    {
                        List<InfoDomandaAreaTematica> rVal = new List<InfoDomandaAreaTematica>();

                        while (rd.Read())
                        {
                            int codice = Convert.ToInt32(rd["CodiceDomanda"]);
                            string desc = rd["domanda"].ToString();

                            rVal.Add(new InfoDomandaAreaTematica(codice, desc));
                        }

                        return rVal;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        public List<InfoEndo> GetInfoEndoDomanda(string idComune, int idDomanda)
        {
            string sql = @"SELECT 
								inventarioprocedimenti.codiceinventario,
								inventarioprocedimenti.procedimento
							FROM 
								domandefront_endo,
								inventarioprocedimenti
							WHERE
								inventarioprocedimenti.idComune =  domandefront_endo.idComune AND
								inventarioprocedimenti.codiceinventario = domandefront_endo.codiceinventario and
								domandefront_endo.idcomune = {0} AND
								domandefront_endo.codicedomanda = {1}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("IdComune"),
                                        this.db.Specifics.QueryParameterName("idDomanda"));

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idDomanda", idDomanda));

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        List<InfoEndo> rVal = new List<InfoEndo>();

                        while (dr.Read())
                        {
                            int cod = Convert.ToInt32(dr["codiceinventario"]);
                            string desc = dr["procedimento"].ToString();

                            rVal.Add(new InfoEndo(cod, desc));
                        }

                        return rVal;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }


        }

        #endregion


        public List<DomandeFrontAlbero> GetDomandeRicorsivo(string idComune, string software)
        {
            DomandeFrontAlbero filtro = new DomandeFrontAlbero();
            filtro.Idcomune = idComune;
            filtro.Software = software;
            filtro.Idpadre = -1;
            filtro.OrderBy = "ordine asc";
            filtro.UseForeign = useForeignEnum.Recoursive;

            return this.db.GetClassList(filtro, false);
        }

        public List<DomandeFrontAlbero> GetSottoaree(string idComune, int idPadre)
        {
            DomandeFrontAlbero filtro = new DomandeFrontAlbero();
            filtro.Idcomune = idComune;
            filtro.Idpadre = idPadre;
            filtro.OrderBy = "ordine asc";

            return this.db.GetClassList(filtro, false);
        }

        private void EffettuaCancellazioneACascata(DomandeFrontAlbero cls)
        {
            DomandeFrontMgr mgr = new DomandeFrontMgr(this.db);

            List<DomandeFront> domande = mgr.GetByIdArea(cls.Idcomune, cls.Id.GetValueOrDefault(int.MinValue));

            foreach (DomandeFront domanda in domande)
                mgr.Delete(domanda);
        }
    }
}
